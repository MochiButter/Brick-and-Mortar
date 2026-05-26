package com.evandev.brick_and_mortar.registry;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.platform.registry.RegistrationProvider;
import com.evandev.brick_and_mortar.platform.registry.RegistryObject;
import com.evandev.brick_and_mortar.recipe.KilnRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {
    public static final RegistrationProvider<RecipeType<?>> RECIPE_TYPES = RegistrationProvider.get(Registries.RECIPE_TYPE, Constants.MOD_ID);
    public static final RegistrationProvider<RecipeSerializer<?>> RECIPE_SERIALIZERS = RegistrationProvider.get(Registries.RECIPE_SERIALIZER, Constants.MOD_ID);

    public static final RegistryObject<RecipeType<KilnRecipe>> KILN_TYPE = RECIPE_TYPES.register("kiln_firing", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return "kiln_firing";
        }
    });

    public static final RegistryObject<RecipeSerializer<KilnRecipe>> KILN_SERIALIZER = RECIPE_SERIALIZERS.register("kiln_firing", KilnRecipe.Serializer::new);

    public static void init() {
    }
}