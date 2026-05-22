package com.evandev.brick_and_mortar.registry;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.platform.registry.RegistrationProvider;
import com.evandev.brick_and_mortar.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);

    public static final RegistryObject<Item> KILN = ITEMS.register("kiln", () ->
            new BlockItem(ModBlocks.KILN.get(), new Item.Properties()));

    public static void init() {
    }
}