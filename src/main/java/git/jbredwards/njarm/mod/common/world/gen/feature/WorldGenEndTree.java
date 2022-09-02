package git.jbredwards.njarm.mod.common.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class WorldGenEndTree extends WorldGenAbstractTree
{
    @Nonnull public final IBlockState log;
    @Nonnull public final IBlockState leaves;
    @Nonnull public final List<Block> ground;

    public WorldGenEndTree(boolean notify, @Nonnull Block log, @Nonnull BlockLeaves leaves, @Nullable Block... ground) {
        super(notify);
        this.log = log.getStateFromMeta(1);
        this.leaves = leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, true);
        this.ground = Arrays.asList(ground == null ? new Block[] {null} : ground);
    }

    @Override
    public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos position) {
        int height = rand.nextInt(3) + 8;
        height += rand.nextInt(4);

        boolean flag = true;
        if(position.getY() >= 1 && position.getY() + height + 1 <= 256) {
            for (int j = position.getY(); j <= position.getY() + 1 + height; ++j) {
                int k = 1;

                if(j == position.getY()) {
                    k = 0;
                }

                if(j >= position.getY() + 1 + height - 2) {
                    k = 2;
                }

                BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

                for(int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
                    for(int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
                        if(j >= 0 && j < worldIn.getHeight()) {
                            if(!this.isReplaceable(worldIn, mutablePos.setPos(l, j, i1))) {
                                flag = false;
                            }
                        }
                        else {
                            flag = false;
                        }
                    }
                }
            }

            if(flag) {
                BlockPos down = position.down();
                IBlockState state = worldIn.getBlockState(down);
                //boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SAPLING);
                boolean isSoil = (ground.contains(null) ? state.getMaterial().isSolid() : ground.contains(state.getBlock()));

                if (isSoil && position.getY() < worldIn.getHeight() - height - 1) {
                    state.getBlock().onPlantGrow(state, worldIn, down, position);

                    for (int i2 = position.getY() - 3 + height; i2 <= position.getY() + height; ++i2) {
                        int k2 = i2 - (position.getY() + height);
                        int l2 = 1 - k2 / 2;

                        for (int i3 = position.getX() - l2; i3 <= position.getX() + l2; ++i3) {
                            int j1 = i3 - position.getX();

                            for (int k1 = position.getZ() - l2; k1 <= position.getZ() + l2; ++k1) {
                                int l1 = k1 - position.getZ();

                                if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || rand.nextInt(2) != 0 && k2 != 0) {
                                    BlockPos blockpos = new BlockPos(i3, i2, k1);
                                    IBlockState state2 = worldIn.getBlockState(blockpos);

                                    if (state2.getBlock().isAir(state2, worldIn, blockpos) || state2.getBlock().isAir(state2, worldIn, blockpos)) {
                                        this.setBlockAndNotifyAdequately(worldIn, blockpos, leaves);
                                    }
                                }
                            }
                        }
                    }

                    for (int j2 = 0; j2 < height; ++j2) {
                        BlockPos upN = position.up(j2);
                        IBlockState state2 = worldIn.getBlockState(upN);

                        if (state2.getBlock().isAir(state2, worldIn, upN) || state2.getBlock().isLeaves(state2, worldIn, upN)) {
                            this.setBlockAndNotifyAdequately(worldIn, position.up(j2), log);
                        }
                    }

                    return true;
                }

            }
            return false;
        }

        return false;
    }

    @Override
    public boolean isReplaceable(@Nonnull World world, @Nonnull BlockPos pos) {
        final IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getBlock().isWood(world, pos) || canGrowInto(state.getBlock());
    }
}
