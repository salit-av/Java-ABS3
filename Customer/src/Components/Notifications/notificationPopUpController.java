package Components.Notifications;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class notificationPopUpController {
    @FXML
    Label notificationLabel;

    private SimpleStringProperty notificationMessagePro;

    public notificationPopUpController() {
        notificationMessagePro = new SimpleStringProperty();
    }

    @FXML
    private void initialize() {
        notificationLabel.textProperty().bind(notificationMessagePro);
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessagePro.set(notificationMessage);
    }
}
