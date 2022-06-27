package Components.Customers;

import javafx.beans.property.SimpleStringProperty;

public class SingleCustomerData {
    protected SimpleStringProperty namePro;
    protected SimpleStringProperty currentBalancePro;

    protected SimpleStringProperty numOfLoansAsBorrowerNewPro;
    protected SimpleStringProperty numOfLoansAsBorrowerPendingPro;
    protected SimpleStringProperty numOfLoansAsBorrowerActivePro;
    protected SimpleStringProperty numOfLoansAsBorrowerRiskPro;
    protected SimpleStringProperty numOfLoansAsBorrowerFinishedPro;

    protected SimpleStringProperty numOfLoansAsLenderNewPro;
    protected SimpleStringProperty numOfLoansAsLenderPendingPro;
    protected SimpleStringProperty numOfLoansAsLenderActivePro;
    protected SimpleStringProperty numOfLoansAsLenderRiskPro;
    protected SimpleStringProperty numOfLoansAsLenderFinishedPro;

    public SingleCustomerData(String name, String currentBalance, String numOfLoansAsBorrowerNew, String numOfLoansAsBorrowerPending, String numOfLoansAsBorrowerActive, String numOfLoansAsBorrowerRisk, String numOfLoansAsBorrowerFinished, String numOfLoansAsLenderNew, String numOfLoansAsLenderPending, String numOfLoansAsLenderActive, String numOfLoansAsLenderRisk, String numOfLoansAsLenderFinished) {
        this.namePro = new SimpleStringProperty(name);
        this.currentBalancePro = new SimpleStringProperty(currentBalance);
        this.numOfLoansAsBorrowerNewPro = new SimpleStringProperty(numOfLoansAsBorrowerNew);
        this.numOfLoansAsBorrowerPendingPro = new SimpleStringProperty(numOfLoansAsBorrowerPending);
        this.numOfLoansAsBorrowerActivePro = new SimpleStringProperty(numOfLoansAsBorrowerActive);
        this.numOfLoansAsBorrowerRiskPro = new SimpleStringProperty(numOfLoansAsBorrowerRisk);
        this.numOfLoansAsBorrowerFinishedPro = new SimpleStringProperty(numOfLoansAsBorrowerFinished);
        this.numOfLoansAsLenderNewPro = new SimpleStringProperty(numOfLoansAsLenderNew);
        this.numOfLoansAsLenderPendingPro = new SimpleStringProperty(numOfLoansAsLenderPending);
        this.numOfLoansAsLenderActivePro = new SimpleStringProperty(numOfLoansAsLenderActive);
        this.numOfLoansAsLenderRiskPro = new SimpleStringProperty(numOfLoansAsLenderRisk);
        this.numOfLoansAsLenderFinishedPro = new SimpleStringProperty(numOfLoansAsLenderFinished);
    }
}
