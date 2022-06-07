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

    //needed for gson
    public ParticlesConfig(@Nonnull String magicOreParticleOverlay) {
        this.magicOreParticleOverlay = magicOreParticleOverlay;
    }
}
