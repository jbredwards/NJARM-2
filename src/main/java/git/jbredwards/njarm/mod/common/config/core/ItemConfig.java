package git.jbredwards.njarm.mod.common.config.core;

import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.config.item.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class ItemConfig implements IConfig
{
    @Nonnull
    private final NonNullList<IConfig> CONFIGS = NonNullList.create();

    @Config.LangKey("config.njarm.core.item.bonusHeart")
    @Nonnull public final BonusHeartConfig bonusHeartCfg;

    @Config.LangKey("config.njarm.core.item.chargedSunstone")
    @Nonnull public final ChargedSunstoneConfig chargedSunstoneCfg;

    @Config.LangKey("config.njarm.core.item.dummy")
    @Nonnull public final DummyConfig dummyCfg;

    @Config.LangKey("config.njarm.core.item.eggShells")
    @Nonnull public final EggShellsConfig eggShellCfg;

    @Config.LangKey("config.njarm.core.item.equipment")
    @Nonnull public final EquipmentConfig equipmentCfg;

    @Config.LangKey("config.njarm.core.item.resistant")
    @Nonnull public final ResistantItemsConfig resistantCfg;

    @Config.LangKey("config.njarm.core.item.rupee")
    @Nonnull public final RupeeConfig rupeeCfg;

    @Config.LangKey("config.njarm.core.item.teleportStaff")
    @Nonnull public final TeleportStaffConfig teleportStaffCfg;

    //create a new config category while also adding it to the internal list
    @Nonnull
    private <T extends IConfig> T register(@Nonnull T cfg) {
        CONFIGS.add(cfg);
        return cfg;
    }

    //update internal configs
    @Override
    public void onUpdate() { CONFIGS.forEach(IConfig::onUpdate); }

    //initialize internal configs
    @Override
    public void onFMLInit() { CONFIGS.forEach(IConfig::onFMLInit); }

    //needed for gson
    public ItemConfig(@Nonnull BonusHeartConfig bonusHeartCfg, @Nonnull ChargedSunstoneConfig chargedSunstoneCfg, @Nonnull DummyConfig dummyCfg, @Nonnull EggShellsConfig eggShellCfg, @Nonnull EquipmentConfig equipmentCfg, @Nonnull ResistantItemsConfig resistantCfg, @Nonnull RupeeConfig rupeeCfg, @Nonnull TeleportStaffConfig teleportStaffCfg) {
        this.bonusHeartCfg = register(bonusHeartCfg);
        this.chargedSunstoneCfg = register(chargedSunstoneCfg);
        this.dummyCfg = register(dummyCfg);
        this.eggShellCfg = register(eggShellCfg);
        this.equipmentCfg = register(equipmentCfg);
        this.resistantCfg = register(resistantCfg);
        this.rupeeCfg = register(rupeeCfg);
        this.teleportStaffCfg = register(teleportStaffCfg);
    }
}
