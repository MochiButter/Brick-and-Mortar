package com.evandev.brick_and_mortar.block;

import com.evandev.brick_and_mortar.block.entity.KilnBlockEntity;
import com.evandev.brick_and_mortar.registry.ModBlockEntities;
import com.evandev.brick_and_mortar.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KilnBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty SOUL = BooleanProperty.create("soul");
    public static final BooleanProperty OPEN_FRONT = BooleanProperty.create("open_front");
    public static final BooleanProperty OPEN_LEFT = BooleanProperty.create("open_left");
    public static final BooleanProperty OPEN_BACK = BooleanProperty.create("open_back");
    public static final BooleanProperty OPEN_RIGHT = BooleanProperty.create("open_right");

    public KilnBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, false)
                .setValue(SOUL, false)
                .setValue(OPEN_FRONT, false)
                .setValue(OPEN_LEFT, false)
                .setValue(OPEN_BACK, false)
                .setValue(OPEN_RIGHT, false));
    }


    public static boolean isSoulBase(LevelAccessor level, BlockPos pos) {
        BlockState stateBelow = level.getBlockState(pos.below());
        if (stateBelow.is(BlockTags.SOUL_FIRE_BASE_BLOCKS)) {
            return true;
        }
        if (stateBelow.hasBlockEntity()) {
            return level.getBlockState(pos.below(2)).is(BlockTags.SOUL_FIRE_BASE_BLOCKS);
        }
        return false;
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT, SOUL, OPEN_FRONT, OPEN_LEFT, OPEN_BACK, OPEN_RIGHT);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(LIT)) {
            if (random.nextDouble() < 0.1D) {
                level.playLocalSound((double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D,
                        ModSounds.KILN_CRACKLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean isSoul = isSoulBase(context.getLevel(), context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(SOUL, isSoul);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof KilnBlockEntity kiln) {
            player.openMenu(kiln);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            boolean isSoul = isSoulBase(level, currentPos);

            if (state.getValue(SOUL) != isSoul) {
                return state.setValue(SOUL, isSoul);
            }
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof KilnBlockEntity kilnBlockEntity) {
                Containers.dropContents(level, pos, kilnBlockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new KilnBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, ModBlockEntities.KILN_BLOCK_ENTITY.get(), KilnBlockEntity::serverTick);
    }
}