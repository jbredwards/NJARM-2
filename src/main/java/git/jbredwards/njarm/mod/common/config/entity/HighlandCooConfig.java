package git.jbredwards.njarm.mod.common.config.entity;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.entity.util.ILivingEntityConfig;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class HighlandCooConfig implements ILivingEntityConfig
{
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 1)
    @Config.LangKey("config.njarm.entity.generic.trackerRange")
    public final int trackerRange;
    public int trackerRange() { return trackerRange; }

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 1)
    @Config.LangKey("config.njarm.entity.generic.trackerUpdateFrequency")
    public final int trackerUpdateFrequency;
    public int trackerUpdateFrequency() { return trackerUpdateFrequency; }

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.entity.generic.trackerSendVelocityUpdates")
    public final boolean trackerSendVelocityUpdates;
    public boolean trackerSendVelocityUpdates() { return trackerSendVelocityUpdates; }

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.entity.generic.spawnWeight")
    public final int spawnWeight;
    public int spawnWeight() { return spawnWeight; }

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.entity.generic.minSpawnCount")
    public final int minSpawnCount;
    public int minSpawnCount() { return minSpawnCount; }

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.entity.generic.maxSpawnCount")
    public final int maxSpawnCount;
    public int maxSpawnCount() { return maxSpawnCount; }

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.entity.generic.biomeData")
    @Nonnull public final String[] biomeData;
    @Nonnull public String[] biomeData() { return biomeData; }

    @Config.LangKey("config.njarm.entity.highlandCoo.dyeable")
    public final boolean dyeable;
    public static boolean isDyeable() { return ConfigHandler.entityCfg.highlandCooCfg.dyeable; }

    @Config.LangKey("config.njarm.entity.highlandCoo.useRandomColorSpawn")
    public final boolean useRandomColorSpawn;
    public static boolean useRandomColorSpawn() { return ConfigHandler.entityCfg.highlandCooCfg.useRandomColorSpawn; }

    //needed for gson
    public HighlandCooConfig(int trackerRange, int trackerUpdateFrequency, boolean trackerSendVelocityUpdates, int spawnWeight, int minSpawnCount, int maxSpawnCount, @Nonnull String[] biomeData, boolean dyeable, boolean useRandomColorSpawn) {
        this.trackerRange = trackerRange;
        this.trackerUpdateFrequency = trackerUpdateFrequency;
        this.trackerSendVelocityUpdates = trackerSendVelocityUpdates;
        this.spawnWeight = spawnWeight;
        this.minSpawnCount = minSpawnCount;
        this.maxSpawnCount = maxSpawnCount;
        this.biomeData = biomeData;
        this.dyeable = dyeable;
        this.useRandomColorSpawn = useRandomColorSpawn;
    }
}
