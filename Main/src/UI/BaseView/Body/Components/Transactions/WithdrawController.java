package UI.BaseView.Body.Components.Transactions;

import DTO.Customers.DTOBalace;
import DTO.Customers.DTOprintCustomer;
import Engine.Engine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class WithdrawController {
    @FXML
    Label helloLabel;
    @FXML
    TextField moneyTF;
    @FXML
    Button submitButton;

    private int money;
    private Engine engine;
    private DTOprintCustomer customer;

    public WithdrawController() {
    }

    @FXML
    public void initialize(){

    }

    public void setEngein(Engine engine) {
        this.engine = engine;
    }

    public void setCustomer(DTOprintCustomer customer) {
        this.customer = customer;
    }

    public void submit(){
        try {
            money = Integer.parseInt(moneyTF.getText());
            if(money > customer.getBalance() || money < 0){
                helloLabel.setText("Sorry you do not have enough money!");
            }
            else {
                engine.removeBalanceToCustomer(new DTOBalace(customer.getName(), money));
            }
        } catch (NumberFormatException e) {
            helloLabel.setText("Please enter only numbers");
        }
    }

}
