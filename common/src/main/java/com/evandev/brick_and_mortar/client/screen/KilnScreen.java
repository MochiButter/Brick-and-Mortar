package com.evandev.brick_and_mortar.client.screen;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.menu.KilnMenu;
import com.evandev.brick_and_mortar.recipe.KilnRecipe;
import com.evandev.brick_and_mortar.registry.ModRecipes;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
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

    private static final ResourceLocation PREVIEW_LOWERED = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/preview_lowered");
    private static final ResourceLocation PREVIEW_LOWERED_HIGHLIGHTED = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/preview_lowered_highlighted");
    private static final ResourceLocation PREVIEW = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/preview");
    private static final ResourceLocation PREVIEW_HIGHLIGHTED = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/preview_highlighted");
    private static final ResourceLocation PREVIEW_SOUL = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/preview_soul");
    private static final ResourceLocation PREVIEW_SOUL_HIGHLIGHTED = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/preview_soul_highlighted");
    private static final ResourceLocation EMPTY_PREVIEW_ITEM = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "container/kiln/empty_preview_item");

    private static final int PREVIEW_WIDTH = 92;
    private static final int PREVIEW_EXPANDED_HEIGHT = 45;
    private static final int PREVIEW_LOWERED_HEIGHT = 8;
    private static final int PREVIEW_ITEM_Y_OFFSET = 26;
    private static final int PREVIEW_ITEM_START_X = 5;
    private static final int PREVIEW_ITEM_SPACING = 22;
    private final ItemStack[] previewOutputs = new ItemStack[4];

    private boolean isExpanded = false;
    private float expandProgress = 0.0f;
    private long lastFrameTime;

    private ItemStack lastInput = ItemStack.EMPTY;
    private boolean lastSoul = false;

    public KilnScreen(KilnMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        Arrays.fill(this.previewOutputs, ItemStack.EMPTY);
        this.lastFrameTime = Util.getMillis();
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
    public void containerTick() {
        super.containerTick();
        updatePreviewItems();
    }

    private void updatePreviewItems() {
        ItemStack currentInput = this.menu.getSlot(0).getItem();
        boolean currentSoul = this.menu.isSoul();

        if (!ItemStack.isSameItemSameComponents(currentInput, this.lastInput) || currentSoul != this.lastSoul) {
            this.lastInput = currentInput.copy();
            this.lastSoul = currentSoul;
            Arrays.fill(this.previewOutputs, ItemStack.EMPTY);

            if (!currentInput.isEmpty() && this.minecraft != null && this.minecraft.level != null) {
                var recipes = this.minecraft.level.getRecipeManager().getAllRecipesFor(ModRecipes.KILN_TYPE.get());
                for (var holder : recipes) {
                    KilnRecipe recipe = holder.value();
                    if (recipe.input().test(currentInput) && recipe.requiresSoulBase() == currentSoul) {
                        int doors = recipe.requiredDoorsOpen();
                        if (doors >= 0 && doors < 4) {
                            this.previewOutputs[doors] = recipe.getResultItem(this.minecraft.level.registryAccess());
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        int widgetX = relX + this.imageWidth - PREVIEW_WIDTH - 10;
        int widgetY = (int) (relY - PREVIEW_LOWERED_HEIGHT - (PREVIEW_EXPANDED_HEIGHT - PREVIEW_LOWERED_HEIGHT) * this.expandProgress);

        if (mouseX >= widgetX && mouseX < widgetX + PREVIEW_WIDTH && mouseY >= widgetY && mouseY < relY) {
            this.isExpanded = !this.isExpanded;
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        long currentTime = Util.getMillis();
        float dt = (currentTime - this.lastFrameTime) / 150.0f;
        this.lastFrameTime = currentTime;

        if (this.isExpanded && this.expandProgress < 1.0f) {
            this.expandProgress = Math.min(1.0f, this.expandProgress + dt);
        } else if (!this.isExpanded && this.expandProgress > 0.0f) {
            this.expandProgress = Math.max(0.0f, this.expandProgress - dt);
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        if (this.expandProgress > 0.0f) {
            int relX = (this.width - this.imageWidth) / 2;
            int relY = (this.height - this.imageHeight) / 2;
            int widgetX = relX + this.imageWidth - PREVIEW_WIDTH - 10;
            int widgetY = (int) (relY - PREVIEW_LOWERED_HEIGHT - (PREVIEW_EXPANDED_HEIGHT - PREVIEW_LOWERED_HEIGHT) * this.expandProgress);

            for (int i = 0; i < 4; i++) {
                int itemX = widgetX + PREVIEW_ITEM_START_X + i * PREVIEW_ITEM_SPACING;
                int itemY = widgetY + PREVIEW_ITEM_Y_OFFSET;

                if (mouseX >= itemX && mouseX < itemX + 16 && mouseY >= itemY && mouseY < itemY + 16 && mouseY < relY) {
                    ItemStack stack = this.previewOutputs[i];
                    if (stack != null && !stack.isEmpty()) {
                        guiGraphics.renderTooltip(this.font, stack, mouseX, mouseY);
                    }
                }
            }
        }
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        renderPreviewWidget(guiGraphics, x, y, mouseX, mouseY);

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

    private void renderPreviewWidget(GuiGraphics guiGraphics, int relX, int relY, int mouseX, int mouseY) {
        int widgetX = relX + this.imageWidth - PREVIEW_WIDTH - 10;
        int widgetY = (int) (relY - PREVIEW_LOWERED_HEIGHT - (PREVIEW_EXPANDED_HEIGHT - PREVIEW_LOWERED_HEIGHT) * this.expandProgress);

        boolean isHovered = mouseX >= widgetX && mouseX < widgetX + PREVIEW_WIDTH && mouseY >= widgetY && mouseY < relY;

        if (this.expandProgress == 0.0f) {
            ResourceLocation texture = isHovered ? PREVIEW_LOWERED_HIGHLIGHTED : PREVIEW_LOWERED;
            guiGraphics.blitSprite(texture, widgetX, widgetY, PREVIEW_WIDTH, PREVIEW_LOWERED_HEIGHT);
        } else {
            ResourceLocation texture;
            if (this.menu.isSoul()) {
                texture = isHovered ? PREVIEW_SOUL_HIGHLIGHTED : PREVIEW_SOUL;
            } else {
                texture = isHovered ? PREVIEW_HIGHLIGHTED : PREVIEW;
            }

            guiGraphics.enableScissor(widgetX, 0, widgetX + PREVIEW_WIDTH, relY);

            guiGraphics.blitSprite(texture, widgetX, widgetY, PREVIEW_WIDTH, PREVIEW_EXPANDED_HEIGHT);

            for (int i = 0; i < 4; i++) {
                int itemX = widgetX + PREVIEW_ITEM_START_X + i * PREVIEW_ITEM_SPACING;
                int itemY = widgetY + PREVIEW_ITEM_Y_OFFSET;

                ItemStack stack = this.previewOutputs[i];
                if (stack != null && !stack.isEmpty()) {
                    guiGraphics.renderFakeItem(stack, itemX, itemY);
                    guiGraphics.renderItemDecorations(this.font, stack, itemX, itemY);
                } else {
                    guiGraphics.blitSprite(EMPTY_PREVIEW_ITEM, itemX + 2, itemY + 2, 12, 12);
                }
            }

            guiGraphics.disableScissor();
        }
    }
}