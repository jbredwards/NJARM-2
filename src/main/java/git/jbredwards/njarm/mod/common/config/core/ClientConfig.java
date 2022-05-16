package git.jbredwards.njarm.mod.common.config.core;

import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.config.client.*;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
public final class ClientConfig implements IConfig
{
    @Nonnull
    private final List<IConfig> CONFIGS = new ArrayList<>();

    //config categories
    @Config.Name("rendering")
    @Config.Comment("Enable/disable changes this mod makes to miscellaneous rendering-related stuffs.")
    @Nonnull public final RenderingConfig renderingCfg;

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
    public ClientConfig(@Nonnull RenderingConfig renderingCfg) {
        this.renderingCfg = register(renderingCfg);
    }
}
