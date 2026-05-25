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

    public static final RegistryObject<Item> BLUE_BRICK = registerBrickItem("blue_brick");
    public static final RegistryObject<Item> BROWN_BRICK = registerBrickItem("brown_brick");
    public static final RegistryObject<Item> CHARRED_NETHER_BRICK = registerBrickItem("charred_nether_brick");
    public static final RegistryObject<Item> CHARRED_SOUL_NETHER_BRICK = registerBrickItem("charred_soul_nether_brick");
    public static final RegistryObject<Item> CLINKER_BRICK = registerBrickItem("clinker_brick");
    public static final RegistryObject<Item> CREAM_BRICK = registerBrickItem("cream_brick");
    public static final RegistryObject<Item> GRAY_BRICK = registerBrickItem("gray_brick");
    public static final RegistryObject<Item> LIGHT_NETHER_BRICK = registerBrickItem("light_nether_brick");
    public static final RegistryObject<Item> LIGHT_SOUL_NETHER_BRICK = registerBrickItem("light_soul_nether_brick");
    public static final RegistryObject<Item> ORANGE_BRICK = registerBrickItem("orange_brick");
    public static final RegistryObject<Item> SOUL_NETHER_BRICK = registerBrickItem("soul_nether_brick");
    public static final RegistryObject<Item> RAW_NETHER_BRICK = registerBrickItem("raw_nether_brick");
    public static final RegistryObject<Item> RAW_SOUL_NETHER_BRICK = registerBrickItem("raw_soul_nether_brick");
    public static final RegistryObject<Item> TAN_BRICK = registerBrickItem("tan_brick");

    public static void init() {
        for (RegistryObject<Block> blockObj : ModBlocks.ALL_DECORATIVE_BLOCKS) {
            ITEMS.register(blockObj.getId().getPath(), () -> new BlockItem(blockObj.get(), new Item.Properties()));
        }
    }

    private static RegistryObject<Item> registerBrickItem(String name) {
        RegistryObject<Item> item = ITEMS.register(name, () -> new Item(new Item.Properties()));
        ALL_BRICK_ITEMS.add(item);
        return item;
    }
}