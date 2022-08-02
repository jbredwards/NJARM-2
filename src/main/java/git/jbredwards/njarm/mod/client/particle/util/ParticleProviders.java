package git.jbredwards.njarm.mod.client.particle.util;

import git.jbredwards.njarm.mod.Main;
import git.jbredwards.njarm.mod.client.particle.ParticleLayeredBlockDust;
import git.jbredwards.njarm.mod.client.particle.ParticleLayeredDigging;
import git.jbredwards.njarm.mod.client.particle.ParticleLitRedstone;
import git.jbredwards.njarm.mod.client.util.SpriteStorage;
import git.jbredwards.njarm.mod.common.config.client.ParticlesConfig;
import git.jbredwards.njarm.mod.common.message.MessageParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public enum ParticleProviders implements IParticleProvider
{
    MAGIC_ORE_BLOCK_DUST(getBlockDustFromTextures(new SpriteStorage(ParticlesConfig::getMagicOreParticle))),
    MAGIC_ORE_DIGGING(getDiggingFromTextures(new SpriteStorage(ParticlesConfig::getMagicOreParticle))),
    XP_ORE_BLOCK_DUST(getBlockDustFromTextures(new SpriteStorage(ParticlesConfig::getXpOreParticle))),
    XP_ORE_DIGGING(getDiggingFromTextures(new SpriteStorage(ParticlesConfig::getXpOreParticle))),
    NETHER_XP_ORE_BLOCK_DUST(getBlockDustFromTextures(new SpriteStorage(ParticlesConfig::getNetherXpOreParticle))),
    NETHER_XP_ORE_DIGGING(getDiggingFromTextures(new SpriteStorage(ParticlesConfig::getNetherXpOreParticle))),
    END_XP_ORE_BLOCK_DUST(getBlockDustFromTextures(new SpriteStorage(ParticlesConfig::getEndXpOreParticle))),
    END_XP_ORE_DIGGING(getDiggingFromTextures(new SpriteStorage(ParticlesConfig::getEndXpOreParticle))),
    OVERGROWN_DIRT_BLOCK_DUST(getBlockDustFromTextures(new SpriteStorage(ParticlesConfig::getOvergrownDirtParticle))),
    OVERGROWN_DIRT_DIGGING(getDiggingFromTextures(new SpriteStorage(ParticlesConfig::getOvergrownDirtParticle))),
    OVERGROWN_STONE_BLOCK_DUST(getBlockDustFromTextures(new SpriteStorage(ParticlesConfig::getOvergrownStoneParticle))),
    OVERGROWN_STONE_DIGGING(getDiggingFromTextures(new SpriteStorage(ParticlesConfig::getOvergrownStoneParticle))),
    LIT_REDSTONE(new IParticleProvider() {
        @Nonnull
        @SideOnly(Side.CLIENT)
        @Override
        public Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
            return new ParticleLitRedstone(world, x, y, z, args.length == 2 ? args[1] : 1, (float)xSpeed, (float)ySpeed, (float)zSpeed, args.length > 0 ? args[0] : 240);
        }
    });

    @Nonnull final IParticleProvider internalProvider;
    ParticleProviders(@Nonnull IParticleProvider internalProvider) { this.internalProvider = internalProvider; }

    @Nonnull
    static IParticleProvider getBlockDustFromTextures(@Nonnull SpriteStorage... layers) {
        return new IParticleProvider() {
            @Nonnull
            @SideOnly(Side.CLIENT)
            @Override
            public Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
                return new ParticleLayeredBlockDust(world, x, y, z, xSpeed, ySpeed, zSpeed, layers, args).init();
            }
        };
    }

    @Nonnull
    static IParticleProvider getDiggingFromTextures(@Nonnull SpriteStorage... layers) {
        return new IParticleProvider() {
            @Nonnull
            @SideOnly(Side.CLIENT)
            @Override
            public Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
                return new ParticleLayeredDigging(world, x, y, z, xSpeed, ySpeed, zSpeed, layers, args).init();
            }
        };
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    @Override
    public Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
        return internalProvider.getParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, args);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void spawnClient(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
        internalProvider.spawnClient(world, x, y, z, xSpeed, ySpeed, zSpeed, args);
    }

    @Override
    public void spawnServer(@Nonnull World world, float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int numOfParticles, int... args) {
        Main.wrapper.sendToAllAround(
                new MessageParticle(this, x, y, z, xOffset, yOffset, zOffset, speed, numOfParticles, args),
                new NetworkRegistry.TargetPoint(world.provider.getDimension(), x, y, z, 32)
        );
    }
}
