package git.jbredwards.njarm.mod.common.config.entity;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.entity.util.ISpawnableConfig;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class HighlandCooConfig implements ISpawnableConfig
{
    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.entity.generic.biomeData")
    @Nonnull public final String biomeData;
    @Nonnull public String biomeData() { return biomeData; }

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.entity.generic.maxSpawnCount")
    public final int maxSpawnCount;
    public int maxSpawnCount() { return maxSpawnCount; }

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.entity.generic.minSpawnCount")
    public final int minSpawnCount;
    public int minSpawnCount() { return minSpawnCount; }

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.entity.generic.spawnWeight")
    public final int spawnWeight;
    public int spawnWeight() { return spawnWeight; }

    @Config.LangKey("config.njarm.entity.highlandCoo.dyeable")
    public final boolean dyeable;
    public static boolean isDyeable() { return ConfigHandler.entityCfg.highlandCooCfg.dyeable; }

    @Config.LangKey("config.njarm.entity.highlandCoo.useRandomColorSpawn")
    public final boolean useRandomColorSpawn;
    public static boolean useRandomColorSpawn() { return ConfigHandler.entityCfg.highlandCooCfg.useRandomColorSpawn; }

    //needed for gson
    public HighlandCooConfig(int spawnWeight, int minSpawnCount, int maxSpawnCount, @Nonnull String biomeData, boolean dyeable, boolean useRandomColorSpawn) {
        this.spawnWeight = spawnWeight;
        this.minSpawnCount = minSpawnCount;
        this.maxSpawnCount = maxSpawnCount;
        this.biomeData = biomeData;
        this.dyeable = dyeable;
        this.useRandomColorSpawn = useRandomColorSpawn;
    }
}
