package com.evandev.brick_and_mortar.datagen.provider;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.datagen.builder.KilnRecipeBuilder;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.KILN.get())
                .pattern("BBB")
                .pattern("BFB")
                .pattern("BIB")
                .define('B', ModItemTagProvider.C_BRICKS)
                .define('I', Items.IRON_INGOT)
                .define('F', Blocks.FURNACE)
                .unlockedBy("has_brick", has(ModItemTagProvider.C_BRICKS))
                .save(exporter);

        addFiringSequence(exporter, "clay_bricks", Items.CLAY_BALL, null,
                ModItems.TAN_BRICK.get(),
                ModItems.ORANGE_BRICK.get(),
                Items.BRICK,
                ModItems.BROWN_BRICK.get()
        );
        addFiringSequence(exporter, "clay_bricks", Items.CLAY_BALL, Blocks.SOUL_SAND,
                ModItems.CREAM_BRICK.get(),
                ModItems.GRAY_BRICK.get(),
                ModItems.BLUE_BRICK.get(),
                ModItems.CLINKER_BRICK.get()
        );

        addFiringSequence(exporter, "nether_bricks", Blocks.NETHERRACK, null,
                ModItems.RAW_NETHER_BRICK.get(),
                ModItems.LIGHT_NETHER_BRICK.get(),
                Items.NETHER_BRICK,
                ModItems.CHARRED_NETHER_BRICK.get()
        );
        addFiringSequence(exporter, "nether_bricks", Blocks.NETHERRACK, Blocks.SOUL_SAND,
                ModItems.RAW_SOUL_NETHER_BRICK.get(),
                ModItems.LIGHT_SOUL_NETHER_BRICK.get(),
                ModItems.SOUL_NETHER_BRICK.get(),
                ModItems.CHARRED_SOUL_NETHER_BRICK.get()
        );

        addFiringSequence(exporter, "purpur_blocks", Items.CHORUS_FRUIT, null,
                ModItems.RAW_POPPED_CHORUS.get(),
                ModItems.LIGHT_POPPED_CHORUS.get(),
                Items.POPPED_CHORUS_FRUIT,
                ModItems.BURNT_POPPED_CHORUS.get()
        );
        addFiringSequence(exporter, "purpur_blocks", Items.CHORUS_FRUIT, Blocks.SOUL_SAND,
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

        addTileRecipe(exporter, Blocks.BRICKS, ModBlocks.RED_TILES.base().get());
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

        addBlockRecipe(exporter, ModItems.RAW_POPPED_CHORUS.get(), ModBlocks.RAW_PURPUR_BLOCK.base().get());
        addBlockRecipe(exporter, ModItems.LIGHT_POPPED_CHORUS.get(), ModBlocks.LIGHT_PURPUR_BLOCK.base().get());
        addBlockRecipe(exporter, ModItems.BURNT_POPPED_CHORUS.get(), ModBlocks.BURNT_PURPUR_BLOCK.base().get());
        addBlockRecipe(exporter, ModItems.RAW_SOUL_POPPED_CHORUS.get(), ModBlocks.RAW_SOUL_PURPUR_BLOCK.base().get());
        addBlockRecipe(exporter, ModItems.LIGHT_SOUL_POPPED_CHORUS.get(), ModBlocks.LIGHT_SOUL_PURPUR_BLOCK.base().get());
        addBlockRecipe(exporter, ModItems.SOUL_POPPED_CHORUS.get(), ModBlocks.SOUL_PURPUR_BLOCK.base().get());
        addBlockRecipe(exporter, ModItems.BURNT_SOUL_POPPED_CHORUS.get(), ModBlocks.BURNT_SOUL_PURPUR_BLOCK.base().get());

        for (var family : ModBlocks.FAMILIES) {
            Block base = family.base().get();
            Block stairs = family.stairs().get();
            Block slab = family.slab().get();

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                    .pattern("B  ").pattern("BB ").pattern("BBB").define('B', base).unlockedBy("has_base", has(base)).save(exporter);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                    .pattern("BBB").define('B', base).unlockedBy("has_base", has(base)).save(exporter);

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, stairs, 1)
                    .unlockedBy("has_base", has(base)).save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, family.stairs().getId().getPath() + "_stonecutting"));
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, slab, 2)
                    .unlockedBy("has_base", has(base)).save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, family.slab().getId().getPath() + "_stonecutting"));


            if (family.wall() != null) {
                Block wall = family.wall().get();
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, wall, 1)
                        .unlockedBy("has_base", has(base)).save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, family.wall().getId().getPath() + "_stonecutting"));
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
                        .pattern("BBB").pattern("BBB").define('B', base).unlockedBy("has_base", has(base)).save(exporter);
            }

            if (family.pillar() != null) {
                Block pillar = family.pillar().get();
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, pillar, 1)
                        .unlockedBy("has_base", has(base)).save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, family.pillar().getId().getPath() + "_stonecutting"));
            }
        }
    }

    /**
     * Helper method to generate a full progression tier of Kiln recipes
     */
    private void addFiringSequence(RecipeOutput exporter, String sequenceName, ItemLike input, Block baseModifier, ItemLike... tiers) {
        for (int doors = 0; doors < tiers.length; doors++) {
            ItemLike output = tiers[doors];

            if (output == null) continue;

            if (baseModifier == null && input.asItem() == output.asItem()) continue;

            var builder = KilnRecipeBuilder.firing(Ingredient.of(input), new ItemStack(output))
                    .requiredDoors(doors)
                    .unlockedBy("has_input", has(input));

            String name = sequenceName;
            if (baseModifier != null) {
                builder.baseBlock(baseModifier);
                name += "_soul";
            }

            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "kiln_firing/" + name + "_tier_" + doors);
            builder.save(exporter, id);
        }
    }

    private void addTileRecipe(RecipeOutput exporter, Block input, Block output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("BB")
                .pattern("BB")
                .define('B', input)
                .unlockedBy("has_brick_block", has(input))
                .save(exporter);
    }

    private void addBlockRecipe(RecipeOutput exporter, Item input, Block output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("II")
                .pattern("II")
                .define('I', input)
                .unlockedBy("has_brick_item", has(input))
                .save(exporter);
    }
}