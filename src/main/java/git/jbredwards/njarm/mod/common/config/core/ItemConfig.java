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

    @Config.LangKey("config.njarm.core.item.eggShells")
    @Nonnull public final EggShellsConfig eggShellCfg;

    @Config.LangKey("config.njarm.core.item.equipment")
    @Nonnull public final EquipmentConfig equipmentCfg;

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
    public ItemConfig(@Nonnull EggShellsConfig eggShellCfg, @Nonnull EquipmentConfig equipmentCfg) {
        this.eggShellCfg = register(eggShellCfg);
        this.equipmentCfg = register(equipmentCfg);
    }
}
