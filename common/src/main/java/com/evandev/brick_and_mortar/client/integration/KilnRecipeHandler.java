package com.evandev.brick_and_mortar.client.integration;

import com.evandev.brick_and_mortar.menu.KilnMenu;
import com.evandev.brick_and_mortar.recipe.KilnRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.handler.EmiCraftContext;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

public class KilnRecipeHandler implements StandardRecipeHandler<KilnMenu> {

    @Override
    public List<Slot> getInputSources(KilnMenu handler) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 3; i < 39; i++) {
            slots.add(handler.getSlot(i));
        }
        return slots;
    }

    @Override
    public List<Slot> getCraftingSlots(KilnMenu handler) {
        return List.of(handler.getSlot(0));
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe.getCategory() == BMEmiPlugin.KILN_CATEGORY && recipe instanceof KilnEmiRecipe;
    }

    @Override
    public boolean craft(EmiRecipe recipe, EmiCraftContext<KilnMenu> context) {
        boolean success = StandardRecipeHandler.super.craft(recipe, context);

        if (success && recipe instanceof KilnEmiRecipe kilnEmiRecipe) {
            KilnRecipe kilnRecipe = kilnEmiRecipe.getRecipe();
            KilnMenu menu = context.getScreenHandler();
            Minecraft mc = Minecraft.getInstance();

            if (mc.gameMode != null) {
                int requiredDoors = kilnRecipe.requiredDoorsOpen();

                boolean targetLeft = requiredDoors >= 1;
                boolean targetRight = requiredDoors >= 2;
                boolean targetBack = requiredDoors >= 3;

                if (menu.isLeftOpen() != targetLeft) {
                    mc.gameMode.handleInventoryButtonClick(menu.containerId, 0);
                }
                if (menu.isBackOpen() != targetBack) {
                    mc.gameMode.handleInventoryButtonClick(menu.containerId, 1);
                }
                if (menu.isRightOpen() != targetRight) {
                    mc.gameMode.handleInventoryButtonClick(menu.containerId, 2);
                }
            }
        }

        return success;
    }
}