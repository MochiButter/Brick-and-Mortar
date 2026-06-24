package com.evandev.brick_and_mortar.recipe;

import com.evandev.brick_and_mortar.registry.ModRecipes;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class KilnRecipe implements Recipe<KilnRecipeInput> {
    private final ResourceLocation id;
    private final Ingredient input;
    private final ItemStack output;
    private final float experience;
    private final int cookingTime;
    private final int requiredDoorsOpen;
    private final boolean requiresSoulBase;

    public KilnRecipe(ResourceLocation id, Ingredient input, ItemStack output, float experience, int cookingTime,
                      int requiredDoorsOpen, boolean requiresSoulBase) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.requiredDoorsOpen = requiredDoorsOpen;
        this.requiresSoulBase = requiresSoulBase;
    }

    @Override
    public boolean matches(@NotNull KilnRecipeInput recipeInput, Level level) {
        if (level.isClientSide) return false;

        if (!input.test(recipeInput.getItem(0))) return false;
        boolean inputIsSoul = recipeInput.baseBlock().defaultBlockState().is(BlockTags.SOUL_FIRE_BASE_BLOCKS);

        if (requiresSoulBase != inputIsSoul) return false;

        return recipeInput.openDoors() == requiredDoorsOpen;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull KilnRecipeInput recipeInput, @NotNull RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(this.input);
        return ingredients;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return output;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.KILN_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.KILN_TYPE.get();
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public float getExperience() {
        return experience;
    }

    public int getRequiredDoorsOpen() {
        return requiredDoorsOpen;
    }

    public boolean getRequiresSoulBase() {
        return requiresSoulBase;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    public static class Serializer implements RecipeSerializer<KilnRecipe> {
        @Override
        public @NotNull KilnRecipe fromJson(@NotNull ResourceLocation recipeID, @NotNull JsonObject jsonObject) {
            int cookingTime = GsonHelper.getAsInt(jsonObject, "cookingtime");
            float experience = GsonHelper.getAsFloat(jsonObject, "experience");
            JsonObject ingredients = GsonHelper.getAsJsonObject(jsonObject, "ingredient");
            Ingredient input = Ingredient.fromJson(ingredients, false);
            int requiredDoors = GsonHelper.getAsInt(jsonObject, "required_doors");
            boolean requiresSoulBase = jsonObject.has("requires_soul") &&
                    GsonHelper.getAsBoolean(jsonObject, "requires_soul");
            JsonObject result = GsonHelper.getAsJsonObject(jsonObject, "result");
            ItemStack output = ShapedRecipe.itemStackFromJson(result);
            return new KilnRecipe(recipeID, input, output, experience, cookingTime, requiredDoors, requiresSoulBase);
        }

        @Override
        public @NotNull KilnRecipe fromNetwork(@NotNull ResourceLocation recipeID, @NotNull FriendlyByteBuf buf) {
            Ingredient input = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            float experience = buf.readFloat();
            int cookingTime = buf.readInt();
            int requiredDoorsOpen = buf.readInt();
            boolean requiresSoulBase = buf.readBoolean();
            return new KilnRecipe(recipeID, input, output, experience, cookingTime, requiredDoorsOpen, requiresSoulBase);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buf, KilnRecipe recipe) {
            recipe.input.toNetwork(buf);
            buf.writeItem(recipe.output);
            buf.writeFloat(recipe.experience);
            buf.writeInt(recipe.cookingTime);
            buf.writeInt(recipe.requiredDoorsOpen);
            buf.writeBoolean(recipe.requiresSoulBase);
        }
    }
}