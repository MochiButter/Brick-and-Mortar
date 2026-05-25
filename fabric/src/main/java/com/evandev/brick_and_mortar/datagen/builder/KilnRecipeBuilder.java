package com.evandev.brick_and_mortar.datagen.builder;

import com.evandev.brick_and_mortar.recipe.KilnRecipe;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class KilnRecipeBuilder implements RecipeBuilder {
    private final Ingredient input;
    private final ItemStack output;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private int cookingTime = 200;
    private int requiredDoors = 0;
    private Optional<Block> baseBlock = Optional.empty();
    @Nullable
    private String group;

    private KilnRecipeBuilder(Ingredient input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public static KilnRecipeBuilder firing(Ingredient input, ItemStack output) {
        return new KilnRecipeBuilder(input, output);
    }

    public static KilnRecipeBuilder firing(Ingredient input, Item output) {
        return new KilnRecipeBuilder(input, new ItemStack(output));
    }

    public static KilnRecipeBuilder firing(Ingredient input, Block output) {
        return new KilnRecipeBuilder(input, new ItemStack(output.asItem()));
    }

    public KilnRecipeBuilder cookingTime(int time) {
        this.cookingTime = time;
        return this;
    }

    public KilnRecipeBuilder requiredDoors(int doors) {
        this.requiredDoors = doors;
        return this;
    }

    public KilnRecipeBuilder baseBlock(Block block) {
        this.baseBlock = Optional.of(block);
        return this;
    }

    @Override
    public @NotNull KilnRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public @NotNull KilnRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return output.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        this.ensureValid(id);
        var advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        KilnRecipe recipe = new KilnRecipe(this.input, this.output, this.cookingTime, this.requiredDoors, this.baseBlock);
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/kiln_firing/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}