package link.botwmcs.samchai.realmshost.util;


import link.botwmcs.samchai.realmshost.RealmsHost;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public class AnnouncementGetter {
    private OkHttpClient client = new OkHttpClient.Builder().build();

    public CompletableFuture<List<String>> getLines() {
        CompletableFuture<List<String>> future = new CompletableFuture<>();

        Request request = new Request.Builder().url("https://raw.githubusercontent.com/BotW-Minecraft-Server/LTSX/main/announcement.txt").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() == 200) {
                    String responseBody = response.body().string();
                    List<String> lines = Arrays.asList(responseBody.split("\\r?\\n"));
                    future.complete(lines);
                } else {
                    RealmsHost.LOGGER.warn("Failed to get announcement from link. Response code: " + response.code());
                    future.complete(Arrays.asList("Loading..."));
                }
                response.close();
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                RealmsHost.LOGGER.warn("Failed to get announcement from link.", e);
                future.complete(Arrays.asList("Failed to get announcement."));
            }
        });
        return future;
    }
}
