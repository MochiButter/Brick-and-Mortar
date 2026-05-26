package com.evandev.brick_and_mortar.client.integration;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.recipe.KilnRecipe;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModMenus;
import com.evandev.brick_and_mortar.registry.ModRecipes;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

@EmiEntrypoint
public class BMEmiPlugin implements EmiPlugin {
    public static final ResourceLocation KILN_CATEGORY_ID = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "kiln_firing");
    public static final EmiStack KILN_WORKSTATION = EmiStack.of(ModBlocks.KILN.get());

    public static final EmiRecipeCategory KILN_CATEGORY = new EmiRecipeCategory(
            KILN_CATEGORY_ID,
            KILN_WORKSTATION,
            KILN_WORKSTATION
    );

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(KILN_CATEGORY);

        registry.addWorkstation(KILN_CATEGORY, KILN_WORKSTATION);
        registry.addRecipeHandler(ModMenus.KILN_MENU.get(), new KilnRecipeHandler());

        RecipeManager recipeManager = registry.getRecipeManager();
        for (RecipeHolder<KilnRecipe> recipe : recipeManager.getAllRecipesFor(ModRecipes.KILN_TYPE.get())) {
            registry.addRecipe(new KilnEmiRecipe(recipe));
        }
    }
}