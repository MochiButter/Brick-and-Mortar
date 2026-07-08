package com.evandev.brick_and_mortar.compat;

import com.evandev.brick_and_mortar.platform.registry.RegistryObject;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.minecraft.world.item.Item;

public class SupplementariesCompat {
    public static final RegistryObject<Item> WHITE_ASH_BRICK = registerBrickItem("white_ash_brick");
    public static final RegistryObject<Item> GRAY_ASH_BRICK = registerBrickItem("gray_ash_brick");
    public static final RegistryObject<Item> BLACK_ASH_BRICK = registerBrickItem("black_ash_brick");

    public static final RegistryObject<Item> SOUL_ASH_BRICK = registerBrickItem("soul_ash_brick");
    public static final RegistryObject<Item> WHITE_SOUL_ASH_BRICK = registerBrickItem("white_soul_ash_brick");
    public static final RegistryObject<Item> GRAY_SOUL_ASH_BRICK = registerBrickItem("gray_soul_ash_brick");
    public static final RegistryObject<Item> BLACK_SOUL_ASH_BRICK = registerBrickItem("black_soul_ash_brick");

    public static final ModBlocks.DecorativeFamily WHITE_ASH_BRICKS = registerFamily("white_ash_bricks");
    public static final ModBlocks.DecorativeFamily GRAY_ASH_BRICKS = registerFamily("gray_ash_bricks");
    public static final ModBlocks.DecorativeFamily BLACK_ASH_BRICKS = registerFamily("black_ash_bricks");

    public static final ModBlocks.DecorativeFamily SOUL_ASH_BRICKS = registerFamily("soul_ash_bricks");
    public static final ModBlocks.DecorativeFamily WHITE_SOUL_ASH_BRICKS = registerFamily("white_soul_ash_bricks");
    public static final ModBlocks.DecorativeFamily GRAY_SOUL_ASH_BRICKS = registerFamily("gray_soul_ash_bricks");
    public static final ModBlocks.DecorativeFamily BLACK_SOUL_ASH_BRICKS = registerFamily("black_soul_ash_bricks");

    public static final ModBlocks.DecorativeFamily ASH_TILES = registerFamily("ash_tiles");
    public static final ModBlocks.DecorativeFamily WHITE_ASH_TILES = registerFamily("white_ash_tiles");
    public static final ModBlocks.DecorativeFamily GRAY_ASH_TILES = registerFamily("gray_ash_tiles");
    public static final ModBlocks.DecorativeFamily BLACK_ASH_TILES = registerFamily("black_ash_tiles");

    public static final ModBlocks.DecorativeFamily SOUL_ASH_TILES = registerFamily("soul_ash_tiles");
    public static final ModBlocks.DecorativeFamily WHITE_SOUL_ASH_TILES = registerFamily("white_soul_ash_tiles");
    public static final ModBlocks.DecorativeFamily GRAY_SOUL_ASH_TILES = registerFamily("gray_soul_ash_tiles");
    public static final ModBlocks.DecorativeFamily BLACK_SOUL_ASH_TILES = registerFamily("black_soul_ash_tiles");

    private static ModBlocks.DecorativeFamily registerFamily(String name) {
        return ModBlocks.registerFamily(name, CompatMods.SUPPLEMENTARIES);
    }

    private static RegistryObject<Item> registerBrickItem(String name) {
        return ModItems.registerBrickItem(name, CompatMods.SUPPLEMENTARIES);
    }

    public static void init() {
        if (System.getProperty("fabric-api.datagen") != null) {
            CompatHandler.registerDummyItemIfMissing(CompatMods.SUPPLEMENTARIES, "ash");
            CompatHandler.registerDummyItemIfMissing(CompatMods.SUPPLEMENTARIES, "ash_brick");
            CompatHandler.registerDummyBlockIfMissing(CompatMods.SUPPLEMENTARIES, "ash_bricks");
            CompatHandler.registerDummyBlockIfMissing(CompatMods.SUPPLEMENTARIES, "ash_bricks_stairs");
            CompatHandler.registerDummyBlockIfMissing(CompatMods.SUPPLEMENTARIES, "ash_bricks_slab");
            CompatHandler.registerDummyBlockIfMissing(CompatMods.SUPPLEMENTARIES, "ash_bricks_wall");
        }
    }
}