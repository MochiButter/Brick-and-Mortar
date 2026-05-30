package com.evandev.brick_and_mortar.datagen.provider;

import com.evandev.brick_and_mortar.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> C_BRICKS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "bricks"));

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        var bricksTag = getOrCreateTagBuilder(C_BRICKS);

        for (var itemObj : ModItems.ALL_BRICK_ITEMS) {
            bricksTag.addOptional(itemObj.getId());
        }
    }
}