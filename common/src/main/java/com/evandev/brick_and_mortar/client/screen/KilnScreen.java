package com.evandev.brick_and_mortar.client.screen;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.menu.KilnMenu;
import com.evandev.brick_and_mortar.recipe.KilnRecipe;
import com.evandev.brick_and_mortar.registry.ModRecipes;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Supplier;

public class KilnScreen extends AbstractContainerScreen<KilnMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/gui/container/kiln.png");

    private static final String PATH_PREFIX = "textures/gui/sprites/container/kiln/";
    private static final ImmutablePair<ResourceLocation, ResourceLocation> CLOSED_SPRITES = new ImmutablePair<>(
            new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "door_closed.png"),
            new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "door_closed_highlighted.png"));
    private static final ImmutablePair<ResourceLocation, ResourceLocation> OPEN_SPRITES = new ImmutablePair<>(
            new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "door_open.png"),
            new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "door_open_highlighted.png"));
    private static final ImmutablePair<ResourceLocation, ResourceLocation> OPEN_SOUL_SPRITES = new ImmutablePair<>(
            new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "door_open_soul.png"),
            new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "door_open_soul_highlighted.png"));
    private static final ResourceLocation BURN_PROGRESS_SPRITE =
            new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "burn_progress.png");
    private static final ResourceLocation LIT_PROGRESS_SPRITE =
            new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "lit_progress.png");

    private static final ResourceLocation PREVIEW_LOWERED = new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "preview_lowered.png");
    private static final ResourceLocation PREVIEW_LOWERED_HIGHLIGHTED = new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "preview_lowered_highlighted.png");
    private static final ResourceLocation PREVIEW = new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "preview.png");
    private static final ResourceLocation PREVIEW_HIGHLIGHTED = new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "preview_highlighted.png");
    private static final ResourceLocation PREVIEW_SOUL = new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "preview_soul.png");
    private static final ResourceLocation PREVIEW_SOUL_HIGHLIGHTED = new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "preview_soul_highlighted.png");
    private static final ResourceLocation EMPTY_PREVIEW_ITEM = new ResourceLocation(Constants.MOD_ID, PATH_PREFIX + "empty_preview_item.png");

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
        this.addRenderableWidget(new ImageButton(x, y, 14, 14, 0, 0, CLOSED_SPRITES.left, (button) -> {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, buttonId);
        }) {
            @Override
            public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                ImmutablePair<ResourceLocation, ResourceLocation> currentSprites = CLOSED_SPRITES;
                if (isOpen.get()) {
                    currentSprites = menu.isSoul() ? OPEN_SOUL_SPRITES : OPEN_SPRITES;
                }
                ResourceLocation hoverSprite = this.isHoveredOrFocused() ? currentSprites.right : currentSprites.left;
                guiGraphics.blit(hoverSprite, this.getX(), this.getY(), 0, 0, 0, this.getWidth(), this.getHeight(), 14, 14);
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

        if (!ItemStack.isSameItem(currentInput, this.lastInput) || currentSoul != this.lastSoul) {
            this.lastInput = currentInput.copy();
            this.lastSoul = currentSoul;
            Arrays.fill(this.previewOutputs, ItemStack.EMPTY);

            if (!currentInput.isEmpty() && this.minecraft != null && this.minecraft.level != null) {
                var recipes = this.minecraft.level.getRecipeManager().getAllRecipesFor(ModRecipes.KILN_TYPE.get());
                for (KilnRecipe recipe : recipes) {
                    if (recipe.getIngredients().get(0).test(currentInput) && recipe.getRequiresSoulBase() == currentSoul) {
                        int doors = recipe.getRequiredDoorsOpen();
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

        renderBackground(guiGraphics);
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
            guiGraphics.blit(LIT_PROGRESS_SPRITE, x + 32, y + 36 + 14 - l, 0, 0, 14 - l,
                    14, l, 14, 14);
        }

        int progress = this.menu.getProgressionScaled();
        if (progress > 0) {
            guiGraphics.blit(BURN_PROGRESS_SPRITE, x + 55, y + 34, 0, 0, 0,
                    progress, 16, 24, 16);
        }
    }

    private void renderPreviewWidget(GuiGraphics guiGraphics, int relX, int relY, int mouseX, int mouseY) {
        int widgetX = relX + this.imageWidth - PREVIEW_WIDTH - 10;
        int widgetY = (int) (relY - PREVIEW_LOWERED_HEIGHT - (PREVIEW_EXPANDED_HEIGHT - PREVIEW_LOWERED_HEIGHT) * this.expandProgress);

        boolean isHovered = mouseX >= widgetX && mouseX < widgetX + PREVIEW_WIDTH && mouseY >= widgetY && mouseY < relY;

        if (this.expandProgress == 0.0f) {
            ResourceLocation texture = isHovered ? PREVIEW_LOWERED_HIGHLIGHTED : PREVIEW_LOWERED;
            guiGraphics.blit(texture, widgetX, widgetY, 0, 0, PREVIEW_WIDTH, PREVIEW_LOWERED_HEIGHT, PREVIEW_WIDTH, PREVIEW_LOWERED_HEIGHT);
        } else {
            ResourceLocation texture;
            if (this.menu.isSoul()) {
                texture = isHovered ? PREVIEW_SOUL_HIGHLIGHTED : PREVIEW_SOUL;
            } else {
                texture = isHovered ? PREVIEW_HIGHLIGHTED : PREVIEW;
            }

            guiGraphics.enableScissor(widgetX, 0, widgetX + PREVIEW_WIDTH, relY);

            guiGraphics.blit(texture, widgetX, widgetY, 0, 0, PREVIEW_WIDTH, PREVIEW_EXPANDED_HEIGHT, PREVIEW_WIDTH, PREVIEW_EXPANDED_HEIGHT);

            for (int i = 0; i < 4; i++) {
                int itemX = widgetX + PREVIEW_ITEM_START_X + i * PREVIEW_ITEM_SPACING;
                int itemY = widgetY + PREVIEW_ITEM_Y_OFFSET;

                ItemStack stack = this.previewOutputs[i];
                if (stack != null && !stack.isEmpty()) {
                    guiGraphics.renderFakeItem(stack, itemX, itemY);
                    guiGraphics.renderItemDecorations(this.font, stack, itemX, itemY);
                } else {
                    guiGraphics.blit(EMPTY_PREVIEW_ITEM, itemX + 2, itemY + 2, 0, 0, 12, 12, 12, 12);
                }
            }

            guiGraphics.disableScissor();
        }
    }
}