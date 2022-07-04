package main;

import Components.Main.MainAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static main.ResourcesPath.MAIN_APP_FXML;

public class mainCustomer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Login as Customer");

        URL loginPage = getClass().getResource(MAIN_APP_FXML);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPage);
            ScrollPane root = fxmlLoader.load();
            MainAppController mainAppController = fxmlLoader.getController();
            mainAppController.switchToLogin();

            Scene scene = new Scene(root, 1500, 800);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
