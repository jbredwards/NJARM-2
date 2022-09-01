package git.jbredwards.njarm.mod.common.config.item;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class DummyConfig implements IConfig
{
    @Config.LangKey("config.njarm.item.dummy.displayCombo")
    public final boolean displayCombo;
    public static boolean displayCombo() { return ConfigHandler.itemCfg.dummyCfg.displayCombo; }

    //needed for gson
    public DummyConfig(boolean displayCombo) { this.displayCombo = displayCombo; }
}
