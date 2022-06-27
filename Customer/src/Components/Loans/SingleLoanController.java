package Components.Loans;

import DTO.Loan.DTOLoan;
import Engine.Engine;
import Status.Status;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SingleLoanController extends SingleLoanData {
    @FXML
    TreeView<String> singleLoanTV;

    private TreeItem<String> treeLoan;

    private TreeItem<String> newInfo1;
    private TreeItem<String> newInfo2;
    private TreeItem<String> newInfo3;
    private TreeItem<String> newInfo4;
    private TreeItem<String> newInfo5;
    private TreeItem<String> newInfo6;
    private TreeItem<String> newInfo7;

    // pending
    private TreeItem<String> pendingTree;
    private TreeItem<String> penInfo1;

    // active or risk
    private TreeItem<String> activeTree;
    private TreeItem<String> acInfo1;
    private TreeItem<String> acInfo2;

    // risk
    private TreeItem<String> riskTree;
    private TreeItem<String> risInfo1;
    private TreeItem<String> risInfo2;

    // finished
    private TreeItem<String> finishedTree;
    private TreeItem<String> finInfo1;
    private TreeItem<String> finInfo2;

    private Engine engine;

    public SingleLoanController() {
        super("", "","","","","","","","","","","","","");

        treeLoan = new TreeItem<>();
        newInfo1 = new TreeItem<>();
        newInfo2 = new TreeItem<>();
        newInfo3 = new TreeItem<>();
        newInfo4 = new TreeItem<>();
        newInfo5 = new TreeItem<>();
        newInfo6 = new TreeItem<>();
        newInfo7 = new TreeItem<>();

        pendingTree = new TreeItem<>("Pending Info");
        penInfo1 = new TreeItem<>();

        activeTree = new TreeItem<>("Active Info");
        acInfo1 = new TreeItem<>();
        acInfo2 = new TreeItem<>();

        riskTree = new TreeItem<>("Risk Info");
        risInfo1 = new TreeItem<>();
        risInfo2 = new TreeItem<>();

        finishedTree = new TreeItem<>("Finished Info");
        finInfo1 = new TreeItem<>();
        finInfo2 = new TreeItem<>();
    }

    public void initialize() {
        this.treeLoan.valueProperty().bind(headerPro);

        this.newInfo1.valueProperty().bind(idPro);
        this.newInfo2.valueProperty().bind(categoryPro);
        this.newInfo3.valueProperty().bind(capitalAtStartPro);
        this.newInfo4.valueProperty().bind(paysEveryYazPro);
        this.newInfo5.valueProperty().bind(interestPerPaymentPro);
        this.newInfo6.valueProperty().bind(allPaymentPro);
        this.newInfo7.valueProperty().bind(statusPro);

        // pending
        this.penInfo1.valueProperty().bind(payLeftFromPendingToActivePro);

        // active
        this.acInfo1.valueProperty().bind(nextYazForPaymentPro);
        this.acInfo2.valueProperty().bind(nextPayIncludesInterestAndCapitalPro);

        // risk
        this.risInfo1.valueProperty().bind(countUnpaidPaymentsPro);
        this.risInfo2.valueProperty().bind(totalUnpaidPaymentsPro);

        // finished
        this.finInfo1.valueProperty().bind(yazAtFirstPro);
        this.finInfo2.valueProperty().bind(yazAtTheEndPro);

    }

    public void setInfoOfLoan(DTOLoan loan) {
        headerPro.set(loan.getId());
        idPro.set("ID: " + loan.getId());
        categoryPro.set("Category: " + loan.getCategory());
        capitalAtStartPro.set("Capital at start: " + loan.getCapitalAtStart());
        paysEveryYazPro.set("Pays Every Yaz: " + loan.getPaysEveryYaz());

        interestPerPaymentPro.set("Interest Per Payment: " + loan.getInterestPerPayment());
        allPaymentPro.set("All Payment: " + loan.getCapitalAndInterestAtStart());
        Status status = loan.getStatus();
        statusPro.set("Status: " + status.toString());
        treeLoan.getChildren().addAll(newInfo1, newInfo2, newInfo3, newInfo4, newInfo5, newInfo6, newInfo7);

        // pending
        if(status.equals(Status.PENDING)) {
            payLeftFromPendingToActivePro.set("Total Amount Left From Pending To Active: " + loan.getPayLeftFromPendingToActive());
            pendingTree.getChildren().addAll(penInfo1);
            treeLoan.getChildren().addAll(pendingTree);
        }

        // active
        if(status.equals(Status.ACTIVE) || status.equals(Status.RISK)) {
            nextYazForPaymentPro.set("Next Yaz For Payment: " + loan.getNextYazToPay());
            nextPayIncludesInterestAndCapitalPro.set("Next Pay Includes Interest And Capital: " + loan.getNextPayIncludesInterestAndCapital());
            activeTree.getChildren().addAll(acInfo1, acInfo2);
            treeLoan.getChildren().addAll( activeTree);
        }

        // risk
        if(status.equals(Status.RISK)) {
            countUnpaidPaymentsPro.set("Count Of Unpaid Payments: " + loan.getCountAllUnpaidPayments());
            totalUnpaidPaymentsPro.set("Total Money Of Unpaid Payments: " + loan.getTotalAllUnpaidPayments());
            riskTree.getChildren().addAll(risInfo1, risInfo2);
            treeLoan.getChildren().addAll(riskTree);
        }
        // finished
        if(status.equals(Status.FINISHED)) {
            yazAtFirstPro.set("Yaz at start" + loan.getYazAtFirst());
            yazAtTheEndPro.set("Yaz at the end" + loan.getYazAtTheEnd());
            finishedTree.getChildren().addAll(finInfo1, finInfo2);
            treeLoan.getChildren().addAll(finishedTree);

        }
        singleLoanTV.setRoot(treeLoan);
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public TreeItem<String> getRoot(){
        return treeLoan;
    }
}
