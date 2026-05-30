package com.evandev.brick_and_mortar.datagen.provider;

import com.evandev.brick_and_mortar.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.KILN.get());

        var pickaxeTag = getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE);
        var stairsTag = getOrCreateTagBuilder(BlockTags.STAIRS);
        var slabsTag = getOrCreateTagBuilder(BlockTags.SLABS);
        var wallsTag = getOrCreateTagBuilder(BlockTags.WALLS);

        for (var blockObj : ModBlocks.ALL_DECORATIVE_BLOCKS) {
            pickaxeTag.addOptional(blockObj.getId());
        }

        for (var family : ModBlocks.FAMILIES) {
            stairsTag.addOptional(family.stairs().getId());
            slabsTag.addOptional(family.slab().getId());
            if (family.wall() != null) {
                wallsTag.addOptional(family.wall().getId());
            }
        }
    }
}