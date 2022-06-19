package UI.BaseView.Body.CustomerView.Task;

import DTO.Loan.DTOLoan;
import Engine.Engine;
import UI.BaseView.Body.Components.UiAdapter.UiAdapter;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class task extends Task<Boolean> {

    Engine engine;
     List <DTOLoan> relventLoans;
    List<DTOLoan> allDTOLoans;
    String customerName;
    int amount;
    List<String> categories;
    int interest;
    int totalYaz;
    int amountLoanerLoans;
    UiAdapter uiAdapter;

    public task(Engine engine, List<DTOLoan> allDTOLoans, String customerName, int amount, List<String> categories, int interest, int totalYaz, int amountLoanerLoans,UiAdapter uiAdapter) {
        this.engine = engine;
        this.customerName = customerName;
        this.allDTOLoans = allDTOLoans;
        this.amount = amount;
        this.categories = categories;
        this.interest = interest;
        this.totalYaz = totalYaz;
        this.amountLoanerLoans = amountLoanerLoans;
        this.uiAdapter = uiAdapter;
    }
    
    @Override
    protected Boolean call() throws Exception {
        relventLoans = engine.filterAllInvestmentLoans(allDTOLoans, categories , interest,totalYaz, amountLoanerLoans, customerName);

        final int[] loansCount={1};

        for(DTOLoan loan:relventLoans){
            updateProgress(loansCount[0]++, relventLoans.size());
            uiAdapter.addNewLoan(loan);
            sleepForSomeTime();
        }

        return true;
    }
    private void sleepForSomeTime() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException ignored) {}
    }
}
