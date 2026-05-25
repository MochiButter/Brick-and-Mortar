package com.evandev.brick_and_mortar.block.entity;

import com.evandev.brick_and_mortar.block.KilnBlock;
import com.evandev.brick_and_mortar.menu.KilnMenu;
import com.evandev.brick_and_mortar.recipe.KilnRecipe;
import com.evandev.brick_and_mortar.recipe.KilnRecipeInput;
import com.evandev.brick_and_mortar.registry.ModBlockEntities;
import com.evandev.brick_and_mortar.registry.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class KilnBlockEntity extends BaseContainerBlockEntity implements MenuProvider {
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level level, @NotNull BlockPos pos, BlockState state) {
            level.setBlock(pos, state.setValue(KilnBlock.OPEN_FRONT, true), 3);
        }

        @Override
        protected void onClose(Level level, @NotNull BlockPos pos, BlockState state) {
            level.setBlock(pos, state.setValue(KilnBlock.OPEN_FRONT, false), 3);
        }

        @Override
        protected void openerCountChanged(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, int prev, int now) {
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof KilnMenu menu) {
                return menu.getContainer() == KilnBlockEntity.this;
            }
            return false;
        }
    };

    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    int progress = 0;
    int maxProgress = 200;
    int litTime = 0;
    int litDuration = 0;

    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> KilnBlockEntity.this.progress;
                case 1 -> KilnBlockEntity.this.maxProgress;
                case 2 -> getBlockState().getValue(KilnBlock.SOUL) ? 1 : 0;
                case 3 -> getBlockState().getValue(KilnBlock.OPEN_LEFT) ? 1 : 0;
                case 4 -> getBlockState().getValue(KilnBlock.OPEN_BACK) ? 1 : 0;
                case 5 -> getBlockState().getValue(KilnBlock.OPEN_RIGHT) ? 1 : 0;
                case 6 -> KilnBlockEntity.this.litTime;
                case 7 -> KilnBlockEntity.this.litDuration;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> KilnBlockEntity.this.progress = value;
                case 1 -> KilnBlockEntity.this.maxProgress = value;
                case 6 -> KilnBlockEntity.this.litTime = value;
                case 7 -> KilnBlockEntity.this.litDuration = value;
            }
        }

        @Override
        public int getCount() {
            return 8;
        }
    };

    public KilnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KILN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, KilnBlockEntity entity) {
        entity.openersCounter.recheckOpeners(level, pos, state);

        boolean wasLit = entity.litTime > 0;
        boolean changed = false;

        if (entity.litTime > 0) {
            entity.litTime--;
        }

        ItemStack inputStack = entity.items.get(0);
        ItemStack fuelStack = entity.items.get(1);

        int openDoors = (state.getValue(KilnBlock.OPEN_LEFT) ? 1 : 0) +
                (state.getValue(KilnBlock.OPEN_BACK) ? 1 : 0) +
                (state.getValue(KilnBlock.OPEN_RIGHT) ? 1 : 0);

        Block baseBlock = level.getBlockState(pos.below()).getBlock();
        KilnRecipeInput recipeInput = new KilnRecipeInput(inputStack, baseBlock, openDoors);
        var recipeHolder = inputStack.isEmpty() ? null : level.getRecipeManager().getRecipeFor(ModRecipes.KILN_TYPE.get(), recipeInput, level).orElse(null);
        KilnRecipe recipe = recipeHolder != null ? recipeHolder.value() : null;

        boolean canCraft = false;
        if (recipe != null) {
            ItemStack resultStack = recipe.getResultItem(level.registryAccess());
            ItemStack outputSlot = entity.items.get(2);
            if (outputSlot.isEmpty() || (ItemStack.isSameItemSameComponents(outputSlot, resultStack) && outputSlot.getCount() + resultStack.getCount() <= outputSlot.getMaxStackSize())) {
                canCraft = true;
            }
        }

        if (entity.litTime == 0 && canCraft && !fuelStack.isEmpty()) {
            entity.litDuration = entity.getBurnDuration(fuelStack);
            entity.litTime = entity.litDuration;

            if (entity.litTime > 0) {
                changed = true;
                if (fuelStack.getItem().hasCraftingRemainingItem()) {
                    entity.items.set(1, new ItemStack(fuelStack.getItem().getCraftingRemainingItem()));
                } else {
                    fuelStack.shrink(1);
                    if (fuelStack.isEmpty()) {
                        entity.items.set(1, ItemStack.EMPTY);
                    }
                }
            }
        }

        if (entity.litTime > 0 && canCraft) {
            entity.maxProgress = recipe.cookingTime();
            entity.progress++;
            if (entity.progress >= entity.maxProgress) {
                entity.progress = 0;
                ItemStack resultStack = recipe.getResultItem(level.registryAccess());
                ItemStack outputSlot = entity.items.get(2);

                if (outputSlot.isEmpty()) {
                    entity.items.set(2, resultStack.copy());
                } else {
                    outputSlot.grow(resultStack.getCount());
                }
                inputStack.shrink(1);
                changed = true;
            }
        } else if (!canCraft) {
            entity.progress = 0;
        }

        if (wasLit != (entity.litTime > 0)) {
            level.setBlock(pos, state.setValue(KilnBlock.LIT, entity.litTime > 0), 3);
            changed = true;
        }

        if (changed) {
            entity.setChanged();
        }
    }

    private int getBurnDuration(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        return AbstractFurnaceBlockEntity.getFuel().getOrDefault(stack.getItem(), 0);
    }

    public void toggleDoor(int doorId) {
        if (level != null) {
            BlockState state = getBlockState();
            if (doorId == 0) level.setBlock(getBlockPos(), state.cycle(KilnBlock.OPEN_LEFT), 3);
            else if (doorId == 1) level.setBlock(getBlockPos(), state.cycle(KilnBlock.OPEN_BACK), 3);
            else if (doorId == 2) level.setBlock(getBlockPos(), state.cycle(KilnBlock.OPEN_RIGHT), 3);
        }
    }

    @Override
    public void startOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.kiln");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int id, @NotNull Inventory player) {
        return new KilnMenu(id, player, this, this.dataAccess);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(items, slot, amount);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        items.set(slot, stack);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, net.minecraft.core.HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, this.items, registries);
        this.progress = tag.getInt("CookTime");
        this.maxProgress = tag.getInt("CookTimeTotal");
        this.litTime = tag.getInt("BurnTime");
        this.litDuration = tag.getInt("BurnDuration");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, net.minecraft.core.HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.items, registries);
        tag.putInt("CookTime", this.progress);
        tag.putInt("CookTimeTotal", this.maxProgress);
        tag.putInt("BurnTime", this.litTime);
        tag.putInt("BurnDuration", this.litDuration);
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> items) {
        this.items = items;
    }
}