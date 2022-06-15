package git.jbredwards.njarm.mod.common.config.core;

import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.config.block.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class BlockConfig implements IConfig
{
    @Nonnull
    private final NonNullList<IConfig> CONFIGS = NonNullList.create();

    @Config.Name("blue fire")
    @Config.Comment("Change blue fire damage output & the blocks that can sustain it.")
    @Nonnull public final BlueFireConfig blueFireCfg;

    @Config.Name("food crates")
    @Config.Comment("Change food crate step effects & drop properties.")
    @Nonnull public final FoodCrateConfig foodCrateCfg;

    @Config.Name("fragile ice")
    @Config.Comment("Change fragile ice melting time.")
    @Nonnull public final FragileIceConfig fragileIceCfg;

    @Config.Name("magic dust ores")
    @Config.Comment("Enable/disable legacy njarm 1 behaviors.")
    @Nonnull public final MagicOreConfig magicOreCfg;

    @Config.Name("totem of undying")
    @Config.Comment("Enable/disable legacy njarm 1 behaviors.")
    @Nonnull public final TotemOfUndyingConfig totemOfUndyingCfg;

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
    public BlockConfig(@Nonnull BlueFireConfig blueFireCfg, @Nonnull FoodCrateConfig foodCrateCfg, @Nonnull FragileIceConfig fragileIceCfg, @Nonnull MagicOreConfig magicOreCfg, @Nonnull TotemOfUndyingConfig totemOfUndyingCfg) {
        this.blueFireCfg = register(blueFireCfg);
        this.foodCrateCfg = register(foodCrateCfg);
        this.fragileIceCfg = register(fragileIceCfg);
        this.magicOreCfg = register(magicOreCfg);
        this.totemOfUndyingCfg = register(totemOfUndyingCfg);
    }
}
