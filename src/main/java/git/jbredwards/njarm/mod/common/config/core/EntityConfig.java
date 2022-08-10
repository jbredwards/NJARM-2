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

    @Config.LangKey("config.njarm.core.entity.chocolateCow")
    @Nonnull public final ChocolateCowConfig chocolateCowCfg;

    @Config.LangKey("config.njarm.core.entity.highlandCoo")
    @Nonnull public final HighlandCooConfig highlandCooCfg;

    @Config.LangKey("config.njarm.core.entity.moobloom")
    @Nonnull public final MoobloomConfig moobloomCfg;

    @Config.LangKey("config.njarm.core.entity.mudPig")
    @Nonnull public final MudPigConfig mudPigCfg;

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
    public EntityConfig(@Nonnull ChocolateCowConfig chocolateCowCfg, @Nonnull HighlandCooConfig highlandCooCfg, @Nonnull MoobloomConfig moobloomCfg, @Nonnull MudPigConfig mudPigCfg) {
        this.chocolateCowCfg = chocolateCowCfg;
        this.highlandCooCfg = register(highlandCooCfg);
        this.moobloomCfg = register(moobloomCfg);
        this.mudPigCfg = register(mudPigCfg);
    }
}
