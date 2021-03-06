package Components.CustomerView.Refresher;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.http.HttpClientUtil;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

import static main.ResourcesPath.BALANCE;
import static main.ResourcesPath.GSON_INSTANCE;

public class BalanceRefresher extends TimerTask {
    private String cusName;
    private final Consumer<Integer> balanceConsumer;
    private final BooleanProperty shouldUpdate;


    public BalanceRefresher(String cusName, Consumer<Integer> balanceConsumer, SimpleBooleanProperty autoUpdatePro) {
        this.cusName = cusName;
        this.balanceConsumer = balanceConsumer;
        this.shouldUpdate = autoUpdatePro;
    }

    @Override
    public void run() {
        if (!shouldUpdate.get()) {
            return;
        }

        String finalUrl = HttpUrl
                .parse(BALANCE)
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
                String jsonIntBalance = response.body().string();
                int currBalance = GSON_INSTANCE.fromJson(jsonIntBalance, Integer.class);
                balanceConsumer.accept(currBalance);
            }
        });
    }
}
