package Components.CustomerView.Refresher;

import DTO.Customers.DTOtransaction;
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
import static main.ResourcesPath.LIST_TRANSACTIONS;

public class ListTransactionsRefresher extends TimerTask {
    private String cusName;
    private final Consumer<List<DTOtransaction>> listTransactionsConsumer;
    private final BooleanProperty shouldUpdate;

    public ListTransactionsRefresher(String cusName, Consumer<List<DTOtransaction>> listTransactionsConsumer, SimpleBooleanProperty autoUpdatePro) {
        this.cusName = cusName;
        this.listTransactionsConsumer = listTransactionsConsumer;
        this.shouldUpdate = autoUpdatePro;
    }

    @Override
    public void run() {
        if (!shouldUpdate.get()) {
            return;
        }

        String finalUrl = HttpUrl
                .parse(LIST_TRANSACTIONS)
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
                String jsonArrayOfListTransactions = response.body().string();
                DTOtransaction[] transactions = GSON_INSTANCE.fromJson(jsonArrayOfListTransactions, DTOtransaction[].class);
                listTransactionsConsumer.accept(Arrays.asList(transactions));
            }
        });
    }
}