package com.evandev.brick_and_mortar;

import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class BrickandMortar implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonClass.init();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(content -> {
            content.accept(ModItems.KILN.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(content -> {
            ModBlocks.ALL_DECORATIVE_BLOCKS.forEach(block -> content.accept(block.get()));
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(content -> {
            ModItems.ALL_BRICK_ITEMS.forEach(item -> content.accept(item.get()));
            ModItems.ALL_CHORUS_ITEMS.forEach(item -> content.accept(item.get()));
        });
    }
}