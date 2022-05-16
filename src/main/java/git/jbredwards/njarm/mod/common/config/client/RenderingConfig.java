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

    //needed for gson
    public RenderingConfig(boolean doTranslucentXPOrbs, boolean doBedrockShadowSize) {
        this.doTranslucentXPOrbs = doTranslucentXPOrbs;
        this.doBedrockShadowSize = doBedrockShadowSize;
    }
}
