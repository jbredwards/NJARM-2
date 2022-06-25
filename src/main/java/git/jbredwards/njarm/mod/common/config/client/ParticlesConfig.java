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
    @Config.LangKey("config.njarm.client.particles.magicOreParticleOverlay")
    @Nonnull public final String magicOreParticleOverlay;
    @Nonnull public static String getMagicOreParticle() {
        return ConfigHandler.clientCfg.particlesCfg.magicOreParticleOverlay;
    }

    @Config.LangKey("config.njarm.client.particles.xpOreParticleOverlay")
    @Nonnull public final String xpOreParticleOverlay;
    @Nonnull public static String getXpOreParticle() {
        return ConfigHandler.clientCfg.particlesCfg.xpOreParticleOverlay;
    }

    @Config.LangKey("config.njarm.client.particles.netherXpOreParticleOverlay")
    @Nonnull public final String netherXpOreParticleOverlay;
    @Nonnull public static String getNetherXpOreParticle() {
        return ConfigHandler.clientCfg.particlesCfg.netherXpOreParticleOverlay;
    }

    @Config.LangKey("config.njarm.client.particles.endXpOreParticleOverlay")
    @Nonnull public final String endXpOreParticleOverlay;
    @Nonnull public static String getEndXpOreParticle() {
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
