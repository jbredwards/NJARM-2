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

    @Config.LangKey("config.njarm.client.particles.overgrownDirtParticleOverlay")
    @Nonnull public final String overgrownDirtParticleOverlay;
    @Nonnull public static String getOvergrownDirtParticle() {
        return ConfigHandler.clientCfg.particlesCfg.overgrownDirtParticleOverlay;
    }

    @Config.LangKey("config.njarm.client.particles.overgrownStoneParticleOverlay")
    @Nonnull public final String overgrownStoneParticleOverlay;
    @Nonnull public static String getOvergrownStoneParticle() {
        return ConfigHandler.clientCfg.particlesCfg.overgrownStoneParticleOverlay;
    }

    //needed for gson
    public ParticlesConfig(@Nonnull String magicOreParticleOverlay, @Nonnull String xpOreParticleOverlay, @Nonnull String netherXpOreParticleOverlay, @Nonnull String endXpOreParticleOverlay, @Nonnull String overgrownDirtParticleOverlay, @Nonnull String overgrownStoneParticleOverlay) {
        this.magicOreParticleOverlay = magicOreParticleOverlay;
        this.xpOreParticleOverlay = xpOreParticleOverlay;
        this.netherXpOreParticleOverlay = netherXpOreParticleOverlay;
        this.endXpOreParticleOverlay = endXpOreParticleOverlay;
        this.overgrownDirtParticleOverlay = overgrownDirtParticleOverlay;
        this.overgrownStoneParticleOverlay = overgrownStoneParticleOverlay;
    }
}
