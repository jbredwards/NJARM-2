package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class ChainConfig implements IConfig
{
    @Config.LangKey("config.njarm.block.chain.isLadder")
    public final boolean isLadder;
    public static boolean isLadder() { return ConfigHandler.blockCfg.chainCfg.isLadder; }

    //needed for gson
    public ChainConfig(boolean isLadder) {
        this.isLadder = isLadder;
    }
}
