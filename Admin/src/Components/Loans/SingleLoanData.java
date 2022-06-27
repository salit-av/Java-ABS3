package Components.Loans;

import javafx.beans.property.SimpleStringProperty;

public class SingleLoanData {
    protected SimpleStringProperty headerPro;
    protected SimpleStringProperty idPro;
    protected SimpleStringProperty ownerPro;
    protected SimpleStringProperty categoryPro;
    protected SimpleStringProperty capitalAtStartPro;
    protected SimpleStringProperty interestPerPaymentPro;
    protected SimpleStringProperty intervalOfPaymentPro;
    protected SimpleStringProperty statusPro;
    //pending
    protected SimpleStringProperty totalAmountRaisedByLendersPro;
    protected SimpleStringProperty totalAmountLeftFromPendingToActivePro;

    // active or risk
    protected SimpleStringProperty yazInLoanBecameFromPendingToActivePro;
    protected SimpleStringProperty nextYazForPaymentPro;

    // risk
    protected SimpleStringProperty countOfUnpaidPaymentsPro;
    protected SimpleStringProperty totalMoneyOfUnpaidPaymentsPro;

    // finished
    protected SimpleStringProperty yazAtStartOfTheLoanPro;
    protected SimpleStringProperty yazAtTheEndOfTheLoanPro;



    public SingleLoanData(String id, String owner, String category, String capitalAtStart, String interestPerPayment, String intervalOfPayment, String status, String totalAmountRaisedByLenders, String totalAmountLeftFromPendingToActive,
                          String yazInLoanBecameFromPendingToActive, String nextYazForPayment, String countOfUnpaidPayments, String totalMoneyOfUnpaidPayments, String yazAtStartOfTheLoan, String yazAtTheEndOfTheLoan) {
        this.headerPro = new SimpleStringProperty(id);
        this.idPro = new SimpleStringProperty(id);
        this.ownerPro = new SimpleStringProperty(owner);
        this.categoryPro = new SimpleStringProperty(category);
        this.capitalAtStartPro = new SimpleStringProperty(capitalAtStart);
        this.interestPerPaymentPro = new SimpleStringProperty(interestPerPayment);
        this.intervalOfPaymentPro = new SimpleStringProperty(intervalOfPayment);
        this.statusPro = new SimpleStringProperty(status);
        this.totalAmountRaisedByLendersPro = new SimpleStringProperty(totalAmountRaisedByLenders);
        this.totalAmountLeftFromPendingToActivePro = new SimpleStringProperty(totalAmountLeftFromPendingToActive);

        // active or risk
        this.yazInLoanBecameFromPendingToActivePro = new SimpleStringProperty(yazInLoanBecameFromPendingToActive);
        this.nextYazForPaymentPro = new SimpleStringProperty(nextYazForPayment);

        // risk
        this.countOfUnpaidPaymentsPro = new SimpleStringProperty(countOfUnpaidPayments);
        this.totalMoneyOfUnpaidPaymentsPro = new SimpleStringProperty(totalMoneyOfUnpaidPayments);

        // finished
        this.yazAtStartOfTheLoanPro = new SimpleStringProperty(yazAtStartOfTheLoan);
        this.yazAtTheEndOfTheLoanPro = new SimpleStringProperty(yazAtTheEndOfTheLoan);


    }


}
