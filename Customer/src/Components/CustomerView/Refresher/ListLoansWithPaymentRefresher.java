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

import static main.ResourcesPath.*;

public class ListLoansWithPaymentRefresher extends TimerTask {
    private String cusName;
    private final Consumer<List<DTOLoan>> listLoansWithPaymentConsumer;
    private final BooleanProperty shouldUpdate;

    public ListLoansWithPaymentRefresher(String cusName, Consumer<List<DTOLoan>> listLoansWithPaymentConsumer, SimpleBooleanProperty autoUpdatePro) {
        this.cusName = cusName;
        this.listLoansWithPaymentConsumer = listLoansWithPaymentConsumer;
        this.shouldUpdate = autoUpdatePro;
    }

    @Override
    public void run() {
        if (!shouldUpdate.get()) {
            return;
        }

        String finalUrl = HttpUrl
                .parse(LIST_LOANS_WITH_PAYMENT)
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
                String jsonArrayOfListLoansWithPayment = response.body().string();
                DTOLoan[] listLoansWithPayment = GSON_INSTANCE.fromJson(jsonArrayOfListLoansWithPayment, DTOLoan[].class);
                listLoansWithPaymentConsumer.accept(Arrays.asList(listLoansWithPayment));
            }
        });
    }
}