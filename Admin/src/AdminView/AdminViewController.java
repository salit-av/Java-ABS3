package AdminView;

import AdminView.Refresher.CurrentYazRefresher;
import AdminView.Refresher.ListCustomersRefresher;
import AdminView.Refresher.ListLoansRefresher;
import Components.Customers.SingleCustomerController;
import Components.Loans.SingleLoanView.SingleLoanController;
import Components.Main.MainAppController;
import DTO.Customers.DTOCustomer;
import DTO.Loan.DTOLoan;
import jakarta.servlet.http.HttpServlet;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static main.ResourcesPaths.*;


public class AdminViewController extends HttpServlet {
    @FXML Button increaseYazButton;
    @FXML TreeView<String> loansTV;
    @FXML TreeView<String> customersTV;
    @FXML Label currentYazLabel;
    @FXML ToggleButton autoUpdateButton;

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

    public AdminViewController() {
        this.adminName = USERNAME;
        currYazPro = new SimpleStringProperty();
        autoUpdatePro = new SimpleBooleanProperty();
        isAdminLogin = false;

    }

@FXML
    public void initialize() {
    currentYazLabel.textProperty().bind(currYazPro);
    autoUpdateButton.selectedProperty().set(true);
    autoUpdatePro.bind(autoUpdateButton.selectedProperty());
    loadAdmin();
}

    public void loadAdmin(){
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
        }
        else{
            loansTV.setRoot(new TreeItem<>("There is not list of Loans"));
        }
    }

    public void updateListLoans(List<DTOLoan> allLoans) {
        Platform.runLater(() -> {
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
        });
    }

    public void loadCustomers() {
        if (!adminName.equals(USERNAME)) {
            listCustomersRefresher = new ListCustomersRefresher(adminName, this::updateListCustomers, autoUpdatePro);
            timer2 = new Timer();
            timer2.schedule(listCustomersRefresher, REFRESH_RATE, REFRESH_RATE);
        }
        else{
            customersTV.setRoot(new TreeItem<>("There is not list of Customers"));
        }
    }

    public void updateListCustomers(List<DTOCustomer> allCustomers) {
        Platform.runLater(() -> {
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
        });
    }

    public void setMainController(MainAppController mainAppController) {
        this.mainAppController = mainAppController;
    }

    public void setAdminName(String userName) {
        this.adminName = userName;
    }
/*
    @FXML
    public void increaseYaz(){
       Yaz currYaz = getEngine().getCurrentYaz();
       engine.promoteYaz();
       bodyController.getMainController().getHeaderComponentController().currentYazTimeProperty().set("Current Yaz: " + currYaz.getCurrentYaz());
       engine.setLoansWithPaymentsInCustomers();
    }*/

}
