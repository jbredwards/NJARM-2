package git.jbredwards.njarm.mod.common.config.world;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
public final class DesertSpawnerConfig implements IConfig
{
    @Config.RangeDouble(min = 0, max = 1)
    @Config.LangKey("config.njarm.world.desertSpawner.chance")
    public final float chance;
    public static float chance() { return ConfigHandler.worldCfg.desertSpawnerCfg.chance; }

    @Config.LangKey("config.njarm.world.desertSpawner.biomeData")
    @Nonnull public final String[] biomeData;
    @Nonnull public static final List<Biome> BIOMES = new ArrayList<>();

    @Override
    public void onUpdate() { NBTUtils.gatherBiomesFromData(BIOMES, biomeData); }

    //needed for gson
    public DesertSpawnerConfig(float chance, @Nonnull String[] biomeData) {
        this.chance = chance;
        this.biomeData = biomeData;
    }
}
