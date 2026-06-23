package com.evandev.brick_and_mortar.client;

import com.evandev.brick_and_mortar.client.integration.ClothConfigIntegration;
import com.evandev.brick_and_mortar.platform.Services;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModContainer;

public class ClientConfigSetup {
    public static void register(ModContainer container) {
        if (Services.PLATFORM.isModLoaded("cloth_config")) {
            container.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory(
                            (client, parent) -> ClothConfigIntegration.createScreen(parent)));
        }
    }
}
