package git.jbredwards.njarm.asm.plugins;

import git.jbredwards.njarm.mod.common.config.client.RenderingConfig;
import git.jbredwards.njarm.mod.common.util.IHasRunningEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * class exists cause SpongeForge
 * @author jbred
 *
 */
@SuppressWarnings("unused")
public final class ASMHooks
{
    //PluginBlockCauldron
    public static boolean canCauldronRenderInLayer(@Nonnull Block block, @Nonnull BlockRenderLayer layer) {
        //only apply fix to vanilla cauldrons, as not to potentially ruin any modded ones
        return block == Blocks.CAULDRON && layer == BlockRenderLayer.TRANSLUCENT || block.getRenderLayer() == layer;
    }

    //PluginBlockCauldron
    @Nullable
    public static Boolean isEntityInsideCauldronMaterial(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Entity entity, double yToTest, @Nonnull Material materialIn, boolean testingHead) {
        if(state.getBlock() != Blocks.CAULDRON || materialIn != Material.WATER) return null;

        final int level = state.getValue(BlockCauldron.LEVEL);
        if(!testingHead) yToTest = entity.posY;

        return level > 0 && yToTest < pos.getY() + 0.375 + level * 0.1875;
    }

    //PluginBlockCauldron
    @Nullable
    public static Boolean isAABBInsideCauldronMaterial(@Nonnull Block block, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB boundingBox, @Nonnull Material materialIn) {
        return block != Blocks.CAULDRON || materialIn != Material.WATER ? null : block.isAABBInsideLiquid(world, pos, boundingBox);
    }

    //PluginBlockCauldron
    @Nonnull
    public static Boolean isAABBInsideCauldronLiquid(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB boundingBox) {
        final int level = world.getBlockState(pos).getValue(BlockCauldron.LEVEL);
        return level > 0 && boundingBox.minY < pos.getY() + 0.375 + level * 0.1875;
    }

    //PluginEntityLivingBase
    @Nonnull
    public static Pair<IBlockState, BlockPos> updateFallState(@Nonnull World world, @Nonnull IBlockState state, @Nonnull BlockPos pos) {
        final IBlockState upState = world.getBlockState(pos.up());
        return upState.getBlock() instanceof IHasRunningEffects ? Pair.of(upState, pos.up()) : Pair.of(state, pos);
    }

    //PluginRender
    @SideOnly(Side.CLIENT)
    public static float bedrockShadowSize(float shadowSize, double shadowY, double renderY, double entityYDif) {
        if(!RenderingConfig.doBedrockShadowSize()) return shadowSize;
        final double entityY = renderY - entityYDif;
        final double blockY = (shadowY - 0.015625) - (entityYDif + renderY);
        return shadowSize / (float)(1 - renderY - (blockY - entityY));
    }
}
