package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.fluidlogged_api.api.util.FluidState;
import git.jbredwards.fluidlogged_api.api.util.FluidloggedUtils;
import io.netty.util.internal.IntegerHolder;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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
        tryAbsorb(worldIn, pos);
    }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos) {
        tryAbsorb(worldIn, pos);
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    @Override
    public boolean canEntityDestroy(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        return !(entity instanceof EntityDragon) && super.canEntityDestroy(state, world, pos, entity);
    }

    public void tryAbsorb(@Nonnull World world, @Nonnull BlockPos pos) {
        final int stateId = absorb(world, pos);
        if(stateId != -1) world.playEvent(Constants.WorldEvents.BREAK_BLOCK_EFFECTS, pos, stateId);
    }

    //copied from fluidlogged api, changed to absorb any fluid
    static boolean available = true;
    public static int absorb(@Nonnull World world, @Nonnull BlockPos origin) {
        //temporary fix for strange update bug
        if(available) {
            available = false;
            final Map<Fluid, IntegerHolder> drained = new HashMap<>();
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
                            drained.computeIfAbsent(fluidState.getFluid(), fluid -> new IntegerHolder()).value++;
                        }
                        //drain bad fluid blocks
                        else if(world.setBlockToAir(pos)) {
                            world.playEvent(Constants.WorldEvents.BREAK_BLOCK_EFFECTS, offset, Block.getStateId(fluidState.getState()));
                            fluidState.getBlock().dropBlockAsItem(world, offset, fluidState.getState(), 0);
                            if(distance < 6) queue.add(Pair.of(offset, distance + 1));
                            drained.computeIfAbsent(fluidState.getFluid(), fluid -> new IntegerHolder()).value++;
                        }
                    }
                }
            }

            available = true;
            if(drained.isEmpty()) return -1;

            //find which fluid was drained the most and return its state id
            int mostCount = -1;
            Fluid mostFluid = null;
            for(Map.Entry<Fluid, IntegerHolder> entry : drained.entrySet()) {
                if(mostCount < entry.getValue().value && entry.getKey().canBePlacedInWorld()) {
                    mostCount = entry.getValue().value;
                    mostFluid = entry.getKey();
                }
            }

            return mostFluid != null ? getStateId(mostFluid.getBlock().getDefaultState()) : -1;
        }

        return -1;
    }
}
