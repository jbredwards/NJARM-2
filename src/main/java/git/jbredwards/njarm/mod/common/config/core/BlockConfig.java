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

    @Config.LangKey("config.njarm.core.block.blueFire")
    @Nonnull public final BlueFireConfig blueFireCfg;

    @Config.LangKey("config.njarm.core.block.bubbleColumn")
    @Nonnull public final BubbleColumnConfig bubbleColumnCfg;

    @Config.LangKey("config.njarm.core.block.chain")
    @Nonnull public final ChainConfig chainCfg;

    @Config.LangKey("config.njarm.core.block.foodCrates")
    @Nonnull public final FoodCrateConfig foodCrateCfg;

    @Config.LangKey("config.njarm.core.block.fragileIce")
    @Nonnull public final FragileIceConfig fragileIceCfg;

    @Config.LangKey("config.njarm.core.block.magicOre")
    @Nonnull public final MagicOreConfig magicOreCfg;

    @Config.LangKey("config.njarm.core.block.netherCore")
    @Nonnull public final NetherCoreConfig netherCoreCfg;

    @Config.LangKey("config.njarm.core.block.totemOfUndying")
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
    public BlockConfig(@Nonnull BlueFireConfig blueFireCfg, @Nonnull BubbleColumnConfig bubbleColumnCfg, @Nonnull ChainConfig chainCfg, @Nonnull FoodCrateConfig foodCrateCfg, @Nonnull FragileIceConfig fragileIceCfg, @Nonnull MagicOreConfig magicOreCfg, @Nonnull NetherCoreConfig netherCoreCfg, @Nonnull TotemOfUndyingConfig totemOfUndyingCfg) {
        this.blueFireCfg = register(blueFireCfg);
        this.bubbleColumnCfg = register(bubbleColumnCfg);
        this.chainCfg = register(chainCfg);
        this.foodCrateCfg = register(foodCrateCfg);
        this.fragileIceCfg = register(fragileIceCfg);
        this.magicOreCfg = register(magicOreCfg);
        this.netherCoreCfg = register(netherCoreCfg);
        this.totemOfUndyingCfg = register(totemOfUndyingCfg);
    }
}
