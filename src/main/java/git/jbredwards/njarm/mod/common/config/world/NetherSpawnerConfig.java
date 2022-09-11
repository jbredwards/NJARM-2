package git.jbredwards.njarm.mod.common.config.world;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public final class NetherSpawnerConfig implements IConfig
{
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.world.netherSpawner.triesPerChunk")
    public final int triesPerChunk;
    public static int triesPerChunk() { return ConfigHandler.worldCfg.netherSpawnerCfg.triesPerChunk; }

    @Config.LangKey("config.njarm.world.netherSpawner.spawnerEntries")
    @Nonnull public final String[] spawnerEntries;
    @Nonnull public static ResourceLocation[] ENTRIES = new ResourceLocation[0];
    @Nonnull public static ResourceLocation getRandomEntry(@Nonnull Random rand) {
        return ENTRIES[rand.nextInt(ENTRIES.length)];
    }

    @Override
    public void onUpdate() {
        ENTRIES = new ResourceLocation[spawnerEntries.length];
        for(int i = 0; i < spawnerEntries.length; i++)
            ENTRIES[i] = new ResourceLocation(spawnerEntries[i]);
    }

    //needed for gson
    public NetherSpawnerConfig(int triesPerChunk, @Nonnull String[] spawnerEntries) {
        this.triesPerChunk = triesPerChunk;
        this.spawnerEntries = spawnerEntries;
    }
}
