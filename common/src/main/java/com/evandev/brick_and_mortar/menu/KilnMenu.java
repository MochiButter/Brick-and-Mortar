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
        this(containerId, playerInventory, new SimpleContainer(3), new SimpleContainerData(6));
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

    public int getProgressionScaled() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        return maxProgress != 0 && progress != 0 ? progress * 24 / maxProgress : 0;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.container.stillValid(player);
    }
}