package UI.BaseView.Body.Components.Transactions;

import DTO.Customers.DTOBalace;
import DTO.Customers.DTOCustomer;
import Engine.Engine;
import UI.BaseView.Body.CustomerView.CustomerViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ChargeController {
    @FXML Label helloLabel;
    @FXML TextField moneyTF;
    @FXML Button submitButton;

    private int money;
    private Engine engine;
    private DTOCustomer customer;
    private CustomerViewController customerViewController;

    public ChargeController() {
    }

    @FXML
    public void initialize(){

    }

    public void setEngein(Engine engine) {
        this.engine = engine;
    }

    public void setCustomer(DTOCustomer customer) {
        this.customer = customer;
    }

    public void submit(){
        try {
            money = Integer.parseInt(moneyTF.getText());
            if(money < 0) {
                helloLabel.setText("Please enter only numbers");
            }
            else {
                engine.addBalanceToCustomer(new DTOBalace(customer.getName(), money));
                customerViewController.loadTransactions();
            }
        } catch (NumberFormatException e) {
            helloLabel.setText("Please enter only numbers");
        }
    }


    public void setCustomerController(CustomerViewController customerViewController) {
        this.customerViewController = customerViewController;
    }
}
