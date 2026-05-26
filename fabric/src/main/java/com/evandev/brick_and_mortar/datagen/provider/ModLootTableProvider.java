package com.evandev.brick_and_mortar.datagen.provider;

import com.evandev.brick_and_mortar.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {

    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(ModBlocks.KILN.get());

        for (var family : ModBlocks.FAMILIES) {
            dropSelf(family.base().get());
            dropSelf(family.stairs().get());
            add(family.slab().get(), this::createSlabItemTable);
            if (family.wall() != null) dropSelf(family.wall().get());
            if (family.pillar() != null) dropSelf(family.pillar().get());
        }
    }
}