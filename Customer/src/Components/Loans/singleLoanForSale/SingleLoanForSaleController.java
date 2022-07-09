package Components.Loans.singleLoanForSale;

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

import static main.ResourcesPath.SALE_LOAN;

public class SingleLoanForSaleController {
    @FXML
    Label idLabel;
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
    Button saleButton;

    private SimpleStringProperty idPro;
    private SimpleStringProperty capitalLeftPro;
    private SimpleStringProperty interestLeftPro;
    private SimpleStringProperty lenderInvestmentPro;
    private SimpleStringProperty totalYazTimePro;
    private SimpleStringProperty paysEveryYazPro;
    private SimpleStringProperty interestPerPaymentPro;
    private SimpleStringProperty errorSaleLoansPro;
    private DTOLoan thisLoan;
    private String cusName;


    public SingleLoanForSaleController() {
        idPro = new SimpleStringProperty();
        capitalLeftPro = new SimpleStringProperty();
        interestLeftPro = new SimpleStringProperty();
        lenderInvestmentPro = new SimpleStringProperty();
        totalYazTimePro = new SimpleStringProperty();
        paysEveryYazPro = new SimpleStringProperty();
        interestPerPaymentPro = new SimpleStringProperty();
    }

    public void initialize() {
        idLabel.textProperty().bind(idPro);
        capitalLeftLabel.textProperty().bind(capitalLeftPro);
        interestLeftLabel.textProperty().bind(interestLeftPro);
        lenderInvestmentLabel.textProperty().bind(lenderInvestmentPro);
        totalYazTimeLabel.textProperty().bind(totalYazTimePro);
        paysEveryYazLabel.textProperty().bind(paysEveryYazPro);
        interestPerPaymentLabel.textProperty().bind(interestPerPaymentPro);
    }

    public void setLoanInfo(DTOLoan loan, String cusName, SimpleStringProperty errorSaleLoansPro) {
        this.thisLoan = loan;
        this.cusName = cusName;
        this.errorSaleLoansPro = errorSaleLoansPro;
        DTOpayments payments = loan.getLenders().get(cusName);
        idPro.set("Loan: " + loan.getId());
        capitalLeftPro.set(String.valueOf(payments.getCapitalToPay()));
        interestLeftPro.set(String.valueOf(payments.getInterestToPay()));
        lenderInvestmentPro.set(String.valueOf(payments.getInvestment()));
        totalYazTimePro.set(String.valueOf(loan.getTotalYazTime()));
        paysEveryYazPro.set(String.valueOf(loan.getPaysEveryYaz()));
        interestPerPaymentPro.set(String.valueOf(loan.getInterestPerPayment()));
    }

    @FXML
    public void clickSale() {
        String finalUrl = HttpUrl
                .parse(SALE_LOAN)
                .newBuilder()
                .addQueryParameter("username", cusName)
                .addQueryParameter("loanID", thisLoan.getId())
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    errorSaleLoansPro.set("Something went wrong ");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                response.body().string();
                Platform.runLater(() -> {
                    errorSaleLoansPro.set("Loan " + thisLoan.getId() + " moved to shopping list");
                });
            }

        });
    }
}