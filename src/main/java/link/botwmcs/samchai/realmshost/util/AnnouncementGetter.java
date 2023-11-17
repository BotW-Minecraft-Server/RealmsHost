package link.botwmcs.samchai.realmshost.util;


import link.botwmcs.samchai.realmshost.RealmsHost;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AnnouncementGetter {
    private static List<String> lines;
    private static final CountDownLatch latch = new CountDownLatch(1);
    public static void getAnnouncement(String httpLink) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(httpLink).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() == 200) {
                    String responseBody = response.body().string();
                    lines = Arrays.asList(responseBody.split("\\r?\\n"));
                } else {
                    RealmsHost.LOGGER.warn("Failed to get announcement from link. Response code: " + response.code());
                }
                response.close();
                latch.countDown();
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                RealmsHost.LOGGER.warn("Failed to get announcement from link.", e);
                latch.countDown();
            }
        });
    }

    public List<String> getLines() {
        getAnnouncement("https://raw.githubusercontent.com/BotW-Minecraft-Server/LTSX/main/announcement.txt");
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return lines;
    }
}
