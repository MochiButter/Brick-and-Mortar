package com.evandev.brick_and_mortar.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public record KilnRecipeInput(ItemStack item, Block baseBlock) implements RecipeInput {
    @Override
    public @NotNull ItemStack getItem(int index) {
        return item;
    }

    @Override
    public int size() {
        return 1;
    }
}