package git.jbredwards.njarm.mod.common.config.item;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class TeleportStaffConfig implements IConfig
{
    @Config.RangeDouble(min = 0)
    @Config.LangKey("config.njarm.item.teleportStaff.amoChance")
    public final double amoChance;
    public static double amoChance() { return ConfigHandler.itemCfg.teleportStaffCfg.amoChance; }

    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.item.teleportStaff.cooldown")
    public final int cooldown;
    public static int cooldown() { return ConfigHandler.itemCfg.teleportStaffCfg.cooldown; }

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.item.teleportStaff.durability")
    public final int durability;
    public static int durability() { return ConfigHandler.itemCfg.teleportStaffCfg.durability; }

    @Config.RangeDouble(min = 0)
    @Config.LangKey("config.njarm.item.teleportStaff.range")
    public final double range;
    public static double range() { return ConfigHandler.itemCfg.teleportStaffCfg.range; }

    //needed for gson
    public TeleportStaffConfig(double amoChance, int cooldown, int durability, double range) {
        this.amoChance = amoChance;
        this.cooldown = cooldown;
        this.durability = durability;
        this.range = range;
    }
}
