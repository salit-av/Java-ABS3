/*
package UI.BaseView;

import Engine.Engine;
import UI.BaseView.Body.BodyController;
import UI.BaseView.Header.HeaderController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BaseViewController {
    @FXML
    GridPane headerComponent;
    @FXML  HeaderController headerComponentController;
    @FXML ScrollPane bodyComponent;
    @FXML  BodyController bodyComponentController;
@FXML
    VBox baseVB;
    private Engine engine;

    public BaseViewController() {

    }

    @FXML
    public void initialize() {
        if (headerComponentController != null && bodyComponentController != null) {
            headerComponentController.setMainController(this);
            bodyComponentController.setMainController(this);
        }
    }

    public void setHeaderComponentController(HeaderController headerComponentController) {
        this.headerComponentController = headerComponentController;
        headerComponentController.setMainController(this);
    }

    public void setBodyComponentController(BodyController bodyComponentController) {
        this.bodyComponentController = bodyComponentController;
        bodyComponentController.setMainController(this);
    }

    public BodyController getBodyComponentController() {
        return bodyComponentController;
    }

    public HeaderController getHeaderComponentController() {
        return headerComponentController;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setChildren(GridPane headerComponent, ScrollPane bodyComponent) {
        baseVB.getChildren().addAll(headerComponent, bodyComponent);
    }
}
*/
