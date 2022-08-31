package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.fluidlogged_api.api.util.FluidState;
import git.jbredwards.fluidlogged_api.api.util.FluidloggedUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.Queue;

import static net.minecraft.util.EnumFacing.values;

/**
 *
 * @author jbred
 *
 */
public class BlockMagicSponge extends Block
{
    public BlockMagicSponge(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn) { super(materialIn, mapColorIn); }
    public BlockMagicSponge(@Nonnull Material materialIn) { super(materialIn); }

    @Override
    public void onBlockAdded(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        absorb(worldIn, pos);
    }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos) {
        absorb(worldIn, pos);
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    //copied from fluidlogged api, changed to absorb any fluid
    static boolean available = true;
    public static void absorb(@Nonnull World world, @Nonnull BlockPos origin) {
        //temporary fix for strange update bug
        if(available) {
            available = false;
            final Queue<Pair<BlockPos, Integer>> queue = new LinkedList<>();
            queue.add(Pair.of(origin, 0));

            while(!queue.isEmpty()) {
                final Pair<BlockPos, Integer> entry = queue.poll();
                final BlockPos pos = entry.getKey();
                final int distance = entry.getValue();

                for(EnumFacing facing : values()) {
                    final BlockPos offset = pos.offset(facing);
                    final FluidState fluidState = FluidloggedUtils.getFluidState(world, offset);

                    if(!fluidState.isEmpty()) {
                        //don't drain bad fluid blocks (looking at you BOP kelp)
                        if(fluidState.isValid()) {
                            fluidState.getBlock().drain(world, offset, true);
                            if(distance < 6) queue.add(Pair.of(offset, distance + 1));
                        }
                        //drain bad fluid blocks
                        else if(world.setBlockToAir(pos)) {
                            world.playEvent(Constants.WorldEvents.BREAK_BLOCK_EFFECTS, offset, Block.getStateId(fluidState.getState()));
                            fluidState.getBlock().dropBlockAsItem(world, offset, fluidState.getState(), 0);
                            if(distance < 6) queue.add(Pair.of(offset, distance + 1));
                        }
                    }
                }
            }

            available = true;
        }
    }
}
