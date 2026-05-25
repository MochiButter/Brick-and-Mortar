package com.evandev.brick_and_mortar;

import com.evandev.brick_and_mortar.client.ClientConfigSetup;
import com.evandev.brick_and_mortar.platform.NeoForgeRegistrationProvider;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(Constants.MOD_ID)
public class BrickandMortar {
    public BrickandMortar(IEventBus modEventBus, ModContainer modContainer) {
        CommonClass.init();

        NeoForgeRegistrationProvider.registerAll(modEventBus);
        modEventBus.addListener(this::addCreative);

        if (FMLEnvironment.dist.isClient()) {
            ClientConfigSetup.register(modContainer);
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModItems.KILN.get());
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            ModBlocks.ALL_DECORATIVE_BLOCKS.forEach(block -> event.accept(block.get()));
        }

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            ModItems.ALL_BRICK_ITEMS.forEach(item -> event.accept(item.get()));
            ModItems.ALL_CHORUS_ITEMS.forEach(item -> event.accept(item.get()));
        }
    }
}