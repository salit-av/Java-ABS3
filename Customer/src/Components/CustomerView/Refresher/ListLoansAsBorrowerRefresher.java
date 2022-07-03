package Components.CustomerView.Refresher;

import DTO.Loan.DTOLoan;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.http.HttpClientUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

import static main.ResourcesPath.GSON_INSTANCE;
import static main.ResourcesPath.LIST_LOANS_AS_BORROWER;

public class ListLoansAsBorrowerRefresher extends TimerTask {
    private String cusName;
    private final Consumer<List<DTOLoan>> listLoansAsBorrowerConsumer;
    private final BooleanProperty shouldUpdate;


    public ListLoansAsBorrowerRefresher(String cusName, Consumer<List<DTOLoan>> listLoansAsBorrowerConsumer, SimpleBooleanProperty autoUpdatePro) {
        this.cusName = cusName;
        this.listLoansAsBorrowerConsumer = listLoansAsBorrowerConsumer;
        this.shouldUpdate = autoUpdatePro;
    }

    @Override
    public void run() {
        if (!shouldUpdate.get()) {
            return;
        }

        String finalUrl = HttpUrl
                .parse(LIST_LOANS_AS_BORROWER)
                .newBuilder()
                .addQueryParameter("username", cusName)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Ended with failure...");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonArrayOfListLoansAsBorrower = response.body().string();
                DTOLoan[] loansAsBorrower = GSON_INSTANCE.fromJson(jsonArrayOfListLoansAsBorrower, DTOLoan[].class);
                listLoansAsBorrowerConsumer.accept(Arrays.asList(loansAsBorrower));
            }
        });
    }
}
