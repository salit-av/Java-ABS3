package Components.Loans.singleLoanForBuy;

import DTO.Loan.DTOLoan;
import DTO.Loan.DTOpayments;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.http.HttpClientUtil;

import java.io.IOException;

import static main.ResourcesPath.BUY_LOAN;

public class SingleLoanForBuyController {
    @FXML
    Label idLabel;
    @FXML Label fromLenderLabel;
    @FXML
    Label capitalLeftLabel;
    @FXML
    Label interestLeftLabel;
    @FXML
    Label lenderInvestmentLabel;
    @FXML
    Label totalYazTimeLabel;
    @FXML
    Label paysEveryYazLabel;
    @FXML
    Label interestPerPaymentLabel;
    @FXML
    Button buyButton;

    private SimpleStringProperty idPro;
    private SimpleStringProperty fromLenderPro;
    private SimpleStringProperty capitalLeftPro;
    private SimpleStringProperty interestLeftPro;
    private SimpleStringProperty lenderInvestmentPro;
    private SimpleStringProperty totalYazTimePro;
    private SimpleStringProperty paysEveryYazPro;
    private SimpleStringProperty interestPerPaymentPro;
    private SimpleStringProperty errorBuyLoansPro;
    private DTOLoan thisLoan;
    private String cusName;
    private String lendersName;


    public SingleLoanForBuyController() {
        idPro = new SimpleStringProperty();
        fromLenderPro = new SimpleStringProperty();
        capitalLeftPro = new SimpleStringProperty();
        interestLeftPro = new SimpleStringProperty();
        lenderInvestmentPro = new SimpleStringProperty();
        totalYazTimePro = new SimpleStringProperty();
        paysEveryYazPro = new SimpleStringProperty();
        interestPerPaymentPro = new SimpleStringProperty();
    }

    public void initialize() {
        idLabel.textProperty().bind(idPro);
        fromLenderLabel.textProperty().bind(fromLenderPro);
        capitalLeftLabel.textProperty().bind(capitalLeftPro);
        interestLeftLabel.textProperty().bind(interestLeftPro);
        lenderInvestmentLabel.textProperty().bind(lenderInvestmentPro);
        totalYazTimeLabel.textProperty().bind(totalYazTimePro);
        paysEveryYazLabel.textProperty().bind(paysEveryYazPro);
        interestPerPaymentLabel.textProperty().bind(interestPerPaymentPro);
    }

    public void setLoanInfo(DTOLoan loan, String lendersName, String cusName, SimpleStringProperty errorBuyLoansPro) {
        this.thisLoan = loan;
        this.lendersName = lendersName;
        this.cusName = cusName;
        this.errorBuyLoansPro = errorBuyLoansPro;
        DTOpayments payments = loan.getLenders().get(lendersName);
        idPro.set("Loan: " + loan.getId());
        fromLenderPro.set(lendersName);
        capitalLeftPro.set(String.valueOf(payments.getCapitalToPay()));
        interestLeftPro.set(String.valueOf(payments.getInterestToPay()));
        lenderInvestmentPro.set(String.valueOf(payments.getInvestment()));
        totalYazTimePro.set(String.valueOf(loan.getTotalYazTime()));
        paysEveryYazPro.set(String.valueOf(loan.getPaysEveryYaz()));
        interestPerPaymentPro.set(String.valueOf(loan.getInterestPerPayment()));
    }

    @FXML
    public void clickBuy() {
        String finalUrl = HttpUrl
                .parse(BUY_LOAN)
                .newBuilder()
                .addQueryParameter("username", cusName)
                .addQueryParameter("lendersName", lendersName)
                .addQueryParameter("loanID", thisLoan.getId())
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    errorBuyLoansPro.set(e.getMessage());
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                response.body().string();
                Platform.runLater(() -> {
                    errorBuyLoansPro.set("You bought the loan " + thisLoan.getId());
                });
            }

        });
    }
}