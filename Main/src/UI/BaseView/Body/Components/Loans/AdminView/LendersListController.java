package UI.BaseView.Body.Components.Loans.AdminView;

import DTO.Loan.DTOpayment;
import DTO.Loan.DTOpayments;
import Status.Status;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Map;

public class LendersListController extends LendersListData {
    @FXML
    TreeView<String> lendersTV;

    // lenders
    private TreeItem<String> cusTree;
    private TreeItem<String> invTree;
    private TreeItem<String> payTree;
    private TreeItem<String> paymentInfo1;
    private TreeItem<String> paymentInfo2;
    private TreeItem<String> paymentInfo3;
    private TreeItem<String> paymentInfo4;
    private TreeItem<String> currPayment;
    private TreeItem<String> paymentsOfCusInfo1;
    private TreeItem<String> paymentsOfCusInfo2;
    private TreeItem<String> paymentsOfCusInfo3;
    private TreeItem<String> paymentsOfCusInfo4;

    public LendersListController() {
        super("","","","","","","","","","","");
        cusTree = new TreeItem<>();
        invTree = new TreeItem<>();
        payTree = new TreeItem<>("There is no list of payments!");
        paymentInfo1 = new TreeItem<>();
        paymentInfo2 = new TreeItem<>();
        paymentInfo3 = new TreeItem<>();
        paymentInfo4 = new TreeItem<>();
        currPayment = new TreeItem<>();
        paymentsOfCusInfo1 = new TreeItem<>();
        paymentsOfCusInfo2 = new TreeItem<>();
        paymentsOfCusInfo3 = new TreeItem<>();
        paymentsOfCusInfo4 = new TreeItem<>();
    }


    @FXML
    public void initialize() {
        // lenders
        cusTree.valueProperty().bind(cusPro);
        invTree.valueProperty().bind(invPro);
        paymentInfo1.valueProperty().bind(paymentInfo1Pro);
        paymentInfo2.valueProperty().bind(paymentInfo2Pro);
        paymentInfo3.valueProperty().bind(paymentInfo3Pro);
        paymentInfo4.valueProperty().bind(paymentInfo4Pro);
        currPayment.valueProperty().bind(currPaymentPro);
        paymentsOfCusInfo1.valueProperty().bind(paymentsOfCusInfo1Pro);
        paymentsOfCusInfo2.valueProperty().bind(paymentsOfCusInfo2Pro);
        paymentsOfCusInfo3.valueProperty().bind(paymentsOfCusInfo3Pro);
        paymentsOfCusInfo4.valueProperty().bind(paymentsOfCusInfo4Pro);
    }



    public void setInfoOfLender(DTOpayments paymentsOfCus, String name) {
        cusPro.set("Lender's name: " + name);

        invPro.set("Privet investment: " + paymentsOfCus.getInvestment());
        cusTree.getChildren().add(invTree);

        List<DTOpayment> allPayments = paymentsOfCus.getAllPayments();

        if (allPayments != null || !allPayments.isEmpty()) {
            payTree.setValue("List of payments");
            cusTree.getChildren().add(payTree);
            int i = 1;
            for (DTOpayment payment : allPayments) {
                paymentInfo1Pro.set("Yaz of payment: " + payment.getYazPayment());
                paymentInfo2Pro.set("Interest per payment: " + payment.getInterestPerPayment());
                paymentInfo3Pro.set("Capital per payment: " + payment.getCapitalPerPayment());
                paymentInfo4Pro.set("Total payment (interest + capital): " + payment.getTotalPayment());

                if (payment.getTotalPayment() == 0) {   // ->TODO risk
                    System.out.println("-Unpaid Payment!!!-");
                }
                TreeItem<String> currPayment = new TreeItem<>("Payment number " + i);
                i++;
                currPayment.getChildren().addAll(paymentInfo1, paymentInfo2, paymentInfo3, paymentInfo4);
                payTree.getChildren().add(currPayment);
            }
            paymentsOfCusInfo1Pro.set("Capital until now: " + paymentsOfCus.getCapitalUntilNow());
            paymentsOfCusInfo2Pro.set("Interest until now: " + paymentsOfCus.getInterestUntilNow());
            paymentsOfCusInfo3Pro.set("Capital left to pay: " + (paymentsOfCus.getCapital() - paymentsOfCus.getCapitalUntilNow()));
            paymentsOfCusInfo4Pro.set("Interest left to pay: " + (paymentsOfCus.getInterest() - paymentsOfCus.getInterestUntilNow()));
            cusTree.getChildren().addAll(paymentsOfCusInfo1, paymentsOfCusInfo2, paymentsOfCusInfo3, paymentsOfCusInfo4);
        }
    }

    public TreeItem<String> getRoot() {
        return cusTree;
    }



    /*
        @FXML
    public void setLendersList(Map<String, DTOpayments> lenders) {
        if (!lenders.isEmpty()) {
            treeLenders.setValue("List of all lenders");

            for (String name : lenders.keySet()) {
                DTOpayments paymentsOfCus = lenders.get(name);

                TreeItem<String> cusTree = new TreeItem<>("Customer's name: " + name);
                treeLenders.getChildren().add(cusTree);

                TreeItem<String> invTree = new TreeItem<>("Privet invesment: " + paymentsOfCus.getInvestment());
                cusTree.getChildren().add(invTree);

                List<DTOpayment> allPayments = paymentsOfCus.getAllPayments();

                if (allPayments == null || allPayments.isEmpty()) {
                    TreeItem<String> payTree = new TreeItem<>("There is no list of payments!");
                    cusTree.getChildren().add(payTree);

                } else {
                    TreeItem<String> payTree = new TreeItem<>("List of payments");
                    cusTree.getChildren().add(payTree);
                    int i = 1;
                    for (DTOpayment payment : allPayments) {
                        TreeItem<String> paymentInfo1 = new TreeItem<>("Yaz of payment: " + payment.getYazPayment());
                        TreeItem<String> paymentInfo2 = new TreeItem<>("Interest per payment: " + payment.getInterestPerPayment());
                        TreeItem<String> paymentInfo3 = new TreeItem<>("Capital per payment: " + payment.getCapitalPerPayment());
                        TreeItem<String> paymentInfo4 = new TreeItem<>("Total payment (interest + capital): " + payment.getTotalPayment());

                        if (payment.getTotalPayment() == 0) {   // ->TODO risk
                            System.out.println("-Unpaid Payment!!!-");
                        }
                        TreeItem<String> currPayment = new TreeItem<>("Payment number " + i);
                        i++;
                        currPayment.getChildren().addAll(paymentInfo1, paymentInfo2, paymentInfo3, paymentInfo4);
                        payTree.getChildren().add(currPayment);
                    }
                    TreeItem<String> paymentsOfCusInfo1 = new TreeItem<>("Capital until now: " + paymentsOfCus.getCapitalUntilNow());
                    TreeItem<String> paymentsOfCusInfo2 = new TreeItem<>("Interest until now: " + paymentsOfCus.getInterestUntilNow());
                    TreeItem<String> paymentsOfCusInfo3 = new TreeItem<>("Capital left to pay: " + (paymentsOfCus.getCapital() - paymentsOfCus.getCapitalUntilNow()));
                    TreeItem<String> paymentsOfCusInfo4 = new TreeItem<>("Interest left to pay: " + (paymentsOfCus.getInterest() - paymentsOfCus.getInterestUntilNow()));
                    cusTree.getChildren().addAll(paymentsOfCusInfo1, paymentsOfCusInfo2, paymentsOfCusInfo3, paymentsOfCusInfo4);

                }

            }
        }
    }
     */
}

