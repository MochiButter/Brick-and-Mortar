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

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);

    public static final RegistryObject<Block> KILN = BLOCKS.register("kiln", () ->
            new KilnBlock(BlockBehaviour.Properties.of()
                    .strength(3.5F)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(KilnBlock.LIT) ? 13 : 0)));

    public static final List<RegistryObject<Block>> ALL_DECORATIVE_BLOCKS = new ArrayList<>();
    public static final List<DecorativeFamily> FAMILIES = new ArrayList<>();
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
    public static final DecorativeFamily RED_TILES = registerFamily("red_tiles");
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
    public static final DecorativeFamily NETHER_TILES = registerFamily("nether_tiles");
    public static final DecorativeFamily BURNT_PURPUR_BLOCK = registerFamily("burnt_purpur_block");
    public static final DecorativeFamily BURNT_SOUL_PURPUR_BLOCK = registerFamily("burnt_soul_purpur_block");
    public static final DecorativeFamily LIGHT_PURPUR_BLOCK = registerFamily("light_purpur_block");
    public static final DecorativeFamily LIGHT_SOUL_PURPUR_BLOCK = registerFamily("light_soul_purpur_block");
    public static final DecorativeFamily RAW_PURPUR_BLOCK = registerFamily("raw_purpur_block");
    public static final DecorativeFamily RAW_SOUL_PURPUR_BLOCK = registerFamily("raw_soul_purpur_block");
    public static final DecorativeFamily SOUL_PURPUR_BLOCK = registerFamily("soul_purpur_block");

    private static DecorativeFamily registerFamily(String name) {
        RegistryObject<Block> base = BLOCKS.register(name, () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));
        String childName = name.replace("bricks", "brick").replace("tiles", "tile").replace("_block", "");

        RegistryObject<Block> stairs = BLOCKS.register(childName + "_stairs", () -> new ModStairBlock(Blocks.BRICKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));
        RegistryObject<Block> slab = BLOCKS.register(childName + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));

        RegistryObject<Block> wall = null;
        RegistryObject<Block> pillar = null;

        if (!name.contains("purpur")) {
            wall = BLOCKS.register(childName + "_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));
        } else {
            pillar = BLOCKS.register(childName + "_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PURPUR_PILLAR)));
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

    public static void init() {
    }

    public record DecorativeFamily(RegistryObject<Block> base, RegistryObject<Block> stairs, RegistryObject<Block> slab,
                                   @Nullable RegistryObject<Block> wall, @Nullable RegistryObject<Block> pillar) {
    }
}