package UI.BaseView.Header;

import DTO.Customers.DTOprintCustomer;
import Engine.Engine;
import UI.BaseView.BaseViewController;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


import java.util.HashMap;
import java.util.Map;
import java.util.PrimitiveIterator;

public class HeaderController {
    @FXML ComboBox<String> viewByCombo;
    @FXML Label loadedFilePathLabel;
    @FXML Label currentYazLabel;

    @FXML
    Button aniButton;
    @FXML ImageView dollar1;
    @FXML ImageView dollar2;
    @FXML ImageView dollar3;
    @FXML ImageView dollar4;
    @FXML ImageView dollar5;

    @FXML private BaseViewController mainController;
    private Map<String, DTOprintCustomer> customersMap;

    private SimpleListProperty customersListProperty;
    private SimpleStringProperty currentYazTime;
    private SimpleStringProperty currentFilePath;
    private Engine engine;

    public HeaderController() {
        customersListProperty = new SimpleListProperty();
        currentYazTime = new SimpleStringProperty("Current Yaz: 1");
        currentFilePath = new SimpleStringProperty();
        currentFilePath.set("No file, please upload one");
        customersMap = new HashMap<>();
    }

    @FXML
    private void initialize() {
        viewByCombo.getItems().add("Admin");
        //viewByCombo.promptTextProperty().bind(customersMap.keySet().toString());
        loadedFilePathLabel.textProperty().bind(currentFilePath);
        currentYazLabel.textProperty().bind(currentYazTime);
    }

    @FXML
    public void moveAnimation() {
        // 1
        // translate
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(dollar1);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByX(500);
        translate.setByY(-250);
        translate.setAutoReverse(true);
        translate.play();

        // rotate
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(dollar1);
        rotate.setDuration(Duration.millis(500));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.play();

        // fade
        FadeTransition fade = new FadeTransition();
        fade.setNode(dollar1);
        fade.setDuration(Duration.millis(1000));
        fade.setCycleCount(TranslateTransition.INDEFINITE);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        // scale
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(dollar1);
        scale.setDuration(Duration.millis(1000));
        scale.setCycleCount(TranslateTransition.INDEFINITE);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(2.0);
        scale.setByY(2.0);
        scale.setAutoReverse(true);
        scale.play();

        // 2
        // translate
        TranslateTransition translate2 = new TranslateTransition();
        translate2.setNode(dollar2);
        translate2.setDuration(Duration.millis(1000));
        translate2.setCycleCount(TranslateTransition.INDEFINITE);
        translate2.setByX(500);
        translate2.setByY(-250);
        translate2.setAutoReverse(true);
        translate2.play();

        // rotate
        RotateTransition rotate2 = new RotateTransition();
        rotate2.setNode(dollar2);
        rotate2.setDuration(Duration.millis(500));
        rotate2.setCycleCount(TranslateTransition.INDEFINITE);
        rotate2.setInterpolator(Interpolator.LINEAR);
        rotate2.setByAngle(360);
        rotate2.setAxis(Rotate.Z_AXIS);
        rotate2.play();

        // fade
        FadeTransition fade2 = new FadeTransition();
        fade2.setNode(dollar2);
        fade2.setDuration(Duration.millis(1000));
        fade2.setCycleCount(TranslateTransition.INDEFINITE);
        fade2.setInterpolator(Interpolator.LINEAR);
        fade2.setFromValue(0);
        fade2.setToValue(1);
        fade2.play();

        // scale
        ScaleTransition scale2 = new ScaleTransition();
        scale2.setNode(dollar2);
        scale2.setDuration(Duration.millis(1000));
        scale2.setCycleCount(TranslateTransition.INDEFINITE);
        scale2.setInterpolator(Interpolator.LINEAR);
        scale2.setByX(2.0);
        scale2.setByY(2.0);
        scale2.setAutoReverse(true);
        scale2.play();

        // 3
        // translate
        TranslateTransition translate3 = new TranslateTransition();
        translate3.setNode(dollar3);
        translate3.setDuration(Duration.millis(1000));
        translate3.setCycleCount(TranslateTransition.INDEFINITE);
        translate3.setByX(500);
        translate3.setByY(-250);
        translate3.setAutoReverse(true);
        translate3.play();

        // rotate
        RotateTransition rotate3 = new RotateTransition();
        rotate3.setNode(dollar3);
        rotate3.setDuration(Duration.millis(500));
        rotate3.setCycleCount(TranslateTransition.INDEFINITE);
        rotate3.setInterpolator(Interpolator.LINEAR);
        rotate3.setByAngle(360);
        rotate3.setAxis(Rotate.Z_AXIS);
        rotate3.play();

        // fade
        FadeTransition fade3 = new FadeTransition();
        fade3.setNode(dollar3);
        fade3.setDuration(Duration.millis(1000));
        fade3.setCycleCount(TranslateTransition.INDEFINITE);
        fade3.setInterpolator(Interpolator.LINEAR);
        fade3.setFromValue(0);
        fade3.setToValue(1);
        fade3.play();

        // scale
        ScaleTransition scale3 = new ScaleTransition();
        scale3.setNode(dollar3);
        scale3.setDuration(Duration.millis(1000));
        scale3.setCycleCount(TranslateTransition.INDEFINITE);
        scale3.setInterpolator(Interpolator.LINEAR);
        scale3.setByX(2.0);
        scale3.setByY(2.0);
        scale3.setAutoReverse(true);
        scale3.play();

        // 4
        // translate
        TranslateTransition translate4 = new TranslateTransition();
        translate4.setNode(dollar4);
        translate4.setDuration(Duration.millis(1000));
        translate4.setCycleCount(TranslateTransition.INDEFINITE);
        translate4.setByX(500);
        translate4.setByY(-250);
        translate4.setAutoReverse(true);
        translate4.play();

        // rotate
        RotateTransition rotate4 = new RotateTransition();
        rotate4.setNode(dollar4);
        rotate4.setDuration(Duration.millis(500));
        rotate4.setCycleCount(TranslateTransition.INDEFINITE);
        rotate4.setInterpolator(Interpolator.LINEAR);
        rotate4.setByAngle(360);
        rotate4.setAxis(Rotate.Z_AXIS);
        rotate4.play();

        // fade
        FadeTransition fade4 = new FadeTransition();
        fade4.setNode(dollar4);
        fade4.setDuration(Duration.millis(1000));
        fade4.setCycleCount(TranslateTransition.INDEFINITE);
        fade4.setInterpolator(Interpolator.LINEAR);
        fade4.setFromValue(0);
        fade4.setToValue(1);
        fade4.play();

        // scale
        ScaleTransition scale4 = new ScaleTransition();
        scale4.setNode(dollar4);
        scale4.setDuration(Duration.millis(1000));
        scale4.setCycleCount(TranslateTransition.INDEFINITE);
        scale4.setInterpolator(Interpolator.LINEAR);
        scale4.setByX(2.0);
        scale4.setByY(2.0);
        scale4.setAutoReverse(true);
        scale4.play();

        // 5
        // translate
        TranslateTransition translate5 = new TranslateTransition();
        translate5.setNode(dollar5);
        translate5.setDuration(Duration.millis(1000));
        translate5.setCycleCount(TranslateTransition.INDEFINITE);
        translate5.setByX(500);
        translate5.setByY(-250);
        translate5.setAutoReverse(true);
        translate5.play();

        // rotate
        RotateTransition rotate5 = new RotateTransition();
        rotate5.setNode(dollar5);
        rotate5.setDuration(Duration.millis(500));
        rotate5.setCycleCount(TranslateTransition.INDEFINITE);
        rotate5.setInterpolator(Interpolator.LINEAR);
        rotate5.setByAngle(360);
        rotate5.setAxis(Rotate.Z_AXIS);
        rotate5.play();

        // fade
        FadeTransition fade5 = new FadeTransition();
        fade5.setNode(dollar5);
        fade5.setDuration(Duration.millis(1000));
        fade5.setCycleCount(TranslateTransition.INDEFINITE);
        fade5.setInterpolator(Interpolator.LINEAR);
        fade5.setFromValue(0);
        fade5.setToValue(1);
        fade5.play();

        // scale
        ScaleTransition scale5 = new ScaleTransition();
        scale5.setNode(dollar5);
        scale5.setDuration(Duration.millis(1000));
        scale5.setCycleCount(TranslateTransition.INDEFINITE);
        scale5.setInterpolator(Interpolator.LINEAR);
        scale5.setByX(2.0);
        scale5.setByY(2.0);
        scale5.setAutoReverse(true);
        scale5.play();
    }
    public void setMainController(BaseViewController mainController) {
        this.mainController = mainController;
    }

    public void switchUsers(ActionEvent event){
        if(viewByCombo.getValue().toString().equals("Admin")){
            switchToAdmin();
        }
        else{
            switchToCustomer(viewByCombo.getValue().toString());
        }
    }

    public void switchToAdmin(){
        mainController.getBodyComponentController().switchToAdminView();
    }

    public void switchToCustomer(String cusName) {
        mainController.getBodyComponentController().switchToCustomerView(cusName);
    }

    public Labeled getSelectedFileName() {
        return loadedFilePathLabel;
    }

    public SimpleStringProperty currentFilePathProperty() {
        return currentFilePath;
    }

    public SimpleStringProperty currentYazTimeProperty() {
        return currentYazTime;
    }

    public ComboBox<String> getViewByCombo() {
        return viewByCombo;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
