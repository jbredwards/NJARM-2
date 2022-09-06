package git.jbredwards.njarm.mod.common.world.gen;

import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.world.gen.feature.WorldGenSmallGrass;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class MyceliumGrassGenerator implements IWorldGenerator
{
    @Override
    public void generate(@Nonnull Random random, int chunkX, int chunkZ, @Nonnull World world, @Nonnull IChunkGenerator chunkGenerator, @Nonnull IChunkProvider chunkProvider) {
        final BlockPos origin = new BlockPos((chunkX << 4) + random.nextInt(16) + 8, 0, (chunkZ << 4) + random.nextInt(16) + 8);
        if(BiomeDictionary.hasType(world.getBiomeForCoordsBody(origin), BiomeDictionary.Type.MUSHROOM))
            new WorldGenSmallGrass(ModBlocks.MYCELIUM_GRASS).generate(world, random, world.getHeight(origin));
    }
}
