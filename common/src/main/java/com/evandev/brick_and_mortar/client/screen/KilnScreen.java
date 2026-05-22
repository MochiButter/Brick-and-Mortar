package com.evandev.brick_and_mortar.client.screen;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.menu.KilnMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class KilnScreen extends AbstractContainerScreen<KilnMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/container/kiln.png");

    private static final WidgetSprites CLOSED_SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/door_closed"),
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/door_closed_highlighted"));
    private static final WidgetSprites OPEN_SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/door_open"),
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/door_open_highlighted"));
    private static final WidgetSprites OPEN_SOUL_SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/door_open_soul"),
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/door_open_soul_highlighted"));

    public KilnScreen(KilnMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();

        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;

        addDoorWidget(0, relX + 86, relY + 40, this.menu::isLeftOpen);
        addDoorWidget(1, relX + 113, relY + 18, this.menu::isBackOpen);
        addDoorWidget(2, relX + 140, relY + 40, this.menu::isRightOpen);
    }

    private void addDoorWidget(int buttonId, int x, int y, Supplier<Boolean> isOpen) {
        this.addRenderableWidget(new ImageButton(x, y, 14, 14, CLOSED_SPRITES, (button) -> {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, buttonId);
        }) {
            @Override
            public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                WidgetSprites currentSprites = CLOSED_SPRITES;
                if (isOpen.get()) {
                    currentSprites = menu.isSoul() ? OPEN_SOUL_SPRITES : OPEN_SPRITES;
                }
                guiGraphics.blitSprite(currentSprites.get(this.isActive(), this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
            }
        });
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

        int progress = this.menu.getProgressionScaled();
        guiGraphics.blit(TEXTURE, x + 79, y + 34, 176, 14, progress + 1, 16);
    }
}