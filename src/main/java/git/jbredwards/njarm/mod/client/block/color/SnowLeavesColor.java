package git.jbredwards.njarm.mod.client.block.color;

import git.jbredwards.njarm.mod.common.config.client.RenderingConfig;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockSnowBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class SnowLeavesColor implements IBlockColor
{
    @Nullable
    protected final IBlockColor oldColorHandler;
    public SnowLeavesColor(@Nullable IBlockColor oldColorHandler) { this.oldColorHandler = oldColorHandler; }

    @Override
    public int colorMultiplier(@Nonnull IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        final int oldColor = oldColorHandler == null ? -1 : oldColorHandler.colorMultiplier(state, worldIn, pos, tintIndex);
        if(worldIn != null && pos != null) {
            final int radius = RenderingConfig.biomeColorBlendRadius();
            final DoubleList snowDistances = new DoubleArrayList();
            for(BlockPos offset : BlockPos.getAllInBox(pos.add(-radius, 0, -radius), pos.add(radius, 0, radius))) {
                if(hasSnowAt(worldIn, offset)) snowDistances.add(pos.distanceSq(offset));
            }

            double closest = Double.MAX_VALUE;
            for(double distance : snowDistances)
                if(distance <= closest)
                    closest = distance;

            if(closest < Double.MAX_VALUE) {
                final double furthest = pos.distanceSq(pos.add(radius, 0, radius));
                final float snowFactor = (float)((furthest - closest) / furthest);
                final float leafFactor = 1 - snowFactor;

                final float[] snowColors = new Color(11862015).getColorComponents(new float[3]);
                final float[] leafColors = new Color(oldColor).getColorComponents(new float[3]);

                return new Color(
                        snowColors[0] * snowFactor + leafColors[0] * leafFactor,
                        snowColors[1] * snowFactor + leafColors[1] * leafFactor,
                        snowColors[2] * snowFactor + leafColors[2] * leafFactor).getRGB();
            }
        }

        return oldColor;
    }

    protected boolean hasSnowAt(@Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        for(int y = 1; pos.getY() + y < 255; y++) {
            final IBlockState state = world.getBlockState(pos.up(y));
            if(state.getBlock().isLeaves(state, world, pos) || state.getBlock().isWood(world, pos)) continue;
            return state.getBlock() instanceof BlockSnow || state.getBlock() instanceof BlockSnowBlock;
        }

        return false;
    }
}
