package git.jbredwards.njarm.mod.client.particle.util;

import git.jbredwards.njarm.mod.Main;
import git.jbredwards.njarm.mod.client.particle.ParticleLayeredBlockDust;
import git.jbredwards.njarm.mod.client.particle.ParticleLayeredDigging;
import git.jbredwards.njarm.mod.common.message.MessageParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public enum ParticleProvider
{
    MAGIC_ORE_BLOCK_DUST(getBlockDustFromTextures()),
    MAGIC_ORE_DIGGING(getDiggingFromTextures()),
    XP_ORE_BLOCK_DUST(getBlockDustFromTextures()),
    XP_ORE_DIGGING(getDiggingFromTextures()),
    NETHER_XP_ORE_BLOCK_DUST(getBlockDustFromTextures()),
    NETHER_XP_ORE_DIGGING(getDiggingFromTextures()),
    END_XP_ORE_BLOCK_DUST(getBlockDustFromTextures()),
    END_XP_ORE_DIGGING(getDiggingFromTextures());

    @Nonnull
    private final IParticleProvider provider;

    ParticleProvider(@Nonnull IParticleProvider provider) {
        this.provider = provider;
    }

    @Nonnull
    private static IParticleProvider getBlockDustFromTextures(@Nonnull ParticleLayer[] layers) {
        return new IParticleProvider() {
            @Nonnull
            @SideOnly(Side.CLIENT)
            @Override
            public Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
                return new ParticleLayeredBlockDust(world, x, y, z, xSpeed, ySpeed, zSpeed, layers);
            }
        };
    }

    @Nonnull
    private static IParticleProvider getDiggingFromTextures(@Nonnull ParticleLayer[] layers) {
        return new IParticleProvider() {
            @Nonnull
            @SideOnly(Side.CLIENT)
            @Override
            public Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
                return new ParticleLayeredDigging(world, x, y, z, xSpeed, ySpeed, zSpeed, layers);
            }
        };
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
        return provider.getParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, args);
    }

    @SideOnly(Side.CLIENT)
    public void spawnClient(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
        Minecraft.getMinecraft().effectRenderer.addEffect(getParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, args));
    }

    public void spawnServer(@Nonnull World world, float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int numOfParticles, int... args) {
        Main.wrapper.sendToAllAround(
                new MessageParticle(this, x, y, z, xOffset, yOffset, zOffset, speed, numOfParticles, args),
                new NetworkRegistry.TargetPoint(world.provider.getDimension(), x, y, z, 32)
        );
    }
}
