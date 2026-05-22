package com.evandev.brick_and_mortar.datagen.provider;

import com.evandev.brick_and_mortar.registry.ModBlocks;
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
    }
}