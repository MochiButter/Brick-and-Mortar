package com.evandev.brick_and_mortar;

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
    }
}