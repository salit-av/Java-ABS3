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

public class LoansForBuyRefresher extends TimerTask {
    private String cusName;
    private final Consumer<List<DTOLoan>> loansForBuyConsumer;
    private final BooleanProperty shouldUpdate;

    public LoansForBuyRefresher(String cusName, Consumer<List<DTOLoan>> loansForBuyConsumer, SimpleBooleanProperty autoUpdatePro) {
        this.cusName = cusName;
        this.loansForBuyConsumer = loansForBuyConsumer;
        this.shouldUpdate = autoUpdatePro;
    }

    @Override
    public void run() {
        if (!shouldUpdate.get()) {
            return;
        }

        String finalUrl = HttpUrl
                .parse(LOANS_FOR_BUY)
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
                String jsonArrayOfListLoansForBuy = response.body().string();
                DTOLoan[] allLoansForBuy = GSON_INSTANCE.fromJson(jsonArrayOfListLoansForBuy, DTOLoan[].class);
                loansForBuyConsumer.accept(Arrays.asList(allLoansForBuy));
            }
        });
    }
}