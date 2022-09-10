package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class LightningRodConfig implements IConfig
{
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.block.lightningRod.radius")
    public final int radius;
    public static int radius() { return ConfigHandler.blockCfg.lightningRodCfg.radius; }

    //needed for gson
    public LightningRodConfig(int radius) { this.radius = radius; }
}
