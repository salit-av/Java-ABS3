/*
package UI;

import Engine.Engine;
import UI.BaseView.BaseViewController;
import UI.BaseView.Body.BodyController;
import UI.BaseView.Header.HeaderController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static UI.CommonResourcesPaths.*;


public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Engine engine = new Engine();
        // load header component and controller from fxml
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(HEADER_fXML_RESOURCE);
        fxmlLoader.setLocation(url);
        GridPane headerComponent = fxmlLoader.load(url.openStream());
        HeaderController headerController = fxmlLoader.getController();
        headerController.setEngine(engine);

        // load body component and controller from fxml
        fxmlLoader = new FXMLLoader();
        url = getClass().getResource(BODY_FXML_RESOURCE);
        fxmlLoader.setLocation(url);
        ScrollPane bodyComponent = fxmlLoader.load(url.openStream());
        BodyController bodyController = fxmlLoader.getController();
        bodyController.setBodyComponent(bodyComponent);
        bodyController.setEngine(engine);
        bodyController.switchToAdminView();

        // load master app and controller from fxml
        fxmlLoader = new FXMLLoader();
        url = getClass().getResource(BASE_VIEW_FXML);
        fxmlLoader.setLocation(url);
        ScrollPane root = fxmlLoader.load(url.openStream());
        BaseViewController appController = fxmlLoader.getController();

        // add sub components to master app placeholders
       // root.setTop(headerComponent);
        //root.setCenter(bodyComponent);
        //root.getChildren().addAll(headerComponent, bodyComponent);
        appController.setChildren(headerComponent,bodyComponent);

        // connect between controllers
        appController.setBodyComponentController(bodyController);
        appController.setHeaderComponentController(headerController);
        appController.setEngine(engine);

        Scene scene = new Scene(root, 1500, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

*/
