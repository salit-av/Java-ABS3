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

import static main.ResourcesPath.CURR_YAZ;
import static main.ResourcesPath.GSON_INSTANCE;

public class CurrYazRefresher extends TimerTask {
    private String cusName;
    private final Consumer<Integer> currYazConsumer;
    private final BooleanProperty shouldUpdate;


    public CurrYazRefresher(String cusName, Consumer<Integer> currYazConsumer, SimpleBooleanProperty autoUpdatePro) {
        this.cusName = cusName;
        this.currYazConsumer = currYazConsumer;
        this.shouldUpdate = autoUpdatePro;
    }

    @Override
    public void run() {
        if (!shouldUpdate.get()) {
            return;
        }

        String finalUrl = HttpUrl
                .parse(CURR_YAZ)
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
                String jsonIntCurrYaz = response.body().string();
                int currYaz = GSON_INSTANCE.fromJson(jsonIntCurrYaz, Integer.class);
                currYazConsumer.accept(currYaz);
            }
        });
    }
}
