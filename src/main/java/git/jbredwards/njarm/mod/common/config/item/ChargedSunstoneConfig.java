package git.jbredwards.njarm.mod.common.config.item;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class ChargedSunstoneConfig implements IConfig
{
    @Config.LangKey("config.njarm.item.chargedSunstone.lightning")
    public final boolean lightning;
    public static boolean lightning() { return ConfigHandler.itemCfg.chargedSunstoneCfg.lightning; }

    @Config.LangKey("config.njarm.item.chargedSunstone.explode")
    public final boolean explode;
    public static boolean explode() { return ConfigHandler.itemCfg.chargedSunstoneCfg.explode; }

    @Config.LangKey("config.njarm.item.chargedSunstone.explodeDmg")
    public final boolean explodeDmg;
    public static boolean explodeDmg() { return ConfigHandler.itemCfg.chargedSunstoneCfg.explodeDmg; }

    @Config.LangKey("config.njarm.item.chargedSunstone.explodeFire")
    public final boolean explodeFire;
    public static boolean explodeFire() { return ConfigHandler.itemCfg.chargedSunstoneCfg.explodeFire; }

    //needed for gson
    public ChargedSunstoneConfig(boolean lightning, boolean explode, boolean explodeDmg, boolean explodeFire) {
        this.lightning = lightning;
        this.explode = explode;
        this.explodeDmg = explodeDmg;
        this.explodeFire = explodeFire;
    }
}
