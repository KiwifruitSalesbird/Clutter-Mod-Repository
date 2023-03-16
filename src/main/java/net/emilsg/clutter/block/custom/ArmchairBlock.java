package net.emilsg.clutter.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public class ArmchairBlock extends SeatBlock{
    protected static final VoxelShape NORTH = VoxelShapes.union(
            Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 2.0, 2.0),
            Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 2.0, 2.0),
            Block.createCuboidShape(0.0, 0.0, 14.0, 2.0, 2.0, 16.0),
            Block.createCuboidShape(14.0, 0.0, 14.0, 16.0, 2.0, 16.0),
            Block.createCuboidShape(0.0, 2.0, 0.0, 16.0, 6.0, 16.0),
            Block.createCuboidShape(0.0, 6.0, 12.0, 16.0, 15.0, 16.0),
            Block.createCuboidShape(1.0, 15.0, 12.0, 15.0, 16.0, 16.0),
            Block.createCuboidShape(0.0, 6.0, 0.0, 2.0, 8.0, 13.0),
            Block.createCuboidShape(14.0, 6.0, 0.0, 16.0, 8.0, 13.0),
            Block.createCuboidShape(0.0, 8.0, 1.0, 2.0, 9.0, 13.0),
            Block.createCuboidShape(14.0, 8.0, 1.0, 16.0, 9.0, 13.0)
    );
    protected static final VoxelShape SOUTH = VoxelShapes.union(
            Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 2.0, 2.0),
            Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 2.0, 2.0),
            Block.createCuboidShape(0.0, 0.0, 14.0, 2.0, 2.0, 16.0),
            Block.createCuboidShape(14.0, 0.0, 14.0, 16.0, 2.0, 16.0),
            Block.createCuboidShape(0.0, 2.0, 0.0, 16.0, 6.0, 16.0),
            Block.createCuboidShape(0.0, 6.0, 0.0, 16.0, 15.0, 4.0),
            Block.createCuboidShape(1.0, 15.0, 0.0, 15.0, 16.0, 4.0),
            Block.createCuboidShape(0.0, 6.0, 3.0, 2.0, 8.0, 16.0),
            Block.createCuboidShape(14.0, 6.0, 3.0, 16.0, 8.0, 16.0),
            Block.createCuboidShape(0.0, 8.0, 3.0, 2.0, 9.0, 15.0),
            Block.createCuboidShape(14.0, 8.0, 3.0, 16.0, 9.0, 15.0)
    );
    protected static final VoxelShape EAST = VoxelShapes.union(
            Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 2.0, 2.0),
            Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 2.0, 2.0),
            Block.createCuboidShape(0.0, 0.0, 14.0, 2.0, 2.0, 16.0),
            Block.createCuboidShape(14.0, 0.0, 14.0, 16.0, 2.0, 16.0),
            Block.createCuboidShape(0.0, 2.0, 0.0, 16.0, 6.0, 16.0),
            Block.createCuboidShape(0.0, 6.0, 0.0, 4.0, 15.0, 16.0),
            Block.createCuboidShape(0.0, 15.0, 1.0, 4.0, 16.0, 15.0),
            Block.createCuboidShape(0.0, 6.0, 0.0, 16.0, 8.0, 2.0),
            Block.createCuboidShape(0.0, 6.0, 14.0, 16.0, 8.0, 16.0),
            Block.createCuboidShape(1.0, 8.0, 0.0, 15.0, 9.0, 2.0),
            Block.createCuboidShape(1.0, 8.0, 14.0, 15.0, 9.0, 16.0)
    );
    protected static final VoxelShape WEST = VoxelShapes.union(
            Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 2.0, 2.0),
            Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 2.0, 2.0),
            Block.createCuboidShape(0.0, 0.0, 14.0, 2.0, 2.0, 16.0),
            Block.createCuboidShape(14.0, 0.0, 14.0, 16.0, 2.0, 16.0),
            Block.createCuboidShape(0.0, 2.0, 0.0, 16.0, 6.0, 16.0),
            Block.createCuboidShape(12.0, 6.0, 0.0, 16.0, 15.0, 16.0),
            Block.createCuboidShape(12.0, 15.0, 1.0, 16.0, 16.0, 15.0),
            Block.createCuboidShape(0.0, 6.0, 0.0, 13.0, 8.0, 2.0),
            Block.createCuboidShape(0.0, 6.0, 14.0, 13.0, 8.0, 16.0),
            Block.createCuboidShape(1.0, 8.0, 0.0, 13.0, 9.0, 2.0),
            Block.createCuboidShape(1.0, 8.0, 14.0, 13.0, 9.0, 16.0)
    );

    public ArmchairBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction i = state.get(FACING);
        return switch (i) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            default -> VoxelShapes.empty(); // return an empty shape if no matching direction is found
        };
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, HORIZONTAL_FACING);
    }
}
