package net.emilsg.clutter.block.custom.oxidizable;

import net.emilsg.clutter.block.custom.LargeChainBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class OxidizableLargeChainBlock extends LargeChainBlock implements Oxidizable {

    private final OxidationLevel oxidationLevel;

    public OxidizableLargeChainBlock(OxidationLevel oxidationLevel, Settings settings) {
        super(settings);
        this.oxidationLevel = oxidationLevel;
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
