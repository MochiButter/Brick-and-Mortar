package com.evandev.brick_and_mortar;

import com.evandev.brick_and_mortar.config.ModConfig;
import com.evandev.brick_and_mortar.registry.*;

public class CommonClass {

    public static void init() {
        ModConfig.load();

        ModSounds.init();
        ModBlocks.init();
        ModItems.init();
        ModBlockEntities.init();
        ModMenus.init();
        ModRecipes.init();
    }
}