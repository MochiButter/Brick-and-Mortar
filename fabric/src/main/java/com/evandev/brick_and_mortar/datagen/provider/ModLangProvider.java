package com.evandev.brick_and_mortar.datagen.provider;

import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModLangProvider extends FabricLanguageProvider {

    public ModLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModBlocks.KILN.get(), "Kiln");
        translationBuilder.add("container.kiln", "Kiln");

        translationBuilder.add("subtitles.brick_and_mortar.block.kiln.fire_crackle", "Kiln crackles");

        translationBuilder.add("config.brick_and_mortar.title", "Brick and Mortar Config");
        translationBuilder.add("config.brick_and_mortar.category.general", "General");

        translationBuilder.add("emi.category.brick_and_mortar.kiln_firing", "Kiln Firing");
        translationBuilder.add("emi.brick_and_mortar.requires_soul", "Requires a Soul Fire base");
        translationBuilder.add("emi.brick_and_mortar.doors_required", "Requires %s open door(s)");
        translationBuilder.add("emi.brick_and_mortar.doors_closed", "Requires doors to be closed");

        translationBuilder.add("tag.item.brick_and_mortar.nether_masonry", "Nether Masonry");
        translationBuilder.add("tag.item.brick_and_mortar.purpur_masonry", "Purpur Masonry");
        translationBuilder.add("tag.item.brick_and_mortar.soul_nether_masonry", "Soul Nether Masonry");
        translationBuilder.add("tag.item.brick_and_mortar.soul_purpur_masonry", "Soul Purpur Masonry");
        translationBuilder.add("tag.item.brick_and_mortar.ash_masonry", "Ash Masonry");
        translationBuilder.add("tag.item.brick_and_mortar.soul_ash_masonry", "Soul Ash Masonry");
        translationBuilder.add("tag.item.brick_and_mortar.resin_masonry", "Resin Masonry");
        translationBuilder.add("tag.item.brick_and_mortar.soul_resin_masonry", "Soul Resin Masonry");

        for (var blockObj : ModBlocks.ALL_DECORATIVE_BLOCKS) {
            translationBuilder.add(blockObj.get(), formatName(blockObj.getId().getPath()));
        }

        for (var itemObj : ModItems.ALL_BRICK_ITEMS) {
            translationBuilder.add(itemObj.get(), formatName(itemObj.getId().getPath()));
        }

        for (var itemObj : ModItems.ALL_CHORUS_ITEMS) {
            translationBuilder.add(itemObj.get(), formatName(itemObj.getId().getPath()));
        }
    }

    private String formatName(String path) {
        String[] words = path.split("_");
        StringBuilder name = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                name.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        return name.toString().trim();
    }
}