package com.evandev.brick_and_mortar.compat;

import com.evandev.brick_and_mortar.platform.registry.RegistryObject;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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

    public static void init() {
        if (System.getProperty("fabric-api.datagen") != null) {
            registerDummyIfMissing(CompatMods.SUPPLEMENTARIES, "ash");
            registerDummyIfMissing(CompatMods.SUPPLEMENTARIES, "ash_brick");
        }
    }

    private static void registerDummyIfMissing(String namespace, String path) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace, path);
        if (!BuiltInRegistries.ITEM.containsKey(id)) {
            Registry.register(BuiltInRegistries.ITEM, id, new Item(new Item.Properties()));
        }
    }
}