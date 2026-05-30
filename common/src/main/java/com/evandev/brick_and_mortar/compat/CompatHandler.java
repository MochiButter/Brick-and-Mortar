package com.evandev.brick_and_mortar.compat;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.platform.Services;

import java.util.ArrayList;
import java.util.List;

public class CompatHandler {
    private static final List<Runnable> COMPAT_TASKS = new ArrayList<>();

    static {
        register(CompatMods.SUPPLEMENTARIES, SupplementariesCompat::init);

        // register(CompatMods.ARCHITECTS_PALETTE, ArchitectsPaletteCompat::init);
    }

    public static boolean isDatagen() {
        return System.getProperty("fabric-api.datagen") != null;
    }

    public static boolean shouldLoad(String modId) {
        return isDatagen() || Services.PLATFORM.isModLoaded(modId);
    }

    public static void register(String modId, Runnable initTask) {
        if (shouldLoad(modId)) {
            COMPAT_TASKS.add(initTask);
        }
    }

    public static void init() {
        for (Runnable task : COMPAT_TASKS) {
            try {
                task.run();
            } catch (Exception e) {
                Constants.LOG.error("Failed to initialize a compat module", e);
            }
        }
    }
}