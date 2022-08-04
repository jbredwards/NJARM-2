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
    @Config.LangKey("config.njarm.item.chargedSunstone.canDispense")
    public final boolean canDispense;
    public static boolean canDispense() { return ConfigHandler.itemCfg.chargedSunstoneCfg.canDispense; }

    @Config.LangKey("config.njarm.item.chargedSunstone.canThrow")
    public final boolean canThrow;
    public static boolean canThrow() { return ConfigHandler.itemCfg.chargedSunstoneCfg.canThrow; }

    @Config.LangKey("config.njarm.item.chargedSunstone.explode")
    public final boolean explode;
    public static boolean explode() { return ConfigHandler.itemCfg.chargedSunstoneCfg.explode; }

    @Config.LangKey("config.njarm.item.chargedSunstone.explodeDmg")
    public final boolean explodeDmg;
    public static boolean explodeDmg() { return ConfigHandler.itemCfg.chargedSunstoneCfg.explodeDmg; }

    @Config.LangKey("config.njarm.item.chargedSunstone.explodeFire")
    public final boolean explodeFire;
    public static boolean explodeFire() { return ConfigHandler.itemCfg.chargedSunstoneCfg.explodeFire; }

    @Config.LangKey("config.njarm.item.chargedSunstone.explodeStrength")
    public final float explodeStrength;
    public static float explodeStrength() { return ConfigHandler.itemCfg.chargedSunstoneCfg.explodeStrength; }

    @Config.LangKey("config.njarm.item.chargedSunstone.fromLightning")
    public final boolean fromLightning;
    public static boolean fromLightning() { return ConfigHandler.itemCfg.chargedSunstoneCfg.fromLightning; }

    @Config.LangKey("config.njarm.item.chargedSunstone.lightning")
    public final boolean lightning;
    public static boolean lightning() { return ConfigHandler.itemCfg.chargedSunstoneCfg.lightning; }

    //needed for gson
    public ChargedSunstoneConfig(boolean lightning, boolean explode, boolean explodeDmg, boolean explodeFire, boolean canThrow, boolean canDispense, float explodeStrength, boolean fromLightning) {
        this.lightning = lightning;
        this.explode = explode;
        this.explodeDmg = explodeDmg;
        this.explodeFire = explodeFire;
        this.canThrow = canThrow;
        this.canDispense = canDispense;
        this.explodeStrength = explodeStrength;
        this.fromLightning = fromLightning;
    }
}
