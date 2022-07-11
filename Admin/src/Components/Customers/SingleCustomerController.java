package Components.Customers;

import DTO.Customers.DTOCustomer;
import Engine.Engine;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SingleCustomerController extends SingleCustomerData {
@FXML TreeView<String> customerTV;

    private TreeItem<String> nameLabel;
    private TreeItem<String> currentBalanceLabel;

    public SingleCustomerController() {
        super("","");
        nameLabel = new TreeItem<>();
        currentBalanceLabel = new TreeItem<>();
    }

    @FXML
    public void initialize() {
        nameLabel.valueProperty().bind(namePro);
        currentBalanceLabel.valueProperty().bind(currentBalancePro);
    }


    public void setInfoOfCustomer(DTOCustomer cus) {
        this.namePro.set("Name: " + cus.getName());
        this.currentBalancePro.set("Balance: "+cus.getBalance());
        nameLabel.getChildren().add(currentBalanceLabel);
    }

    public TreeItem<String> getRoot() {
        return nameLabel;
    }
}
