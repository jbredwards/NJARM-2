package git.jbredwards.njarm.mod.common.config.item;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class BonusHeartConfig implements IConfig
{
    @Config.RangeDouble(min = 1)
    @Config.LangKey("config.njarm.item.bonusHeart.amount")
    public final float amount;
    public static float amount() { return ConfigHandler.itemCfg.bonusHeartCfg.amount; }

    @Config.LangKey("config.njarm.item.bonusHeart.heal")
    public final boolean heal;
    public static boolean heal() { return ConfigHandler.itemCfg.bonusHeartCfg.heal; }

    @Config.LangKey("config.njarm.item.bonusHeart.keepOnDeath")
    public final boolean keepOnDeath;
    public static boolean keepOnDeath() { return ConfigHandler.itemCfg.bonusHeartCfg.keepOnDeath; }

    //needed for gson
    public BonusHeartConfig(float amount, boolean heal, boolean keepOnDeath) {
        this.amount = amount;
        this.heal = heal;
        this.keepOnDeath = keepOnDeath;
    }
}
