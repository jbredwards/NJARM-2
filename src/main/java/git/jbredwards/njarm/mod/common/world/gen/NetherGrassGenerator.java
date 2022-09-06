package git.jbredwards.njarm.mod.common.world.gen;

import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.world.gen.feature.WorldGenSmallGrass;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
public class NetherGrassGenerator implements IWorldGenerator
{
    @Override
    public void generate(@Nonnull Random random, int chunkX, int chunkZ, @Nonnull World world, @Nonnull IChunkGenerator chunkGenerator, @Nonnull IChunkProvider chunkProvider) {
        for(int i = 0; i < 8; i++) {
            final BlockPos origin = new BlockPos((chunkX << 4) + random.nextInt(16) + 8, MathHelper.getInt(random, 10, 100), (chunkZ << 4) + random.nextInt(16) + 8);
            if(world.getBiomeForCoordsBody(origin) == Biomes.HELL)
                new WorldGenSmallGrass(ModBlocks.NETHER_GRASS).generate(world, random, origin);
        }
    }
}
