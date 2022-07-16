package git.jbredwards.njarm.mod.common.world.generation;

import git.jbredwards.njarm.mod.common.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class BasaltPillarGenerator implements IWorldGenerator
{
    @Override
    public void generate(@Nonnull Random random, int chunkX, int chunkZ, @Nonnull World world, @Nonnull IChunkGenerator chunkGenerator, @Nonnull IChunkProvider chunkProvider) {
        if(world.provider.getDimension() == -1) for(int i = 0; i < 10; i++) {
            final BlockPos pos = new BlockPos(
                    (chunkX << 4) + 8 + random.nextInt(16),
                    random.nextInt(128),
                    (chunkZ << 4) + 8 + random.nextInt(16));

            if(world.getBlockState(pos).getBlock() != Blocks.NETHERRACK)
                    continue;

            final List<BlockPos> positions = new ArrayList<>();
            final BlockPos.MutableBlockPos posMutable = new BlockPos.MutableBlockPos(pos);
            while(!world.isOutsideBuildHeight(posMutable.setPos(posMutable.down())) && world.isAirBlock(posMutable))
                positions.add(posMutable.toImmutable());

            if(positions.size() >= 20) {
                generatePillar(world, positions, random);
                return;
            }
        }
    }

    protected void generatePillar(@Nonnull World world, @Nonnull List<BlockPos> positions, @Nonnull Random rand) {
        final IBlockState basalt = ModBlocks.BASALT.getStateFromMeta(2);
        boolean movedN = true, movedS = true, movedE = true, movedW = true;

        for(BlockPos pos : positions) {
            world.setBlockState(pos, basalt, 2);
            if(movedN) movedN = rand.nextFloat() < 0.9 && world.setBlockState(pos.north(), basalt, 2);
            if(movedS) movedS = rand.nextFloat() < 0.9 && world.setBlockState(pos.south(), basalt, 2);
            if(movedE) movedE = rand.nextFloat() < 0.9 && world.setBlockState(pos.east(), basalt, 2);
            if(movedW) movedW = rand.nextFloat() < 0.9 && world.setBlockState(pos.west(), basalt, 2);
        }

        for(int x = -3; x < 4; x++) {
            for(int z = -3; z < 4; z++) {
                final int weight = 10 - Math.abs(x) * Math.abs(z);
                if(rand.nextInt(10) < weight) {
                    final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(
                            positions.get(positions.size() - 1).add(x, 0, z));

                    if(world.isAirBlock(pos)) {
                        if(!world.isAirBlock(pos.down())) world.setBlockState(pos.toImmutable(), basalt, 2);
                        if(!world.isAirBlock(pos.add(1, -2, 0)) || !world.isAirBlock(pos.add(0, -2, 1)) || !world.isAirBlock(pos.add(-1, -2, 0)) || !world.isAirBlock(pos.add(0, -2, -1))) {
                            for(int i = 5; rand.nextInt(10) < weight - i && world.getBlockState(pos.setPos(pos.down())).getBlock().isReplaceable(world, pos); i++)
                                world.setBlockState(pos.toImmutable(), basalt, 2);
                        }
                    }

                    else if(world.isAirBlock(pos.setPos(pos.up()))) world.setBlockState(pos.toImmutable(), basalt, 2);
                }
            }
        }
    }
}
