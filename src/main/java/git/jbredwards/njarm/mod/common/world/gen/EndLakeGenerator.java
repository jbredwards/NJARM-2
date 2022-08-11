package git.jbredwards.njarm.mod.common.world.gen;

import net.darkhax.bookshelf.util.MathsUtils;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class EndLakeGenerator implements IWorldGenerator
{
    @Override
    public void generate(@Nonnull Random random, int chunkX, int chunkZ, @Nonnull World world, @Nonnull IChunkGenerator chunkGenerator, @Nonnull IChunkProvider chunkProvider) {
        if(world.provider.getDimension() == 1 && (!MathsUtils.isInRange(-15, 15, chunkX) || !MathsUtils.isInRange(-15, 15, chunkZ)) && random.nextFloat() < 0.3f) {
            final BlockPos pos = new BlockPos((chunkX << 4) + random.nextInt(16) + 8, 70, (chunkZ << 4) + random.nextInt(16) + 8);
            new WorldGenLakes(Blocks.WATER).generate(world, random, pos);
        }
    }
}
