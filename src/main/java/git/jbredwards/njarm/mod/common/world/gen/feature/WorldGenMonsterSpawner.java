package git.jbredwards.njarm.mod.common.world.gen.feature;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

/**
 *
 * @author jbred
 *
 */
public class WorldGenMonsterSpawner extends WorldGenerator
{
    @Nonnull public final IBlockState mainBlock, mossyBlock;
    @Nonnull public final Function<Random, ResourceLocation> mobGenerator;
    @Nonnull public final ResourceLocation lootTable;
    public final int radiusX, radiusZ;

    public WorldGenMonsterSpawner(@Nonnull IBlockState mainBlock, @Nonnull IBlockState mossyBlock, int radiusX, int radiusZ, @Nonnull ResourceLocation lootTable, @Nonnull Function<Random, ResourceLocation> mobGenerator) {
        this.mainBlock = mainBlock;
        this.mossyBlock = mossyBlock;
        this.mobGenerator = mobGenerator;
        this.radiusX = radiusX;
        this.radiusZ = radiusZ;
        this.lootTable = lootTable;
    }

    @Override
    public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos origin) {
        //generates the walls
        for(int x = -radiusX; x <= radiusX; x++) {
            for(int y = 3; y >= -1; y--) {
                for(int z = -radiusZ; z <= radiusZ; z++) {
                    final BlockPos pos = origin.add(x, y, z);
                    final IBlockState state = worldIn.getBlockState(pos);

                    if(x != -radiusX && x != radiusX && y != -1 && z != -radiusZ && z != radiusZ) {
                        if(state.getBlock() != Blocks.CHEST) worldIn.setBlockToAir(pos);
                    }

                    else if(pos.getY() >= 0 && worldIn.getBlockState(pos.down()).getMaterial() == Material.AIR)
                        worldIn.setBlockToAir(pos);

                    else if(state.getMaterial() != Material.AIR && state.getBlock() != Blocks.CHEST)
                        worldIn.setBlockState(pos, y != -1 || rand.nextInt(4) > 0 ? mainBlock : mossyBlock,
                                Constants.BlockFlags.SEND_TO_CLIENTS | 32);
                }
            }
        }

        placeChest(worldIn, rand, origin);
        placeChest(worldIn, rand, origin);
        if(worldIn.setBlockState(origin, Blocks.MOB_SPAWNER.getDefaultState(), Constants.BlockFlags.SEND_TO_CLIENTS | 32))
            ((TileEntityMobSpawner)Objects.requireNonNull(worldIn.getTileEntity(origin)))
                    .getSpawnerBaseLogic().setEntityId(mobGenerator.apply(rand));

        return true;
    }

    protected void placeChest(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos origin) {
        final boolean alongXWall = rand.nextBoolean();
        final int x = alongXWall
                ? (rand.nextBoolean() ? radiusX - 1 : 1 - radiusX)
                : rand.nextInt((radiusX - 1) * 2) - radiusX + 1;

        final int z = alongXWall
                ? rand.nextInt((radiusZ - 1) * 2) - radiusZ + 1
                : (rand.nextBoolean() ? radiusZ - 1 : 1 - radiusZ);

        final BlockPos pos = origin.add(x, 0, z);
        if(worldIn.isAirBlock(pos)) {
            for(EnumFacing facing : EnumFacing.HORIZONTALS) {
                if(worldIn.getBlockState(pos.offset(facing)).getMaterial().isSolid()) {
                    if(worldIn.setBlockState(pos, Blocks.CHEST.correctFacing(worldIn, pos, Blocks.CHEST.getDefaultState()),
                            Constants.BlockFlags.SEND_TO_CLIENTS | 32)) {

                        ((TileEntityChest)Objects.requireNonNull(worldIn.getTileEntity(pos)))
                                .setLootTable(lootTable, rand.nextLong());

                        return;
                    }
                }
            }
        }
    }
}
