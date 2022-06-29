package Components.Transactions;

import Components.CustomerView.CustomerViewController;
import DTO.Customers.DTOBalace;
import Engine.Engine;
import jakarta.servlet.http.HttpServlet;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.ServletUtils;

public class WithdrawController extends HttpServlet {
    @FXML
    Label helloLabel;
    @FXML
    TextField moneyTF;
    @FXML
    Button submitButton;

    private SimpleStringProperty balancePro;
    private int money;
    private Engine engine;
    private String cusName;
    private CustomerViewController customerViewController;

    public WithdrawController() {
    }

    @FXML
    public void initialize(){

    }
    public void setCustomerController(CustomerViewController customerViewController) {
        this.customerViewController = customerViewController;
    }

    public void setCustomer(String cusName) {
        this.cusName = cusName;
    }

    public void submit(){
        try {
            Engine engine = ServletUtils.getEngine(getServletContext());
            money = Integer.parseInt(moneyTF.getText());
            int balance = engine.printAllCustomers().findCustomer(cusName).getBalance();
            if(money > balance || money < 0){
                helloLabel.setText("Sorry you do not have enough money!");
            }
            else {
                engine.removeBalanceToCustomer(new DTOBalace(cusName, money));
                customerViewController.loadTransactions();
                balancePro.set("Balance: " + (balance - money) );
            }
        } catch (NumberFormatException e) {
            helloLabel.setText("Please enter only numbers");
        }
    }

    public void setBalancePro(SimpleStringProperty balancePro) {
        this.balancePro = balancePro;
    }
}
