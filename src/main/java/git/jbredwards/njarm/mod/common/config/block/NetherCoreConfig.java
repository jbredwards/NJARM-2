package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class NetherCoreConfig implements IConfig
{
    @Config.LangKey("config.njarm.block.netherCore.altReactorBehavior")
    public final boolean altReactorBehavior;
    public static boolean getAltReactorBehavior() { return ConfigHandler.blockCfg.netherCoreCfg.altReactorBehavior; }

    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.block.netherCore.range")
    public final int range;
    public static int getRange() { return ConfigHandler.blockCfg.netherCoreCfg.range; }

    @Config.RangeInt(min = 100)
    @Config.LangKey("config.njarm.block.netherCore.duration")
    public final int duration;
    public static int getDuration() { return ConfigHandler.blockCfg.netherCoreCfg.duration; }

    @Config.RangeInt(min = 1)
    @Config.LangKey("config.njarm.block.netherCore.pigmanCooldown")
    public final int pigmanCooldown;
    public static int getPigmanCooldown() { return ConfigHandler.blockCfg.netherCoreCfg.pigmanCooldown; }

    @Config.RangeInt(min = 1)
    @Config.LangKey("config.njarm.block.netherCore.itemCooldown")
    public final int itemCooldown;
    public static int getItemCooldown() { return ConfigHandler.blockCfg.netherCoreCfg.itemCooldown; }

    @Config.RangeInt(min = 1)
    @Config.LangKey("config.njarm.block.netherCore.itemCount")
    public final int itemCount;
    public static int getItemCount() { return ConfigHandler.blockCfg.netherCoreCfg.itemCount; }

    @Config.LangKey("config.njarm.block.netherCore.dynamicDifficulty")
    public final boolean dynamicDifficulty;
    public static boolean isDynamicDifficulty() { return ConfigHandler.blockCfg.netherCoreCfg.dynamicDifficulty; }

    //needed for gson
    public NetherCoreConfig(boolean altReactorBehavior, int range, int duration, int pigmanCooldown, int itemCooldown, int itemCount, boolean dynamicDifficulty) {
        this.altReactorBehavior = altReactorBehavior;
        this.range = range;
        this.duration = duration;
        this.pigmanCooldown = pigmanCooldown;
        this.itemCooldown = itemCooldown;
        this.itemCount = itemCount;
        this.dynamicDifficulty = dynamicDifficulty;
    }
}
