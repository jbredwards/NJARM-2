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

    @Config.LangKey("config.njarm.core.entity.blestem")
    @Nonnull public final BlestemConfig blestemCfg;

    @Config.LangKey("config.njarm.core.entity.blood")
    @Nonnull public final BloodConfig bloodCfg;

    @Config.LangKey("config.njarm.core.entity.chocolateCow")
    @Nonnull public final ChocolateCowConfig chocolateCowCfg;

    @Config.LangKey("config.njarm.core.entity.fireCreeper")
    @Nonnull public final FireCreeperConfig fireCreeperCfg;

    @Config.LangKey("config.njarm.core.entity.fireSkeleton")
    @Nonnull public final FireSkeletonConfig fireSkeletonCfg;

    @Config.LangKey("config.njarm.core.entity.glowSquid")
    @Nonnull public final GlowSquidConfig glowSquidCfg;

    @Config.LangKey("config.njarm.core.entity.highlandCoo")
    @Nonnull public final HighlandCooConfig highlandCooCfg;

    @Config.LangKey("config.njarm.core.entity.moobloom")
    @Nonnull public final MoobloomConfig moobloomCfg;

    @Config.LangKey("config.njarm.core.entity.mudPig")
    @Nonnull public final MudPigConfig mudPigCfg;

    @Config.LangKey("config.njarm.core.entity.mummy")
    @Nonnull public final MummyConfig mummyCfg;

    @Config.LangKey("config.njarm.core.entity.pigman")
    @Nonnull public final PigmanConfig pigmanCfg;

    @Config.LangKey("config.njarm.core.entity.sap")
    @Nonnull public final SapConfig sapCfg;

    @Config.LangKey("config.njarm.core.entity.soulSkeleton")
    @Nonnull public final SoulSkeletonConfig soulSkeletonCfg;

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
    public EntityConfig(@Nonnull BlestemConfig blestemCfg, @Nonnull BloodConfig bloodCfg, @Nonnull ChocolateCowConfig chocolateCowCfg, @Nonnull FireCreeperConfig fireCreeperCfg, @Nonnull FireSkeletonConfig fireSkeletonCfg, @Nonnull GlowSquidConfig glowSquidCfg, @Nonnull HighlandCooConfig highlandCooCfg, @Nonnull MoobloomConfig moobloomCfg, @Nonnull MudPigConfig mudPigCfg, @Nonnull MummyConfig mummyCfg, @Nonnull PigmanConfig pigmanCfg, @Nonnull SapConfig sapCfg, @Nonnull SoulSkeletonConfig soulSkeletonCfg) {
        this.blestemCfg = register(blestemCfg);
        this.bloodCfg = register(bloodCfg);
        this.chocolateCowCfg = register(chocolateCowCfg);
        this.fireCreeperCfg = register(fireCreeperCfg);
        this.fireSkeletonCfg = register(fireSkeletonCfg);
        this.glowSquidCfg = register(glowSquidCfg);
        this.highlandCooCfg = register(highlandCooCfg);
        this.moobloomCfg = register(moobloomCfg);
        this.mudPigCfg = register(mudPigCfg);
        this.mummyCfg = register(mummyCfg);
        this.pigmanCfg = register(pigmanCfg);
        this.sapCfg = register(sapCfg);
        this.soulSkeletonCfg = register(soulSkeletonCfg);
    }
}
