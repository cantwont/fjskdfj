package org._123.fjskdfj.client;

import net.fabricmc.api.ClientModInitializer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FjskdfjClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(FjskdfjScreenshots::takeMinecraftScreenshot, 0, 1, TimeUnit.MINUTES);
    }
}