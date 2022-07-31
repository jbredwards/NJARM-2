package git.jbredwards.njarm.mod.client.particle.util;

import git.jbredwards.njarm.mod.client.particle.ParticleLayeredDigging;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.util.TriConsumer;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class ParticleUtils
{
    //spawns redstone-like particles at the position
    public static void spawnRedstoneParticles(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull TriConsumer<Double, Double, Double> generator) {
        for(EnumFacing facing : EnumFacing.values()) {
            //spawns the particles
            if(!world.getBlockState(pos.offset(facing)).isOpaqueCube()) {
                double x = pos.getX() + world.rand.nextFloat();
                double y = pos.getY() + world.rand.nextFloat();
                double z = pos.getZ() + world.rand.nextFloat();
                if(facing.getXOffset() != 0) x = pos.getX() + (facing.getXOffset() == 1 ? 1.0625 : -0.0625);
                if(facing.getYOffset() != 0) y = pos.getY() + (facing.getYOffset() == 1 ? 1.0625 : -0.0625);
                if(facing.getZOffset() != 0) z = pos.getZ() + (facing.getZOffset() == 1 ? 1.0625 : -0.0625);
                generator.accept(x, y, z);
            }
        }
    }

    public static boolean addLandingEffects(@Nonnull World world, @Nonnull Entity entity, int numberOfParticles, @Nonnull IParticleProvider provider, @Nonnull int... args) {
        provider.spawnServer(world, (float)entity.posX, (float)entity.posY, (float)entity.posZ, 0, 0, 0, 0.15f, numberOfParticles, args);
        return true;
    }

    public static boolean addRunningParticles(@Nonnull World world, @Nonnull Entity entity, @Nonnull IParticleProvider provider, @Nonnull int... args) {
        if(world.isRemote) {
            final double x = entity.posX + world.rand.nextFloat() - 0.5 * entity.width;
            final double y = entity.getEntityBoundingBox().minY + 0.1;
            final double z = entity.posZ + world.rand.nextFloat() - 0.5 * entity.width;
            provider.spawnClient(world, x, y, z, -entity.motionX * 4, 1.5, -entity.motionZ * 4, args);
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    public static boolean addHitEffects(@Nonnull IBlockState state, @Nonnull World world, @Nonnull RayTraceResult target, @Nonnull ParticleManager manager, @Nonnull IParticleProvider provider, @Nonnull int... args) {
        final BlockPos pos = target.getBlockPos();
        final EnumFacing side = target.sideHit;
        final AxisAlignedBB aabb = state.getBoundingBox(world, pos);
        double x = pos.getX() + world.rand.nextDouble() * (aabb.maxX - aabb.minX - 0.2) + 0.1 + aabb.minX;
        double y = pos.getY() + world.rand.nextDouble() * (aabb.maxY - aabb.minY - 0.2) + 0.1 + aabb.minY;
        double z = pos.getZ() + world.rand.nextDouble() * (aabb.maxZ - aabb.minZ - 0.2) + 0.1 + aabb.minZ;

        if(side == EnumFacing.DOWN) y = pos.getY() + aabb.minY - 0.1;
        if(side == EnumFacing.UP) y = pos.getY() + aabb.maxY + 0.1;
        if(side == EnumFacing.NORTH) z = pos.getZ() + aabb.minZ - 0.1;
        if(side == EnumFacing.SOUTH) z = pos.getZ() + aabb.maxZ + 0.1;
        if(side == EnumFacing.WEST) x = pos.getX() + aabb.minX - 0.1;
        if(side == EnumFacing.EAST) x = pos.getX() + aabb.maxX + 0.1;

        final ParticleLayeredDigging particle = (ParticleLayeredDigging)provider.getParticle(world, x, y, z, 0, 0, 0, args);
        manager.addEffect(particle.setBlockPos(pos).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6f));

        return true;
    }

    @SideOnly(Side.CLIENT)
    public static boolean addDestroyEffects(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ParticleManager manager, @Nonnull IParticleProvider provider, @Nonnull int... args) {
        for(int x = 0; x < 4; ++x) {
            for(int y = 0; y < 4; ++y) {
                for(int z = 0; z < 4; ++z) {
                    final double posX = (x + 0.5) / 4;
                    final double posY = (y + 0.5) / 4;
                    final double posZ = (z + 0.5) / 4;

                    ParticleLayeredDigging particle = (ParticleLayeredDigging)provider.getParticle(world, pos.getX() + posX, pos.getY() + posY, pos.getZ() + posZ, posX - 0.5, posY - 0.5, posZ - 0.5, args);
                    manager.addEffect(particle.setBlockPos(pos));
                }
            }
        }

        return true;
    }
}
