package org._123.fjskdfj.client;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FjskdfjClient implements ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger("Fjskdfj");

    @Override
    public void onInitializeClient() {
        LOGGER.info("mod running lalala");
    }
}
