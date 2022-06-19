package UI.BaseView.Body.Components.Notifications;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.*;

public class NotificationAreaController {
    @FXML VBox notiVB;
    @FXML Label titleLabel;
    @FXML Label contentLabel;
    @FXML Label endLabel;

    private SimpleStringProperty titlePro;
    private SimpleStringProperty contentPro;
    private SimpleStringProperty endPro;

    public NotificationAreaController() {
        titlePro = new SimpleStringProperty();
        contentPro = new SimpleStringProperty();
        endPro = new SimpleStringProperty();
    }

    @FXML
    private void initialize() {
        titleLabel.textProperty().bind(titlePro);
        contentLabel.textProperty().bind(contentPro);
        endLabel.textProperty().bind(endPro);
    }

    public void setInfoNoti(String title, String content, String end){
        titlePro.set(title);
        contentPro.set(content);
        endPro.set(end);
    }
}
