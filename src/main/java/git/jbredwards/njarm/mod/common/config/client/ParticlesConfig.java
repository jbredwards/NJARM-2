package git.jbredwards.njarm.mod.common.config.client;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class ParticlesConfig implements IConfig
{
    @Config.Comment("Magic Ore particle overlay texture.")
    @Nonnull public final String magicOreParticleOverlay;
    @Nonnull public static String getMagicOreParticle() {
        return ConfigHandler.clientCfg.particlesCfg.magicOreParticleOverlay;
    }

    @Config.Comment("Experience Ore particle overlay texture.")
    @Nonnull public final String xpOreParticleOverlay;
    @Nonnull public static String xpMagicOreParticle() {
        return ConfigHandler.clientCfg.particlesCfg.xpOreParticleOverlay;
    }

    @Config.Comment("Nether Experience Ore particle overlay texture.")
    @Nonnull public final String netherXpOreParticleOverlay;
    @Nonnull public static String netherXpMagicOreParticle() {
        return ConfigHandler.clientCfg.particlesCfg.netherXpOreParticleOverlay;
    }

    @Config.Comment("Ender Experience Ore particle overlay texture.")
    @Nonnull public final String endXpOreParticleOverlay;
    @Nonnull public static String endXpMagicOreParticle() {
        return ConfigHandler.clientCfg.particlesCfg.endXpOreParticleOverlay;
    }

    //needed for gson
    public ParticlesConfig(@Nonnull String magicOreParticleOverlay, @Nonnull String xpOreParticleOverlay, @Nonnull String netherXpOreParticleOverlay, @Nonnull String endXpOreParticleOverlay) {
        this.magicOreParticleOverlay = magicOreParticleOverlay;
        this.xpOreParticleOverlay = xpOreParticleOverlay;
        this.netherXpOreParticleOverlay = netherXpOreParticleOverlay;
        this.endXpOreParticleOverlay = endXpOreParticleOverlay;
    }
}
