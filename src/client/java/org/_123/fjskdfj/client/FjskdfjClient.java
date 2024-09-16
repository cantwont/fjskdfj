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

    @Override
    public void onInitializeClient() {
        LOGGER.info("Fjskdfj has been started");

        FjskdfjFSChecker.checkAndInitialize();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(FjskdfjScreenshots::takeMinecraftScreenshot, 0, 1, TimeUnit.MINUTES);
    }
}