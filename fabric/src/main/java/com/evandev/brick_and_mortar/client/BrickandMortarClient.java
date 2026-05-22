package com.evandev.brick_and_mortar.client;

import com.evandev.brick_and_mortar.client.screen.KilnScreen;
import com.evandev.brick_and_mortar.registry.ModMenus;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class BrickandMortarClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenus.KILN_MENU.get(), KilnScreen::new);
    }
}