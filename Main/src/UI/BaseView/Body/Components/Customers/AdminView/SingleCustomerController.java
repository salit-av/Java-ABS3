package UI.BaseView.Body.Components.Customers.AdminView;

import DTO.Customers.DTOCustomer;
import DTO.Loan.DTOLoan;
import Engine.Engine;
import Status.Status;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Map;

public class SingleCustomerController extends SingleCustomerData {
@FXML TreeView<String> customerTV;

    private TreeItem<String> nameLabel;
    private TreeItem<String> currentBalanceLabel;

    private TreeItem<String> asBorrower;
    private TreeItem<String> numOfLoansAsBorrowerNewLabel;
    private TreeItem<String> numOfLoansAsBorrowerPendingLabel;
    private TreeItem<String> numOfLoansAsBorrowerActiveLabel;
    private TreeItem<String> numOfLoansAsBorrowerRiskLabel;
    private TreeItem<String> numOfLoansAsBorrowerFinishedLabel;

    private TreeItem<String> asLender;
    private TreeItem<String> numOfLoansAsLenderNewLabel;
    private TreeItem<String>  numOfLoansAsLenderPendingLabel;
    private TreeItem<String> numOfLoansAsLenderActiveLabel;
    private TreeItem<String> numOfLoansAsLenderRiskLabel;
    private TreeItem<String> numOfLoansAsLenderFinishedLabel;



    private Engine engine;

    public SingleCustomerController() {
        super("","","","","","","","","","","","");
        nameLabel = new TreeItem<>();
        currentBalanceLabel = new TreeItem<>();

        asBorrower = new TreeItem<>("Num Of Loans As Borrower ");
        numOfLoansAsBorrowerNewLabel = new TreeItem<>();
        numOfLoansAsBorrowerPendingLabel = new TreeItem<>();
        numOfLoansAsBorrowerActiveLabel = new TreeItem<>();
        numOfLoansAsBorrowerRiskLabel = new TreeItem<>();
        numOfLoansAsBorrowerFinishedLabel = new TreeItem<>();

        asLender = new TreeItem<>("Num Of Loans As Lender ");
        numOfLoansAsLenderNewLabel = new TreeItem<>();
        numOfLoansAsLenderPendingLabel = new TreeItem<>();
        numOfLoansAsLenderActiveLabel = new TreeItem<>();
        numOfLoansAsLenderRiskLabel = new TreeItem<>();
        numOfLoansAsLenderFinishedLabel = new TreeItem<>();
    }

    @FXML
    public void initialize() {
        nameLabel.valueProperty().bind(namePro);
        currentBalanceLabel.valueProperty().bind(currentBalancePro);

        numOfLoansAsBorrowerNewLabel.valueProperty().bind(numOfLoansAsBorrowerNewPro);
        numOfLoansAsBorrowerPendingLabel.valueProperty().bind(numOfLoansAsBorrowerPendingPro);
        numOfLoansAsBorrowerActiveLabel.valueProperty().bind(numOfLoansAsBorrowerActivePro);
        numOfLoansAsBorrowerRiskLabel.valueProperty().bind(numOfLoansAsBorrowerRiskPro);
        numOfLoansAsBorrowerFinishedLabel.valueProperty().bind(numOfLoansAsBorrowerFinishedPro);

        numOfLoansAsLenderNewLabel.valueProperty().bind(numOfLoansAsLenderNewPro);
        numOfLoansAsLenderPendingLabel.valueProperty().bind(numOfLoansAsLenderPendingPro);
        numOfLoansAsLenderActiveLabel.valueProperty().bind(numOfLoansAsLenderActivePro);
        numOfLoansAsLenderRiskLabel.valueProperty().bind(numOfLoansAsLenderRiskPro);
        numOfLoansAsLenderFinishedLabel.valueProperty().bind(numOfLoansAsLenderFinishedPro);
    }


    public void setInfoOfCustomer(DTOCustomer cus) {
        this.namePro.set("Name: " + cus.getName());
        this.currentBalancePro.set("Balance: "+cus.getBalance());
        nameLabel.getChildren().addAll(currentBalanceLabel, asBorrower, asLender);

        // as borrower
        Map<String, DTOLoan> loansAsBorrower = cus.getDTOloansAsBorrower();
        int ne = 0, pen = 0, act = 0, ris = 0, fini = 0;
        for (String loanID : loansAsBorrower.keySet()) {
            DTOLoan loan = loansAsBorrower.get(loanID);
            if (loan.getStatus() == Status.NEW) {
                ne++;
            }
            if (loan.getStatus() == Status.PENDING) {
                pen++;
            }
            if (loan.getStatus() == Status.ACTIVE) {
                act++;
            }
            if (loan.getStatus() == Status.RISK) {
                ris++;
            }
            if (loan.getStatus() == Status.FINISHED) {
                fini++;
            }
        }
        numOfLoansAsBorrowerNewPro.set("New: " + ne);
        numOfLoansAsBorrowerPendingPro.set("Pending: " + pen);
        numOfLoansAsBorrowerActivePro.set("Action: " + act);
        numOfLoansAsBorrowerRiskPro.set("Risk: " + ris);
        numOfLoansAsBorrowerFinishedPro.set("Finished: " + fini);
        asBorrower.getChildren().addAll(numOfLoansAsBorrowerNewLabel, numOfLoansAsBorrowerPendingLabel,numOfLoansAsBorrowerActiveLabel,numOfLoansAsBorrowerRiskLabel,numOfLoansAsBorrowerFinishedLabel);

        //as lender
        Map<String, DTOLoan> loansAsLender = cus.getDTOloansAsLender();
        ne = 0;
        pen = 0;
        act = 0;
        ris = 0;
        fini = 0;
        for (String loanID : loansAsLender.keySet()) {
            DTOLoan loan = loansAsLender.get(loanID);
            if (loan.getStatus() == Status.NEW) {
                ne++;
            }
            if (loan.getStatus() == Status.PENDING) {
                pen++;
            }
            if (loan.getStatus() == Status.ACTIVE) {
                act++;
            }
            if (loan.getStatus() == Status.RISK) {
                ris++;
            }
            if (loan.getStatus() == Status.FINISHED) {
                fini++;
            }
        }
        numOfLoansAsLenderNewPro.set("New: " + ne);
        numOfLoansAsLenderPendingPro.set("Pending: " + pen);
        numOfLoansAsLenderActivePro.set("Action: " + act);
        numOfLoansAsLenderRiskPro.set("Risk: " + ris);
        numOfLoansAsLenderFinishedPro.set("Finished: " + fini);
        asLender.getChildren().addAll(numOfLoansAsLenderNewLabel, numOfLoansAsLenderPendingLabel,numOfLoansAsLenderActiveLabel,numOfLoansAsLenderRiskLabel,numOfLoansAsLenderFinishedLabel);
        customerTV.setRoot(nameLabel);
    }

    public TreeItem<String> getRoot() {
        return nameLabel;
    }
}
