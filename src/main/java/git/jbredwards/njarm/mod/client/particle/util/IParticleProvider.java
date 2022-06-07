package git.jbredwards.njarm.mod.client.particle.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * More useful version of IParticleFactory that isn't linked to just the client & that can also spawn the particle
 * @author jbred
 *
 */
public interface IParticleProvider
{
    @Nonnull
    @SideOnly(Side.CLIENT)
    Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args);

    @SideOnly(Side.CLIENT)
    default void spawnClient(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
        Minecraft.getMinecraft().effectRenderer.addEffect(getParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, args));
    }

    default void spawnServer(@Nonnull World world, float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int numOfParticles, int... args) {}
}
