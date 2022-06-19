package UI.BaseView.Body.Components.Loans.CustomerView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SingleLoanData implements singleLoanInterface{
    protected SimpleStringProperty headerPro;
    protected SimpleStringProperty idPro;
    protected SimpleStringProperty categoryPro;
    protected SimpleStringProperty capitalAtStartPro;
    protected SimpleStringProperty paysEveryYazPro;
    protected SimpleStringProperty interestPerPaymentPro;
    protected SimpleStringProperty allPaymentPro;
    protected SimpleStringProperty statusPro;

    // pending
    protected SimpleStringProperty payLeftFromPendingToActivePro;

    // active
    protected SimpleStringProperty nextYazForPaymentPro;
    protected SimpleStringProperty nextPayIncludesInterestAndCapitalPro;

    // risk
    protected SimpleStringProperty countUnpaidPaymentsPro;
    protected SimpleStringProperty totalUnpaidPaymentsPro;

    // finished
    protected SimpleStringProperty yazAtFirstPro;
    protected SimpleStringProperty yazAtTheEndPro;

    public SingleLoanData(String id, String category, String capitalAtStart, String paysEveryYaz, String interestPerPayment, String allPayment, String status, String payLeftFromPendingToActive, String nextYazForPayment,
                          String nextPayIncludesInterestAndCapital, String countUnpaidPayments, String totalUnpaidPayments, String yazAtFirst, String yazAtTheEnd) {
        this.headerPro = new SimpleStringProperty(id);
        this.idPro = new SimpleStringProperty(id);
        this.categoryPro = new SimpleStringProperty(category);
        this.capitalAtStartPro = new SimpleStringProperty(capitalAtStart);
        this.paysEveryYazPro = new SimpleStringProperty(paysEveryYaz);
        this.interestPerPaymentPro = new SimpleStringProperty(interestPerPayment);
        this.allPaymentPro = new SimpleStringProperty(allPayment);
        this.statusPro = new SimpleStringProperty(status);
        this.payLeftFromPendingToActivePro = new SimpleStringProperty(payLeftFromPendingToActive);
        this.nextYazForPaymentPro = new SimpleStringProperty(nextYazForPayment);
        this.nextPayIncludesInterestAndCapitalPro = new SimpleStringProperty(nextPayIncludesInterestAndCapital);
        this.countUnpaidPaymentsPro = new SimpleStringProperty(countUnpaidPayments);
        this.totalUnpaidPaymentsPro = new SimpleStringProperty(totalUnpaidPayments);
        this.yazAtFirstPro = new SimpleStringProperty(yazAtFirst);
        this.yazAtTheEndPro = new SimpleStringProperty(yazAtTheEnd);
    }
}



