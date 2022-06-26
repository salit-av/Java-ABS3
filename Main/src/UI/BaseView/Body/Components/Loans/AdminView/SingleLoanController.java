package UI.BaseView.Body.Components.Loans.AdminView;

import DTO.Loan.DTOLoan;
import DTO.Loan.DTOpayments;
import Status.Status;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static UI.CommonResourcesPaths.*;

public class SingleLoanController extends SingleLoanData {
    @FXML
    TreeView<String> singleLoanTV;
    private TreeItem<String> treeLoan;

    private TreeItem<String> idLabel;
    private TreeItem<String> ownerLabel;
    private TreeItem<String> categoryLabel;
    private TreeItem<String> capitalAtStartLabel;
    private TreeItem<String> interestPerPaymentLabel;
    private TreeItem<String> intervalOfPaymentLabel;
    private TreeItem<String> statusLabel;

    //pending
    private TreeItem<String> pendingTree;
    private TreeItem<String> totalAmountRaisedByLendersLabel;
    private TreeItem<String> totalAmountLeftFromPendingToActiveLabel;

    // active or risk
    private TreeItem<String> activeTree;
    private TreeItem<String> yazInLoanBecameFromPendingToActiveLabel;
    private TreeItem<String> nextYazForPaymentLabel;

    // risk
    private TreeItem<String> riskTree;
    private TreeItem<String> countOfUnpaidPaymentsLabel;
    private TreeItem<String> totalMoneyOfUnpaidPaymentsLabel;

    // finished
    private TreeItem<String> finishedTree;
    private TreeItem<String> yazAtStartOfTheLoanLabel;
    private TreeItem<String> yazAtTheEndOfTheLoanLabel;

    private TreeItem<String> lendersTree;


    public SingleLoanController() {
        super("", "", "", "", "", "", "", "", "", "", "", "", "", "", "");

        treeLoan = new TreeItem<>();
        idLabel = new TreeItem<>();
        ownerLabel = new TreeItem<>();
        categoryLabel = new TreeItem<>();
        capitalAtStartLabel = new TreeItem<>();
        interestPerPaymentLabel = new TreeItem<>();
        intervalOfPaymentLabel = new TreeItem<>();
        statusLabel = new TreeItem<>();

        pendingTree = new TreeItem<>("Pending Info");
        totalAmountRaisedByLendersLabel = new TreeItem<>();
        totalAmountLeftFromPendingToActiveLabel = new TreeItem<>();

        activeTree = new TreeItem<>("Active Info");
        yazInLoanBecameFromPendingToActiveLabel = new TreeItem<>();
        nextYazForPaymentLabel = new TreeItem<>();

        riskTree = new TreeItem<>("Risk Info");
        countOfUnpaidPaymentsLabel = new TreeItem<>();
        totalMoneyOfUnpaidPaymentsLabel = new TreeItem<>();

        finishedTree = new TreeItem<>("Finished Info");
        yazAtStartOfTheLoanLabel = new TreeItem<>();
        yazAtTheEndOfTheLoanLabel = new TreeItem<>();

        lendersTree = new TreeItem<>("There is not list of lenders");

    }

    @FXML
    public void initialize() {
        this.treeLoan.valueProperty().bind(headerPro);

        idLabel.valueProperty().bind(idPro);
        ownerLabel.valueProperty().bind(ownerPro);
        categoryLabel.valueProperty().bind(categoryPro);
        capitalAtStartLabel.valueProperty().bind(capitalAtStartPro);
        interestPerPaymentLabel.valueProperty().bind(interestPerPaymentPro);
        intervalOfPaymentLabel.valueProperty().bind(intervalOfPaymentPro);
        statusLabel.valueProperty().bind(statusPro);

        //pending
        totalAmountRaisedByLendersLabel.valueProperty().bind(totalAmountRaisedByLendersPro);
        totalAmountLeftFromPendingToActiveLabel.valueProperty().bind(totalAmountLeftFromPendingToActivePro);

        // active or risk
        yazInLoanBecameFromPendingToActiveLabel.valueProperty().bind(yazInLoanBecameFromPendingToActivePro);
        nextYazForPaymentLabel.valueProperty().bind(nextYazForPaymentPro);

        // risk
        countOfUnpaidPaymentsLabel.valueProperty().bind(countOfUnpaidPaymentsPro);
        totalMoneyOfUnpaidPaymentsLabel.valueProperty().bind(totalMoneyOfUnpaidPaymentsPro);

        // finished
        yazAtStartOfTheLoanLabel.valueProperty().bind(yazAtStartOfTheLoanPro);
        yazAtTheEndOfTheLoanLabel.valueProperty().bind(yazAtTheEndOfTheLoanPro);


    }

    public void setInfoOfLoan(DTOLoan loan) {
        headerPro.set(loan.getId());
        idPro.set("ID: " + loan.getId());
        ownerPro.set("Owner: " + loan.getOwnersName());
        categoryPro.set("Category: " + loan.getCategory());
        capitalAtStartPro.set("Capital at start: " + loan.getCapitalAtStart());
        interestPerPaymentPro.set("Interest Per Payment: " + loan.getInterestPerPayment());
        intervalOfPaymentPro.set("Interval of payment: " + loan.getPaysEveryYaz());
        Status status = loan.getStatus();
        statusPro.set("Status: " + status.toString());

        treeLoan.getChildren().addAll(idLabel, ownerLabel, categoryLabel, capitalAtStartLabel, interestPerPaymentLabel, intervalOfPaymentLabel, statusLabel);

        if (status.equals(Status.PENDING)) {
            totalAmountRaisedByLendersPro.set("Total Amount Raised By Lenders: " + (loan.getCapitalAtStart() - loan.getPayLeftFromPendingToActive()));
            totalAmountLeftFromPendingToActivePro.set("Total Amount Left From Pending To Active: " + loan.getPayLeftFromPendingToActive());
            pendingTree.getChildren().addAll(totalAmountRaisedByLendersLabel, totalAmountLeftFromPendingToActiveLabel);
            treeLoan.getChildren().add(pendingTree);
        }

        // active or risk
        if (status.equals(Status.ACTIVE) || status.equals(Status.RISK)) {
            yazInLoanBecameFromPendingToActivePro.set("Yaz In Loan Became From Pending To Active: " + loan.getYazFromPendingToActive());
            nextYazForPaymentPro.set("Next Yaz For Payment: " + loan.getNextYazToPay());
            activeTree.getChildren().addAll(yazInLoanBecameFromPendingToActiveLabel, nextYazForPaymentLabel);
            treeLoan.getChildren().add(activeTree);
        }

        // risk
        if (status.equals(Status.RISK)) {
            countOfUnpaidPaymentsPro.set("Count Of Unpaid Payments: " + loan.getCountAllUnpaidPayments());
            totalMoneyOfUnpaidPaymentsPro.set("Total Money Of Unpaid Payments: " + loan.getTotalAllUnpaidPayments());
            riskTree.getChildren().addAll(countOfUnpaidPaymentsLabel, totalMoneyOfUnpaidPaymentsLabel);
            treeLoan.getChildren().add(riskTree);
        }

        // finished
        if (status.equals(Status.FINISHED)) {
            yazAtStartOfTheLoanPro.set("Yaz At Start Of The Loan: " + loan.getYazAtFirst());
            yazAtTheEndOfTheLoanPro.set("Yaz At The End Of The Loan: " + loan.getYazAtTheEnd());
            finishedTree.getChildren().addAll(yazAtStartOfTheLoanLabel, yazAtTheEndOfTheLoanLabel);
            treeLoan.getChildren().add(finishedTree);
        }

        Map<String, DTOpayments> lenders = loan.getLenders();
        treeLoan.getChildren().add(lendersTree);
        if (!lenders.isEmpty()) {
            lendersTree.setValue("List of all lenders");

            for (String name : lenders.keySet()) {
                DTOpayments paymentsOfCus = lenders.get(name);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    URL url = getClass().getResource(LENDERS_LIST_FXML_RESOURCE);
                    fxmlLoader.setLocation(url);
                    VBox singleLender = fxmlLoader.load(url.openStream());

                    LendersListController lendersListController = fxmlLoader.getController();
                    lendersListController.setInfoOfLender(paymentsOfCus, name);

                    lendersTree.getChildren().add(lendersListController.getRoot());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    public TreeItem<String> getRoot() {
        return treeLoan;
    }
}
