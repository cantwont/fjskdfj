package org._123.fjskdfj.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ScreenshotRecorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FjskdfjScreenshots {

    private static final Logger LOGGER = LogManager.getLogger("Fjskdfj");
    private static final String SAVE_PATH = "C:\\fjskdfj";

    public static void takeMinecraftScreenshot() {
        MinecraftClient.getInstance().execute(() -> {
            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File saveDir = new File(SAVE_PATH);

                if (!saveDir.exists() && !saveDir.mkdirs()) {
                    LOGGER.error("Couldn't create directory: {}", saveDir.getAbsolutePath());
                    return;
                }

                File screenshotFile = new File(saveDir, "screenshot_" + timeStamp + ".png");
                ScreenshotRecorder.saveScreenshot(saveDir, screenshotFile.getName(), MinecraftClient.getInstance().getFramebuffer(), (message) -> {
                    LOGGER.info("Screenshot saved: {}", screenshotFile.getAbsolutePath());
                });
            } catch (Exception e) {
                LOGGER.error("Failed to take screenshot", e);
            }
        });
    }
}
