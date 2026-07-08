package com.evandev.brick_and_mortar.registry;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.block.KilnBlock;
import com.evandev.brick_and_mortar.block.ModStairBlock;
import com.evandev.brick_and_mortar.platform.registry.RegistrationProvider;
import com.evandev.brick_and_mortar.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ModBlocks {
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);

    public static final RegistryObject<Block> KILN = BLOCKS.register("kiln", () ->
            new KilnBlock(BlockBehaviour.Properties.of()
                    .strength(3.5F)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(KilnBlock.LIT) ? 13 : 0)));

    public static final List<RegistryObject<Block>> ALL_DECORATIVE_BLOCKS = new ArrayList<>();
    public static final List<DecorativeFamily> FAMILIES = new ArrayList<>();
    public static final List<RegistryObject<Block>> CHISELED_BLOCKS = new ArrayList<>();

    public static final Map<String, List<RegistryObject<Block>>> COMPAT_BLOCKS = new Hashtable<>();

    public static final DecorativeFamily BLACK_BRICKS = registerFamily("black_bricks");
    public static final DecorativeFamily BLUE_BRICKS = registerFamily("blue_bricks");
    public static final DecorativeFamily BROWN_BRICKS = registerFamily("brown_bricks");
    public static final DecorativeFamily CREAM_BRICKS = registerFamily("cream_bricks");
    public static final DecorativeFamily GRAY_BRICKS = registerFamily("gray_bricks");
    public static final DecorativeFamily ORANGE_BRICKS = registerFamily("orange_bricks");
    public static final DecorativeFamily TAN_BRICKS = registerFamily("tan_bricks");

    public static final DecorativeFamily BLACK_TILES = registerFamily("black_tiles");
    public static final DecorativeFamily BLUE_TILES = registerFamily("blue_tiles");
    public static final DecorativeFamily BROWN_TILES = registerFamily("brown_tiles");
    public static final DecorativeFamily CREAM_TILES = registerFamily("cream_tiles");
    public static final DecorativeFamily GRAY_TILES = registerFamily("gray_tiles");
    public static final DecorativeFamily ORANGE_TILES = registerFamily("orange_tiles");
    public static final DecorativeFamily TAN_TILES = registerFamily("tan_tiles");
    public static final DecorativeFamily BRICK_TILES = registerFamily("brick_tiles");
    public static final DecorativeFamily NETHER_TILES = registerFamily("nether_tiles");

    public static final DecorativeFamily CHARRED_NETHER_BRICKS = registerFamily("charred_nether_bricks");
    public static final DecorativeFamily CHARRED_SOUL_NETHER_BRICKS = registerFamily("charred_soul_nether_bricks");
    public static final DecorativeFamily LIGHT_NETHER_BRICKS = registerFamily("light_nether_bricks");
    public static final DecorativeFamily LIGHT_SOUL_NETHER_BRICKS = registerFamily("light_soul_nether_bricks");
    public static final DecorativeFamily RAW_NETHER_BRICKS = registerFamily("raw_nether_bricks");
    public static final DecorativeFamily RAW_SOUL_NETHER_BRICKS = registerFamily("raw_soul_nether_bricks");
    public static final DecorativeFamily SOUL_NETHER_BRICKS = registerFamily("soul_nether_bricks");

    public static final DecorativeFamily CHARRED_NETHER_TILES = registerFamily("charred_nether_tiles");
    public static final DecorativeFamily CHARRED_SOUL_NETHER_TILES = registerFamily("charred_soul_nether_tiles");
    public static final DecorativeFamily LIGHT_NETHER_TILES = registerFamily("light_nether_tiles");
    public static final DecorativeFamily LIGHT_SOUL_NETHER_TILES = registerFamily("light_soul_nether_tiles");
    public static final DecorativeFamily RAW_NETHER_TILES = registerFamily("raw_nether_tiles");
    public static final DecorativeFamily RAW_SOUL_NETHER_TILES = registerFamily("raw_soul_nether_tiles");
    public static final DecorativeFamily SOUL_NETHER_TILES = registerFamily("soul_nether_tiles");

    public static final DecorativeFamily BURNT_PURPUR_BLOCK = registerFamily("burnt_purpur_block");
    public static final DecorativeFamily BURNT_SOUL_PURPUR_BLOCK = registerFamily("burnt_soul_purpur_block");
    public static final DecorativeFamily LIGHT_PURPUR_BLOCK = registerFamily("light_purpur_block");
    public static final DecorativeFamily LIGHT_SOUL_PURPUR_BLOCK = registerFamily("light_soul_purpur_block");
    public static final DecorativeFamily RAW_PURPUR_BLOCK = registerFamily("raw_purpur_block");
    public static final DecorativeFamily RAW_SOUL_PURPUR_BLOCK = registerFamily("raw_soul_purpur_block");
    public static final DecorativeFamily SOUL_PURPUR_BLOCK = registerFamily("soul_purpur_block");

    public static final DecorativeFamily PURPUR_BRICKS = registerFamily("purpur_bricks");
    public static final DecorativeFamily BURNT_PURPUR_BRICKS = registerFamily("burnt_purpur_bricks");
    public static final DecorativeFamily BURNT_SOUL_PURPUR_BRICKS = registerFamily("burnt_soul_purpur_bricks");
    public static final DecorativeFamily LIGHT_PURPUR_BRICKS = registerFamily("light_purpur_bricks");
    public static final DecorativeFamily LIGHT_SOUL_PURPUR_BRICKS = registerFamily("light_soul_purpur_bricks");
    public static final DecorativeFamily RAW_PURPUR_BRICKS = registerFamily("raw_purpur_bricks");
    public static final DecorativeFamily RAW_SOUL_PURPUR_BRICKS = registerFamily("raw_soul_purpur_bricks");
    public static final DecorativeFamily SOUL_PURPUR_BRICKS = registerFamily("soul_purpur_bricks");

    public static final RegistryObject<Block> CHISELED_PURPUR = registerChiseled("chiseled_purpur");
    public static final RegistryObject<Block> CHISELED_BURNT_PURPUR = registerChiseled("chiseled_burnt_purpur");
    public static final RegistryObject<Block> CHISELED_BURNT_SOUL_PURPUR = registerChiseled("chiseled_burnt_soul_purpur");
    public static final RegistryObject<Block> CHISELED_LIGHT_PURPUR = registerChiseled("chiseled_light_purpur");
    public static final RegistryObject<Block> CHISELED_LIGHT_SOUL_PURPUR = registerChiseled("chiseled_light_soul_purpur");
    public static final RegistryObject<Block> CHISELED_RAW_PURPUR = registerChiseled("chiseled_raw_purpur");
    public static final RegistryObject<Block> CHISELED_RAW_SOUL_PURPUR = registerChiseled("chiseled_raw_soul_purpur");
    public static final RegistryObject<Block> CHISELED_SOUL_PURPUR = registerChiseled("chiseled_soul_purpur");

    public static DecorativeFamily registerFamily(String name) {
        RegistryObject<Block> base = BLOCKS.register(name, () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
        String childName = name.replace("bricks", "brick").replace("tiles", "tile").replace("_block", "");

        RegistryObject<Block> stairs = BLOCKS.register(childName + "_stairs", () -> new ModStairBlock(Blocks.BRICKS.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BRICKS)));
        RegistryObject<Block> slab = BLOCKS.register(childName + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)));

        RegistryObject<Block> wall = null;
        RegistryObject<Block> pillar = null;

        if (!name.contains("purpur") || name.contains("bricks") || name.contains("tiles")) {
            wall = BLOCKS.register(childName + "_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
        } else {
            pillar = BLOCKS.register(childName + "_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_PILLAR)));
        }

        DecorativeFamily family = new DecorativeFamily(base, stairs, slab, wall, pillar);
        FAMILIES.add(family);

        ALL_DECORATIVE_BLOCKS.add(base);
        ALL_DECORATIVE_BLOCKS.add(stairs);
        ALL_DECORATIVE_BLOCKS.add(slab);

        if (wall != null) ALL_DECORATIVE_BLOCKS.add(wall);
        if (pillar != null) ALL_DECORATIVE_BLOCKS.add(pillar);

        return family;
    }

    public static DecorativeFamily registerFamily(String name, String compatMod) {
        DecorativeFamily family = registerFamily(name);
        List<RegistryObject<Block>> modBlocksList = COMPAT_BLOCKS.computeIfAbsent(compatMod, k -> new ArrayList<>());
        modBlocksList.add(family.base);
        modBlocksList.add(family.stairs);
        modBlocksList.add(family.slab);
        if (family.wall != null) modBlocksList.add(family.wall);
        if (family.pillar != null) modBlocksList.add(family.pillar);
        return family;
    }

    public static RegistryObject<Block> registerChiseled(String name) {
        RegistryObject<Block> block = BLOCKS.register(name, () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
        CHISELED_BLOCKS.add(block);
        ALL_DECORATIVE_BLOCKS.add(block);
        return block;
    }

    public static void init() {
    }

    public record DecorativeFamily(RegistryObject<Block> base, RegistryObject<Block> stairs, RegistryObject<Block> slab,
                                   @Nullable RegistryObject<Block> wall, @Nullable RegistryObject<Block> pillar) {
    }
}