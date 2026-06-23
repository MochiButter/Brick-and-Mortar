package com.evandev.brick_and_mortar.client.integration;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.recipe.KilnRecipe;
import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class KilnEmiRecipe implements EmiRecipe {
    private static final EmiTexture[][] STATUS_ICONS = new EmiTexture[2][4];

    static {
        for (int soul = 0; soul < 2; soul++) {
            for (int doors = 0; doors < 4; doors++) {
                String baseName = switch (doors) {
                    case 1 -> "one_open";
                    case 2 -> "two_open";
                    case 3 -> "three_open";
                    default -> "all_closed";
                };

                String textureName = soul == 1 ? baseName + "_soul" : baseName;

                STATUS_ICONS[soul][doors] = new EmiTexture(
                        new ResourceLocation(Constants.MOD_ID,
                                "textures/gui/sprites/emi/" + textureName + ".png"),
                        0, 0, 16, 16,
                        16, 16, 16, 16
                );
            }
        }
    }

    private final ResourceLocation id;
    private final EmiIngredient input;
    private final EmiStack output;
    private final KilnRecipe recipe;

    public KilnEmiRecipe(KilnRecipe recipe) {
        this.id = recipe.getId();
        this.recipe = recipe;
        this.input = EmiIngredient.of(recipe.getIngredients().get(0));
        this.output = EmiStack.of(recipe.getOutput());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return BMEmiPlugin.KILN_CATEGORY;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(this.input);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(this.output);
    }

    @Override
    public int getDisplayWidth() {
        return 82;
    }

    @Override
    public int getDisplayHeight() {
        return 62;
    }

    public KilnRecipe getRecipe() {
        return this.recipe;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_FLAME, 1, 42);
        widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 1, 42, 4000, false, true, true);

        widgets.addSlot(this.input, 0, 22);

        widgets.addFillingArrow(24, 23, 50 * this.recipe.getCookingTime()).tooltip((mx, my) ->
                List.of(ClientTooltipComponent.create(
                        EmiPort.ordered(EmiPort.translatable("emi.cooking.time", this.recipe.getCookingTime() / 20f))
                )));

        widgets.addText(EmiPort.ordered(EmiPort.translatable("emi.cooking.experience", this.recipe.getExperience())), 26, 46, -1, true);

        int doors = Math.max(0, Math.min(3, this.recipe.getRequiredDoorsOpen()));
        boolean soul = this.recipe.getRequiresSoulBase();
        EmiTexture statusIcon = STATUS_ICONS[soul ? 1 : 0][doors];

        widgets.addTexture(statusIcon, 61, 0).tooltip((mx, my) -> {
            List<ClientTooltipComponent> tooltip = new ArrayList<>();

            if (this.recipe.getRequiredDoorsOpen() > 0) {
                tooltip.add(ClientTooltipComponent.create(
                        EmiPort.ordered(EmiPort.translatable("emi.brick_and_mortar.doors_required", this.recipe.getRequiredDoorsOpen()))
                ));
            } else {
                tooltip.add(ClientTooltipComponent.create(
                        EmiPort.ordered(EmiPort.translatable("emi.brick_and_mortar.doors_closed"))
                ));
            }

            if (this.recipe.getRequiresSoulBase()) {
                tooltip.add(ClientTooltipComponent.create(
                        EmiPort.ordered(EmiPort.translatable("emi.brick_and_mortar.requires_soul"))
                ));
            }

            return tooltip;
        });

        widgets.addSlot(this.output, 56, 18).large(true).recipeContext(this);
    }
}