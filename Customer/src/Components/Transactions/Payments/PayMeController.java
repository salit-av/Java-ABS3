package Components.Transactions.Payments;

import Components.Notifications.notificationPopUpController;
import DTO.Loan.DTOLoan;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.http.HttpClientUtil;

import java.io.IOException;
import java.net.URL;

import static main.ResourcesPath.*;


public class PayMeController extends PayMeData{
    @FXML Label loansIDLabel;
    @FXML Label yazToPayLabel;
    @FXML Label payRibitAndCapitalLabel;
    @FXML Label payLeftInLoanLabel;
    @FXML Button payThisPaymentButton;
    @FXML Button payAllLoanButton;

    private DTOLoan thisLoan;
    private String borrowerName;

    public PayMeController() {
        super(" ", "","","");
    }

    public void initialize() {
        loansIDLabel.textProperty().bind(idPro);
        yazToPayLabel.textProperty().bind(yazToPayPro);
        payRibitAndCapitalLabel.textProperty().bind(allPayPro);
        payLeftInLoanLabel.textProperty().bind(payLeftInLoanPro);
    }

    public void setInfoOfLoan(DTOLoan loan, String borrowerName) {
        this.thisLoan = loan;
        this.borrowerName = borrowerName;
        this.idPro.set("Loan: " + loan.getId());
        this.yazToPayPro.set("Next Yaz for payment: " + loan.getNextYazToPay());
        this.allPayPro.set("Payment: " + loan.getNextPayIncludesInterestAndCapital());
        this.payLeftInLoanPro.set("All payment left in loan: " +  (loan.getCapitalAndInterestAtStart() - loan.getTotalInterestAndCapitalUntilNow()));
    }

    public void payThisPayment(){
        String finalUrl = HttpUrl
                .parse(PAY_THIS_PAYMENT)
                .newBuilder()
                .addQueryParameter("username", borrowerName)
                .addQueryParameter("id", thisLoan.getId())
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    popUpInformation(false);
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Platform.runLater(() ->
                        popUpInformation(true)
                );
            }
        });
    }

    public void payAllLoan(){
        String finalUrl = HttpUrl
                .parse(PAY_ALL_LOAN)
                .newBuilder()
                .addQueryParameter("username", borrowerName)
                .addQueryParameter("id", thisLoan.getId())
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    popUpInformation(false);
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Platform.runLater(() ->
                        popUpInformation(true)
                );
            }
        });
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
