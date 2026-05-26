package com.evandev.brick_and_mortar.recipe;

import com.evandev.brick_and_mortar.registry.ModRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record KilnRecipe(Ingredient input, ItemStack output, float experience, int cookingTime,
                         int requiredDoorsOpen, boolean requiresSoulBase) implements Recipe<KilnRecipeInput> {

    @Override
    public boolean matches(KilnRecipeInput recipeInput, @NotNull Level level) {
        if (!input.test(recipeInput.item())) return false;

        boolean inputIsSoul = recipeInput.baseBlock().defaultBlockState().is(BlockTags.SOUL_FIRE_BASE_BLOCKS);

        if (requiresSoulBase != inputIsSoul) return false;

        return recipeInput.openDoors() == requiredDoorsOpen;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull KilnRecipeInput recipeInput, HolderLookup.@NotNull Provider lookupProvider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider lookupProvider) {
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

    public static class Serializer implements RecipeSerializer<KilnRecipe> {
        public static final MapCodec<KilnRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(KilnRecipe::input),
                ItemStack.CODEC.fieldOf("result").forGetter(KilnRecipe::output),
                Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(KilnRecipe::experience),
                Codec.INT.optionalFieldOf("cookingtime", 200).forGetter(KilnRecipe::cookingTime),
                Codec.INT.optionalFieldOf("required_doors", 0).forGetter(KilnRecipe::requiredDoorsOpen),
                Codec.BOOL.optionalFieldOf("requires_soul_base", false).forGetter(KilnRecipe::requiresSoulBase)
        ).apply(inst, KilnRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, KilnRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, KilnRecipe::input,
                ItemStack.STREAM_CODEC, KilnRecipe::output,
                ByteBufCodecs.FLOAT, KilnRecipe::experience,
                ByteBufCodecs.INT, KilnRecipe::cookingTime,
                ByteBufCodecs.INT, KilnRecipe::requiredDoorsOpen,
                ByteBufCodecs.BOOL, KilnRecipe::requiresSoulBase,
                KilnRecipe::new
        );

        @Override
        public @NotNull MapCodec<KilnRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, KilnRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}