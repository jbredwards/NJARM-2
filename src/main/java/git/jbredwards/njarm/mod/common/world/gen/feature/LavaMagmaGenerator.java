package git.jbredwards.njarm.mod.common.world.gen.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Predicate;

/**
 *
 * @author jbred
 *
 */
public class LavaMagmaGenerator implements IWorldGenerator
{
    @Override
    public void generate(@Nonnull Random random, int chunkX, int chunkZ, @Nonnull World world, @Nonnull IChunkGenerator chunkGenerator, @Nonnull IChunkProvider chunkProvider) {
        if(chunkProvider instanceof ChunkProviderServer && world.getWorldType() != WorldType.DEBUG_ALL_BLOCK_STATES) {
            final ChunkProviderServer provider = (ChunkProviderServer)chunkProvider;
            final Chunk chunk = provider.provideChunk(chunkX, chunkZ);

            for(int x = 0; x < 16; x++) {
                for(int y = 1; y < 11; y++) {
                    for(int z = 0; z < 16; z++) {
                        final IBlockState here = chunk.getBlockState(x, y, z);
                        if(here == Blocks.STONE.getDefaultState())
                            replace(random, chunk, provider, x, y, z, chunkX, chunkZ,
                                    state -> state == Blocks.LAVA.getDefaultState() || state == Blocks.FLOWING_LAVA.getDefaultState());

                        else if(here == Blocks.LAVA.getDefaultState() || here == Blocks.FLOWING_LAVA.getDefaultState())
                            replace(random, chunk, provider, x, y, z, chunkX, chunkZ, state -> state == Blocks.STONE.getDefaultState());
                    }
                }
            }
        }
    }

    void replace(@Nonnull Random rand, @Nonnull Chunk chunkIn, @Nonnull ChunkProviderServer provider, int x, int y, int z, int chunkX, int chunkZ, @Nonnull Predicate<IBlockState> checker) {
        final int prevX = x, prevY = y, prevZ = z;
        for(EnumFacing facing : EnumFacing.values()) {
            if(facing != EnumFacing.UP && rand.nextFloat() < 0.8f) {
                x = prevX + facing.getXOffset();
                y = prevY + facing.getYOffset();
                z = prevZ + facing.getZOffset();

                if(x >= 0 && x < 16 && z >= 0 && z < 16) {
                    if(checker.test(chunkIn.getBlockState(x, y, z)))
                        chunkIn.setBlockState(new ChunkPos(chunkX, chunkZ).getBlock(x, y, z), Blocks.MAGMA.getDefaultState());
                }

                else {
                    final Chunk chunk = provider.loadedChunks.get(ChunkPos.asLong(chunkX + (x >> 4), chunkZ + (z >> 4)));
                    if(chunk != null && checker.test(chunk.getBlockState(x, y, z)))
                        chunk.setBlockState(new ChunkPos(chunkX, chunkZ).getBlock(x, y, z), Blocks.MAGMA.getDefaultState());
                }
            }
        }
    }
}
