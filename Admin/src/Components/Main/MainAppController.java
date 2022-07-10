package Components.Main;

import AdminView.AdminViewController;
import Login.LoginAdminController;
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

import static main.ResourcesPaths.ADMIN_LOGIN_FXML;
import static main.ResourcesPaths.ADMIN_VIEW_FXML;

public class MainAppController {
    @FXML VBox mainVbox;
    @FXML Label titleLabel;

    private final StringProperty currentUserName;

    private GridPane loginComponent;
    private LoginAdminController loginAdminController;

    private GridPane adminComponent;
    private AdminViewController adminViewController;

    public MainAppController() {
        currentUserName = new SimpleStringProperty("welcome to ABS!");
    }

    @FXML
    public void initialize() {
        titleLabel.textProperty().bind(Bindings.concat("Hello Admin ", currentUserName));

        // prepare components
        loadLoginPage();
        loadAdminPage();
    }

    public void updateUserName(String userName) {
        currentUserName.set(userName);
    }

    private void setMainPanelTo(Pane pane) {
        mainVbox.getChildren().clear();
        mainVbox.getChildren().addAll(titleLabel, pane);
    }

    private void loadLoginPage() {
        URL loginPageUrl = getClass().getResource(ADMIN_LOGIN_FXML);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            loginComponent = fxmlLoader.load();
            loginAdminController = fxmlLoader.getController();
            loginAdminController.setMainController(this);
            setMainPanelTo(loginComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAdminPage() {
        URL loginPageUrl = getClass().getResource(ADMIN_VIEW_FXML);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            adminComponent = fxmlLoader.load();
            adminViewController = fxmlLoader.getController();
            adminViewController.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void switchToAdminPage(String userName) {
        currentUserName.set(userName);
        adminViewController.setAdminName(userName);
        adminViewController.loadAdmin();
        setMainPanelTo(adminComponent);
    }

    public void switchToLogin() {
        Platform.runLater(() -> {
            currentUserName.set("welcome to ABS!");
            setMainPanelTo(loginComponent);
        });
    }

}
