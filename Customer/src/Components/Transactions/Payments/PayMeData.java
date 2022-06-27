package Components.Transactions.Payments;

import javafx.beans.property.SimpleStringProperty;

public class PayMeData {
    protected SimpleStringProperty idPro;
    protected SimpleStringProperty yazToPayPro;
    protected SimpleStringProperty allPayPro;
    protected SimpleStringProperty payLeftInLoanPro;

    public PayMeData(String id, String yazToPay, String allPay, String payLeftInLoan) {
        this.idPro = new SimpleStringProperty(id);
        this.yazToPayPro = new SimpleStringProperty(yazToPay);
        this.allPayPro = new SimpleStringProperty(allPay);
        this.payLeftInLoanPro = new SimpleStringProperty(payLeftInLoan);
    }
}
