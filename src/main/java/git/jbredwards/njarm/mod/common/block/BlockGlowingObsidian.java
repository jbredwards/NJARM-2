package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.client.particle.util.ParticleUtils;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class BlockGlowingObsidian extends net.minecraft.block.BlockObsidian
{
    @Nonnull
    @Override
    public EnumPushReaction getPushReaction(@Nonnull IBlockState state) { return EnumPushReaction.BLOCK; }

    @Override
    public boolean isToolEffective(@Nonnull String type, @Nonnull IBlockState state) { return false; }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if(Minecraft.isFancyGraphicsEnabled() && rand.nextFloat() < 0.1) ParticleUtils.spawnRedstoneParticles(worldIn, pos,
                (x, y, z) -> worldIn.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 0, 0, 0));
    }
}
