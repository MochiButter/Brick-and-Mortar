package com.evandev.brick_and_mortar.compat;

import com.evandev.brick_and_mortar.platform.registry.RegistryObject;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.minecraft.world.item.Item;

public class SupplementariesCompat {

    public static final RegistryObject<Item> WHITE_ASH_BRICK = ModItems.registerBrickItem("white_ash_brick");
    public static final RegistryObject<Item> GRAY_ASH_BRICK = ModItems.registerBrickItem("gray_ash_brick");
    public static final RegistryObject<Item> BLACK_ASH_BRICK = ModItems.registerBrickItem("black_ash_brick");

    public static final RegistryObject<Item> SOUL_ASH_BRICK = ModItems.registerBrickItem("soul_ash_brick");
    public static final RegistryObject<Item> WHITE_SOUL_ASH_BRICK = ModItems.registerBrickItem("white_soul_ash_brick");
    public static final RegistryObject<Item> GRAY_SOUL_ASH_BRICK = ModItems.registerBrickItem("gray_soul_ash_brick");
    public static final RegistryObject<Item> BLACK_SOUL_ASH_BRICK = ModItems.registerBrickItem("black_soul_ash_brick");

    public static final ModBlocks.DecorativeFamily WHITE_ASH_BRICKS = ModBlocks.registerFamily("white_ash_bricks");
    public static final ModBlocks.DecorativeFamily GRAY_ASH_BRICKS = ModBlocks.registerFamily("gray_ash_bricks");
    public static final ModBlocks.DecorativeFamily BLACK_ASH_BRICKS = ModBlocks.registerFamily("black_ash_bricks");

    public static final ModBlocks.DecorativeFamily SOUL_ASH_BRICKS = ModBlocks.registerFamily("soul_ash_bricks");
    public static final ModBlocks.DecorativeFamily WHITE_SOUL_ASH_BRICKS = ModBlocks.registerFamily("white_soul_ash_bricks");
    public static final ModBlocks.DecorativeFamily GRAY_SOUL_ASH_BRICKS = ModBlocks.registerFamily("gray_soul_ash_bricks");
    public static final ModBlocks.DecorativeFamily BLACK_SOUL_ASH_BRICKS = ModBlocks.registerFamily("black_soul_ash_bricks");

    public static final ModBlocks.DecorativeFamily ASH_TILES = ModBlocks.registerFamily("ash_tiles");
    public static final ModBlocks.DecorativeFamily WHITE_ASH_TILES = ModBlocks.registerFamily("white_ash_tiles");
    public static final ModBlocks.DecorativeFamily GRAY_ASH_TILES = ModBlocks.registerFamily("gray_ash_tiles");
    public static final ModBlocks.DecorativeFamily BLACK_ASH_TILES = ModBlocks.registerFamily("black_ash_tiles");

    public static final ModBlocks.DecorativeFamily SOUL_ASH_TILES = ModBlocks.registerFamily("soul_ash_tiles");
    public static final ModBlocks.DecorativeFamily WHITE_SOUL_ASH_TILES = ModBlocks.registerFamily("white_soul_ash_tiles");
    public static final ModBlocks.DecorativeFamily GRAY_SOUL_ASH_TILES = ModBlocks.registerFamily("gray_soul_ash_tiles");
    public static final ModBlocks.DecorativeFamily BLACK_SOUL_ASH_TILES = ModBlocks.registerFamily("black_soul_ash_tiles");

    public static void init() {
        if (System.getProperty("fabric-api.datagen") != null) {
            CompatHandler.registerDummyItemIfMissing(CompatMods.SUPPLEMENTARIES, "ash");
            CompatHandler.registerDummyItemIfMissing(CompatMods.SUPPLEMENTARIES, "ash_brick");
            CompatHandler.registerDummyBlockIfMissing(CompatMods.SUPPLEMENTARIES, "ash_bricks");
        }
    }
}