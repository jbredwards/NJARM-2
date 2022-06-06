package git.jbredwards.njarm.mod.client.particle.util;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * IParticleFactory that isn't linked to only the client
 * @author jbred
 *
 */
public abstract class IParticleProvider
{
    @Nonnull
    @SideOnly(Side.CLIENT)
    public abstract Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args);
}
