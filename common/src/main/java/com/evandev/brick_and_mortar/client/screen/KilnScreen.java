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
    private static final ResourceLocation BURN_PROGRESS_SPRITE =
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/burn_progress");
    private static final ResourceLocation LIT_PROGRESS_SPRITE =
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/lit_progress");

    public KilnScreen(KilnMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();

        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;

        addDoorWidget(0, relX + 86, relY + 40, this.menu::isLeftOpen);
        addDoorWidget(1, relX + 113, relY + 14, this.menu::isBackOpen);
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

        if (this.menu.isLit()) {
            int l = this.menu.getLitProgressScaled();
            guiGraphics.blitSprite(LIT_PROGRESS_SPRITE, 14, 14, 0, 14 - l, x + 32, y + 36 + 14 - l, 14, l);
        }

        int progress = this.menu.getProgressionScaled();
        if (progress > 0) {
            guiGraphics.blitSprite(BURN_PROGRESS_SPRITE, 24, 16, 0, 0, x + 55, y + 34, progress, 16);
        }
    }
}