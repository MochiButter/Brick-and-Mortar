package com.evandev.brick_and_mortar.registry;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.menu.KilnMenu;
import com.evandev.brick_and_mortar.platform.Services;
import com.evandev.brick_and_mortar.platform.registry.RegistrationProvider;
import com.evandev.brick_and_mortar.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
    public static final RegistrationProvider<MenuType<?>> MENUS = RegistrationProvider.get(Registries.MENU, Constants.MOD_ID);

    public static final RegistryObject<MenuType<KilnMenu>> KILN_MENU = MENUS.register("kiln", () ->
            Services.PLATFORM.createMenuType(KilnMenu::new));

    public static void init() {}
}