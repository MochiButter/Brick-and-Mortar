package com.evandev.brick_and_mortar.client;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.client.screen.KilnScreen;
import com.evandev.brick_and_mortar.registry.ModMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NeoForgeClientEvents {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.KILN_MENU.get(), KilnScreen::new);
    }
}