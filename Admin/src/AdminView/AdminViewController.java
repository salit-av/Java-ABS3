package AdminView;

import AdminView.Refresher.CurrentYazRefresher;
import AdminView.Refresher.ListCustomersRefresher;
import AdminView.Refresher.ListLoansRefresher;
import Components.Customers.SingleCustomerController;
import Components.Loans.SingleLoanView.SingleLoanController;
import Components.Main.MainAppController;
import DTO.Customers.DTOCustomer;
import DTO.Loan.DTOLoan;
import http.HttpClientUtil;
import jakarta.servlet.http.HttpServlet;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static main.ResourcesPaths.*;


public class AdminViewController extends HttpServlet {
    @FXML
    Button increaseYazButton;
    @FXML Button readonlyButton;
    @FXML ComboBox<String> readonlyCB;
    @FXML
    TreeView<String> loansTV;
    @FXML
    TreeView<String> customersTV;
    @FXML
    Label currentYazLabel;
    @FXML
    ToggleButton autoUpdateButton;

    private SimpleStringProperty currYazPro;
    private SimpleBooleanProperty autoUpdatePro;
    private String adminName;
    private boolean isAdminLogin;
    private MainAppController mainAppController;
    private Timer timer1;
    private Timer timer2;
    private Timer timer3;
    private TimerTask listLoansRefresher;
    private TimerTask currentYazRefresher;
    private TimerTask listCustomersRefresher;
    private SimpleBooleanProperty isReadonly;
    private List<DTOLoan> loans;
    private List<DTOCustomer> customers;
    public AdminViewController() {
        this.adminName = USERNAME;
        currYazPro = new SimpleStringProperty();
        autoUpdatePro = new SimpleBooleanProperty();
        isAdminLogin = false;
        isReadonly = new SimpleBooleanProperty(false);
        loans = new ArrayList<>();
        customers = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        currentYazLabel.textProperty().bind(currYazPro);
        autoUpdateButton.selectedProperty().set(true);
        autoUpdatePro.bind(autoUpdateButton.selectedProperty());
        readonlyCB.getItems().add("1");
        readonlyCB.setValue("1");
        increaseYazButton.disableProperty().bind(isReadonly);
        loadAdmin();
    }

    public void loadAdmin() {
        loadHeader();
        loadLoans();
        loadCustomers();
    }

    public void loadHeader() {
        setCurrentYaz();
    }


    public void setCurrentYaz() {
        if (!adminName.equals(USERNAME)) {
            currentYazRefresher = new CurrentYazRefresher(adminName, this::updateCurrentYaz, autoUpdatePro);
            timer3 = new Timer();
            timer3.schedule(currentYazRefresher, REFRESH_RATE, REFRESH_RATE);
        }
    }

    public void updateCurrentYaz(int currYaz) {
        Platform.runLater(() -> {
            currYazPro.set("Current Yaz: " + currYaz);
        });
    }

    public void loadLoans() {
        if (!adminName.equals(USERNAME)) {
            listLoansRefresher = new ListLoansRefresher(adminName, this::updateListLoans, autoUpdatePro);
            timer1 = new Timer();
            timer1.schedule(listLoansRefresher, REFRESH_RATE, REFRESH_RATE);
        } else {
            loansTV.setRoot(new TreeItem<>("There is not list of Loans"));
        }
    }

    public void updateListLoans(List<DTOLoan> allLoans) {
        Platform.runLater(() -> {
            if(allLoans.size() != loans.size() || loanUpdate(allLoans)) {
                loans = allLoans;
                TreeItem<String> treeLoans = new TreeItem<>("There is not list of Loans");
                if (!allLoans.isEmpty()) {
                    treeLoans.setValue("List of Loans");
                    for (DTOLoan loan : allLoans) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            URL url = getClass().getResource(SINGLE_LOAN_ADMIN_VIEW_FXML_RESOURCE);
                            fxmlLoader.setLocation(url);
                            VBox singleLoan = fxmlLoader.load(url.openStream());

                            SingleLoanController singleLoanController = fxmlLoader.getController();
                            singleLoanController.setInfoOfLoan(loan);

                            treeLoans.getChildren().add(singleLoanController.getRoot());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                loansTV.setRoot(treeLoans);
            }
        });
    }

    public boolean loanUpdate(List<DTOLoan> allLoans) {
        for(DTOLoan loan: allLoans){
            DTOLoan lo = getLoan(loan);
            if(lo != null && lo.isDifferent(loan)){
                return true;
            }
        }
        return false;
    }

    public DTOLoan getLoan(DTOLoan loan) {
        for(DTOLoan lo: loans){
            if(lo.getId().equals(loan.getId())){
                return lo;
            }
        }
        return null;
    }

    public void loadCustomers() {
        if (!adminName.equals(USERNAME)) {
            listCustomersRefresher = new ListCustomersRefresher(adminName, this::updateListCustomers, autoUpdatePro);
            timer2 = new Timer();
            timer2.schedule(listCustomersRefresher, REFRESH_RATE, REFRESH_RATE);
        } else {
            customersTV.setRoot(new TreeItem<>("There is not list of Customers"));
        }
    }

    public void updateListCustomers(List<DTOCustomer> allCustomers) {
        Platform.runLater(() -> {
            if (allCustomers.size() != customers.size() || balanceChange(allCustomers)) {
                customers = allCustomers;
                TreeItem<String> treeCustomers = new TreeItem<>("There is not list of Customers");
                if (!allCustomers.isEmpty()) {
                    treeCustomers.setValue("List of Customers");
                    for (DTOCustomer cus : allCustomers) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            URL url = getClass().getResource(SINGLE_CUSTOMER_ADMIN_VIEW_FXML_RESOURCE);
                            fxmlLoader.setLocation(url);
                            VBox singleCus = fxmlLoader.load(url.openStream());

                            SingleCustomerController singleCustomerController = fxmlLoader.getController();
                            singleCustomerController.setInfoOfCustomer(cus);

                            treeCustomers.getChildren().add(singleCustomerController.getRoot());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                customersTV.setRoot(treeCustomers);
            }
        });
    }

    public boolean balanceChange(List<DTOCustomer> allCustomers) {
        for(DTOCustomer customer: allCustomers){
            DTOCustomer cus = getCus(customer);
            if(customer.getBalance() != cus.getBalance()){
                return true;
            }
        }
        return false;
    }

    public DTOCustomer getCus(DTOCustomer customer) {
        for(DTOCustomer cus: customers){
            if(cus.getName().equals(customer.getName())){
                return cus;
            }
        }
        return null;
    }

    public void setMainController(MainAppController mainAppController) {
        this.mainAppController = mainAppController;
    }

    public void setAdminName(String userName) {
        this.adminName = userName;
    }

    @FXML
    public void increaseYaz() {
        String finalUrl = HttpUrl
                .parse(INCREASE_YAZ)
                .newBuilder()
                .addQueryParameter("username", adminName)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Ended with failure...");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String yaz = response.body().string();
                readonlyCB.getItems().add(yaz);
            }
        });
    }

    @FXML
    public void clickReadonly(){
        isReadonly.set(!isReadonly.get());
        if(isReadonly.get()){
            readonlyButton.textProperty().set("Stop readonly");

            String finalUrl = HttpUrl
                    .parse(START_READONLY_MOOD)
                    .newBuilder()
                    .addQueryParameter("username", adminName)
                    .addQueryParameter("yaz", readonlyCB.getValue())
                    .build()
                    .toString();

            HttpClientUtil.runAsync(finalUrl, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("Ended with failure...");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String yaz = response.body().string();
                }
            });
        }
        else{
            readonlyButton.textProperty().set("Readonly");

            String finalUrl = HttpUrl
                    .parse(STOP_READONLY_MOOD)
                    .newBuilder()
                    .addQueryParameter("username", adminName)
                    .build()
                    .toString();

            HttpClientUtil.runAsync(finalUrl, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("Ended with failure...");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String yaz = response.body().string();
                }
            });
        }
    }
}
