package com.evandev.brick_and_mortar;

import com.evandev.brick_and_mortar.client.ClientConfigSetup;
import com.evandev.brick_and_mortar.platform.ForgeRegistrationProvider;
import com.evandev.brick_and_mortar.platform.registry.RegistryObject;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            List<RegistryObject<Block>> toSkip = new ArrayList<>();
            ModBlocks.COMPAT_BLOCKS.values().forEach(toSkip::addAll);

            List<RegistryObject<Block>> toAdd = new ArrayList<>();
            for (Map.Entry<String, List<RegistryObject<Block>>> entry : ModBlocks.COMPAT_BLOCKS.entrySet()) {
                if (!ModList.get().isLoaded(entry.getKey())) continue;
                toAdd.addAll(entry.getValue());
            }

            ModBlocks.ALL_DECORATIVE_BLOCKS.forEach(block -> {
                if (!toSkip.contains(block)) event.accept(block.get());
            });

            toAdd.forEach(block -> event.accept(block.get()));
        }

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            List<RegistryObject<Item>> toSkip = new ArrayList<>();
            ModItems.COMPAT_BRICK_ITEMS.values().forEach(toSkip::addAll);

            List<RegistryObject<Item>> toAdd = new ArrayList<>();
            for (Map.Entry<String, List<RegistryObject<Item>>> entry : ModItems.COMPAT_BRICK_ITEMS.entrySet()) {
                if (!ModList.get().isLoaded(entry.getKey())) continue;
                toAdd.addAll(entry.getValue());
            }

            ModItems.ALL_BRICK_ITEMS.forEach(item -> {
                if (!toSkip.contains(item)) event.accept(item.get());
            });

            toAdd.forEach(item -> event.accept(item.get()));

            ModItems.ALL_CHORUS_ITEMS.forEach(item -> event.accept(item.get()));
        }
    }
}