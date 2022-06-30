package Components.CustomerView;

import AllParticipants.Notification;
import Components.Exceptions.ExceptionsController;
import Components.Loans.SingleLoanController;
import Components.Main.MainAppController;
import Components.Notifications.NotificationAreaController;
import Components.Notifications.ScrambleAreaController;
import Components.Notifications.notificationPopUpController;
import Components.Transactions.ChargeController;
import Components.Transactions.Payments.PayMeController;
import Components.Transactions.SingleTransactionController;
import Components.Transactions.WithdrawController;
import DTO.Customers.DTOCustomer;
import DTO.Customers.DTOtransaction;
import DTO.DTOactivate;
import DTO.Loan.DTOLoan;
import Engine.Engine;
import Status.Status;
import jakarta.servlet.http.HttpServlet;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.controlsfx.control.CheckComboBox;
import org.jetbrains.annotations.NotNull;
import utils.ServletUtils;
import utils.http.HttpClientUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static main.ResourcesPath.*;


public class CustomerViewController extends HttpServlet {
    // header
    @FXML Label currentYazLabel;
    @FXML Label currenBalanceLabel;
    @FXML Label filePathLabel;
    @FXML Button loadFileButton;

    private SimpleStringProperty balancePro;
    private SimpleStringProperty currentYazPro;
    private Stage primaryStage;

    @FXML Tab informationTab;
    @FXML TreeView<String> loanerLoansTV1;
    @FXML TreeView<String> lenderLoansTV;
    @FXML TreeView<String> transactionsTV;
    @FXML Button chargeButton;
    @FXML Button withdrawButton;

    @FXML Tab ScrambleTab;
    @FXML Label errorScrambleLabel;
    @FXML Label progressLabel;
    @FXML TextField investmentTF;
    @FXML TextField interestFilterTF;
    @FXML TextField yazFilterTF;
    @FXML TextField loansOpenFilterTF;
    @FXML TextField ownershipFilterTF;
    @FXML CheckComboBox<String> categoryFilterCB;
    @FXML CheckComboBox<String> loansToChoseCB;
    @FXML Button submitScrambleButton;
    @FXML Button OKScrambleButton;

    private List<DTOLoan> loansAfterFilter;
    private int investment;

    @FXML Tab PaymentTab;
    @FXML TreeView<String> loanerLoansTV2;
    @FXML FlowPane paymentFP;
    @FXML FlowPane notificationEP;

    private ObservableList<String> categoriesOL;
    private ObservableList<String> loansOL;

    private String cusName;
    // private Engine engine;

    private MainAppController mainAppController;

    public CustomerViewController() {
        balancePro = new SimpleStringProperty("Balance: 0");
        currentYazPro = new SimpleStringProperty("Current Yaz: 1");
        categoriesOL = FXCollections.observableArrayList();
        loansOL = FXCollections.observableArrayList();
        loansAfterFilter = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        // header
        currenBalanceLabel.textProperty().bind(balancePro);
        // Scramble tab
        errorScrambleLabel.setVisible(false);
        categoryFilterCB.getItems().addAll(categoriesOL);
        loansToChoseCB.getItems().addAll(loansOL);
        progressLabel.setVisible(false);
        setCusInfo(USERNAME);
    }

/*    public void setEngine(Engine engine) {
        this.engine = engine;
    }*/

    public Engine getEngine() {
        return ServletUtils.getEngine(getServletContext());
    }

    public DTOCustomer getCustomer() {
        return getEngine().printAllCustomers().findCustomer(cusName);
    }

    public void setCusInfo(String cusName) {
        //this.engine = engine;
        //this.customer = engine.printAllCustomers().findCustomer(cusName);
        this.cusName = cusName;
        setInfoInInformationTab();
        setInfoInScrambleTab();
        setInfoInPaymentTab();
    }

    // Information TAB
    public void setInfoInInformationTab() {
        loadLoanerLoans();
        loadLendersLoans();
        loadTransactions();
    }

    public void loadLoanerLoans() {
        TreeItem<String> treeLoans = new TreeItem<>("There is no list of loans in status active or risk as a borrower");
        if (!cusName.equals(USERNAME)) {
            DTOCustomer customer = getCustomer();
            Map<String, DTOLoan> allLoansAsBorrower = customer.getDTOloansAsBorrower();
            if (!allLoansAsBorrower.isEmpty()) {
                treeLoans.setValue("List of Loans in status active or risk as a borrower");

                for (String loanID : allLoansAsBorrower.keySet()) {
                    DTOLoan loan = allLoansAsBorrower.get(loanID);
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        URL url = getClass().getResource(SINGLE_LOAN_CUSTOMERS_VIEW_FXML_RESOURCE);
                        fxmlLoader.setLocation(url);
                        VBox singleLoan = fxmlLoader.load(url.openStream());

                        SingleLoanController singleLoanController = fxmlLoader.getController();
                        singleLoanController.setInfoOfLoan(loan);
                        singleLoanController.setEngine(getEngine());

                        treeLoans.getChildren().add(singleLoanController.getRoot());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        loanerLoansTV1.setRoot(treeLoans);
    }

    public void loadLendersLoans() {
        TreeItem<String> treeLoans = new TreeItem<>("There is no list of loans as a lender");

        if (!cusName.equals(USERNAME)) {
            DTOCustomer customer = getCustomer();
            Map<String, DTOLoan> allLoansAsLender = customer.getDTOloansAsLender();
            if (!allLoansAsLender.isEmpty()) {
                treeLoans.setValue("List of Loans as a lender");

                for (String loanID : allLoansAsLender.keySet()) {
                    DTOLoan loan = allLoansAsLender.get(loanID);
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        URL url = getClass().getResource(SINGLE_LOAN_CUSTOMERS_VIEW_FXML_RESOURCE);
                        fxmlLoader.setLocation(url);
                        VBox singleLoan = fxmlLoader.load(url.openStream());

                        SingleLoanController singleLoanController = fxmlLoader.getController();
                        singleLoanController.setInfoOfLoan(loan);
                        singleLoanController.setEngine(getEngine());

                        treeLoans.getChildren().add(singleLoanController.getRoot());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        lenderLoansTV.setRoot(treeLoans);
    }

    public void loadTransactions() {
        TreeItem<String> treeTransactions = new TreeItem<>("There is no list of transactions");
        if (!cusName.equals(USERNAME)) {
            DTOCustomer customer = getCustomer();
            List<DTOtransaction> transactions = customer.getDTOtransactions();
            if (!transactions.isEmpty()) {
                treeTransactions.setValue("List of transactions");
                int i = 1;
                for (DTOtransaction transaction : transactions) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        URL url = getClass().getResource(SINGLE_TRANSACTION_CUSTOMERS_VIEW_FXML_RESOURCE);
                        fxmlLoader.setLocation(url);
                        VBox singleTran = fxmlLoader.load(url.openStream());

                        SingleTransactionController singleTransactionController = fxmlLoader.getController();
                        singleTransactionController.setInfoOfTransaction(transaction);
                        treeTransactions.getChildren().add(singleTransactionController.getRoot());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        transactionsTV.setRoot(treeTransactions);
    }

    public void chargeToCusBalance() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(CHARGE_VIEW_FXML_RESOURCE);
            fxmlLoader.setLocation(url);
            VBox moneyVB = fxmlLoader.load(url.openStream());
            ChargeController chargeController = fxmlLoader.getController();
            chargeController.setCustomerController(this);
            chargeController.setCustomer(cusName);
            chargeController.setBalancePro(balancePro);

            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);

            Scene popUpScene = new Scene(moneyVB, 300, 200);
            popup.setScene(popUpScene);
            popup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        balancePro.set("Balance: " + getCustomer().getBalance());
    }

    public void withdrawFromCusBalance() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(WITHDRAW_VIEW_FXML_RESOURCE);
            fxmlLoader.setLocation(url);
            VBox moneyVB = fxmlLoader.load(url.openStream());
            WithdrawController withdrawController = fxmlLoader.getController();
            withdrawController.setCustomerController(this);
            withdrawController.setCustomer(cusName);
            withdrawController.setBalancePro(balancePro);

            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);

            Scene popUpScene = new Scene(moneyVB, 300, 200);
            popup.setScene(popUpScene);
            popup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        balancePro.set("Balance: " + getCustomer().getBalance());
    }


    // Scramble TAB
    public void setInfoInScrambleTab() {
        addToCategoryFilterCB();
    }

    public void addToCategoryFilterCB() {
        if (!cusName.equals(USERNAME)) {
            List<String> categories = getEngine().getCategories();
            for (String category : categories) {
                categoriesOL.add(category);
            }
            categoryFilterCB.getItems().addAll(categoriesOL);
        }
    }



    public void selectInfo() {
    }

    public void selectPay() {
    }

    public void selectScramble() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(SCRAMBLE_AREA_VIEW_FXML_RESOURCE);
            fxmlLoader.setLocation(url);
            VBox scrambleVB = fxmlLoader.load(url.openStream());
            ScrambleAreaController scrambleAreaController = fxmlLoader.getController();

            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);

            Scene popUpScene = new Scene(scrambleVB, 350, 150);
            popup.setScene(popUpScene);
            popup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void submitScramble() {
        loansOL.clear();
        errorScrambleLabel.setText("ERROR");
        errorScrambleLabel.setVisible(false);

        loansToChoseCB.getItems().clear();
        DTOactivate activate = getEngine().activate();
        investment = getInvestment();
        List<String> categories = getCategoriesFromFilter();
        int minInterest = getMinInterestFromFilter();
        int minYaz = getMinYazFromFilter();
        int maxLoansOpen = getMaxLoansOpenFromFilter();
        int maxOwnership = getMaxOwnershipFromFilter();

        if (errorScrambleLabel.getText().equals("ERROR")) {
            loansAfterFilter = getEngine().filterAllInvestmentLoans(activate.getDtoAllLoans(), categories, minInterest, minYaz, maxLoansOpen, cusName);
            progressLabel.setText("Now please chose loan below and click OK");
            setLoansInCCB(loansAfterFilter);
        }

    }

    public List<String> getCategoriesFromFilter() {
        List<String> res = categoryFilterCB.getCheckModel().getCheckedItems().stream().collect(Collectors.toList());
        if (res.isEmpty()) {
            return categoryFilterCB.getItems().stream().collect(Collectors.toList());
        } else {
            return res;
        }
    }

    public int getInvestment() {
        int money = 0;
        try {
            money = Integer.parseInt(investmentTF.getText());
            if (money > getCustomer().getBalance() || money < 0) {
                errorScrambleLabel.setText("Sorry you do not have enough money!");
                errorScrambleLabel.setVisible(true);
            }
        } catch (NumberFormatException e) {
            errorScrambleLabel.setText("Please enter correct investment!");
            errorScrambleLabel.setVisible(true);
        }
        return money;
    }

    public int getMinInterestFromFilter() {
        int num = 0;
        if (!interestFilterTF.getText().trim().isEmpty()) {
            try {
                num = Integer.parseInt(interestFilterTF.getText());
                if (num < 0) {
                    errorScrambleLabel.setText("please enter Integers!");
                    errorScrambleLabel.setVisible(true);
                }
            } catch (NumberFormatException e) {
                errorScrambleLabel.setText("Please enter only numbers");
                errorScrambleLabel.setVisible(true);
            }
        }
        return num;
    }

    public int getMinYazFromFilter() {
        int num = 0;
        if (!yazFilterTF.getText().trim().isEmpty()) {
            try {
                num = Integer.parseInt(yazFilterTF.getText());
                if (num < 0) {
                    errorScrambleLabel.setText("please enter Integers!");
                    errorScrambleLabel.setVisible(true);
                }
            } catch (NumberFormatException e) {
                errorScrambleLabel.setText("Please enter only numbers");
                errorScrambleLabel.setVisible(true);
            }
        }
        return num;
    }

    public int getMaxLoansOpenFromFilter() {
        int num = 0;
        if (!loansOpenFilterTF.getText().trim().isEmpty()) {
            try {
                num = Integer.parseInt(loansOpenFilterTF.getText());
                if (num < 0) {
                    errorScrambleLabel.setText("please enter Integers!");
                    errorScrambleLabel.setVisible(true);
                }
            } catch (NumberFormatException e) {
                errorScrambleLabel.setText("Please enter only numbers");
                errorScrambleLabel.setVisible(true);
            }
        }
        return num;
    }

    public int getMaxOwnershipFromFilter() {
        int num = 0;
        if (!ownershipFilterTF.getText().trim().isEmpty()) {
            try {
                num = Integer.parseInt(ownershipFilterTF.getText());
                if (num < 0) {
                    errorScrambleLabel.setText("please enter Integers!");
                    errorScrambleLabel.setVisible(true);
                }
            } catch (NumberFormatException e) {
                errorScrambleLabel.setText("Please enter only numbers");
                errorScrambleLabel.setVisible(true);
            }
        }
        return num;
    }

    public void setLoansInCCB(List<DTOLoan> loansAfterFilter) {
        for (DTOLoan loan : loansAfterFilter) {
            loansOL.add(loan.getId());
        }
        if (!loansOL.isEmpty()) {
            loansToChoseCB.getItems().addAll(loansOL);
        }
    }

    @FXML
    public void distributionOfMoneyForLoans() {
        List<String> loansChosen = loansToChoseCB.getCheckModel().getCheckedItems().stream().collect(Collectors.toList());
        Map<String, DTOLoan> loansToSend = new HashMap<>();
        for (DTOLoan loan : loansAfterFilter) {
            if (loansChosen.contains(loan.getId())) {
                loansToSend.put(loan.getId(), loansAfterFilter.stream().filter(dtoLoan -> dtoLoan.getId().equals(loan.getId())).collect(Collectors.toList()).stream().findFirst().get());
            }
        }

        String mess = "Scramble did not passed successfully";
        if (!loansToSend.isEmpty()) {
            getEngine().distributionOfMoneyForLoans(getCustomer(), investment, loansToSend);
            mess = "Scramble passed successfully";
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(POPUP_NOTIFICATION_FXML_RESOURCE);
            fxmlLoader.setLocation(url);
            HBox notiHB = fxmlLoader.load(url.openStream());
            notificationPopUpController notificationController = fxmlLoader.getController();
            notificationController.setNotificationMessage(mess);
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);

            Scene popUpScene = new Scene(notiHB, 300, 200);
            popup.setScene(popUpScene);
            popup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Payment Tab
    public void setInfoInPaymentTab() {
        setInfoInPaymentFP();
        loadLoanerLoansInPayment();
        loadNotifications();
    }

    public void setInfoInPaymentFP() {
        if (!cusName.equals(USERNAME)) {

            List<DTOLoan> allLoansWithPayment = getCustomer().getDTOloansWithPayments();
            for (DTOLoan loan : allLoansWithPayment) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    URL url = getClass().getResource(PAYME_VIEW_FXML_RESOURCE);
                    fxmlLoader.setLocation(url);
                    VBox singlePayMe = fxmlLoader.load(url.openStream());

                    PayMeController payMeController = fxmlLoader.getController();
                    payMeController.setInfoOfLoan(loan, getCustomer(), getEngine(), paymentFP);
                    paymentFP.getChildren().add(singlePayMe);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadLoanerLoansInPayment() {
        TreeItem<String> treeLoans = new TreeItem<>("There is no list of loans as a borrower");
        if(!cusName.equals(USERNAME)) {
            DTOCustomer customer = getCustomer();
            Map<String, DTOLoan> allLoansAsBorrower = customer.getDTOloansAsBorrower();
            if (!allLoansAsBorrower.isEmpty()) {
                for (String loanID : allLoansAsBorrower.keySet()) {
                    DTOLoan loan = allLoansAsBorrower.get(loanID);
                    if (loan.getStatus().equals(Status.ACTIVE) || loan.getStatus().equals(Status.RISK)) {
                        treeLoans.setValue("List of Loans as a borrower");
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            URL url = getClass().getResource(SINGLE_LOAN_CUSTOMERS_VIEW_FXML_RESOURCE);
                            fxmlLoader.setLocation(url);
                            VBox singleLoan = fxmlLoader.load(url.openStream());

                            SingleLoanController singleLoanController = fxmlLoader.getController();
                            singleLoanController.setInfoOfLoan(loan);
                            singleLoanController.setEngine(getEngine());

                            treeLoans.getChildren().add(singleLoanController.getRoot());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        loanerLoansTV2.setRoot(treeLoans);
    }

    public void loadNotifications() {
        if(!cusName.equals(USERNAME)) {
            List<Notification> cusNotifi = getCustomer().getNotificationList();
            for (Notification notification : cusNotifi) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    URL url = getClass().getResource(NOTIFICATION_AREA_FXML_RESOURCE);
                    fxmlLoader.setLocation(url);
                    VBox singleInfo = fxmlLoader.load(url.openStream());

                    NotificationAreaController notificationAreaController = fxmlLoader.getController();
                    notificationAreaController.setInfoNoti(notification.getTitle(), notification.getContent(), notification.getEnd());

                    notificationEP.getChildren().add(singleInfo);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setMainController(MainAppController mainAppController) {
        this.mainAppController = mainAppController;
    }

    // Header
    @FXML
    public void openFileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        String absolutePath = selectedFile.getAbsolutePath();
        loadXmlAndCheckExceptions(absolutePath);

    }


    public void loadXmlAndCheckExceptions(String absolutePath) {
        String finalUrl = HttpUrl
                .parse(LOAD_FILE)
                .newBuilder()
                .addQueryParameter("username", cusName)
                .addQueryParameter("path_xml", absolutePath)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        URL url = getClass().getResource(EXCEPTIONS_FXML_RESOURCE);
                        fxmlLoader.setLocation(url);
                        HBox exceptionHB = fxmlLoader.load(url.openStream());
                        ExceptionsController exceptionsController = fxmlLoader.getController();
                        exceptionsController.setExceptionMessage(e.getMessage());

                        Stage popup = new Stage();
                        popup.initModality(Modality.APPLICATION_MODAL);

                        Scene popUpScene = new Scene(exceptionHB, 700, 300);
                        popup.setScene(popUpScene);
                        popup.show();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            filePathLabel.setText("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                        loadFileInfo();
                    });
                }
            }
        });

        /*try {
            getEngine().loadFromXML(absolutePath,"");
            return true;
        } catch (loansWithTheSameNameException | paymentRateIncorrectException | referenceToCategoryThatIsntDefinedException ex) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL url = getClass().getResource(EXCEPTIONS_FXML_RESOURCE);
                fxmlLoader.setLocation(url);
                HBox exceptionHB = fxmlLoader.load(url.openStream());
                ExceptionsController exceptionsController = fxmlLoader.getController();
                exceptionsController.setExceptionMessage(ex.getMessage());

                Stage popup = new Stage();
                popup.initModality(Modality.APPLICATION_MODAL);

                Scene popUpScene = new Scene(exceptionHB, 700, 300);
                popup.setScene(popUpScene);
                popup.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }*/
    }

    public void loadFileInfo() {
        filePathLabel.setText("Proper file uploaded successfully");
        setCusInfo(cusName);
    }

}
