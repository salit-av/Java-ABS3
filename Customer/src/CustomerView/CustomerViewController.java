/*
package CustomerView;

import AllParticipants.Notification;
import DTO.Customers.DTOCustomer;
import DTO.Customers.DTOtransaction;
import DTO.DTOactivate;
import DTO.Loan.DTOLoan;
import Engine.Engine;
import Status.Status;
import UI.BaseView.Body.BodyController;
import Components.Loans.SingleLoanController;
import Components.Notifications.NotificationAreaController;
import Components.Notifications.ScrambleAreaController;
import Components.Notifications.notificationPopUpController;
import Components.Transactions.ChargeController;
import Components.Transactions.Payments.PayMeController;
import Components.Transactions.SingleTransactionController;
import Components.Transactions.WithdrawController;
import UI.BaseView.Body.Components.UiAdapter.UiAdapter;
import CustomerView.Task.task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static UI.CommonResourcesPaths.*;

public class CustomerViewController {
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

    @FXML
    Tab ScrambleTab;
    @FXML
    Label errorScrambleLabel;
    @FXML Label progressLabel;
    @FXML ProgressBar progressBar;
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

    @FXML
    Tab PaymentTab;
    @FXML
    TreeView<String> loanerLoansTV2;
    @FXML
    FlowPane paymentFP;
    @FXML
    FlowPane notificationEP;

    private ObservableList<String> categoriesOL;
    private ObservableList<String> loansOL;

    @FXML
    private BodyController bodyController;
    private DTOCustomer customer;
    private Engine engine;


    public CustomerViewController() {
        categoriesOL = FXCollections.observableArrayList();
        loansOL = FXCollections.observableArrayList();
        loansAfterFilter = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        // Scramble tab
        errorScrambleLabel.setVisible(false);
        categoryFilterCB.getItems().addAll(categoriesOL);
        loansToChoseCB.getItems().addAll(loansOL);
        progressLabel.setVisible(false);

    }

    public void setBodyController(BodyController bodyController) {
        this.bodyController = bodyController;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setCusInfo(Engine engine, String cusName) {
        this.engine = engine;
        this.customer = engine.printAllCustomers().findCustomer(cusName);
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
        customer = engine.printAllCustomers().findCustomer(customer.getName());
        Map<String, DTOLoan> allLoansAsBorrower = customer.getDTOloansAsBorrower();
        TreeItem<String> treeLoans = new TreeItem<>("There is no list of loans in status active or risk as a borrower");
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
                    singleLoanController.setEngine(bodyController.getMainController().getEngine());

                    treeLoans.getChildren().add(singleLoanController.getRoot());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        loanerLoansTV1.setRoot(treeLoans);
    }

    public void loadLendersLoans() {
        customer = engine.printAllCustomers().findCustomer(customer.getName());
        Map<String, DTOLoan> allLoansAsLender = customer.getDTOloansAsLender();
        TreeItem<String> treeLoans = new TreeItem<>("There is no list of loans as a lender");
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
                    singleLoanController.setEngine(bodyController.getMainController().getEngine());

                    treeLoans.getChildren().add(singleLoanController.getRoot());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        lenderLoansTV.setRoot(treeLoans);
    }

    public void loadTransactions() {
        customer = engine.printAllCustomers().findCustomer(customer.getName());
        List<DTOtransaction> transactions = customer.getDTOtransactions();
        TreeItem<String> treeTransactions = new TreeItem<>("There is no list of transactions");
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
            chargeController.setEngein(engine);
            chargeController.setCustomer(customer);

            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);

            Scene popUpScene = new Scene(moneyVB, 300, 200);
            popup.setScene(popUpScene);
            popup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void withdrawFromCusBalance() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(WITHDRAW_VIEW_FXML_RESOURCE);
            fxmlLoader.setLocation(url);
            VBox moneyVB = fxmlLoader.load(url.openStream());
            WithdrawController withdrawController = fxmlLoader.getController();
            withdrawController.setEngein(engine);
            withdrawController.setCustomer(customer);

            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);

            Scene popUpScene = new Scene(moneyVB, 300, 200);
            popup.setScene(popUpScene);
            popup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Scramble TAB
    public void setInfoInScrambleTab() {
        addToCategoryFilterCB();
    }

    public void addToCategoryFilterCB() {
        List<String> categories = engine.getCategories();
        for (String category : categories) {
            categoriesOL.add(category);
        }
        categoryFilterCB.getItems().addAll(categoriesOL);
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
        DTOactivate activate = engine.activate();
        investment = getInvestment();
        List<String> categories = getCategoriesFromFilter();
        int minInterest = getMinInterestFromFilter();
        int minYaz = getMinYazFromFilter();
        int maxLoansOpen = getMaxLoansOpenFromFilter();
        int maxOwnership = getMaxOwnershipFromFilter();

        UiAdapter uiAdapter = createUiAdapter();

        if (errorScrambleLabel.getText().equals("ERROR")) {
            progressLabel.setVisible(true);
            progressLabel.setText("Finding loans for you....");

            loansAfterFilter = engine.filterAllInvestmentLoans(activate.getDtoAllLoans(), categories, minInterest, minYaz, maxLoansOpen, customer.getName());


            task scrambleTask = new task(engine, activate.getDtoAllLoans(), customer.getName(), investment, categories, minInterest, minYaz, maxLoansOpen, uiAdapter);
            progressBar.progressProperty().bind(scrambleTask.progressProperty());
            new Thread(scrambleTask).start();

            progressLabel.setText("Now please chose loan below and click OK");

            setLoansInCCB(loansAfterFilter);
        }






        */
/*
        loansOL.clear();
        errorScrambleLabel.setText("ERROR");
        errorScrambleLabel.setVisible(false);

        //UIAdapter uiAdapter = createUIAdapter();

        //engine.runTask(uiAdapter);

        loansToChoseCB.getItems().clear();
        DTOactivate activate = engine.activate();
        investment = getInvestment();
        List<String> categories = getCategoriesFromFilter();
        int minInterest = getMinInterestFromFilter();
        int minYaz = getMinYazFromFilter();
        int maxLoansOpen = getMaxLoansOpenFromFilter();
        int maxOwnership = getMaxOwnershipFromFilter();

        if (errorScrambleLabel.getText().equals("ERROR")) {
            errorScrambleLabel.setText("Now please chose loan below and click OK");
            errorScrambleLabel.setVisible(true);

            loansAfterFilter = engine.filterAllInvestmentLoans(activate.getDtoAllLoans(), categories, minInterest, minYaz, maxLoansOpen, customer.getName());
            setLoansInCCB(loansAfterFilter);
        }*//*

    }

    private UiAdapter createUiAdapter(){
        return new UiAdapter(
                loanData -> {
                });

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
            if (money > customer.getBalance() || money < 0) {
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
            engine.distributionOfMoneyForLoans(customer, investment, loansToSend);
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
        List<DTOLoan> allLoansWithPayment = customer.getDTOloansWithPayments();
        for (DTOLoan loan : allLoansWithPayment) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL url = getClass().getResource(PAYME_VIEW_FXML_RESOURCE);
                fxmlLoader.setLocation(url);
                VBox singlePayMe = fxmlLoader.load(url.openStream());

                PayMeController payMeController = fxmlLoader.getController();
                payMeController.setInfoOfLoan(loan, customer, engine, paymentFP);
                paymentFP.getChildren().add(singlePayMe);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadLoanerLoansInPayment() {
        customer = engine.printAllCustomers().findCustomer(customer.getName());
        Map<String, DTOLoan> allLoansAsBorrower = customer.getDTOloansAsBorrower();
        TreeItem<String> treeLoans = new TreeItem<>("There is no list of loans as a borrower");
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
                        singleLoanController.setEngine(engine);

                        treeLoans.getChildren().add(singleLoanController.getRoot());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        loanerLoansTV2.setRoot(treeLoans);
    }

    public void loadNotifications() {
        List<Notification> cusNotifi = customer.getNotificationList();
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
*/
