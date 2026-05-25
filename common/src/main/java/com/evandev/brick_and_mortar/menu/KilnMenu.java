package com.evandev.brick_and_mortar.menu;

import com.evandev.brick_and_mortar.block.entity.KilnBlockEntity;
import com.evandev.brick_and_mortar.registry.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KilnMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData data;

    public KilnMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(3), new SimpleContainerData(8));
    }

    public KilnMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(ModMenus.KILN_MENU.get(), containerId);
        this.container = container;
        this.data = data;

        this.addSlot(new Slot(container, 0, 32, 17));
        this.addSlot(new Slot(container, 1, 32, 53));
        this.addSlot(new Slot(container, 2, 112, 39) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlots(data);
        this.container.startOpen(playerInventory.player);
    }

    public Container getContainer() {
        return this.container;
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    @Override
    public boolean clickMenuButton(@NotNull Player player, int id) {
        if (this.container instanceof KilnBlockEntity kiln) {
            if (id >= 0 && id <= 2) {
                kiln.toggleDoor(id); // 0 = Left, 1 = Back, 2 = Right
                return true;
            }
        }
        return false;
    }

    public int getProgressionScaled() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        return maxProgress != 0 && progress != 0 ? progress * 24 / maxProgress : 0;
    }

    public int getLitProgressScaled() {
        int litTime = this.data.get(6);
        int litDuration = this.data.get(7);
        if (litDuration == 0) litDuration = 200;
        return litTime * 14 / litDuration;
    }

    public boolean isLit() {
        return this.data.get(6) > 0;
    }

    public boolean isSoul() {
        return this.data.get(2) == 1;
    }

    public boolean isLeftOpen() {
        return this.data.get(3) == 1;
    }

    public boolean isBackOpen() {
        return this.data.get(4) == 1;
    }

    public boolean isRightOpen() {
        return this.data.get(5) == 1;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemStack = slotStack.copy();

            if (index == 2) {
                if (!this.moveItemStackTo(slotStack, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, itemStack);
            } else if (index == 1 || index == 0) {
                if (!this.moveItemStackTo(slotStack, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // TODO: check if the item is valid fuel/input here
                if (!this.moveItemStackTo(slotStack, 0, 1, false)) {
                    if (!this.moveItemStackTo(slotStack, 1, 2, false)) {
                        if (index >= 3 && index < 30) {
                            if (!this.moveItemStackTo(slotStack, 30, 39, false)) return ItemStack.EMPTY;
                        } else if (index >= 30 && index < 39 && !this.moveItemStackTo(slotStack, 3, 30, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }

            if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.container.stillValid(player);
    }
}