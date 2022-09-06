package git.jbredwards.njarm.mod.common.world.gen.feature;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class WorldGenSmallGrass extends WorldGenerator
{
    @Nonnull public final BlockBush grass;
    @Nonnull public final IBlockState grassState;

    public WorldGenSmallGrass(@Nonnull BlockBush grass) { this(grass, grass.getDefaultState()); }
    public WorldGenSmallGrass(@Nonnull BlockBush grass, @Nonnull IBlockState grassState) {
        this.grass = grass;
        this.grassState = grassState;
    }

    @Override
    public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos origin) {
        for(int i = 0; i < 128; ++i) {
            final BlockPos pos = origin.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            if(worldIn.isAirBlock(pos) && grass.canBlockStay(worldIn, pos, grassState)) worldIn.setBlockState(pos, grassState, 2);
        }

        return true;
    }
}
