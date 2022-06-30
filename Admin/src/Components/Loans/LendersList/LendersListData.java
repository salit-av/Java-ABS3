package Components.Loans.LendersList;

import javafx.beans.property.SimpleStringProperty;

public class LendersListData {
    //lenders
    protected SimpleStringProperty cusPro;
    protected SimpleStringProperty invPro;
    protected SimpleStringProperty paymentInfo1Pro;
    protected SimpleStringProperty  paymentInfo2Pro;
    protected SimpleStringProperty paymentInfo3Pro;
    protected SimpleStringProperty paymentInfo4Pro;
    protected SimpleStringProperty currPaymentPro;
    protected SimpleStringProperty paymentsOfCusInfo1Pro;
    protected SimpleStringProperty paymentsOfCusInfo2Pro;
    protected SimpleStringProperty  paymentsOfCusInfo3Pro;
    protected SimpleStringProperty paymentsOfCusInfo4Pro;

    public LendersListData(String cus, String inv, String paymentInfo1, String paymentInfo2, String paymentInfo3, String paymentInfo4, String currPayment, String paymentsOfCusInfo1, String paymentsOfCusInfo2, String paymentsOfCusInfo3, String paymentsOfCusInfo4) {
        //lenders
        this.cusPro = new SimpleStringProperty(cus);
        this.invPro = new SimpleStringProperty(inv);
        this.paymentInfo1Pro = new SimpleStringProperty(paymentInfo1);
        this.paymentInfo2Pro = new SimpleStringProperty(paymentInfo2);
        this.paymentInfo3Pro = new SimpleStringProperty(paymentInfo3);
        this.paymentInfo4Pro = new SimpleStringProperty(paymentInfo4);
        this.currPaymentPro = new SimpleStringProperty(currPayment);
        this.paymentsOfCusInfo1Pro = new SimpleStringProperty(paymentsOfCusInfo1);
        this.paymentsOfCusInfo2Pro = new SimpleStringProperty(paymentsOfCusInfo2);
        this.paymentsOfCusInfo3Pro = new SimpleStringProperty(paymentsOfCusInfo3);
        this.paymentsOfCusInfo4Pro = new SimpleStringProperty(paymentsOfCusInfo4);
    }
}
