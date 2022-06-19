package UI.BaseView.Body.Components.UiAdapter;

import DTO.Loan.DTOLoan;
import javafx.application.Platform;

import java.util.function.Consumer;

public class UiAdapter {
    private Consumer<DTOLoan> introduceNewLoan;

    public UiAdapter(Consumer<DTOLoan> introduceNewLoan) {
        this.introduceNewLoan = introduceNewLoan;
    }

    public void addNewLoan(DTOLoan loan){
        Platform.runLater(
                ()->introduceNewLoan.accept(loan)

        );

    }
}

