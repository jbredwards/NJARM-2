package git.jbredwards.njarm.mod.common.config.entity;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.entity.util.ISpawnableConfig;
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
public final class SoulSkeletonConfig implements ISpawnableConfig
{
    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.entity.generic.spawnData")
    @Nonnull
    public final String[] spawnData;
    @Nonnull public String[] spawnData() { return ConfigHandler.entityCfg.soulSkeletonCfg.spawnData; }
    @Nonnull public static final List<Biome> spawnBiomes = new ArrayList<>();
    @Nonnull public List<Biome> allSpawnBiomes() { return spawnBiomes; }

    //needed for gson
    public SoulSkeletonConfig(@Nonnull String[] spawnData) { this.spawnData = spawnData; }
}
