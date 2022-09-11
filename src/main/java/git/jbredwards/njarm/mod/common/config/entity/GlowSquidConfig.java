package git.jbredwards.njarm.mod.common.config.entity;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.entity.util.ISpawnableConfig;
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
public final class GlowSquidConfig implements ISpawnableConfig
{
    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.entity.generic.spawnData")
    @Nonnull public final String[] spawnData;
    @Nonnull public String[] spawnData() { return ConfigHandler.entityCfg.glowSquidCfg.spawnData; }
    @Nonnull public static final List<Biome> spawnBiomes = new ArrayList<>();
    @Nonnull public List<Biome> allSpawnBiomes() { return spawnBiomes; }

    @Config.LangKey("config.njarm.entity.glowSquid.overrideBiomes")
    @Nonnull public final String[] overrideBiomes;
    @Nonnull public static final List<Biome> OVERRIDE_BIOMES = new ArrayList<>();

    @Override
    public void onUpdate() { NBTUtils.gatherBiomesFromData(OVERRIDE_BIOMES, overrideBiomes); }

    //needed for gson
    public GlowSquidConfig(@Nonnull String[] spawnData, @Nonnull String[] overrideBiomes) {
        this.spawnData = spawnData;
        this.overrideBiomes = overrideBiomes;
    }
}
