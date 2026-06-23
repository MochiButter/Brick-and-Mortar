package com.evandev.brick_and_mortar;

import com.evandev.brick_and_mortar.client.ClientConfigSetup;
import com.evandev.brick_and_mortar.platform.ForgeRegistrationProvider;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class BrickandMortar {
    public BrickandMortar() {
        CommonClass.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModContainer modContainer = ModLoadingContext.get().getActiveContainer();

        ForgeRegistrationProvider.registerAll(modEventBus);
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