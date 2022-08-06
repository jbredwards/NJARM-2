package git.jbredwards.njarm.mod.common.config.core;

import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.config.entity.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class EntityConfig implements IConfig
{
    @Nonnull
    private final NonNullList<IConfig> CONFIGS = NonNullList.create();

    @Config.LangKey("config.njarm.core.entity.highlandCoo")
    @Nonnull public final HighlandCooConfig highlandCooCfg;

    @Config.LangKey("config.njarm.core.entity.moobloom")
    @Nonnull public final MoobloomConfig moobloomCfg;

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
    public EntityConfig(@Nonnull HighlandCooConfig highlandCooCfg, @Nonnull MoobloomConfig moobloomCfg) {
        this.highlandCooCfg = register(highlandCooCfg);
        this.moobloomCfg = register(moobloomCfg);
    }
}
