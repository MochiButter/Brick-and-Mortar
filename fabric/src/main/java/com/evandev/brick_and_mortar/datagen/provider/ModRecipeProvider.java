package com.evandev.brick_and_mortar.datagen.provider;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.compat.CompatMods;
import com.evandev.brick_and_mortar.compat.SupplementariesCompat;
import com.evandev.brick_and_mortar.compat.VanillaBackportCompat;
import com.evandev.brick_and_mortar.datagen.builder.KilnRecipeBuilder;
import com.evandev.brick_and_mortar.platform.registry.RegistryObject;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.KILN.get())
                .pattern("BBB")
                .pattern("BFB")
                .pattern("BIB")
                .define('B', Items.BRICK)
                .define('I', Items.IRON_INGOT)
                .define('F', Blocks.FURNACE)
                .unlockedBy("has_brick", has(Items.BRICK))
                .save(exporter);

        addFiringSequence(exporter, "clay_bricks", Items.CLAY_BALL, false,
                ModItems.TAN_BRICK.get(),
                ModItems.ORANGE_BRICK.get(),
                Items.BRICK,
                ModItems.BROWN_BRICK.get()
        );
        addFiringSequence(exporter, "clay_bricks", Items.CLAY_BALL, true,
                ModItems.CREAM_BRICK.get(),
                ModItems.GRAY_BRICK.get(),
                ModItems.BLUE_BRICK.get(),
                ModItems.CLINKER_BRICK.get()
        );

        addFiringSequence(exporter, "nether_bricks", Blocks.NETHERRACK, false,
                ModItems.RAW_NETHER_BRICK.get(),
                ModItems.LIGHT_NETHER_BRICK.get(),
                Items.NETHER_BRICK,
                ModItems.CHARRED_NETHER_BRICK.get()
        );
        addFiringSequence(exporter, "nether_bricks", Blocks.NETHERRACK, true,
                ModItems.RAW_SOUL_NETHER_BRICK.get(),
                ModItems.LIGHT_SOUL_NETHER_BRICK.get(),
                ModItems.SOUL_NETHER_BRICK.get(),
                ModItems.CHARRED_SOUL_NETHER_BRICK.get()
        );

        addFiringSequence(exporter, "purpur_blocks", Items.CHORUS_FRUIT, false,
                ModItems.RAW_POPPED_CHORUS.get(),
                ModItems.LIGHT_POPPED_CHORUS.get(),
                Items.POPPED_CHORUS_FRUIT,
                ModItems.BURNT_POPPED_CHORUS.get()
        );
        addFiringSequence(exporter, "purpur_blocks", Items.CHORUS_FRUIT, true,
                ModItems.RAW_SOUL_POPPED_CHORUS.get(),
                ModItems.LIGHT_SOUL_POPPED_CHORUS.get(),
                ModItems.SOUL_POPPED_CHORUS.get(),
                ModItems.BURNT_SOUL_POPPED_CHORUS.get()
        );

        addTileRecipe(exporter, ModBlocks.BLACK_BRICKS.base().get(), ModBlocks.BLACK_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.BLUE_BRICKS.base().get(), ModBlocks.BLUE_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.BROWN_BRICKS.base().get(), ModBlocks.BROWN_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.CREAM_BRICKS.base().get(), ModBlocks.CREAM_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.GRAY_BRICKS.base().get(), ModBlocks.GRAY_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.ORANGE_BRICKS.base().get(), ModBlocks.ORANGE_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.TAN_BRICKS.base().get(), ModBlocks.TAN_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.CHARRED_NETHER_BRICKS.base().get(), ModBlocks.CHARRED_NETHER_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.CHARRED_SOUL_NETHER_BRICKS.base().get(), ModBlocks.CHARRED_SOUL_NETHER_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.LIGHT_NETHER_BRICKS.base().get(), ModBlocks.LIGHT_NETHER_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.LIGHT_SOUL_NETHER_BRICKS.base().get(), ModBlocks.LIGHT_SOUL_NETHER_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.RAW_NETHER_BRICKS.base().get(), ModBlocks.RAW_NETHER_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.RAW_SOUL_NETHER_BRICKS.base().get(), ModBlocks.RAW_SOUL_NETHER_TILES.base().get());
        addTileRecipe(exporter, ModBlocks.SOUL_NETHER_BRICKS.base().get(), ModBlocks.SOUL_NETHER_TILES.base().get());

        addTileRecipe(exporter, Blocks.BRICKS, ModBlocks.BRICK_TILES.base().get());
        addTileRecipe(exporter, Blocks.NETHER_BRICKS, ModBlocks.NETHER_TILES.base().get());

        addBlockRecipe(exporter, ModItems.CLINKER_BRICK.get(), ModBlocks.BLACK_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.BLUE_BRICK.get(), ModBlocks.BLUE_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.BROWN_BRICK.get(), ModBlocks.BROWN_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.CREAM_BRICK.get(), ModBlocks.CREAM_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.GRAY_BRICK.get(), ModBlocks.GRAY_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.ORANGE_BRICK.get(), ModBlocks.ORANGE_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.TAN_BRICK.get(), ModBlocks.TAN_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.SOUL_NETHER_BRICK.get(), ModBlocks.SOUL_NETHER_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.CHARRED_NETHER_BRICK.get(), ModBlocks.CHARRED_NETHER_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.CHARRED_SOUL_NETHER_BRICK.get(), ModBlocks.CHARRED_SOUL_NETHER_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.LIGHT_NETHER_BRICK.get(), ModBlocks.LIGHT_NETHER_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.LIGHT_SOUL_NETHER_BRICK.get(), ModBlocks.LIGHT_SOUL_NETHER_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.RAW_NETHER_BRICK.get(), ModBlocks.RAW_NETHER_BRICKS.base().get());
        addBlockRecipe(exporter, ModItems.RAW_SOUL_NETHER_BRICK.get(), ModBlocks.RAW_SOUL_NETHER_BRICKS.base().get());

        addBlockRecipe(exporter, ModItems.RAW_POPPED_CHORUS.get(), ModBlocks.RAW_PURPUR_BLOCK.base().get(), 4);
        addBlockRecipe(exporter, ModItems.LIGHT_POPPED_CHORUS.get(), ModBlocks.LIGHT_PURPUR_BLOCK.base().get(), 4);
        addBlockRecipe(exporter, ModItems.BURNT_POPPED_CHORUS.get(), ModBlocks.BURNT_PURPUR_BLOCK.base().get(), 4);
        addBlockRecipe(exporter, ModItems.RAW_SOUL_POPPED_CHORUS.get(), ModBlocks.RAW_SOUL_PURPUR_BLOCK.base().get(), 4);
        addBlockRecipe(exporter, ModItems.LIGHT_SOUL_POPPED_CHORUS.get(), ModBlocks.LIGHT_SOUL_PURPUR_BLOCK.base().get(), 4);
        addBlockRecipe(exporter, ModItems.SOUL_POPPED_CHORUS.get(), ModBlocks.SOUL_PURPUR_BLOCK.base().get(), 4);
        addBlockRecipe(exporter, ModItems.BURNT_SOUL_POPPED_CHORUS.get(), ModBlocks.BURNT_SOUL_PURPUR_BLOCK.base().get(), 4);

        addPurpurBrickAndChiseledRecipes(exporter, Blocks.PURPUR_BLOCK, ModBlocks.PURPUR_BRICKS, ModBlocks.CHISELED_PURPUR);
        addPurpurBrickAndChiseledRecipes(exporter, ModBlocks.BURNT_PURPUR_BLOCK.base().get(), ModBlocks.BURNT_PURPUR_BRICKS, ModBlocks.CHISELED_BURNT_PURPUR);
        addPurpurBrickAndChiseledRecipes(exporter, ModBlocks.BURNT_SOUL_PURPUR_BLOCK.base().get(), ModBlocks.BURNT_SOUL_PURPUR_BRICKS, ModBlocks.CHISELED_BURNT_SOUL_PURPUR);
        addPurpurBrickAndChiseledRecipes(exporter, ModBlocks.LIGHT_PURPUR_BLOCK.base().get(), ModBlocks.LIGHT_PURPUR_BRICKS, ModBlocks.CHISELED_LIGHT_PURPUR);
        addPurpurBrickAndChiseledRecipes(exporter, ModBlocks.LIGHT_SOUL_PURPUR_BLOCK.base().get(), ModBlocks.LIGHT_SOUL_PURPUR_BRICKS, ModBlocks.CHISELED_LIGHT_SOUL_PURPUR);
        addPurpurBrickAndChiseledRecipes(exporter, ModBlocks.RAW_PURPUR_BLOCK.base().get(), ModBlocks.RAW_PURPUR_BRICKS, ModBlocks.CHISELED_RAW_PURPUR);
        addPurpurBrickAndChiseledRecipes(exporter, ModBlocks.RAW_SOUL_PURPUR_BLOCK.base().get(), ModBlocks.RAW_SOUL_PURPUR_BRICKS, ModBlocks.CHISELED_RAW_SOUL_PURPUR);
        addPurpurBrickAndChiseledRecipes(exporter, ModBlocks.SOUL_PURPUR_BLOCK.base().get(), ModBlocks.SOUL_PURPUR_BRICKS, ModBlocks.CHISELED_SOUL_PURPUR);

        addStoneSmelting(exporter, "stone_from_cobblestone", Ingredient.of(Blocks.COBBLESTONE), Blocks.STONE, 0.1F);
        addStoneSmelting(exporter, "smooth_stone", Ingredient.of(Blocks.STONE), Blocks.SMOOTH_STONE, 0.1F);
        addStoneSmelting(exporter, "glass", Ingredient.of(ItemTags.SMELTS_TO_GLASS), Blocks.GLASS, 0.1F);
        addStoneSmelting(exporter, "smooth_sandstone", Ingredient.of(Blocks.SANDSTONE), Blocks.SMOOTH_SANDSTONE, 0.1F);
        addStoneSmelting(exporter, "smooth_red_sandstone", Ingredient.of(Blocks.RED_SANDSTONE), Blocks.SMOOTH_RED_SANDSTONE, 0.1F);
        addStoneSmelting(exporter, "deepslate_from_cobbled", Ingredient.of(Blocks.COBBLED_DEEPSLATE), Blocks.DEEPSLATE, 0.1F);
        addStoneSmelting(exporter, "terracotta_from_clay_block", Ingredient.of(Blocks.CLAY), Blocks.TERRACOTTA, 0.35F);
        addStoneSmelting(exporter, "smooth_quartz", Ingredient.of(Blocks.QUARTZ_BLOCK), Blocks.SMOOTH_QUARTZ, 0.1F);
        addStoneSmelting(exporter, "smooth_basalt", Ingredient.of(Blocks.BASALT), Blocks.SMOOTH_BASALT, 0.1F);

        addStoneSmelting(exporter, "cracked_stone_bricks", Ingredient.of(Blocks.STONE_BRICKS), Blocks.CRACKED_STONE_BRICKS, 0.1F);
        addStoneSmelting(exporter, "cracked_nether_bricks", Ingredient.of(Blocks.NETHER_BRICKS), Blocks.CRACKED_NETHER_BRICKS, 0.1F);
        addStoneSmelting(exporter, "cracked_deepslate_bricks", Ingredient.of(Blocks.DEEPSLATE_BRICKS), Blocks.CRACKED_DEEPSLATE_BRICKS, 0.1F);
        addStoneSmelting(exporter, "cracked_deepslate_tiles", Ingredient.of(Blocks.DEEPSLATE_TILES), Blocks.CRACKED_DEEPSLATE_TILES, 0.1F);
        addStoneSmelting(exporter, "cracked_polished_blackstone_bricks", Ingredient.of(Blocks.POLISHED_BLACKSTONE_BRICKS), Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, 0.1F);

        addStoneSmelting(exporter, "white_glazed_terracotta", Ingredient.of(Blocks.WHITE_TERRACOTTA), Blocks.WHITE_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "orange_glazed_terracotta", Ingredient.of(Blocks.ORANGE_TERRACOTTA), Blocks.ORANGE_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "magenta_glazed_terracotta", Ingredient.of(Blocks.MAGENTA_TERRACOTTA), Blocks.MAGENTA_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "light_blue_glazed_terracotta", Ingredient.of(Blocks.LIGHT_BLUE_TERRACOTTA), Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "yellow_glazed_terracotta", Ingredient.of(Blocks.YELLOW_TERRACOTTA), Blocks.YELLOW_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "lime_glazed_terracotta", Ingredient.of(Blocks.LIME_TERRACOTTA), Blocks.LIME_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "pink_glazed_terracotta", Ingredient.of(Blocks.PINK_TERRACOTTA), Blocks.PINK_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "gray_glazed_terracotta", Ingredient.of(Blocks.GRAY_TERRACOTTA), Blocks.GRAY_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "light_gray_glazed_terracotta", Ingredient.of(Blocks.LIGHT_GRAY_TERRACOTTA), Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "cyan_glazed_terracotta", Ingredient.of(Blocks.CYAN_TERRACOTTA), Blocks.CYAN_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "purple_glazed_terracotta", Ingredient.of(Blocks.PURPLE_TERRACOTTA), Blocks.PURPLE_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "blue_glazed_terracotta", Ingredient.of(Blocks.BLUE_TERRACOTTA), Blocks.BLUE_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "brown_glazed_terracotta", Ingredient.of(Blocks.BROWN_TERRACOTTA), Blocks.BROWN_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "green_glazed_terracotta", Ingredient.of(Blocks.GREEN_TERRACOTTA), Blocks.GREEN_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "red_glazed_terracotta", Ingredient.of(Blocks.RED_TERRACOTTA), Blocks.RED_GLAZED_TERRACOTTA, 0.1F);
        addStoneSmelting(exporter, "black_glazed_terracotta", Ingredient.of(Blocks.BLACK_TERRACOTTA), Blocks.BLACK_GLAZED_TERRACOTTA, 0.1F);

        for (var family : ModBlocks.FAMILIES) {
            Block base = family.base().get();
            Block stairs = family.stairs().get();
            Block slab = family.slab().get();

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                    .pattern("B  ").pattern("BB ").pattern("BBB").define('B', base).unlockedBy("has_base", has(base)).save(exporter);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                    .pattern("BBB").define('B', base).unlockedBy("has_base", has(base)).save(exporter);

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, stairs, 1)
                    .unlockedBy("has_base", has(base)).save(exporter, new ResourceLocation(Constants.MOD_ID, family.stairs().getId().getPath() + "_stonecutting"));
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, slab, 2)
                    .unlockedBy("has_base", has(base)).save(exporter, new ResourceLocation(Constants.MOD_ID, family.slab().getId().getPath() + "_stonecutting"));

            if (family.wall() != null) {
                Block wall = family.wall().get();
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, wall, 1)
                        .unlockedBy("has_base", has(base)).save(exporter, new ResourceLocation(Constants.MOD_ID, family.wall().getId().getPath() + "_stonecutting"));
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
                        .pattern("BBB").pattern("BBB").define('B', base).unlockedBy("has_base", has(base)).save(exporter);
            }

            if (family.pillar() != null) {
                Block pillar = family.pillar().get();
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, pillar, 1)
                        .unlockedBy("has_base", has(base)).save(exporter, new ResourceLocation(Constants.MOD_ID, family.pillar().getId().getPath() + "_stonecutting"));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pillar, 1)
                        .pattern("S")
                        .pattern("S")
                        .define('S', slab)
                        .unlockedBy("has_slab", has(slab))
                        .save(exporter);
            }
        }

        buildSupplementariesCompat(exporter);
        buildVanillaBackportCompat(exporter);
    }

    private void buildSupplementariesCompat(Consumer<FinishedRecipe> exporter) {
        Consumer<FinishedRecipe> suppExporter = this.withConditions(exporter, DefaultResourceConditions.allModsLoaded(CompatMods.SUPPLEMENTARIES));

        Item suppAshBrick = BuiltInRegistries.ITEM.get(new ResourceLocation(CompatMods.SUPPLEMENTARIES, "ash_brick"));
        Item suppAsh = BuiltInRegistries.ITEM.get(new ResourceLocation(CompatMods.SUPPLEMENTARIES, "ash"));

        addFiringSequence(suppExporter, "ash_bricks", suppAsh, false,
                SupplementariesCompat.WHITE_ASH_BRICK.get(),
                SupplementariesCompat.GRAY_ASH_BRICK.get(),
                suppAshBrick,
                SupplementariesCompat.BLACK_ASH_BRICK.get()
        );

        addFiringSequence(suppExporter, "ash_bricks", suppAsh, true,
                SupplementariesCompat.WHITE_SOUL_ASH_BRICK.get(),
                SupplementariesCompat.GRAY_SOUL_ASH_BRICK.get(),
                SupplementariesCompat.SOUL_ASH_BRICK.get(),
                SupplementariesCompat.BLACK_SOUL_ASH_BRICK.get()
        );

        addBlockRecipe(suppExporter, SupplementariesCompat.WHITE_ASH_BRICK.get(), SupplementariesCompat.WHITE_ASH_BRICKS.base().get());
        addBlockRecipe(suppExporter, SupplementariesCompat.GRAY_ASH_BRICK.get(), SupplementariesCompat.GRAY_ASH_BRICKS.base().get());
        addBlockRecipe(suppExporter, SupplementariesCompat.BLACK_ASH_BRICK.get(), SupplementariesCompat.BLACK_ASH_BRICKS.base().get());

        addBlockRecipe(suppExporter, SupplementariesCompat.SOUL_ASH_BRICK.get(), SupplementariesCompat.SOUL_ASH_BRICKS.base().get());
        addBlockRecipe(suppExporter, SupplementariesCompat.WHITE_SOUL_ASH_BRICK.get(), SupplementariesCompat.WHITE_SOUL_ASH_BRICKS.base().get());
        addBlockRecipe(suppExporter, SupplementariesCompat.GRAY_SOUL_ASH_BRICK.get(), SupplementariesCompat.GRAY_SOUL_ASH_BRICKS.base().get());
        addBlockRecipe(suppExporter, SupplementariesCompat.BLACK_SOUL_ASH_BRICK.get(), SupplementariesCompat.BLACK_SOUL_ASH_BRICKS.base().get());

        Block suppAshBricks = BuiltInRegistries.BLOCK.get(new ResourceLocation(CompatMods.SUPPLEMENTARIES, "ash_bricks"));
        addTileRecipe(suppExporter, suppAshBricks, SupplementariesCompat.ASH_TILES.base().get());

        addTileRecipe(suppExporter, SupplementariesCompat.WHITE_ASH_BRICKS.base().get(), SupplementariesCompat.WHITE_ASH_TILES.base().get());
        addTileRecipe(suppExporter, SupplementariesCompat.GRAY_ASH_BRICKS.base().get(), SupplementariesCompat.GRAY_ASH_TILES.base().get());
        addTileRecipe(suppExporter, SupplementariesCompat.BLACK_ASH_BRICKS.base().get(), SupplementariesCompat.BLACK_ASH_TILES.base().get());

        addTileRecipe(suppExporter, SupplementariesCompat.SOUL_ASH_BRICKS.base().get(), SupplementariesCompat.SOUL_ASH_TILES.base().get());
        addTileRecipe(suppExporter, SupplementariesCompat.WHITE_SOUL_ASH_BRICKS.base().get(), SupplementariesCompat.WHITE_SOUL_ASH_TILES.base().get());
        addTileRecipe(suppExporter, SupplementariesCompat.GRAY_SOUL_ASH_BRICKS.base().get(), SupplementariesCompat.GRAY_SOUL_ASH_TILES.base().get());
        addTileRecipe(suppExporter, SupplementariesCompat.BLACK_SOUL_ASH_BRICKS.base().get(), SupplementariesCompat.BLACK_SOUL_ASH_TILES.base().get());
    }

    private void buildVanillaBackportCompat(Consumer<FinishedRecipe> exporter) {
        Consumer<FinishedRecipe> vbExporter = this.withConditions(exporter, DefaultResourceConditions.allModsLoaded(CompatMods.VANILLA_BACKPORT));

        Item resinClump = BuiltInRegistries.ITEM.get(new ResourceLocation("minecraft", "resin_clump"));
        Item resinBrick = BuiltInRegistries.ITEM.get(new ResourceLocation("minecraft", "resin_brick"));

        addFiringSequence(vbExporter, "resin_bricks", resinClump, false,
                VanillaBackportCompat.MELTED_RESIN_BRICK.get(),
                VanillaBackportCompat.BRIGHT_RESIN_BRICK.get(),
                resinBrick,
                VanillaBackportCompat.SMOKED_RESIN_BRICK.get()
        );

        addFiringSequence(vbExporter, "resin_bricks", resinClump, true,
                VanillaBackportCompat.MELTED_SOUL_RESIN_BRICK.get(),
                VanillaBackportCompat.BRIGHT_SOUL_RESIN_BRICK.get(),
                VanillaBackportCompat.SOUL_RESIN_BRICK.get(),
                VanillaBackportCompat.SMOKED_SOUL_RESIN_BRICK.get()
        );

        addBlockRecipe(vbExporter, VanillaBackportCompat.MELTED_RESIN_BRICK.get(), VanillaBackportCompat.MELTED_RESIN_BRICKS.base().get());
        addBlockRecipe(vbExporter, VanillaBackportCompat.BRIGHT_RESIN_BRICK.get(), VanillaBackportCompat.BRIGHT_RESIN_BRICKS.base().get());
        addBlockRecipe(vbExporter, VanillaBackportCompat.SMOKED_RESIN_BRICK.get(), VanillaBackportCompat.SMOKED_RESIN_BRICKS.base().get());

        addBlockRecipe(vbExporter, VanillaBackportCompat.MELTED_SOUL_RESIN_BRICK.get(), VanillaBackportCompat.MELTED_SOUL_RESIN_BRICKS.base().get());
        addBlockRecipe(vbExporter, VanillaBackportCompat.BRIGHT_SOUL_RESIN_BRICK.get(), VanillaBackportCompat.BRIGHT_SOUL_RESIN_BRICKS.base().get());
        addBlockRecipe(vbExporter, VanillaBackportCompat.SOUL_RESIN_BRICK.get(), VanillaBackportCompat.SOUL_RESIN_BRICKS.base().get());
        addBlockRecipe(vbExporter, VanillaBackportCompat.SMOKED_SOUL_RESIN_BRICK.get(), VanillaBackportCompat.SMOKED_SOUL_RESIN_BRICKS.base().get());
    }

    private void addPurpurBrickAndChiseledRecipes(Consumer<FinishedRecipe> exporter, Block base, ModBlocks.DecorativeFamily brickFamily, RegistryObject<Block> chiseledBlock) {
        Block bricks = brickFamily.base().get();
        Block brickSlab = brickFamily.slab().get();
        Block chiseled = chiseledBlock.get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bricks, 4)
                .pattern("BB")
                .pattern("BB")
                .define('B', base)
                .unlockedBy("has_base", has(base))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, chiseled, 1)
                .pattern("S")
                .pattern("S")
                .define('S', brickSlab)
                .unlockedBy("has_slab", has(brickSlab))
                .save(exporter);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, bricks, 1)
                .unlockedBy("has_base", has(base))
                .save(exporter, new ResourceLocation(Constants.MOD_ID, brickFamily.base().getId().getPath() + "_from_base_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, chiseled, 1)
                .unlockedBy("has_base", has(base))
                .save(exporter, new ResourceLocation(Constants.MOD_ID, chiseledBlock.getId().getPath() + "_from_base_stonecutting"));
    }

    /**
     * Helper method to generate a full progression tier of Kiln recipes
     */
    private void addFiringSequence(Consumer<FinishedRecipe> exporter, String sequenceName, ItemLike input, boolean requiresSoulBase, ItemLike... tiers) {
        for (int doors = 0; doors < tiers.length; doors++) {
            ItemLike output = tiers[doors];

            if (output == null) continue;

            if (!requiresSoulBase && input.asItem() == output.asItem()) continue;

            KilnRecipeBuilder builder = KilnRecipeBuilder.firing(Ingredient.of(input), new ItemStack(output))
                    .requiredDoors(doors)
                    .unlockedBy("has_input", has(input));

            String name = sequenceName;
            if (requiresSoulBase) {
                builder.requiresSoulBase();
                name += "_soul";
            }

            ResourceLocation id = new ResourceLocation(Constants.MOD_ID, "kiln_firing/" + name + "_tier_" + doors);
            builder.save(exporter, id);
        }
    }

    /**
     * Helper method to generate fast, 0-door Kiln smelting recipes.
     */
    private void addStoneSmelting(Consumer<FinishedRecipe> exporter, String name, Ingredient input, ItemLike output, float experience) {
        KilnRecipeBuilder.firing(input, output.asItem())
                .cookingTime(100)
                .experience(experience)
                .requiredDoors(0)
                .unlockedBy("has_input", has(output.asItem()))
                .save(exporter, new ResourceLocation(Constants.MOD_ID, "kiln_firing/" + name));
    }

    private void addTileRecipe(Consumer<FinishedRecipe> exporter, Block input, Block output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("BB")
                .pattern("BB")
                .define('B', input)
                .unlockedBy("has_brick_block", has(input))
                .save(exporter);
    }

    private void addBlockRecipe(Consumer<FinishedRecipe> exporter, Item input, Block output) {
        addBlockRecipe(exporter, input, output, 1);
    }

    private void addBlockRecipe(Consumer<FinishedRecipe> exporter, Item input, Block output, int count) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("II")
                .pattern("II")
                .define('I', input)
                .unlockedBy("has_brick_item", has(input))
                .save(exporter);
    }
}