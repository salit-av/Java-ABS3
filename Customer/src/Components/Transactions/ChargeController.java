package Components.Transactions;

import Components.CustomerView.CustomerViewController;
import jakarta.servlet.http.HttpServlet;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.http.HttpClientUtil;

import java.io.IOException;

import static main.ResourcesPath.CHARGE_BALANCE;

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

    public void submit() {
        try {
            money = Integer.parseInt(moneyTF.getText());
            if (money < 0) {
                helloLabel.setText("Please enter only numbers");
            } else {
                String finalUrl = HttpUrl
                        .parse(CHARGE_BALANCE)
                        .newBuilder()
                        .addQueryParameter("username", cusName)
                        .addQueryParameter("money", String.valueOf(money))
                        .build()
                        .toString();

                HttpClientUtil.runAsync(finalUrl, new Callback() {

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Platform.runLater(() -> {
                            helloLabel.setText(" Something went wrong ");
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Platform.runLater(() -> {
                            try {
                                String balance = response.body().string();
                                helloLabel.setText(" Balance updated ");
                                //balancePro.set("Balance: " + balance);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                });
            }
        } catch (NumberFormatException ex) {
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
