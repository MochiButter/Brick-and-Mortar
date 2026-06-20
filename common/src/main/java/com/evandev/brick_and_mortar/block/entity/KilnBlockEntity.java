package com.evandev.brick_and_mortar.block.entity;

import com.evandev.brick_and_mortar.block.KilnBlock;
import com.evandev.brick_and_mortar.menu.KilnMenu;
import com.evandev.brick_and_mortar.platform.Services;
import com.evandev.brick_and_mortar.recipe.KilnRecipe;
import com.evandev.brick_and_mortar.recipe.KilnRecipeInput;
import com.evandev.brick_and_mortar.registry.ModBlockEntities;
import com.evandev.brick_and_mortar.registry.ModRecipes;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KilnBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, MenuProvider, RecipeCraftingHolder {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level level, @NotNull BlockPos pos, BlockState state) {
            level.setBlock(pos, state.setValue(KilnBlock.OPEN_FRONT, true), 3);
            level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.9F);
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

        BlockState baseBlockState = level.getBlockState(pos.below());
        if (!baseBlockState.is(BlockTags.SOUL_FIRE_BASE_BLOCKS) && baseBlockState.hasBlockEntity()) {
            BlockState below2 = level.getBlockState(pos.below(2));
            if (below2.is(BlockTags.SOUL_FIRE_BASE_BLOCKS)) {
                baseBlockState = below2;
            }
        }
        Block baseBlock = baseBlockState.getBlock();
        boolean isSoul = baseBlockState.is(BlockTags.SOUL_FIRE_BASE_BLOCKS);

        if (state.getValue(KilnBlock.SOUL) != isSoul) {
            state = state.setValue(KilnBlock.SOUL, isSoul);
            level.setBlock(pos, state, 3);
            changed = true;
        }

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

                entity.setRecipeUsed(recipeHolder);
                changed = true;
            }
        } else if (!canCraft && entity.litTime == 0) {
            entity.progress = Mth.clamp(entity.progress - 2, 0, entity.maxProgress);
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

    private static void createExperience(ServerLevel level, Vec3 popVec, int count, float experience) {
        if (experience == 0.0F) return;

        int i = Mth.floor((float) count * experience);
        float f = Mth.frac((float) count * experience);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }
        ExperienceOrb.award(level, popVec, i);
    }

    private int getBurnDuration(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        return Services.PLATFORM.getBurnTime(stack);
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
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, this.items, registries);
        this.progress = tag.getInt("CookTime");
        this.maxProgress = tag.getInt("CookTimeTotal");
        this.litTime = tag.getInt("BurnTime");
        this.litDuration = tag.getInt("BurnDuration");
        CompoundTag recipesTag = tag.getCompound("RecipesUsed");
        for (String key : recipesTag.getAllKeys()) {
            this.recipesUsed.put(ResourceLocation.parse(key), recipesTag.getInt(key));
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.items, registries);
        tag.putInt("CookTime", this.progress);
        tag.putInt("CookTimeTotal", this.maxProgress);
        tag.putInt("BurnTime", this.litTime);
        tag.putInt("BurnDuration", this.litDuration);
        CompoundTag recipesTag = new CompoundTag();
        this.recipesUsed.forEach((id, count) -> recipesTag.putInt(id.toString(), count));
        tag.put("RecipesUsed", recipesTag);
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else if (side == Direction.UP) {
            return SLOTS_FOR_UP;
        } else {
            return SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @NotNull Direction direction) {
        if (direction == Direction.DOWN && index == 1) {
            return stack.is(Items.WATER_BUCKET) || stack.is(Items.BUCKET);
        }
        return true;
    }

    @Override
    public boolean canPlaceItem(int index, @NotNull ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index == 1) {
            return this.getBurnDuration(stack) > 0;
        }
        return true;
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        ItemStack currentStack = this.items.get(slot);
        boolean isSameItem = !stack.isEmpty() && ItemStack.isSameItemSameComponents(currentStack, stack);

        this.items.set(slot, stack);
        stack.limitSize(this.getMaxStackSize(stack));

        if (slot == 0 && !isSameItem) {
            this.progress = 0;
            this.setChanged();
        }
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Nullable
    @Override
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipe) {
        if (recipe != null) {
            this.recipesUsed.addTo(recipe.id(), 1);
        }
    }

    @Override
    public void awardUsedRecipes(@NotNull Player player, @NotNull List<ItemStack> items) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<RecipeHolder<?>> list = this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());
        player.awardRecipes(list);

        for (RecipeHolder<?> recipeholder : list) {
            if (recipeholder != null) {
                player.triggerRecipeCrafted(recipeholder, this.items);
            }
        }
        this.recipesUsed.clear();
    }

    public List<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 popVec) {
        List<RecipeHolder<?>> list = Lists.newArrayList();

        for (Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent(recipeHolder -> {
                list.add(recipeHolder);
                if (recipeHolder.value() instanceof KilnRecipe kilnRecipe) {
                    createExperience(level, popVec, entry.getIntValue(), kilnRecipe.experience());
                }
            });
        }
        return list;
    }
}