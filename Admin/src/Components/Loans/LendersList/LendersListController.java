package Components.Loans.LendersList;

import DTO.Loan.DTOpayment;
import DTO.Loan.DTOpayments;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.List;

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

}

