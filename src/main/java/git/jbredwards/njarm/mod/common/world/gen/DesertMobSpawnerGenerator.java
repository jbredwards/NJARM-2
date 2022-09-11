package git.jbredwards.njarm.mod.common.world.gen;

import git.jbredwards.njarm.mod.common.config.world.DesertSpawnerConfig;
import git.jbredwards.njarm.mod.common.world.gen.feature.WorldGenMonsterSpawner;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * something added back from older minecraft versions cause I miss it
 * @author jbred
 *
 */
public class DesertMobSpawnerGenerator implements IWorldGenerator
{
    @Override
    public void generate(@Nonnull Random random, int chunkX, int chunkZ, @Nonnull World world, @Nonnull IChunkGenerator chunkGenerator, @Nonnull IChunkProvider chunkProvider) {
        if(random.nextFloat() < DesertSpawnerConfig.chance()) {
            BlockPos origin =  new BlockPos((chunkX << 4) + random.nextInt(16) + 8, 0, (chunkZ << 4) + random.nextInt(16) + 8);
            if(DesertSpawnerConfig.BIOMES.contains(world.getBiomeForCoordsBody(origin))) {
                origin = origin.up(world.getTopSolidOrLiquidBlock(origin).down(MathHelper.getInt(random, 6, 7)).getY());
                final int radiusX = MathHelper.getInt(random, 3, 4);
                final int radiusZ = MathHelper.getInt(random, 3, 4);
                int holes = 0;

                //ensure placement is valid
                for(int x = -radiusX; x <= radiusX; x++) {
                    for(int z = -radiusZ; z <= radiusZ; z++) {
                        //allow holes for the wall, but not too many
                        if((x == -radiusX || x == radiusX || z == -radiusZ || z == radiusZ)
                                && world.isAirBlock(origin.add(x, 0, z)) && world.isAirBlock(origin.add(x, 1, z))
                                && ++holes > 5) return;

                        //make sure the floor & ceiling don't have any holes
                        if(!world.getBlockState(origin.add(x, -1, z)).getMaterial().isSolid()
                                || !world.getBlockState(origin.add(x, 4, z)).getMaterial().isSolid()) return;
                    }
                }

                new WorldGenMonsterSpawner(
                        Blocks.COBBLESTONE.getDefaultState(), Blocks.MOSSY_COBBLESTONE.getDefaultState(), radiusX, radiusZ,
                        LootTableList.CHESTS_SIMPLE_DUNGEON, DungeonHooks::getRandomDungeonMob).generate(world, random, origin);

                //update the sand ceiling
                for(int x = -radiusX + 1; x < radiusX; x++) {
                    for(int z = -radiusZ + 1; z < radiusZ; z++) {
                        final IBlockState sand = world.getBlockState(origin.add(x, 4, z));
                        world.markAndNotifyBlock(origin.add(x, 4, z), null, sand, sand, Constants.BlockFlags.DEFAULT);
                    }
                }
            }
        }
    }
}
