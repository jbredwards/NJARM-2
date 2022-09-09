package git.jbredwards.njarm.mod.common.world.gen;

import git.jbredwards.njarm.asm.plugins.ASMHooks;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * adds far better snow & ice generation that doesn't clash weirdly with tree gen
 * @author jbred
 *
 */
public final class SnowGenerator
{
    @SubscribeEvent
    public static void generate(@Nonnull PopulateChunkEvent.Populate event) {
        if(event.getType() == PopulateChunkEvent.Populate.EventType.ICE) {
            final World world = event.getWorld();
            if(world.getChunkProvider() instanceof ChunkProviderServer) {
                final BlockPos chunkPos = new BlockPos((event.getChunkX() << 4) + 8, 0, (event.getChunkZ() << 4) + 8);
                for(int x = -16; x < 32; x++) {
                    for(int z = -16; z < 32; z++) {
                        //fix weird snowy tree issues by going into neighboring chunks
                        final @Nullable Chunk chunk = ((ChunkProviderServer)world.getChunkProvider()).loadedChunks
                                .get(ChunkPos.asLong(event.getChunkX() + (x >> 4), event.getChunkZ() + (z >> 4)));

                        if(chunk != null) {
                            final BlockPos origin = chunk.getPrecipitationHeight(chunkPos.add(x, 0, z));
                            final Biome biome = chunk.getBiome(origin, world.getBiomeProvider());

                            for(int y = 0; origin.getY() + y > 0; y--) {
                                final BlockPos pos = origin.up(y);
                                //check temperature & light conditions
                                if(biome.getTemperature(pos) <= 0.15 && chunk.getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
                                    //check block conditions
                                    final IBlockState state = chunk.getBlockState(pos);

                                    if(state.getBlock().isAir(state, world, pos)
                                            || state.getBlock() == Blocks.TALLGRASS
                                            || state.getBlock().isLeaves(state, world, pos)
                                            || state.getBlock().isWood(world, pos)) {
                                        final IBlockState down = chunk.getBlockState(pos.down());
                                        if(canPlaceSnowAt(world, pos.down(), down)) {
                                            //place snowy grass
                                            if(state.getBlock() == Blocks.TALLGRASS && state.getValue(BlockTallGrass.TYPE) == BlockTallGrass.EnumType.GRASS) {
                                                chunk.setBlockState(pos, ModBlocks.SNOW_GRASS.getDefaultState().withProperty(
                                                        BlockSnow.LAYERS, MathHelper.getInt(event.getRand(), 1, 3)));

                                                break;
                                            }

                                            //place snow layers
                                            else if(state.getBlock().isAir(state, world, pos)) {
                                                chunk.setBlockState(pos, Blocks.SNOW_LAYER.getDefaultState().withProperty(
                                                        BlockSnow.LAYERS, MathHelper.getInt(event.getRand(), 1, 3)));

                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean canPlaceSnowAt(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if(state.getBlock() instanceof BlockIce
                || state.getBlock() instanceof BlockPackedIce
                || state.getBlock() instanceof BlockBarrier
                || ASMHooks.canFallThrough(state)) return false;

        return state.getBlockFaceShape(world, pos, EnumFacing.UP) == BlockFaceShape.SOLID
                || state.getBlock().isLeaves(state, world, pos);
    }
}
