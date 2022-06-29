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

public class ChargeController extends HttpServlet {
    @FXML Label helloLabel;
    @FXML TextField moneyTF;
    @FXML Button submitButton;

    private SimpleStringProperty balancePro;
    private int money;
    private String cusName;
    private CustomerViewController customerViewController;

    public ChargeController() {
    }

    @FXML
    public void initialize(){

    }


    public void setCustomer(String cusName) {
        this.cusName = cusName;
    }

    public void submit(){
        try {
            money = Integer.parseInt(moneyTF.getText());
            if(money < 0) {
                helloLabel.setText("Please enter only numbers");
            }
            else {
                Engine engine = ServletUtils.getEngine(getServletContext());
                engine.addBalanceToCustomer(new DTOBalace(cusName, money));
                customerViewController.loadTransactions();
                balancePro.set("Balance: " + (engine.printAllCustomers().findCustomer(cusName).getBalance() + money) );
            }
        } catch (NumberFormatException e) {
            helloLabel.setText("Please enter only numbers");
        }
    }


    public void setCustomerController(CustomerViewController customerViewController) {
        this.customerViewController = customerViewController;
    }

    public void setBalancePro(SimpleStringProperty balancePro) {
        this.balancePro = balancePro;
    }
}
