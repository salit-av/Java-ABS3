package Components.Main;

import Components.CustomerView.CustomerViewController;
import Components.Login.LoginCustomersController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

import static main.ResourcesPath.CUSTOMERS_LOGIN_FXML;
import static main.ResourcesPath.CUSTOMER_VIEW_FXML;

public class MainAppController {
    @FXML VBox mainVbox;
    @FXML Label titleLabel;

    private final StringProperty currentUserName;

    private GridPane loginComponent;
    private LoginCustomersController loginCustomersController;

    private VBox customerComponent;
    private CustomerViewController customerViewController;

    public MainAppController() {
        currentUserName = new SimpleStringProperty("welcome to ABS!");
    }

    @FXML
    public void initialize() {
        titleLabel.textProperty().bind(Bindings.concat("Hello Customer ", currentUserName));

        // prepare components
        loadLoginPage();
        loadCustomerPage();
    }

    public void updateUserName(String userName) {
        currentUserName.set(userName);
    }

    private void setMainPanelTo(Pane pane) {
        mainVbox.getChildren().clear();
        mainVbox.getChildren().addAll(titleLabel, pane);
    }

    private void loadLoginPage() {
        URL loginPageUrl = getClass().getResource(CUSTOMERS_LOGIN_FXML);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            loginComponent = fxmlLoader.load();
            loginCustomersController = fxmlLoader.getController();
            loginCustomersController.setMainController(this);
            setMainPanelTo(loginComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomerPage() {
        URL loginPageUrl = getClass().getResource(CUSTOMER_VIEW_FXML);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            customerComponent = fxmlLoader.load();
            customerViewController = fxmlLoader.getController();
            customerViewController.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void switchToCustomerPage(String userName) {
        currentUserName.set(userName);
        customerViewController.setCusName(userName);
        customerViewController.setCusInfo(userName);
        setMainPanelTo(customerComponent);
    }

    public void switchToLogin() {
        Platform.runLater(() -> {
            currentUserName.set("welcome to ABS!");
            setMainPanelTo(loginComponent);
        });
    }

}
