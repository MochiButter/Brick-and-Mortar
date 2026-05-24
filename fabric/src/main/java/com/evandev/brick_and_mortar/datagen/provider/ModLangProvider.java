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

        translationBuilder.add("config.brick_and_mortar.title", "Brick and Mortar Config");
        translationBuilder.add("config.brick_and_mortar.category.general", "General");

        for (var blockObj : ModBlocks.ALL_DECORATIVE_BLOCKS) {
            translationBuilder.add(blockObj.get(), formatName(blockObj.getId().getPath()));
        }

        for (var itemObj : ModItems.ALL_BRICK_ITEMS) {
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