package git.jbredwards.njarm.mod.common.config.core;

import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.config.block.BlueFireConfig;
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
    public BlockConfig(@Nonnull BlueFireConfig blueFireCfg) {
        this.blueFireCfg = register(blueFireCfg);
    }
}
