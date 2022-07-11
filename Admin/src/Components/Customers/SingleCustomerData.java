package Components.Customers;

import javafx.beans.property.SimpleStringProperty;

public class SingleCustomerData {
    protected SimpleStringProperty namePro;
    protected SimpleStringProperty currentBalancePro;


    public SingleCustomerData(String name, String currentBalance) {
        this.namePro = new SimpleStringProperty(name);
        this.currentBalancePro = new SimpleStringProperty(currentBalance);
    }
}
