package Components.Transactions.Payments;

import DTO.Customers.DTOCustomer;
import DTO.Loan.DTOLoan;
import Engine.Engine;
import Components.Notifications.notificationPopUpController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static main.ResourcesPath.POPUP_NOTIFICATION_FXML_RESOURCE;


public class PayMeController extends PayMeData{
    @FXML Label loansIDLabel;
    @FXML Label yazToPayLabel;
    @FXML Label payRibitAndCapitalLabel;
    @FXML Label payLeftInLoanLabel;
    @FXML Button payThisPaymentButton;
    @FXML Button payAllLoanButton;

    private DTOLoan thisLoan;
    private DTOCustomer thisBorrower;
    private Engine engine;
    @FXML FlowPane paymentFP;

    public PayMeController() {
        super(" ", "","","");
    }

    public void initialize() {
        loansIDLabel.textProperty().bind(idPro);
        yazToPayLabel.textProperty().bind(yazToPayPro);
        payRibitAndCapitalLabel.textProperty().bind(allPayPro);
        payLeftInLoanLabel.textProperty().bind(payLeftInLoanPro);
    }

    public void setInfoOfLoan(DTOLoan loan, DTOCustomer borrower, Engine engine, FlowPane paymentFP) {
        this.thisLoan = loan;
        this.thisBorrower = borrower;
        this.engine = engine;
        this.paymentFP = paymentFP;
        this.idPro.set("Loan: " + loan.getId());
        this.yazToPayPro.set("Next Yaz for payment: " + loan.getNextYazToPay());
        this.allPayPro.set("Payment: " + loan.getNextPayIncludesInterestAndCapital());
        this.payLeftInLoanPro.set("All payment left in loan: " +  (loan.getCapitalAndInterestAtStart() - loan.getTotalInterestAndCapitalUntilNow()));
    }

    public void payThisPayment(){
        boolean done = engine.payPaymentToLoan(thisLoan, thisBorrower);
        popUpInformation(done);
    }
    public void payAllLoan(){
        boolean done =  engine.payAllLoan(thisLoan, thisBorrower);
        popUpInformation(done);
    }

    private void popUpInformation(boolean done) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(POPUP_NOTIFICATION_FXML_RESOURCE);
            fxmlLoader.setLocation(url);
            HBox notiHB = fxmlLoader.load(url.openStream());
            notificationPopUpController notificationController = fxmlLoader.getController();
            if (done) {
                notificationController.setNotificationMessage("Payment completed successfully");
            }
            else{
                notificationController.setNotificationMessage("Payment failed, you do not have enough money!");
            }
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);

            Scene popUpScene = new Scene(notiHB, 300, 200);
            popup.setScene(popUpScene);
            popup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
