package Login;

import Components.Main.MainAppController;
import com.sun.istack.internal.NotNull;
import http.HttpClientUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;

import static main.ResourcesPaths.LOGIN_PAGE;

public class LoginAdminController {
    @FXML Button loginButton;
    @FXML TextField userNameTextField;
    @FXML Label errorMessageLabel;

    private SimpleStringProperty errorMessageProperty;
    private MainAppController mainAppController;

    public LoginAdminController() {
        errorMessageProperty = new SimpleStringProperty(" Entering admin mode ");
    }

    @FXML
    public void initialize() {errorMessageLabel.textProperty().bind(errorMessageProperty);}

    @FXML
    private void loginButtonClicked(ActionEvent event) {
        String userName = userNameTextField.getText();
        if (userName.isEmpty()) {
            errorMessageProperty.set("User name is empty. You can't login with empty user name");
            return;
        }

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(LOGIN_PAGE)
                .newBuilder()
                .addQueryParameter("username", userName)
                .build()
                .toString();

        //updateHttpStatusLine("New request is launched for: " + finalUrl);

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            errorMessageProperty.set("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                        mainAppController.switchToAdminPage(userName);
                    });
                }
            }
        });
    }

    public void setMainController(MainAppController mainAppController) {
        this.mainAppController = mainAppController;
    }
}
