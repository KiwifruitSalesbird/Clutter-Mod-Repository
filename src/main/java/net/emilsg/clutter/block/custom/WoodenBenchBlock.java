package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.util.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class WoodenBenchBlock extends SeatBlock {

    public static final EnumProperty<WoodenBenchBlock.LegPosition> LEGPOSITIONS = EnumProperty.of("leg_positions", LegPosition.class);

    protected static final VoxelShape NORTH_NONE_SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0, 8.0, 3.0, 16.0, 11.0, 5.0),
            Block.createCuboidShape(0.0, 11.0, 2.0, 16.0, 14.0, 4.0), Block.createCuboidShape(0.0, 13.0, 1.0, 16.0, 16.0, 3.0),
            Block.createCuboidShape(0.0, 6.0, 5.0, 16.0, 7.0, 7.0), Block.createCuboidShape(0.0, 7.0, 4.0, 16.0, 8.0, 14.0),
            Block.createCuboidShape(0.0, 6.0, 11.0, 16.0, 7.0, 13.0));
    protected static final VoxelShape NORTH_ALL_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 13.0, 3.0, 16.0, 16.0, 4.0), Block.createCuboidShape(0.0, 7.0, 4.0, 16.0, 8.0, 14.0),
            Block.createCuboidShape(14.0, 0.0, 11.0, 15.0, 6.0, 13.0), Block.createCuboidShape(14.0, 0.0, 5.0, 15.0, 6.0, 7.0),
            Block.createCuboidShape(14.0, 6.0, 4.0, 15.0, 7.0, 14.0), Block.createCuboidShape(2.0, 6.0, 11.0, 14.0, 7.0, 13.0),
            Block.createCuboidShape(2.0, 6.0, 5.0, 14.0, 7.0, 7.0), Block.createCuboidShape(1.0, 6.0, 4.0, 2.0, 7.0, 14.0),
            Block.createCuboidShape(1.0, 0.0, 11.0, 2.0, 6.0, 13.0), Block.createCuboidShape(1.0, 0.0, 5.0, 2.0, 6.0, 7.0),
            Block.createCuboidShape(0.0, 8.0, 3.0, 16.0, 11.0, 5.0), Block.createCuboidShape(0.0, 11.0, 2.0, 16.0, 14.0, 4.0));
    protected static final VoxelShape NORTH_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 8.0, 3.0, 16.0, 11.0, 5.0), Block.createCuboidShape(0.0, 11.0, 2.0, 16.0, 14.0, 4.0),
            Block.createCuboidShape(0.0, 13.0, 1.0, 16.0, 16.0, 3.0), Block.createCuboidShape(0.0, 7.0, 4.0, 16.0, 8.0, 14.0),
            Block.createCuboidShape(14.0, 0.0, 11.0, 15.0, 6.0, 13.0), Block.createCuboidShape(14.0, 0.0, 5.0, 15.0, 6.0, 7.0),
            Block.createCuboidShape(14.0, 6.0, 4.0, 15.0, 7.0, 14.0), Block.createCuboidShape(0.0, 6.0, 11.0, 14.0, 7.0, 13.0),
            Block.createCuboidShape(0.0, 6.0, 5.0, 14.0, 7.0, 7.0));
    protected static final VoxelShape NORTH_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 8.0, 3.0, 16.0, 11.0, 5.0), Block.createCuboidShape(0.0, 11.0, 2.0, 16.0, 14.0, 4.0),
            Block.createCuboidShape(0.0, 13.0, 1.0, 16.0, 16.0, 3.0), Block.createCuboidShape(0.0, 7.0, 4.0, 16.0, 8.0, 14.0),
            Block.createCuboidShape(1.0, 0.0, 11.0, 2.0, 6.0, 13.0), Block.createCuboidShape(1.0, 0.0, 5.0, 2.0, 6.0, 7.0),
            Block.createCuboidShape(1.0, 6.0, 4.0, 2.0, 7.0, 14.0), Block.createCuboidShape(2.0, 6.0, 11.0, 16.0, 7.0, 13.0),
            Block.createCuboidShape(2.0, 6.0, 5.0, 16.0, 7.0, 7.0));
    protected static final VoxelShape SOUTH_NONE_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 8.0, 11.0, 16.0, 11.0, 13.0), Block.createCuboidShape(0.0, 11.0, 12.0, 16.0, 14.0, 14.0),
            Block.createCuboidShape(0.0, 13.0, 13.0, 16.0, 16.0, 15.0), Block.createCuboidShape(0.0, 6.0, 9.0, 16.0, 7.0, 11.0),
            Block.createCuboidShape(0.0, 7.0, 2.0, 16.0, 8.0, 12.0), Block.createCuboidShape(0.0, 6.0, 3.0, 16.0, 7.0, 5.0));
    protected static final VoxelShape SOUTH_ALL_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 8.0, 11.0, 16.0, 11.0, 13.0), Block.createCuboidShape(0.0, 7.0, 2.0, 16.0, 8.0, 12.0),
            Block.createCuboidShape(1.0, 0.0, 3.0, 2.0, 6.0, 5.0), Block.createCuboidShape(1.0, 0.0, 9.0, 2.0, 6.0, 11.0),
            Block.createCuboidShape(1.0, 6.0, 2.0, 2.0, 7.0, 12.0), Block.createCuboidShape(2.0, 6.0, 3.0, 14.0, 7.0, 5.0),
            Block.createCuboidShape(2.0, 6.0, 9.0, 14.0, 7.0, 11.0), Block.createCuboidShape(14.0, 6.0, 2.0, 15.0, 7.0, 12.0),
            Block.createCuboidShape(14.0, 0.0, 3.0, 15.0, 6.0, 5.0), Block.createCuboidShape(14.0, 0.0, 9.0, 15.0, 6.0, 11.0),
            Block.createCuboidShape(0.0, 11.0, 12.0, 16.0, 14.0, 14.0), Block.createCuboidShape(0.0, 13.0, 13.0, 16.0, 16.0, 15.0));
    protected static final VoxelShape SOUTH_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 8.0, 11.0, 16.0, 11.0, 13.0), Block.createCuboidShape(0.0, 11.0, 12.0, 16.0, 14.0, 14.0),
            Block.createCuboidShape(0.0, 13.0, 13.0, 16.0, 16.0, 15.0), Block.createCuboidShape(0.0, 7.0, 2.0, 16.0, 8.0, 12.0),
            Block.createCuboidShape(14.0, 0.0, 3.0, 15.0, 6.0, 5.0), Block.createCuboidShape(14.0, 0.0, 9.0, 15.0, 6.0, 11.0),
            Block.createCuboidShape(14.0, 6.0, 2.0, 15.0, 7.0, 12.0), Block.createCuboidShape(0.0, 6.0, 3.0, 14.0, 7.0, 5.0),
            Block.createCuboidShape(0.0, 6.0, 9.0, 14.0, 7.0, 11.0));
    protected static final VoxelShape SOUTH_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 8.0, 11.0, 16.0, 11.0, 13.0), Block.createCuboidShape(0.0, 11.0, 12.0, 16.0, 14.0, 14.0),
            Block.createCuboidShape(0.0, 13.0, 13.0, 16.0, 16.0, 15.0), Block.createCuboidShape(0.0, 7.0, 2.0, 16.0, 8.0, 12.0),
            Block.createCuboidShape(1.0, 0.0, 3.0, 2.0, 6.0, 5.0), Block.createCuboidShape(1.0, 0.0, 9.0, 2.0, 6.0, 11.0),
            Block.createCuboidShape(1.0, 6.0, 2.0, 2.0, 7.0, 12.0), Block.createCuboidShape(2.0, 6.0, 3.0, 16.0, 7.0, 5.0),
            Block.createCuboidShape(2.0, 6.0, 9.0, 16.0, 7.0, 11.0));
    protected static final VoxelShape WEST_NONE_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3.0, 8.0, 0.0, 5.0, 11.0, 16.0), Block.createCuboidShape(2.0, 11.0, 0.0, 4.0, 14.0, 16.0),
            Block.createCuboidShape(1.0, 13.0, 0.0, 3.0, 16.0, 16.0), Block.createCuboidShape(5.0, 6.0, 0.0, 7.0, 7.0, 16.0),
            Block.createCuboidShape(4.0, 7.0, 0.0, 14.0, 8.0, 16.0), Block.createCuboidShape(11.0, 6.0, 0.0, 13.0, 7.0, 16.0));
    protected static final VoxelShape WEST_ALL_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3.0, 8.0, 0.0, 5.0, 11.0, 16.0), Block.createCuboidShape(4.0, 7.0, 0.0, 14.0, 8.0, 16.0),
            Block.createCuboidShape(11.0, 0.0, 1.0, 13.0, 6.0, 2.0), Block.createCuboidShape(5.0, 0.0, 1.0, 7.0, 6.0, 2.0),
            Block.createCuboidShape(4.0, 6.0, 1.0, 14.0, 7.0, 2.0), Block.createCuboidShape(11.0, 6.0, 2.0, 13.0, 7.0, 14.0),
            Block.createCuboidShape(5.0, 6.0, 2.0, 7.0, 7.0, 14.0),Block.createCuboidShape(4.0, 6.0, 14.0, 14.0, 7.0, 15.0),
            Block.createCuboidShape(11.0, 0.0, 14.0, 13.0, 6.0, 15.0), Block.createCuboidShape(5.0, 0.0, 14.0, 7.0, 6.0, 15.0),
            Block.createCuboidShape(2.0, 11.0, 0.0, 4.0, 14.0, 16.0), Block.createCuboidShape(1.0, 13.0, 0.0, 3.0, 16.0, 16.0));
    protected static final VoxelShape WEST_NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3.0, 8.0, 0.0, 5.0, 11.0, 16.0), Block.createCuboidShape(2.0, 11.0, 0.0, 4.0, 14.0, 16.0),
            Block.createCuboidShape(1.0, 13.0, 0.0, 3.0, 16.0, 16.0), Block.createCuboidShape(4.0, 7.0, 0.0, 14.0, 8.0, 16.0),
            Block.createCuboidShape(11.0, 0.0, 1.0, 13.0, 6.0, 2.0), Block.createCuboidShape(5.0, 0.0, 1.0, 7.0, 6.0, 2.0),
            Block.createCuboidShape(4.0, 6.0, 1.0, 14.0, 7.0, 2.0), Block.createCuboidShape(11.0, 6.0, 2.0, 13.0, 7.0, 16.0),
            Block.createCuboidShape(5.0, 6.0, 2.0, 7.0, 7.0, 16.0));
    protected static final VoxelShape WEST_SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3.0, 8.0, 0.0, 5.0, 11.0, 16.0), Block.createCuboidShape(2.0, 11.0, 0.0, 4.0, 14.0, 16.0),
            Block.createCuboidShape(1.0, 13.0, 0.0, 3.0, 16.0, 16.0), Block.createCuboidShape(4.0, 7.0, 0.0, 14.0, 8.0, 16.0),
            Block.createCuboidShape(11.0, 0.0, 14.0, 13.0, 6.0, 15.0), Block.createCuboidShape(5.0, 0.0, 14.0, 7.0, 6.0, 15.0),
            Block.createCuboidShape(4.0, 6.0, 14.0, 14.0, 7.0, 15.0), Block.createCuboidShape(11.0, 6.0, 0.0, 13.0, 7.0, 14.0),
            Block.createCuboidShape(5.0, 6.0, 0.0, 7.0, 7.0, 14.0));
    protected static final VoxelShape EAST_NONE_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(11.0, 8.0, 0.0, 13.0, 11.0, 16.0), Block.createCuboidShape(12.0, 11.0, 0.0, 14.0, 14.0, 16.0),
            Block.createCuboidShape(13.0, 13.0, 0.0, 15.0, 16.0, 16.0), Block.createCuboidShape(9.0, 6.0, 0.0, 11.0, 7.0, 16.0),
            Block.createCuboidShape(2.0, 7.0, 0.0, 12.0, 8.0, 16.0), Block.createCuboidShape(3.0, 6.0, 0.0, 5.0, 7.0, 16.0));
    protected static final VoxelShape EAST_ALL_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(11.0, 8.0, 0.0, 13.0, 11.0, 16.0), Block.createCuboidShape(2.0, 7.0, 0.0, 12.0, 8.0, 16.0),
            Block.createCuboidShape(3.0, 0.0, 14.0, 5.0, 6.0, 15.0), Block.createCuboidShape(9.0, 0.0, 14.0, 11.0, 6.0, 15.0),
            Block.createCuboidShape(2.0, 6.0, 14.0, 12.0, 7.0, 15.0), Block.createCuboidShape(3.0, 6.0, 2.0, 5.0, 7.0, 14.0),
            Block.createCuboidShape(9.0, 6.0, 2.0, 11.0, 7.0, 14.0), Block.createCuboidShape(2.0, 6.0, 1.0, 12.0, 7.0, 2.0),
            Block.createCuboidShape(3.0, 0.0, 1.0, 5.0, 6.0, 2.0), Block.createCuboidShape(9.0, 0.0, 1.0, 11.0, 6.0, 2.0),
            Block.createCuboidShape(12.0, 11.0, 0.0, 14.0, 14.0, 16.0), Block.createCuboidShape(13.0, 13.0, 0.0, 15.0, 16.0, 16.0));
    protected static final VoxelShape EAST_NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(11.0, 8.0, 0.0, 13.0, 11.0, 16.0), Block.createCuboidShape(12.0, 11.0, 0.0, 14.0, 14.0, 16.0),
            Block.createCuboidShape(13.0, 13.0, 0.0, 15.0, 16.0, 16.0), Block.createCuboidShape(2.0, 7.0, 0.0, 12.0, 8.0, 16.0),
            Block.createCuboidShape(3.0, 0.0, 1.0, 5.0, 6.0, 2.0), Block.createCuboidShape(9.0, 0.0, 1.0, 11.0, 6.0, 2.0),
            Block.createCuboidShape(2.0, 6.0, 1.0, 12.0, 7.0, 2.0), Block.createCuboidShape(3.0, 6.0, 2.0, 5.0, 7.0, 16.0),
            Block.createCuboidShape(9.0, 6.0, 2.0, 11.0, 7.0, 16.0));
    protected static final VoxelShape EAST_SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(11.0, 8.0, 0.0, 13.0, 11.0, 16.0), Block.createCuboidShape(12.0, 11.0, 0.0, 14.0, 14.0, 16.0),
            Block.createCuboidShape(13.0, 13.0, 0.0, 15.0, 16.0, 16.0), Block.createCuboidShape(2.0, 7.0, 0.0, 12.0, 8.0, 16.0),
            Block.createCuboidShape(3.0, 0.0, 14.0, 5.0, 6.0, 15.0), Block.createCuboidShape(9.0, 0.0, 14.0, 11.0, 6.0, 15.0),
            Block.createCuboidShape(2.0, 6.0, 14.0, 12.0, 7.0, 15.0), Block.createCuboidShape(3.0, 6.0, 0.0, 5.0, 7.0, 14.0),
            Block.createCuboidShape(9.0, 6.0, 0.0, 11.0, 7.0, 14.0));

    public WoodenBenchBlock(Settings settings) {
        super(settings);
    }

    private enum LegPosition implements StringIdentifiable {
        NONE("none"),
        NORTH("north"),
        SOUTH("south"),
        EAST("east"),
        WEST("west"),
        ALL("all");

        private final String name;

        LegPosition(String name) {
            this.name = name;
        }

        public String asString() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static LegPosition fromNeighborBlocks(boolean west, boolean north, boolean east, boolean south) {
            if (west) {
                return LegPosition.WEST;
            } else if (east) {
                return LegPosition.EAST;
            } else if (north) {
                return LegPosition.NORTH;
            } else if (south) {
                return LegPosition.SOUTH;
            } else {
                return LegPosition.ALL;
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction i = state.get(FACING);
        LegPosition j = state.get(LEGPOSITIONS);
        if (i == Direction.NORTH) {
            return switch (j) {
                case ALL -> NORTH_ALL_SHAPE;
                case NONE -> NORTH_NONE_SHAPE;
                case EAST -> NORTH_EAST_SHAPE;
                case WEST -> NORTH_WEST_SHAPE;
                default -> VoxelShapes.empty();
            };
        } else if (i == Direction.SOUTH) {
            return switch (j) {
                case ALL -> SOUTH_ALL_SHAPE;
                case NONE -> SOUTH_NONE_SHAPE;
                case EAST -> SOUTH_EAST_SHAPE;
                case WEST -> SOUTH_WEST_SHAPE;
                default -> VoxelShapes.empty();
            };
        } else if (i == Direction.EAST) {
            return switch (j) {
                case ALL -> EAST_ALL_SHAPE;
                case NONE -> EAST_NONE_SHAPE;
                case SOUTH -> EAST_SOUTH_SHAPE;
                case NORTH -> EAST_NORTH_SHAPE;
                default -> VoxelShapes.empty();
            };
        } else if (i == Direction.WEST) {
            return switch (j) {
                case ALL -> WEST_ALL_SHAPE;
                case NONE -> WEST_NONE_SHAPE;
                case SOUTH -> WEST_SOUTH_SHAPE;
                case NORTH -> WEST_NORTH_SHAPE;
                default -> VoxelShapes.empty();
            };
        }
        return VoxelShapes.empty();
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos;
        World worldAccess = ctx.getWorld();
        boolean bl = worldAccess.getFluidState(blockPos = ctx.getBlockPos()).getFluid() == Fluids.WATER;
        return (BlockState)this.getDefaultState().with(WATERLOGGED, bl).with(FACING, ctx.getHorizontalPlayerFacing());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, LEGPOSITIONS);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        updateBenchLegs(world, pos, state);

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() instanceof AxeItem && state.isIn(ModBlockTags.STRIPPABLE_BENCHES)) {
            BlockState strippedState = getStrippedState(state);
            world.setBlockState(pos, strippedState);
            world.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                itemStack.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
            }
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

    private void updateBenchLegs(World world, BlockPos pos, BlockState state) {
        boolean eastBench = world.getBlockState(pos.east()).getBlock() instanceof WoodenBenchBlock;
        boolean westBench = world.getBlockState(pos.west()).getBlock() instanceof WoodenBenchBlock;
        boolean northBench = world.getBlockState(pos.north()).getBlock() instanceof WoodenBenchBlock;
        boolean southBench = world.getBlockState(pos.south()).getBlock() instanceof WoodenBenchBlock;

        boolean facingNS = state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH;
        boolean facingEW = state.get(FACING) == Direction.EAST || state.get(FACING) == Direction.WEST;

        if (facingNS) {
            if (eastBench && westBench) {
                world.setBlockState(pos, state.with(LEGPOSITIONS, LegPosition.NONE));
            } else if (!eastBench && !westBench) {
                world.setBlockState(pos, state.with(LEGPOSITIONS, LegPosition.ALL));
            } else if (westBench) {
                world.setBlockState(pos, state.with(LEGPOSITIONS, LegPosition.EAST));
            } else if (eastBench) {
                world.setBlockState(pos, state.with(LEGPOSITIONS, LegPosition.WEST));
            }
        }
        else if (facingEW) {
            if (northBench && southBench) {
                world.setBlockState(pos, state.with(LEGPOSITIONS, LegPosition.NONE));
            } else if (!northBench && !southBench) {
                world.setBlockState(pos, state.with(LEGPOSITIONS, LegPosition.ALL));
            } else if (northBench) {
                world.setBlockState(pos, state.with(LEGPOSITIONS, LegPosition.SOUTH));
            } else if (southBench) {
                world.setBlockState(pos, state.with(LEGPOSITIONS, LegPosition.NORTH));
            }
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        boolean eastBench = world.getBlockState(pos.east()).getBlock() instanceof WoodenBenchBlock;
        boolean westBench = world.getBlockState(pos.west()).getBlock() instanceof WoodenBenchBlock;
        boolean northBench = world.getBlockState(pos.north()).getBlock() instanceof WoodenBenchBlock;
        boolean southBench = world.getBlockState(pos.south()).getBlock() instanceof WoodenBenchBlock;

        boolean facingNS = state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH;
        boolean facingEW = state.get(FACING) == Direction.EAST || state.get(FACING) == Direction.WEST;

        if (facingNS) {
            if (eastBench && westBench) {
                return state.with(LEGPOSITIONS, LegPosition.NONE);
            } else if (!eastBench && !westBench) {
                return state.with(LEGPOSITIONS, LegPosition.ALL);
            } else if (westBench) {
                return state.with(LEGPOSITIONS, LegPosition.EAST);
            } else if (eastBench) {
                return state.with(LEGPOSITIONS, LegPosition.WEST);
            }
        }
        else if (facingEW) {
            if (northBench && southBench) {
                return state.with(LEGPOSITIONS, LegPosition.NONE);
            } else if (!northBench && !southBench) {
                return state.with(LEGPOSITIONS, LegPosition.ALL);
            } else if (northBench) {
                return state.with(LEGPOSITIONS, LegPosition.SOUTH);
            } else if (southBench) {
                return state.with(LEGPOSITIONS, LegPosition.NORTH);
            }
        }

        LegPosition legPositions = LegPosition.fromNeighborBlocks(westBench, northBench, eastBench, southBench);
        return state.with(LEGPOSITIONS, legPositions);
    }

    private BlockState getStrippedState(BlockState state) {
        if (state.getBlock() == ModBlocks.OAK_BENCH) {
            return ModBlocks.STRIPPED_OAK_BENCH.getDefaultState().with(FACING, state.get(FACING)).with(LEGPOSITIONS, state.get(LEGPOSITIONS));
        } else if (state.getBlock() == ModBlocks.SPRUCE_BENCH) {
            return ModBlocks.STRIPPED_SPRUCE_BENCH.getDefaultState().with(FACING, state.get(FACING)).with(LEGPOSITIONS, state.get(LEGPOSITIONS));
        } else if (state.getBlock() == ModBlocks.BIRCH_BENCH) {
            return ModBlocks.STRIPPED_BIRCH_BENCH.getDefaultState().with(FACING, state.get(FACING)).with(LEGPOSITIONS, state.get(LEGPOSITIONS));
        } else if (state.getBlock() == ModBlocks.JUNGLE_BENCH) {
            return ModBlocks.STRIPPED_JUNGLE_BENCH.getDefaultState().with(FACING, state.get(FACING)).with(LEGPOSITIONS, state.get(LEGPOSITIONS));
        } else if (state.getBlock() == ModBlocks.ACACIA_BENCH) {
            return ModBlocks.STRIPPED_ACACIA_BENCH.getDefaultState().with(FACING, state.get(FACING)).with(LEGPOSITIONS, state.get(LEGPOSITIONS));
        } else if (state.getBlock() == ModBlocks.DARK_OAK_BENCH) {
            return ModBlocks.STRIPPED_DARK_OAK_BENCH.getDefaultState().with(FACING, state.get(FACING)).with(LEGPOSITIONS, state.get(LEGPOSITIONS));
        } else if (state.getBlock() == ModBlocks.MANGROVE_BENCH) {
            return ModBlocks.STRIPPED_MANGROVE_BENCH.getDefaultState().with(FACING, state.get(FACING)).with(LEGPOSITIONS, state.get(LEGPOSITIONS));
        } else if (state.getBlock() == ModBlocks.CRIMSON_BENCH) {
            return ModBlocks.STRIPPED_CRIMSON_BENCH.getDefaultState().with(FACING, state.get(FACING)).with(LEGPOSITIONS, state.get(LEGPOSITIONS));
        } else if (state.getBlock() == ModBlocks.WARPED_BENCH) {
            return ModBlocks.STRIPPED_WARPED_BENCH.getDefaultState().with(FACING, state.get(FACING)).with(LEGPOSITIONS, state.get(LEGPOSITIONS));
        } else if (state.getBlock() == ModBlocks.CHERRY_BENCH) {
            return ModBlocks.STRIPPED_CHERRY_BENCH.getDefaultState().with(FACING, state.get(FACING));
        }  else {
            return state;
        }
    }
}