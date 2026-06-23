package com.evandev.brick_and_mortar.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public record KilnRecipeInput(ItemStack item, Block baseBlock, int openDoors) implements Container {
    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return item.isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        return item;
    }

    /* While these functions are required for a container, they won't be implemented as the item data is immutable */
    @Override
    public ItemStack removeItem(int index, int count) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return null;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {}

    @Override
    public void setChanged() {}

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void clearContent() {}
}