package com.evandev.brick_and_mortar.compat;

import com.evandev.brick_and_mortar.platform.registry.RegistryObject;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.minecraft.world.item.Item;

public class VanillaBackportCompat {

    public static final RegistryObject<Item> BRIGHT_RESIN_BRICK = ModItems.registerBrickItem("bright_resin_brick");
    public static final RegistryObject<Item> BRIGHT_SOUL_RESIN_BRICK = ModItems.registerBrickItem("bright_soul_resin_brick");
    public static final RegistryObject<Item> MELTED_RESIN_BRICK = ModItems.registerBrickItem("melted_resin_brick");
    public static final RegistryObject<Item> MELTED_SOUL_RESIN_BRICK = ModItems.registerBrickItem("melted_soul_resin_brick");
    public static final RegistryObject<Item> SMOKED_RESIN_BRICK = ModItems.registerBrickItem("smoked_resin_brick");
    public static final RegistryObject<Item> SMOKED_SOUL_RESIN_BRICK = ModItems.registerBrickItem("smoked_soul_resin_brick");
    public static final RegistryObject<Item> SOUL_RESIN_BRICK = ModItems.registerBrickItem("soul_resin_brick");

    public static final ModBlocks.DecorativeFamily BRIGHT_RESIN_BRICKS = ModBlocks.registerFamily("bright_resin_bricks");
    public static final ModBlocks.DecorativeFamily BRIGHT_SOUL_RESIN_BRICKS = ModBlocks.registerFamily("bright_soul_resin_bricks");
    public static final ModBlocks.DecorativeFamily MELTED_RESIN_BRICKS = ModBlocks.registerFamily("melted_resin_bricks");
    public static final ModBlocks.DecorativeFamily MELTED_SOUL_RESIN_BRICKS = ModBlocks.registerFamily("melted_soul_resin_bricks");
    public static final ModBlocks.DecorativeFamily SMOKED_RESIN_BRICKS = ModBlocks.registerFamily("smoked_resin_bricks");
    public static final ModBlocks.DecorativeFamily SMOKED_SOUL_RESIN_BRICKS = ModBlocks.registerFamily("smoked_soul_resin_bricks");
    public static final ModBlocks.DecorativeFamily SOUL_RESIN_BRICKS = ModBlocks.registerFamily("soul_resin_bricks");

    public static void init() {
        if (System.getProperty("fabric-api.datagen") != null) {
            CompatHandler.registerDummyItemIfMissing("minecraft", "resin_clump");
            CompatHandler.registerDummyItemIfMissing("minecraft", "resin_brick");
            CompatHandler.registerDummyBlockIfMissing("minecraft", "resin_bricks");
            CompatHandler.registerDummyBlockIfMissing("minecraft", "resin_brick_stairs");
            CompatHandler.registerDummyBlockIfMissing("minecraft", "resin_brick_slab");
            CompatHandler.registerDummyBlockIfMissing("minecraft", "resin_brick_wall");
        }
    }
}