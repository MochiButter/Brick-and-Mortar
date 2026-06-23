package com.evandev.brick_and_mortar.client;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.client.screen.KilnScreen;
import com.evandev.brick_and_mortar.registry.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientEvents {

    @SubscribeEvent
    public static void registerScreens(FMLClientSetupEvent event) {
        MenuScreens.register(ModMenus.KILN_MENU.get(), KilnScreen::new);
    }
}
