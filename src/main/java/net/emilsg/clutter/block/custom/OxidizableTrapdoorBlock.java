package net.emilsg.clutter.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public class OxidizableTrapdoorBlock extends TrapdoorBlock implements Oxidizable {

    private final OxidationLevel oxidationLevel;

    public OxidizableTrapdoorBlock(OxidationLevel oxidationLevel, Settings settings, SoundEvent closeSound, SoundEvent openSound) {
        super(settings, closeSound, openSound);
        this.oxidationLevel = oxidationLevel;
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction direction = ctx.getSide();
        blockState = ctx.canReplaceExisting() || !direction.getAxis().isHorizontal() ? (BlockState)((BlockState)blockState.with(FACING, ctx.getPlayerFacing())).with(HALF, direction == Direction.UP ? BlockHalf.BOTTOM : BlockHalf.TOP) : (BlockState)((BlockState)blockState.with(FACING, direction)).with(HALF, ctx.getHitPos().y - (double)ctx.getBlockPos().getY() > 0.5 ? BlockHalf.TOP : BlockHalf.BOTTOM);
        if (ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos())) {
            blockState = (BlockState)((BlockState)blockState.with(OPEN, true)).with(POWERED, true);
        }
        return (BlockState)blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    public boolean hasRandomTicks(BlockState state) {
        return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }

    public OxidationLevel getDegradationLevel() {
        return this.oxidationLevel;
    }
}
