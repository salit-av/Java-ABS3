package AdminView.Refresher;

import DTO.Loan.DTOLoan;
import http.HttpClientUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

import static main.ResourcesPaths.*;

public class ListLoansRefresher extends TimerTask {
    private String adminName;
    private final Consumer<List<DTOLoan>> listLoansConsumer;
    private final BooleanProperty shouldUpdate;


    public ListLoansRefresher(String adminName, Consumer<List<DTOLoan>> listLoansConsumer, SimpleBooleanProperty autoUpdatePro) {
        this.adminName = adminName;
        this.listLoansConsumer = listLoansConsumer;
        this.shouldUpdate = autoUpdatePro;
    }

    @Override
    public void run() {
        if (!shouldUpdate.get()) {
            return;
        }

        String finalUrl = HttpUrl
                .parse(LIST_LOANS_ADMIN)
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
                String jsonArrayOfListLoans = response.body().string();
                DTOLoan[] allLoans = GSON_INSTANCE.fromJson(jsonArrayOfListLoans, DTOLoan[].class);
                listLoansConsumer.accept(Arrays.asList(allLoans));
            }
        });
    }
}