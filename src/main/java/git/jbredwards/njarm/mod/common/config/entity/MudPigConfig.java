package git.jbredwards.njarm.mod.common.config.entity;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class MudPigConfig implements IConfig
{
    @Config.RangeInt(min = 0, max = 100)
    @Config.LangKey("config.njarm.entity.mudPig.flowerChance")
    public final int flowerChance;
    public static int flowerChance() { return ConfigHandler.entityCfg.mudPigCfg.flowerChance; }

    @Config.RangeInt(min = 0, max = 100)
    @Config.LangKey("config.njarm.entity.mudPig.spawnChance")
    public final int spawnChance;
    public static int spawnChance() { return ConfigHandler.entityCfg.mudPigCfg.spawnChance; }

    //needed for gson
    public MudPigConfig(int flowerChance, int spawnChance) {
        this.flowerChance = flowerChance;
        this.spawnChance = spawnChance;
    }
}
