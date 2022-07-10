package AdminView.Refresher;

import http.HttpClientUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

import static main.ResourcesPaths.CURR_YAZ;
import static main.ResourcesPaths.GSON_INSTANCE;

public class CurrentYazRefresher extends TimerTask {
    private String adminName;
    private final Consumer<Integer> currYazConsumer;
    private final BooleanProperty shouldUpdate;


    public CurrentYazRefresher(String adminName, Consumer<Integer> currYazConsumer, SimpleBooleanProperty autoUpdatePro) {
        this.adminName = adminName;
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
                .addQueryParameter("username", adminName)
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
