package com.evandev.brick_and_mortar;

import com.evandev.brick_and_mortar.config.ModConfig;
import com.evandev.brick_and_mortar.registry.ModBlockEntities;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import com.evandev.brick_and_mortar.registry.ModMenus;

public class CommonClass {

    public static void init() {
        ModConfig.load();

        ModBlocks.init();
        ModItems.init();
        ModBlockEntities.init();
        ModMenus.init();
    }
}