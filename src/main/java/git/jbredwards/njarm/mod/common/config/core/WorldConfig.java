package git.jbredwards.njarm.mod.common.config.core;

import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.config.world.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class WorldConfig implements IConfig
{
    @Nonnull
    private final NonNullList<IConfig> CONFIGS = NonNullList.create();

    @Config.LangKey("config.njarm.core.world.desertSpawner")
    @Nonnull public final DesertSpawnerConfig desertSpawnerCfg;

    @Config.LangKey("config.njarm.core.world.netherSpawner")
    @Nonnull public final NetherSpawnerConfig netherSpawnerCfg;

    @Config.LangKey("config.njarm.core.world.oreGen")
    @Nonnull public final OreConfig oreCfg;

    //create a new config category while also adding it to the internal list
    @Nonnull
    private <T extends IConfig> T register(@Nonnull T cfg) {
        CONFIGS.add(cfg);
        return cfg;
    }

    //initialize internal configs
    @Override
    public void onFMLInit() { CONFIGS.forEach(IConfig::onFMLInit); }

    //update internal configs
    @Override
    public void onUpdate() { CONFIGS.forEach(IConfig::onUpdate); }

    //needed for gson
    public WorldConfig(@Nonnull DesertSpawnerConfig desertSpawnerCfg, @Nonnull NetherSpawnerConfig netherSpawnerCfg, @Nonnull OreConfig oreCfg) {
        this.desertSpawnerCfg = register(desertSpawnerCfg);
        this.netherSpawnerCfg = register(netherSpawnerCfg);
        this.oreCfg = register(oreCfg);
    }
}
