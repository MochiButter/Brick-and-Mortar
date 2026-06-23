package com.evandev.brick_and_mortar.datagen.builder;

import com.evandev.brick_and_mortar.registry.ModRecipes;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class KilnRecipeBuilder implements RecipeBuilder {
    private final Ingredient input;
    private final ItemStack output;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final CookingBookCategory category;
    private int cookingTime = 100;
    private int requiredDoors = 0;
    private boolean requiresSoulBase = false;
    private float experience = 0.3F;

    private KilnRecipeBuilder(Ingredient input, ItemStack output) {
        this.input = input;
        this.output = output;
        this.category = (output.getItem() instanceof BlockItem) ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
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

    public KilnRecipeBuilder experience(float xp) {
        this.experience = xp;
        return this;
    }

    public KilnRecipeBuilder cookingTime(int time) {
        this.cookingTime = time;
        return this;
    }

    public KilnRecipeBuilder requiredDoors(int doors) {
        this.requiredDoors = doors;
        return this;
    }

    public KilnRecipeBuilder requiresSoulBase() {
        this.requiresSoulBase = true;
        return this;
    }

    @Override
    public KilnRecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public @NotNull KilnRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return output.getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT)
                .addCriterion("has_the_recipe",RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new KilnRecipeBuilder.KilnFinishedRecipe(recipeId, this.input, this.output,
                this.experience, this.cookingTime, this.requiredDoors, this.requiresSoulBase, this.advancement,
                recipeId.withPrefix("recipes/kiln_firing/"), this.category));
    }

    static class KilnFinishedRecipe implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient input;
        private final ItemStack output;
        private final float experience;
        private final int cookingTime;
        private final int requiredDoorsOpen;
        private final boolean requiresSoulBase;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final CookingBookCategory category;

        KilnFinishedRecipe(ResourceLocation id, Ingredient input, ItemStack output, float experience, int cookingTime,
                           int requiredDoorsOpen, boolean requiresSoulBase,
                           Advancement.Builder advancement, ResourceLocation advancementId, CookingBookCategory category) {
            this.id = id;
            this.input = input;
            this.output = output;
            this.experience = experience;
            this.cookingTime = cookingTime;
            this.requiredDoorsOpen = requiredDoorsOpen;
            this.requiresSoulBase = requiresSoulBase;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.category = category;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("category", this.category.getSerializedName());
            json.addProperty("cookingtime", this.cookingTime);
            json.addProperty("experience", this.experience);
            json.add("ingredient", this.input.toJson());
            json.addProperty("required_doors", this.requiredDoorsOpen);
            if (this.requiresSoulBase) {
                json.addProperty("requires_soul", true);
            }
            JsonObject result = new JsonObject();
            result.addProperty("count", 1);
            result.addProperty("item", BuiltInRegistries.ITEM.getKey(this.output.getItem()).toString());
            json.add("result", result);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.KILN_SERIALIZER.get();
        }

        @Override
        public @Nullable JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        public @Nullable ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}