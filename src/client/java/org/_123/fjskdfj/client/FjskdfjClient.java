package org._123.fjskdfj.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ScreenshotRecorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FjskdfjClient implements ClientModInitializer {

    private static final Logger LOGGER = LogManager.getLogger("Fjskdfj");
    private static final String SAVE_PATH = "C:\\fjskdfj";

    @Override
    public void onInitializeClient() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(this::takeMinecraftScreenshot, 0, 1, TimeUnit.MINUTES);
    }

    private void takeMinecraftScreenshot() {
        MinecraftClient.getInstance().execute(() -> {
            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File saveDir = new File(SAVE_PATH);

                if (!saveDir.exists() && !saveDir.mkdirs()) {
                    LOGGER.error("couldn't create directory: {}", saveDir.getAbsolutePath());
                    return;
                }

                File screenshotFile = new File(saveDir, "screenshot_" + timeStamp + ".png");
                ScreenshotRecorder.saveScreenshot(saveDir, screenshotFile.getName(), MinecraftClient.getInstance().getFramebuffer(), (message) -> {
                    LOGGER.info("screenshot saved: {}", screenshotFile.getAbsolutePath());
                });
            } catch (Exception e) {
                LOGGER.error("failed to take screenshot", e);
            }
        });
    }
}