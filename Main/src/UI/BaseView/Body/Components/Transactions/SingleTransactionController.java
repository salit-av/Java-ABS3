package UI.BaseView.Body.Components.Transactions;

import DTO.Customers.DTOtransaction;
import DTO.Loan.DTOLoan;
import Engine.Engine;
import Status.Status;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.awt.*;

public class SingleTransactionController extends singleTransactionData{

    @FXML
    TreeView<String> singleTransactionTV;

    private TreeItem<String> treeTransaction;

    private TreeItem<String> info1;
    private TreeItem<String> info2;
    private TreeItem<String> info3;
    private TreeItem<String> info4;
    private TreeItem<String> info5;

    public SingleTransactionController() {
        super("", "","","","");

        treeTransaction = new TreeItem<>();
        info1 = new TreeItem<>();
        info2 = new TreeItem<>();
        info3 = new TreeItem<>();
        info4 = new TreeItem<>();
        info5 = new TreeItem<>();
    }

    public void initialize() {
        this.treeTransaction.valueProperty().bind(headerPro);

        this.info1.valueProperty().bind(yazPro);
        this.info2.valueProperty().bind(payPro);
        this.info3.valueProperty().bind(inOrOUTPro);
        this.info4.valueProperty().bind(beforeTranPro);
        this.info5.valueProperty().bind(afterTranPro);
    }

    public void setInfoOfTransaction(DTOtransaction transaction) {
        this.headerPro.set("" + transaction.getInOrOut());
        this.yazPro.set("Yaz at execution: " + transaction.getYaz());
        this.payPro.set("Sum: " + transaction.getPay());
        this.inOrOUTPro .set("Income or Expense: " + transaction.getInOrOut());
        this.beforeTranPro .set("Balance before operation: " + transaction.getBeforeTran());
        this.afterTranPro .set("Balance after operation: " + transaction.getAfterTran());
        treeTransaction.getChildren().addAll(info1, info2, info3, info4, info5);
        singleTransactionTV.setRoot(treeTransaction);
    }



    public TreeItem<String> getRoot(){
        return treeTransaction;
    }
}
