package UI.BaseView.Body;

import Engine.Engine;
import UI.BaseView.BaseViewController;
import UI.BaseView.Body.AdminView.AdminViewController;
import UI.BaseView.Body.CustomerView.CustomerViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;

import static UI.CommonResourcesPaths.*;


public class BodyController {
    @FXML HBox bodyHB;
    @FXML ScrollPane bodyComponent;
    @FXML GridPane adminRoot;
    @FXML TabPane customerRoot;

    @FXML private BaseViewController mainController;
    @FXML private AdminViewController adminViewController;
    @FXML private CustomerViewController customerViewController;

    private Engine engine;
    @FXML
    public void initialize() {
        if (adminViewController != null && customerViewController != null) {
            adminViewController.setBodyController(this);
            customerViewController.setBodyController(this);
        }
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setMainController(BaseViewController mainController) {
        this.mainController = mainController;
    }

    public BaseViewController getMainController() {
        return mainController;
    }

    public void setBodyComponent(ScrollPane bodyComponent) {
        this.bodyComponent = bodyComponent;
    }

    public void setAdminViewController(AdminViewController adminViewController) {
        this.adminViewController = adminViewController;
    }

    public void switchToAdminView() {
        // First entry
        if (adminViewController == null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL url = getClass().getResource(BODY_ADMIN_FXML_RESOURCE);
                fxmlLoader.setLocation(url);
                adminRoot = fxmlLoader.load(url.openStream());
                adminViewController = fxmlLoader.getController();
                adminViewController.setBodyController(this);
                adminViewController.setEngine(engine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Not first entry - there is a customer
        if(customerViewController != null){
            bodyHB.getChildren().clear();
            adminViewController.loadLoans();
            adminViewController.loadCustomers();
        }
        bodyHB.getChildren().add(adminRoot);

    }


    public void switchToCustomerView(String cusName) {
        try {
            bodyHB.getChildren().clear();

            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(CUSTOMER_ADMIN_FXML_RESOURCE);
            fxmlLoader.setLocation(url);
            customerRoot = fxmlLoader.load(url.openStream());
            customerViewController = fxmlLoader.getController();
            customerViewController.setBodyController(this);
            customerViewController.setCusInfo(engine, cusName);
            bodyHB.getChildren().add(customerRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
