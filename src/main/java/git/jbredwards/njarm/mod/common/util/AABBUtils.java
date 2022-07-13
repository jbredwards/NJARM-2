package git.jbredwards.njarm.mod.common.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class AABBUtils
{
    @Nonnull
    public static AxisAlignedBB of(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        return new AxisAlignedBB(minX / 16.0, minY / 16.0, minZ / 16.0, maxX / 16.0, maxY / 16.0, maxZ / 16.0);
    }

    @Nonnull
    public static AxisAlignedBB rotate(@Nonnull AxisAlignedBB aabbIn, @Nonnull EnumFacing.Axis axis) {
        switch(axis) {
            case X: return new AxisAlignedBB(aabbIn.minX, aabbIn.minZ, aabbIn.minY, aabbIn.maxX, aabbIn.maxZ, aabbIn.maxY);
            case Y: return new AxisAlignedBB(aabbIn.minZ, aabbIn.minY, aabbIn.minX, aabbIn.maxZ, aabbIn.maxY, aabbIn.maxX);
            case Z: return new AxisAlignedBB(aabbIn.minY, aabbIn.minX, aabbIn.minZ, aabbIn.maxY, aabbIn.maxX, aabbIn.maxZ);
            default: return aabbIn;
        }
    }
}
