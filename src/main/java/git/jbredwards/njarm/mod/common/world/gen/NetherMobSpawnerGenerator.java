package git.jbredwards.njarm.mod.common.world.gen;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.world.gen.feature.WorldGenMonsterSpawner;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class NetherMobSpawnerGenerator implements IWorldGenerator
{
    @Nonnull
    protected static final ResourceLocation[] MOBS = {
            new ResourceLocation(Constants.MODID, "fire_skeleton"),
            new ResourceLocation(Constants.MODID, "soul_skeleton"),
            new ResourceLocation(Constants.MODID, "blood"),
            new ResourceLocation(Constants.MODID, "blood"),
            new ResourceLocation("magma_cube")
    };

    @Override
    public void generate(@Nonnull Random random, int chunkX, int chunkZ, @Nonnull World world, @Nonnull IChunkGenerator chunkGenerator, @Nonnull IChunkProvider chunkProvider) {
        if(world.provider.getDimension() == -1) {
            for(int i = 0; i < 16; i++) {
                final BlockPos origin =  new BlockPos((chunkX << 4) + random.nextInt(16) + 8, MathHelper.getInt(random, 32, 100), (chunkZ << 4) + random.nextInt(16) + 8);
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
                        if(world.getBlockState(origin.add(x, -1, z)).getMaterial() == Material.AIR
                                || !world.getBlockState(origin.add(x, 4, z)).getMaterial().isSolid()) return;
                    }
                }

                //make sure that at least one 1x2x1 hole exists where the a player can enter the dungeon
                if(holes > 0) new WorldGenMonsterSpawner(
                        Blocks.NETHER_BRICK.getDefaultState(), ModBlocks.CRACKED_NETHER_BRICK.getDefaultState(), radiusX, radiusZ,
                        LootTableList.CHESTS_NETHER_BRIDGE, rand -> MOBS[rand.nextInt(MOBS.length)]).generate(world, random, origin);
            }
        }
    }
}
