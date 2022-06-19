package UI.Exceptions;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ExceptionsController {
    @FXML Label exceptionLabel;
    @FXML ImageView xnum1;

    private SimpleStringProperty exceptionMessage;

    public ExceptionsController() {
        exceptionMessage = new SimpleStringProperty();
    }

    @FXML
    private void initialize() {
        exceptionLabel.textProperty().bind(exceptionMessage);

        RotateTransition rotate = new RotateTransition();
        rotate.setNode(xnum1);
        rotate.setDuration(Duration.millis(1000));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.play();
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage.set(exceptionMessage);
    }
}
