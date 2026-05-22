package com.evandev.brick_and_mortar.block.entity;

import com.evandev.brick_and_mortar.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class KilnBlockEntity extends BlockEntity {

    public KilnBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.KILN_BLOCK_ENTITY.get(), pos, blockState);
    }

    // TODO: container, fuel consumption, and double speed brick/stone smelting
}