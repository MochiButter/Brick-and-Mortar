package com.evandev.brick_and_mortar.datagen.provider;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.compat.SupplementariesCompat;
import com.evandev.brick_and_mortar.compat.VanillaBackportCompat;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> C_BRICKS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "bricks"));

    public static final TagKey<Item> ASH_MASONRY = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ash_masonry"));
    public static final TagKey<Item> SOUL_ASH_MASONRY = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "soul_ash_masonry"));
    public static final TagKey<Item> NETHER_MASONRY = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "nether_masonry"));
    public static final TagKey<Item> SOUL_NETHER_MASONRY = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "soul_nether_masonry"));
    public static final TagKey<Item> PURPUR_MASONRY = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "purpur_masonry"));
    public static final TagKey<Item> SOUL_PURPUR_MASONRY = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "soul_purpur_masonry"));
    public static final TagKey<Item> RESIN_MASONRY = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "resin_masonry"));
    public static final TagKey<Item> SOUL_RESIN_MASONRY = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "soul_resin_masonry"));

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        var bricksTag = getOrCreateTagBuilder(C_BRICKS);
        for (var itemObj : ModItems.ALL_BRICK_ITEMS) {
            bricksTag.addOptional(itemObj.getId());
        }

        var stairsTag = getOrCreateTagBuilder(ItemTags.STAIRS);
        var slabsTag = getOrCreateTagBuilder(ItemTags.SLABS);
        var wallsTag = getOrCreateTagBuilder(ItemTags.WALLS);

        for (var family : ModBlocks.FAMILIES) {
            stairsTag.addOptional(family.stairs().getId());
            slabsTag.addOptional(family.slab().getId());
            if (family.wall() != null) wallsTag.addOptional(family.wall().getId());
        }

        var ash = getOrCreateTagBuilder(ASH_MASONRY)
                .addOptional(SupplementariesCompat.WHITE_ASH_BRICK.getId())
                .addOptional(SupplementariesCompat.GRAY_ASH_BRICK.getId())
                .addOptional(SupplementariesCompat.BLACK_ASH_BRICK.getId());
        addFamiliesToTag(ash, SupplementariesCompat.WHITE_ASH_BRICKS, SupplementariesCompat.GRAY_ASH_BRICKS, SupplementariesCompat.BLACK_ASH_BRICKS, SupplementariesCompat.ASH_TILES, SupplementariesCompat.WHITE_ASH_TILES, SupplementariesCompat.GRAY_ASH_TILES, SupplementariesCompat.BLACK_ASH_TILES);

        var soulAsh = getOrCreateTagBuilder(SOUL_ASH_MASONRY)
                .addOptional(SupplementariesCompat.SOUL_ASH_BRICK.getId())
                .addOptional(SupplementariesCompat.WHITE_SOUL_ASH_BRICK.getId())
                .addOptional(SupplementariesCompat.GRAY_SOUL_ASH_BRICK.getId())
                .addOptional(SupplementariesCompat.BLACK_SOUL_ASH_BRICK.getId());
        addFamiliesToTag(soulAsh, SupplementariesCompat.SOUL_ASH_BRICKS, SupplementariesCompat.WHITE_SOUL_ASH_BRICKS, SupplementariesCompat.GRAY_SOUL_ASH_BRICKS, SupplementariesCompat.BLACK_SOUL_ASH_BRICKS, SupplementariesCompat.SOUL_ASH_TILES, SupplementariesCompat.WHITE_SOUL_ASH_TILES, SupplementariesCompat.GRAY_SOUL_ASH_TILES, SupplementariesCompat.BLACK_SOUL_ASH_TILES);

        var nether = getOrCreateTagBuilder(NETHER_MASONRY)
                .addOptional(ModItems.RAW_NETHER_BRICK.getId())
                .addOptional(ModItems.LIGHT_NETHER_BRICK.getId())
                .addOptional(ModItems.CHARRED_NETHER_BRICK.getId());
        addFamiliesToTag(nether, ModBlocks.RAW_NETHER_BRICKS, ModBlocks.LIGHT_NETHER_BRICKS, ModBlocks.CHARRED_NETHER_BRICKS, ModBlocks.RAW_NETHER_TILES, ModBlocks.LIGHT_NETHER_TILES, ModBlocks.CHARRED_NETHER_TILES, ModBlocks.NETHER_TILES);

        var soulNether = getOrCreateTagBuilder(SOUL_NETHER_MASONRY)
                .addOptional(ModItems.SOUL_NETHER_BRICK.getId())
                .addOptional(ModItems.RAW_SOUL_NETHER_BRICK.getId())
                .addOptional(ModItems.LIGHT_SOUL_NETHER_BRICK.getId())
                .addOptional(ModItems.CHARRED_SOUL_NETHER_BRICK.getId());
        addFamiliesToTag(soulNether, ModBlocks.SOUL_NETHER_BRICKS, ModBlocks.RAW_SOUL_NETHER_BRICKS, ModBlocks.LIGHT_SOUL_NETHER_BRICKS, ModBlocks.CHARRED_SOUL_NETHER_BRICKS, ModBlocks.SOUL_NETHER_TILES, ModBlocks.RAW_SOUL_NETHER_TILES, ModBlocks.LIGHT_SOUL_NETHER_TILES, ModBlocks.CHARRED_SOUL_NETHER_TILES);

        var purpur = getOrCreateTagBuilder(PURPUR_MASONRY)
                .addOptional(ModItems.RAW_POPPED_CHORUS.getId())
                .addOptional(ModItems.LIGHT_POPPED_CHORUS.getId())
                .addOptional(ModItems.BURNT_POPPED_CHORUS.getId());
        addFamiliesToTag(purpur, ModBlocks.RAW_PURPUR_BLOCK, ModBlocks.LIGHT_PURPUR_BLOCK, ModBlocks.BURNT_PURPUR_BLOCK, ModBlocks.PURPUR_BRICKS, ModBlocks.RAW_PURPUR_BRICKS, ModBlocks.LIGHT_PURPUR_BRICKS, ModBlocks.BURNT_PURPUR_BRICKS);

        var soulPurpur = getOrCreateTagBuilder(SOUL_PURPUR_MASONRY)
                .addOptional(ModItems.SOUL_POPPED_CHORUS.getId())
                .addOptional(ModItems.RAW_SOUL_POPPED_CHORUS.getId())
                .addOptional(ModItems.LIGHT_SOUL_POPPED_CHORUS.getId())
                .addOptional(ModItems.BURNT_SOUL_POPPED_CHORUS.getId());
        addFamiliesToTag(soulPurpur, ModBlocks.SOUL_PURPUR_BLOCK, ModBlocks.RAW_SOUL_PURPUR_BLOCK, ModBlocks.LIGHT_SOUL_PURPUR_BLOCK, ModBlocks.BURNT_SOUL_PURPUR_BLOCK, ModBlocks.SOUL_PURPUR_BRICKS, ModBlocks.RAW_SOUL_PURPUR_BRICKS, ModBlocks.LIGHT_SOUL_PURPUR_BRICKS, ModBlocks.BURNT_SOUL_PURPUR_BRICKS);

        var resin = getOrCreateTagBuilder(RESIN_MASONRY)
                .addOptional(VanillaBackportCompat.MELTED_RESIN_BRICK.getId())
                .addOptional(VanillaBackportCompat.BRIGHT_RESIN_BRICK.getId())
                .addOptional(VanillaBackportCompat.SMOKED_RESIN_BRICK.getId());
        addFamiliesToTag(resin, VanillaBackportCompat.MELTED_RESIN_BRICKS, VanillaBackportCompat.BRIGHT_RESIN_BRICKS, VanillaBackportCompat.SMOKED_RESIN_BRICKS);

        var soulResin = getOrCreateTagBuilder(SOUL_RESIN_MASONRY)
                .addOptional(VanillaBackportCompat.SOUL_RESIN_BRICK.getId())
                .addOptional(VanillaBackportCompat.MELTED_SOUL_RESIN_BRICK.getId())
                .addOptional(VanillaBackportCompat.BRIGHT_SOUL_RESIN_BRICK.getId())
                .addOptional(VanillaBackportCompat.SMOKED_SOUL_RESIN_BRICK.getId());
        addFamiliesToTag(soulResin, VanillaBackportCompat.SOUL_RESIN_BRICKS, VanillaBackportCompat.MELTED_SOUL_RESIN_BRICKS, VanillaBackportCompat.BRIGHT_SOUL_RESIN_BRICKS, VanillaBackportCompat.SMOKED_SOUL_RESIN_BRICKS);
    }

    private void addFamiliesToTag(FabricTagProvider<Item>.FabricTagBuilder builder, ModBlocks.DecorativeFamily... families) {
        for (var family : families) {
            builder.addOptional(family.base().getId());
            builder.addOptional(family.stairs().getId());
            builder.addOptional(family.slab().getId());
            if (family.wall() != null) builder.addOptional(family.wall().getId());
            if (family.pillar() != null) builder.addOptional(family.pillar().getId());
        }
    }
}