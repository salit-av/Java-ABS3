package Components.CustomerView;

import AllParticipants.Notification;
import Components.CustomerView.Refresher.*;
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
import DTO.Customers.DTOtransaction;
import DTO.Loan.DTOLoan;
import Engine.Engine;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
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
import utils.http.HttpClientUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static Status.Status.ACTIVE;
import static Status.Status.RISK;
import static main.ResourcesPath.*;


public class CustomerViewController extends CustomerViewData {
    // header
    @FXML
    Label currentYazLabel;
    @FXML
    Label currenBalanceLabel;
    @FXML
    Label filePathLabel;
    @FXML
    Button loadFileButton;
    @FXML
    ToggleButton autoUpdateButton;

    private SimpleStringProperty balancePro;
    private SimpleStringProperty currentYazPro;
    private SimpleBooleanProperty autoUpdatePro;
    private Timer timer1;
    private Timer timer2;
    private Timer timer3;
    private Timer timer4;
    private TimerTask balanceRefresher;
    private TimerTask listAsBorrowerRefresher;
    private TimerTask listAsLenderRefresher;
    private TimerTask listTransactionsRefresher;

    // information
    @FXML
    Tab informationTab;
    @FXML
    TreeView<String> loanerLoansTV1;
    @FXML
    TreeView<String> lenderLoansTV;
    @FXML
    TreeView<String> transactionsTV;
    @FXML
    Button chargeButton;
    @FXML
    Button withdrawButton;

    // addLoan
    @FXML
    Tab addLoanTab;
    @FXML
    Label errorAddLoanLabel;
    @FXML
    TextField idTF;
    @FXML
    TextField categoryTF;
    @FXML
    TextField capitalTF;
    @FXML
    TextField totalYazTimeTF;
    @FXML
    TextField paysEveryYazTF;
    @FXML
    TextField internistPerPaymentTF;
    @FXML
    Button submitAddLoanButton;


    // Scramble
    @FXML
    Tab ScrambleTab;
    @FXML
    Label errorScrambleLabel;
    @FXML
    Label progressLabel;
    @FXML
    TextField investmentTF;
    @FXML
    TextField interestFilterTF;
    @FXML
    TextField yazFilterTF;
    @FXML
    TextField loansOpenFilterTF;
    @FXML
    TextField ownershipFilterTF;
    @FXML
    CheckComboBox<String> categoryFilterCB;
    @FXML
    CheckComboBox<String> loansToChoseCB;
    @FXML
    Button submitScrambleButton;
    @FXML
    Button OKScrambleButton;

    private List<DTOLoan> loansAfterFilter;
    private int investment;

    // Payment
    @FXML
    Tab PaymentTab;
    @FXML
    TreeView<String> loanerLoansTV2;
    @FXML
    FlowPane paymentFP;
    @FXML
    FlowPane notificationEP;

    private Timer timer5;
    private Timer timer6;
    private Timer timer7;
    private TimerTask listLoansAsBorrowerInPaymentRefresher;
    private TimerTask listNotificationsRefresher;
    private TimerTask listLoansWithPaymentRefresher;
    private ObservableList<String> categoriesOL;
    private ObservableList<String> loansOL;

    private String cusName;
    // private Engine engine;

    private MainAppController mainAppController;
    private Stage primaryStage;

    public CustomerViewController() {
        super(null, null);
        autoUpdatePro = new SimpleBooleanProperty();
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
        autoUpdateButton.selectedProperty().set(true);
        autoUpdatePro.bind(autoUpdateButton.selectedProperty());
        // Scramble tab
        errorScrambleLabel.setVisible(false);
        categoryFilterCB.getItems().addAll(categoriesOL);
        loansToChoseCB.getItems().addAll(loansOL);
        progressLabel.setVisible(false);
        setCusInfo(USERNAME);
    }

    public Engine getEngine() {
        return null; //ServletUtils.getEngine(getServletContext());
    }

    public void setCusInfo(String cusName) {
        //this.engine = engine;
        //this.customer = engine.printAllCustomers().findCustomer(cusName);
        this.cusName = cusName;
        setHeader();
        setInfoInInformationTab();
        setInfoInScrambleTab();
        setInfoInPaymentTab();
    }

    public void setHeader() {
        setBalance();
        setCurrentYaz();
    }

    public void setBalance() {
        if (!cusName.equals(USERNAME)) {
            balanceRefresher = new BalanceRefresher(cusName, this::updateBalance, autoUpdatePro);
            timer1 = new Timer();
            timer1.schedule(balanceRefresher, REFRESH_RATE, REFRESH_RATE);
        }
    }

    public void updateBalance(int currBalance) {
        Platform.runLater(() -> {
            balancePro.set("Balance: " + currBalance);
        });
    }

    public void setCurrentYaz() {
    }

    // Information TAB
    public void setInfoInInformationTab() {
        loadLoanerLoans();
        loadLendersLoans();
        loadTransactions();
    }

    public void loadLoanerLoans() {
        if (!cusName.equals(USERNAME)) {
            listAsBorrowerRefresher = new ListLoansAsBorrowerRefresher(cusName, this::updateListLoansAsBorrower, autoUpdatePro);
            timer2 = new Timer();
            timer2.schedule(listAsBorrowerRefresher, REFRESH_RATE, REFRESH_RATE);
        } else {
            loanerLoansTV1.setRoot(new TreeItem<>("There is no list of loans as a borrower"));
        }
    }

    public void updateListLoansAsBorrower(List<DTOLoan> allLoansAsBorrower) {
        Platform.runLater(() -> {
            if (allLoansAsBorrower.isEmpty()) {
                loanerLoansTV1.setRoot(new TreeItem<>("There is no list of loans as a borrower"));
            } else {
                TreeItem<String> treeLoans = new TreeItem<>("List of loans in as a borrower");
                for (DTOLoan loan : allLoansAsBorrower) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        URL url = getClass().getResource(SINGLE_LOAN_CUSTOMERS_VIEW_FXML_RESOURCE);
                        fxmlLoader.setLocation(url);
                        VBox singleLoan = fxmlLoader.load(url.openStream());
                        SingleLoanController singleLoanController = fxmlLoader.getController();
                        singleLoanController.setInfoOfLoan(loan);
                        treeLoans.getChildren().add(singleLoanController.getRoot());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                loanerLoansTV1.setRoot(treeLoans);
            }
        });
    }

    public void loadLendersLoans() {
        if (!cusName.equals(USERNAME)) {
            listAsLenderRefresher = new ListLoansAsLenderRefresher(cusName, this::updateListLoansAsLender, autoUpdatePro);
            timer3 = new Timer();
            timer3.schedule(listAsLenderRefresher, REFRESH_RATE, REFRESH_RATE);
        } else {
            lenderLoansTV.setRoot(new TreeItem<>("There is no list of loans as a lender"));
        }
    }

    public void updateListLoansAsLender(List<DTOLoan> allLoansAsLender) {
        Platform.runLater(() -> {
            if (allLoansAsLender.isEmpty()) {
                lenderLoansTV.setRoot(new TreeItem<>("There is no list of loans as a lender"));
            } else {
                TreeItem<String> treeLoans = new TreeItem<>("List of Loans as a lender");
                for (DTOLoan loan : allLoansAsLender) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        URL url = getClass().getResource(SINGLE_LOAN_CUSTOMERS_VIEW_FXML_RESOURCE);
                        fxmlLoader.setLocation(url);
                        VBox singleLoan = fxmlLoader.load(url.openStream());

                        SingleLoanController singleLoanController = fxmlLoader.getController();
                        singleLoanController.setInfoOfLoan(loan);
                        //singleLoanController.setEngine(getEngine());

                        treeLoans.getChildren().add(singleLoanController.getRoot());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                lenderLoansTV.setRoot(treeLoans);
            }
        });
    }

    public void loadTransactions() {
        if (!cusName.equals(USERNAME)) {
            listTransactionsRefresher = new ListTransactionsRefresher(cusName, this::updateTransactions, autoUpdatePro);
            timer4 = new Timer();
            timer4.schedule(listTransactionsRefresher, REFRESH_RATE, REFRESH_RATE);
        } else {
            transactionsTV.setRoot(new TreeItem<>("There is no list of transactions"));
        }
    }

    public void updateTransactions(List<DTOtransaction> transactions) {
        Platform.runLater(() -> {
            if (transactions.isEmpty()) {
                transactionsTV.setRoot(new TreeItem<>("There is no list of transactions"));
            } else {
                TreeItem<String> treeTransactions = new TreeItem<>("List of transactions");
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
                transactionsTV.setRoot(treeTransactions);
            }
        });
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
        //balancePro.set("Balance: " + getCustomer().getBalance());
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
        //balancePro.set("Balance: " + getCustomer().getBalance());
    }

    //addLoan
    @FXML
    public void submitAddLoan() {
        try {
            String id = idTF.getText();
            String category = categoryTF.getText();
            int capital = Integer.parseInt(capitalTF.getText());
            int totalYazTime = Integer.parseInt(totalYazTimeTF.getText());
            int paysEveryYaz = Integer.parseInt(paysEveryYazTF.getText());
            int internistPerPayment = Integer.parseInt(internistPerPaymentTF.getText());

            String finalUrl = HttpUrl
                    .parse(ADD_LOAN)
                    .newBuilder()
                    .addQueryParameter("username", cusName)
                    .addQueryParameter("id", id)
                    .addQueryParameter("category", category)
                    .addQueryParameter("capital", String.valueOf(capital))
                    .addQueryParameter("totalYazTime", String.valueOf(totalYazTime))
                    .addQueryParameter("paysEveryYaz", String.valueOf(paysEveryYaz))
                    .addQueryParameter("internistPerPayment", String.valueOf(internistPerPayment))
                    .build()
                    .toString();

            HttpClientUtil.runAsync(finalUrl, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Platform.runLater(() -> {
                        errorAddLoanLabel.setText(e.getMessage());
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Platform.runLater(() ->
                            errorAddLoanLabel.setText("Loan " + id + " successfully added")
                    );
                }
            });
        } catch (NumberFormatException e) {
            errorAddLoanLabel.setText("Please enter Integer!");
        }
    }


    // Scramble TAB
    public void setInfoInScrambleTab() {
        addToCategoryFilterCB();
    }

    public void addToCategoryFilterCB() {
       /* if (!cusName.equals(USERNAME)) {
            List<String> categories = getEngine().getCategories();
            for (String category : categories) {
                categoriesOL.add(category);
            }
            categoryFilterCB.getItems().addAll(categoriesOL);
        }*/
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
        //DTOactivate activate = getEngine().activate();
        investment = getInvestment();
        List<String> categories = getCategoriesFromFilter();
        int minInterest = getMinInterestFromFilter();
        int minYaz = getMinYazFromFilter();
        int maxLoansOpen = getMaxLoansOpenFromFilter();
        int maxOwnership = getMaxOwnershipFromFilter();

        if (errorScrambleLabel.getText().equals("ERROR")) {
            //loansAfterFilter = getEngine().filterAllInvestmentLoans(activate.getDtoAllLoans(), categories, minInterest, minYaz, maxLoansOpen, cusName);
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
        /*int money = 0;
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
        return money;*/
        return 0;
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
            // getEngine().distributionOfMoneyForLoans(getCustomer(), investment, loansToSend);
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
            listLoansWithPaymentRefresher = new ListLoansWithPaymentRefresher(cusName, this::updateListLoansWithPaymentRefresher, autoUpdatePro);
            timer7 = new Timer();
            timer7.schedule(listLoansWithPaymentRefresher, REFRESH_RATE, REFRESH_RATE);
        } else {
            loanerLoansTV2.setRoot(new TreeItem<>("There is no list of loans as a borrower with status Active or Risk"));
        }
    }

    public void updateListLoansWithPaymentRefresher(List<DTOLoan> allLoansWithPayment) {
        for (DTOLoan loan : allLoansWithPayment) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL url = getClass().getResource(PAYME_VIEW_FXML_RESOURCE);
                fxmlLoader.setLocation(url);
                VBox singlePayMe = fxmlLoader.load(url.openStream());

                PayMeController payMeController = fxmlLoader.getController();
                payMeController.setInfoOfLoan(loan, cusName);
                paymentFP.getChildren().add(singlePayMe);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void loadLoanerLoansInPayment() {
        if (!cusName.equals(USERNAME)) {
            listLoansAsBorrowerInPaymentRefresher = new ListLoansAsBorrowerRefresher(cusName, this::updateListLoansAsBorrowerInPayment, autoUpdatePro);
            timer6 = new Timer();
            timer6.schedule(listLoansAsBorrowerInPaymentRefresher, REFRESH_RATE, REFRESH_RATE);
        } else {
            loanerLoansTV2.setRoot(new TreeItem<>("There is no list of loans as a borrower with status Active or Risk"));
        }
    }

    public void updateListLoansAsBorrowerInPayment(List<DTOLoan> allLoansAsBorrower){
        Platform.runLater(() -> {
            TreeItem<String> treeLoans = new TreeItem<>("There is no list of loans as a borrower with status Active or Risk");
            if (!allLoansAsBorrower.isEmpty()) {
                for (DTOLoan loan : allLoansAsBorrower) {
                    if (loan.getStatus().equals(ACTIVE) || loan.getStatus().equals(RISK)) {
                        treeLoans.setValue("List of loans as a borrower with status Active or Risk");
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            URL url = getClass().getResource(SINGLE_LOAN_CUSTOMERS_VIEW_FXML_RESOURCE);
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
                loanerLoansTV2.setRoot(treeLoans);
            }
        });
    }

    public void loadNotifications() {
        if (!cusName.equals(USERNAME)) {
            listNotificationsRefresher = new ListNotificationsRefresher(cusName, this::updateNotification, autoUpdatePro);
            timer5 = new Timer();
            timer5.schedule(listNotificationsRefresher, REFRESH_RATE, REFRESH_RATE);
        }
    }

    public void updateNotification(List<Notification> notifications) {
        Platform.runLater(() -> {
            for (Notification notification : notifications) {
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
        });
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
                        loadFileInfo();});
                }
            }
        });
    }

    public void loadFileInfo() {
        filePathLabel.setText("Proper file uploaded successfully");
        setCusInfo(cusName);
    }

    public void setCusName(String userName) {
        this.cusName = userName;
    }
}


