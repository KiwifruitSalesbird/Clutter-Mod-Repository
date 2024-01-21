package net.emilsg.clutter.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.emilsg.clutter.world.gen.type.ModFoliagePlacerTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class RedwoodFoliagePlacer extends FoliagePlacer {

    public static final Codec<RedwoodFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) ->
            fillFoliagePlacerFields(instance).and(
                    IntProvider.POSITIVE_CODEC.fieldOf("crown_height").forGetter((placer) -> placer.crownHeight)
            ).apply(instance, RedwoodFoliagePlacer::new)
    );

    private final IntProvider crownHeight;

    public RedwoodFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider crownHeight) {
        super(radius, offset);
        this.crownHeight = crownHeight;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModFoliagePlacerTypes.REDWOOD_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos blockPos = treeNode.getCenter();
        int y = blockPos.getY() + offset;
        int[] radii = new int[]{1, 1, 2, 3, 1, 3, 4, 2, 4, 5, 3, 4, 5, 3, 2, 4, 3};
        int[] radii2 = new int[]{1, 1, 2, 1, 2, 3, 4, 2, 4, 5, 3, 4, 7, 4, 3, 5, 3, 2, 4, 2, 3, 5, 3, 2, 4, 2};

        top(trunkHeight >= 40 ? radii2 : radii, y, random, blockPos, config, placer, world);

    }

    private void top(int[] radii, int y, Random random, BlockPos blockPos, TreeFeatureConfig config, FoliagePlacer.BlockPlacer placer, TestableWorld world) {
        for (int i = 0; i < radii.length; i++) {
            int currentRadius = radii[i];
            if (i == 0) {
                placer.placeBlock(blockPos, config.foliageProvider.get(random, blockPos));
            } else {
                this.generateSquareWithHangingLeaves(world, placer, random, config, new BlockPos(blockPos.getX(), y - i, blockPos.getZ()), currentRadius, 0, false, 0.5f, 0.0f);
            }
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return crownHeight.get(random);
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        if (dx + dz >= 7) {
            return true;
        }
        return dx * dx + dz * dz > radius * radius;
    }
}
