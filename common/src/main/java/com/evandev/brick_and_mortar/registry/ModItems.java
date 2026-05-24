package com.evandev.brick_and_mortar.registry;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.platform.registry.RegistrationProvider;
import com.evandev.brick_and_mortar.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);

    public static final RegistryObject<Item> KILN = ITEMS.register("kiln", () ->
            new BlockItem(ModBlocks.KILN.get(), new Item.Properties()));

    public static final List<RegistryObject<Item>> ALL_BRICK_ITEMS = new ArrayList<>();

    public static final RegistryObject<Item> BLUE_BRICK = registerItem("blue_brick");
    public static final RegistryObject<Item> BROWN_BRICK = registerItem("brown_brick");
    public static final RegistryObject<Item> CHARRED_NETHER_BRICK = registerItem("charred_nether_brick");
    public static final RegistryObject<Item> CHARRED_SOUL_NETHER_BRICK = registerItem("charred_soul_nether_brick");
    public static final RegistryObject<Item> CLINKER_BRICK = registerItem("clinker_brick");
    public static final RegistryObject<Item> CREAM_BRICK = registerItem("cream_brick");
    public static final RegistryObject<Item> GRAY_BRICK = registerItem("gray_brick");
    public static final RegistryObject<Item> LIGHT_NETHER_BRICK = registerItem("light_nether_brick");
    public static final RegistryObject<Item> LIGHT_SOUL_NETHER_BRICK = registerItem("light_soul_nether_brick");
    public static final RegistryObject<Item> ORANGE_BRICK = registerItem("orange_brick");
    public static final RegistryObject<Item> RAW_NETHER_BRICK = registerItem("raw_nether_brick");
    public static final RegistryObject<Item> RAW_SOUL_NETHER_BRICK = registerItem("raw_soul_nether_brick");
    public static final RegistryObject<Item> TAN_BRICK = registerItem("tan_brick");

    public static void init() {
        for (RegistryObject<Block> blockObj : ModBlocks.ALL_DECORATIVE_BLOCKS) {
            ITEMS.register(blockObj.getId().getPath(), () -> new BlockItem(blockObj.get(), new Item.Properties()));
        }
    }

    private static RegistryObject<Item> registerItem(String name) {
        RegistryObject<Item> item = ITEMS.register(name, () -> new Item(new Item.Properties()));
        ALL_BRICK_ITEMS.add(item);
        return item;
    }
}