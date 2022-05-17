package git.jbredwards.njarm.mod.common.config.client;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class RenderingConfig implements IConfig
{
    @Config.Comment("Experience orbs are translucent, like when they were first added.")
    public final boolean doTranslucentXPOrbs;
    public static boolean doTranslucentXPOrbs() { return ConfigHandler.clientCfg.renderingCfg.doTranslucentXPOrbs; }

    @Config.Comment("Entity shadows shrink the further the entity is from the ground, like in the Bedrock Edition of the game.")
    public final boolean doBedrockShadowSize;
    public static boolean doBedrockShadowSize() { return ConfigHandler.clientCfg.renderingCfg.doBedrockShadowSize; }

    @Config.SlidingOption
    @Config.RangeInt(min = 0, max = 200)
    @Config.Comment("How many ticks of night vision remaining when the screen starts to flash? " +
            "Setting this to 0 will disable it entirely, vanilla by default starts at 200 ticks (10 seconds).")
    public final int nightVisionFlashing;
    public static int nightVisionFlashing() { return ConfigHandler.clientCfg.renderingCfg.nightVisionFlashing; }

    //needed for gson
    public RenderingConfig(boolean doTranslucentXPOrbs, boolean doBedrockShadowSize, int nightVisionFlashing) {
        this.doTranslucentXPOrbs = doTranslucentXPOrbs;
        this.doBedrockShadowSize = doBedrockShadowSize;
        this.nightVisionFlashing = nightVisionFlashing;
    }
}
