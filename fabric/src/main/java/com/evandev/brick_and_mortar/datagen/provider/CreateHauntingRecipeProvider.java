package com.evandev.brick_and_mortar.datagen.provider;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.compat.CompatMods;
import com.evandev.brick_and_mortar.compat.SupplementariesCompat;
import com.evandev.brick_and_mortar.compat.VanillaBackportCompat;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CreateHauntingRecipeProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public CreateHauntingRecipeProvider(FabricDataOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipe/haunting");
    }

    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        addHaunting(futures, cache, ModItems.RAW_NETHER_BRICK.get(), ModItems.RAW_SOUL_NETHER_BRICK.get());
        addHaunting(futures, cache, ModItems.LIGHT_NETHER_BRICK.get(), ModItems.LIGHT_SOUL_NETHER_BRICK.get());
        addHaunting(futures, cache, Items.NETHER_BRICK, ModItems.SOUL_NETHER_BRICK.get());
        addHaunting(futures, cache, ModItems.CHARRED_NETHER_BRICK.get(), ModItems.CHARRED_SOUL_NETHER_BRICK.get());

        addHaunting(futures, cache, ModItems.RAW_POPPED_CHORUS.get(), ModItems.RAW_SOUL_POPPED_CHORUS.get());
        addHaunting(futures, cache, ModItems.LIGHT_POPPED_CHORUS.get(), ModItems.LIGHT_SOUL_POPPED_CHORUS.get());
        addHaunting(futures, cache, Items.POPPED_CHORUS_FRUIT, ModItems.SOUL_POPPED_CHORUS.get());
        addHaunting(futures, cache, ModItems.BURNT_POPPED_CHORUS.get(), ModItems.BURNT_SOUL_POPPED_CHORUS.get());

        addHaunting(futures, cache, SupplementariesCompat.WHITE_ASH_BRICK.get(), SupplementariesCompat.WHITE_SOUL_ASH_BRICK.get(), CompatMods.SUPPLEMENTARIES);
        addHaunting(futures, cache, SupplementariesCompat.GRAY_ASH_BRICK.get(), SupplementariesCompat.GRAY_SOUL_ASH_BRICK.get(), CompatMods.SUPPLEMENTARIES);
        addHaunting(futures, cache, BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(CompatMods.SUPPLEMENTARIES, "ash_brick")), SupplementariesCompat.SOUL_ASH_BRICK.get(), CompatMods.SUPPLEMENTARIES);
        addHaunting(futures, cache, SupplementariesCompat.BLACK_ASH_BRICK.get(), SupplementariesCompat.BLACK_SOUL_ASH_BRICK.get(), CompatMods.SUPPLEMENTARIES);

        addHaunting(futures, cache, VanillaBackportCompat.MELTED_RESIN_BRICK.get(), VanillaBackportCompat.MELTED_SOUL_RESIN_BRICK.get(), CompatMods.VANILLA_BACKPORT);
        addHaunting(futures, cache, VanillaBackportCompat.BRIGHT_RESIN_BRICK.get(), VanillaBackportCompat.BRIGHT_SOUL_RESIN_BRICK.get(), CompatMods.VANILLA_BACKPORT);
        addHaunting(futures, cache, BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("minecraft", "resin_brick")), VanillaBackportCompat.SOUL_RESIN_BRICK.get(), CompatMods.VANILLA_BACKPORT);
        addHaunting(futures, cache, VanillaBackportCompat.SMOKED_RESIN_BRICK.get(), VanillaBackportCompat.SMOKED_SOUL_RESIN_BRICK.get(), CompatMods.VANILLA_BACKPORT);

        addFamilyHaunting(futures, cache, ModBlocks.RAW_NETHER_BRICKS, ModBlocks.RAW_SOUL_NETHER_BRICKS);
        addFamilyHaunting(futures, cache, ModBlocks.LIGHT_NETHER_BRICKS, ModBlocks.LIGHT_SOUL_NETHER_BRICKS);
        addFamilyHaunting(futures, cache, ModBlocks.CHARRED_NETHER_BRICKS, ModBlocks.CHARRED_SOUL_NETHER_BRICKS);
        addHaunting(futures, cache, Blocks.NETHER_BRICKS, ModBlocks.SOUL_NETHER_BRICKS.base().get());
        addHaunting(futures, cache, Blocks.NETHER_BRICK_STAIRS, ModBlocks.SOUL_NETHER_BRICKS.stairs().get());
        addHaunting(futures, cache, Blocks.NETHER_BRICK_SLAB, ModBlocks.SOUL_NETHER_BRICKS.slab().get());
        addHaunting(futures, cache, Blocks.NETHER_BRICK_WALL, ModBlocks.SOUL_NETHER_BRICKS.wall().get());

        addFamilyHaunting(futures, cache, ModBlocks.RAW_NETHER_TILES, ModBlocks.RAW_SOUL_NETHER_TILES);
        addFamilyHaunting(futures, cache, ModBlocks.LIGHT_NETHER_TILES, ModBlocks.LIGHT_SOUL_NETHER_TILES);
        addFamilyHaunting(futures, cache, ModBlocks.CHARRED_NETHER_TILES, ModBlocks.CHARRED_SOUL_NETHER_TILES);
        addFamilyHaunting(futures, cache, ModBlocks.NETHER_TILES, ModBlocks.SOUL_NETHER_TILES);

        addFamilyHaunting(futures, cache, ModBlocks.RAW_PURPUR_BLOCK, ModBlocks.RAW_SOUL_PURPUR_BLOCK);
        addFamilyHaunting(futures, cache, ModBlocks.LIGHT_PURPUR_BLOCK, ModBlocks.LIGHT_SOUL_PURPUR_BLOCK);
        addFamilyHaunting(futures, cache, ModBlocks.BURNT_PURPUR_BLOCK, ModBlocks.BURNT_SOUL_PURPUR_BLOCK);
        addHaunting(futures, cache, Blocks.PURPUR_BLOCK, ModBlocks.SOUL_PURPUR_BLOCK.base().get());
        addHaunting(futures, cache, Blocks.PURPUR_STAIRS, ModBlocks.SOUL_PURPUR_BLOCK.stairs().get());
        addHaunting(futures, cache, Blocks.PURPUR_SLAB, ModBlocks.SOUL_PURPUR_BLOCK.slab().get());
        addHaunting(futures, cache, Blocks.PURPUR_PILLAR, ModBlocks.SOUL_PURPUR_BLOCK.pillar().get());

        addFamilyHaunting(futures, cache, ModBlocks.RAW_PURPUR_BRICKS, ModBlocks.RAW_SOUL_PURPUR_BRICKS);
        addFamilyHaunting(futures, cache, ModBlocks.LIGHT_PURPUR_BRICKS, ModBlocks.LIGHT_SOUL_PURPUR_BRICKS);
        addFamilyHaunting(futures, cache, ModBlocks.BURNT_PURPUR_BRICKS, ModBlocks.BURNT_SOUL_PURPUR_BRICKS);
        addFamilyHaunting(futures, cache, ModBlocks.PURPUR_BRICKS, ModBlocks.SOUL_PURPUR_BRICKS);

        addHaunting(futures, cache, ModBlocks.CHISELED_RAW_PURPUR.get(), ModBlocks.CHISELED_RAW_SOUL_PURPUR.get());
        addHaunting(futures, cache, ModBlocks.CHISELED_LIGHT_PURPUR.get(), ModBlocks.CHISELED_LIGHT_SOUL_PURPUR.get());
        addHaunting(futures, cache, ModBlocks.CHISELED_BURNT_PURPUR.get(), ModBlocks.CHISELED_BURNT_SOUL_PURPUR.get());
        addHaunting(futures, cache, ModBlocks.CHISELED_PURPUR.get(), ModBlocks.CHISELED_SOUL_PURPUR.get());

        addFamilyHaunting(futures, cache, SupplementariesCompat.WHITE_ASH_BRICKS, SupplementariesCompat.WHITE_SOUL_ASH_BRICKS, CompatMods.SUPPLEMENTARIES);
        addFamilyHaunting(futures, cache, SupplementariesCompat.GRAY_ASH_BRICKS, SupplementariesCompat.GRAY_SOUL_ASH_BRICKS, CompatMods.SUPPLEMENTARIES);
        addFamilyHaunting(futures, cache, SupplementariesCompat.BLACK_ASH_BRICKS, SupplementariesCompat.BLACK_SOUL_ASH_BRICKS, CompatMods.SUPPLEMENTARIES);

        addHaunting(futures, cache, BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(CompatMods.SUPPLEMENTARIES, "ash_bricks")), SupplementariesCompat.SOUL_ASH_BRICKS.base().get(), CompatMods.SUPPLEMENTARIES);
        addHaunting(futures, cache, BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(CompatMods.SUPPLEMENTARIES, "ash_bricks_stairs")), SupplementariesCompat.SOUL_ASH_BRICKS.stairs().get(), CompatMods.SUPPLEMENTARIES);
        addHaunting(futures, cache, BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(CompatMods.SUPPLEMENTARIES, "ash_bricks_slab")), SupplementariesCompat.SOUL_ASH_BRICKS.slab().get(), CompatMods.SUPPLEMENTARIES);
        addHaunting(futures, cache, BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(CompatMods.SUPPLEMENTARIES, "ash_bricks_wall")), SupplementariesCompat.SOUL_ASH_BRICKS.wall().get(), CompatMods.SUPPLEMENTARIES);

        addFamilyHaunting(futures, cache, SupplementariesCompat.WHITE_ASH_TILES, SupplementariesCompat.WHITE_SOUL_ASH_TILES, CompatMods.SUPPLEMENTARIES);
        addFamilyHaunting(futures, cache, SupplementariesCompat.GRAY_ASH_TILES, SupplementariesCompat.GRAY_SOUL_ASH_TILES, CompatMods.SUPPLEMENTARIES);
        addFamilyHaunting(futures, cache, SupplementariesCompat.BLACK_ASH_TILES, SupplementariesCompat.BLACK_SOUL_ASH_TILES, CompatMods.SUPPLEMENTARIES);
        addFamilyHaunting(futures, cache, SupplementariesCompat.ASH_TILES, SupplementariesCompat.SOUL_ASH_TILES, CompatMods.SUPPLEMENTARIES);

        addFamilyHaunting(futures, cache, VanillaBackportCompat.MELTED_RESIN_BRICKS, VanillaBackportCompat.MELTED_SOUL_RESIN_BRICKS, CompatMods.VANILLA_BACKPORT);
        addFamilyHaunting(futures, cache, VanillaBackportCompat.BRIGHT_RESIN_BRICKS, VanillaBackportCompat.BRIGHT_SOUL_RESIN_BRICKS, CompatMods.VANILLA_BACKPORT);
        addFamilyHaunting(futures, cache, VanillaBackportCompat.SMOKED_RESIN_BRICKS, VanillaBackportCompat.SMOKED_SOUL_RESIN_BRICKS, CompatMods.VANILLA_BACKPORT);

        addHaunting(futures, cache, BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("minecraft", "resin_bricks")), VanillaBackportCompat.SOUL_RESIN_BRICKS.base().get(), CompatMods.VANILLA_BACKPORT);
        addHaunting(futures, cache, BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("minecraft", "resin_brick_stairs")), VanillaBackportCompat.SOUL_RESIN_BRICKS.stairs().get(), CompatMods.VANILLA_BACKPORT);
        addHaunting(futures, cache, BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("minecraft", "resin_brick_slab")), VanillaBackportCompat.SOUL_RESIN_BRICKS.slab().get(), CompatMods.VANILLA_BACKPORT);
        addHaunting(futures, cache, BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("minecraft", "resin_brick_wall")), VanillaBackportCompat.SOUL_RESIN_BRICKS.wall().get(), CompatMods.VANILLA_BACKPORT);

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    private void addFamilyHaunting(List<CompletableFuture<?>> futures, CachedOutput cache, ModBlocks.DecorativeFamily input, ModBlocks.DecorativeFamily output) {
        addFamilyHaunting(futures, cache, input, output, null);
    }

    private void addFamilyHaunting(List<CompletableFuture<?>> futures, CachedOutput cache, ModBlocks.DecorativeFamily input, ModBlocks.DecorativeFamily output, String requiredMod) {
        addHaunting(futures, cache, input.base().get(), output.base().get(), requiredMod);
        addHaunting(futures, cache, input.stairs().get(), output.stairs().get(), requiredMod);
        addHaunting(futures, cache, input.slab().get(), output.slab().get(), requiredMod);
        if (input.wall() != null && output.wall() != null) {
            addHaunting(futures, cache, input.wall().get(), output.wall().get(), requiredMod);
        }
        if (input.pillar() != null && output.pillar() != null) {
            addHaunting(futures, cache, input.pillar().get(), output.pillar().get(), requiredMod);
        }
    }

    private void addHaunting(List<CompletableFuture<?>> futures, CachedOutput cache, ItemLike input, ItemLike output) {
        addHaunting(futures, cache, input, output, null);
    }

    private void addHaunting(List<CompletableFuture<?>> futures, CachedOutput cache, ItemLike input, ItemLike output, String requiredMod) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "create:haunting");

        JsonArray ingredients = new JsonArray();
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", BuiltInRegistries.ITEM.getKey(input.asItem()).toString());
        ingredients.add(ingredient);
        json.add("ingredients", ingredients);

        JsonArray results = new JsonArray();
        JsonObject result = new JsonObject();
        result.addProperty("id", BuiltInRegistries.ITEM.getKey(output.asItem()).toString());
        results.add(result);
        json.add("results", results);

        JsonArray conditions = new JsonArray();
        JsonObject createCondition = new JsonObject();
        createCondition.addProperty("condition", "fabric:all_mods_loaded");
        JsonArray values = new JsonArray();
        values.add("create");
        if (requiredMod != null) values.add(requiredMod);
        createCondition.add("values", values);
        conditions.add(createCondition);

        json.add("fabric:load_conditions", conditions);

        String path = BuiltInRegistries.ITEM.getKey(output.asItem()).getPath() + "_haunting";
        ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
        futures.add(DataProvider.saveStable(cache, json, this.pathProvider.json(loc)));
    }

    @Override
    public @NotNull String getName() {
        return "Create Brick Haunting Recipes";
    }
}