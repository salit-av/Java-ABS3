package UI.BaseView.Body.Components.Transactions;

import javafx.beans.property.SimpleStringProperty;

public class singleTransactionData {
    protected SimpleStringProperty headerPro;
    protected SimpleStringProperty yazPro;
    protected SimpleStringProperty payPro;
    protected SimpleStringProperty inOrOUTPro;
    protected SimpleStringProperty beforeTranPro;
    protected SimpleStringProperty afterTranPro;

    public singleTransactionData(String yaz, String pay, String inOrOUT, String beforeTran, String afterTran) {
        this.headerPro = new SimpleStringProperty(inOrOUT);
        this.yazPro = new SimpleStringProperty(yaz);
        this.payPro = new SimpleStringProperty(pay);
        this.inOrOUTPro = new SimpleStringProperty(inOrOUT);
        this.beforeTranPro = new SimpleStringProperty(beforeTran);
        this.afterTranPro = new SimpleStringProperty(afterTran);
    }

}
