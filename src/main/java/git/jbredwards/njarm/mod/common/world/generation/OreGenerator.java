package git.jbredwards.njarm.mod.common.world.generation;

import git.jbredwards.njarm.mod.common.config.world.OreConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

/**
 *
 * @author jbred
 *
 */
public class OreGenerator extends BiomeDecorator implements IWorldGenerator
{
    @Override
    public void generate(@Nonnull Random rand, int chunkX, int chunkZ, @Nonnull World world, @Nonnull IChunkGenerator chunkGenerator, @Nonnull IChunkProvider chunkProvider) {
        chunkPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        for(Data data : OreConfig.ORES) {
            //only continue if the dimension is valid
            if(data.dimensions.length == 0 || Arrays.stream(data.dimensions).anyMatch(dim -> dim == world.provider.getDimension())) {
                genStandardOre1(world, rand, data.perChunk,
                    data.clumpSize > 1 ? new WorldGenMinable(data.oreState, data.clumpSize, data.condition::test) {
                        @Override
                        public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos) {
                            if(data.biomes.isEmpty()|| data.biomes.contains(worldIn.getBiomeForCoordsBody(pos)))
                                return super.generate(worldIn, rand, pos);

                            return true;
                        }
                    }:
                    //special case for single ores (ie. emeralds), since WorldGenMinable doesn't work for those
                    new WorldGenerator() {
                        @Override
                        public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos) {
                            if(data.biomes.isEmpty() || data.biomes.contains(worldIn.getBiomeForCoordsBody(pos))) {
                                final IBlockState here = worldIn.getBlockState(pos);
                                if(here.getBlock().isReplaceableOreGen(here, worldIn, pos, data.condition::test))
                                    worldIn.setBlockState(pos, data.oreState, 18);
                            }

                            return true;
                        }
                    },
                    data.minY, data.maxY
                );
            }
        }
    }

    public static final class Data
    {
        @Nonnull public final IBlockState oreState;
        @Nonnull public final Predicate<IBlockState> condition;
        @Nonnull public final List<Biome> biomes;
        public final int minY, maxY, clumpSize, perChunk;
        public final int[] dimensions;

        public Data(@Nonnull IBlockState oreState, @Nonnull Predicate<IBlockState> condition, @Nonnull List<Biome> biomes, int minY, int maxY, int clumpSize, int perChunk, @Nonnull int[] dimensions) {
            this.oreState = oreState;
            this.condition = condition;
            this.biomes = biomes;
            this.minY = minY;
            this.maxY = maxY;
            this.clumpSize = clumpSize;
            this.perChunk = perChunk;
            this.dimensions = dimensions;
        }
    }
}
