package com.evandev.brick_and_mortar.compat;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompatHandler {
    private static final List<Runnable> COMPAT_TASKS = new ArrayList<>();

    static {
        register(CompatMods.SUPPLEMENTARIES, SupplementariesCompat::init);
        register(CompatMods.VANILLA_BACKPORT, VanillaBackportCompat::init);

        // register(CompatMods.ARCHITECTS_PALETTE, ArchitectsPaletteCompat::init);
    }

    public static boolean isDatagen() {
        return System.getProperty("fabric-api.datagen") != null;
    }

    public static boolean shouldLoad(String modId) {
        return isDatagen() || Services.PLATFORM.isModLoaded(modId) || Objects.equals(Services.PLATFORM.getPlatformName(), "Forge");
    }

    public static void register(String modId, Runnable initTask) {
        if (shouldLoad(modId)) {
            COMPAT_TASKS.add(initTask);
        }
    }


    public static void registerDummyItemIfMissing(String namespace, String path) {
        ResourceLocation id = new ResourceLocation(namespace, path);
        if (!BuiltInRegistries.ITEM.containsKey(id)) {
            Registry.register(BuiltInRegistries.ITEM, id, new Item(new Item.Properties()));
        }
    }

    public static void registerDummyBlockIfMissing(String namespace, String path) {
        ResourceLocation id = new ResourceLocation(namespace, path);
        if (!BuiltInRegistries.BLOCK.containsKey(id)) {
            Block block = new Block(BlockBehaviour.Properties.of());
            Registry.register(BuiltInRegistries.BLOCK, id, block);
            Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, new Item.Properties()));
        }
    }

    public static void init() {
        for (Runnable task : COMPAT_TASKS) {
            try {
                task.run();
            } catch (Exception e) {
                Constants.LOG.error("Failed to initialize a compat module", e);
            }
        }
    }
}