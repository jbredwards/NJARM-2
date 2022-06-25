package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class FragileIceConfig implements IConfig
{
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.block.fragileIce.meltingTicks")
    public final int meltingTicks;
    public static int getMeltingTicks() { return ConfigHandler.blockCfg.fragileIceCfg.meltingTicks; }

    //needed for gson
    public FragileIceConfig(int meltingTicks) { this.meltingTicks = meltingTicks; }
}
