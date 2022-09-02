package git.jbredwards.njarm.mod.common.world.gen;

import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.world.gen.feature.WorldGenEndTree;
import net.darkhax.bookshelf.util.MathsUtils;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class EndForestGenerator implements IWorldGenerator
{
    @Override
    public void generate(@Nonnull Random rand, int chunkX, int chunkZ, @Nonnull World world, @Nonnull IChunkGenerator chunkGenerator, @Nonnull IChunkProvider chunkProvider) {
        if(world.provider.getDimension() == 1) {
            for(int i = 0; i < 51; i++) {
                int x = chunkX * 16 + 8 + rand.nextInt(16);
                int z = chunkZ * 16 + 8 + rand.nextInt(16);
                int y = world.getChunk(x >> 4, z >> 4).getHeight(new BlockPos(x & 15, 0, z & 15));
                BlockPos pos = new BlockPos(x, y, z);

                if(world.getBlockState(pos.down()).getBlock() == Blocks.END_STONE && world.isAirBlock(pos)) {
                    if(rand.nextFloat() < 0.1 && (!MathsUtils.isInRange(-50, 50, x) || !MathsUtils.isInRange(-50, 50, z)))
                        (rand.nextFloat() < 0.1 //chance of generating a cursed tree
                                ? new WorldGenEndTree(false, ModBlocks.ENDER_LOG_CURSED, ModBlocks.ENDER_LEAVES_CURSED, Blocks.END_STONE)
                                : new WorldGenEndTree(false, ModBlocks.ENDER_LOG, ModBlocks.ENDER_LEAVES, Blocks.END_STONE)
                        ).generate(world, rand, pos);

                    else world.setBlockState(pos, ModBlocks.ENDER_GRASS.getDefaultState(), 2);
                }
            }
        }
    }
}
