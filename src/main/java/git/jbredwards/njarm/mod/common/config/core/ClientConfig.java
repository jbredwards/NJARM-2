package git.jbredwards.njarm.mod.common.config.core;

import git.jbredwards.njarm.mod.Main;
import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.config.client.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class ClientConfig implements IConfig
{
    @Nonnull
    private final NonNullList<IConfig> CONFIGS = NonNullList.create();

    @Config.LangKey("config.njarm.core.client.rendering")
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
    public void onUpdate() {
        CONFIGS.forEach(IConfig::onUpdate);
        Main.proxy.markRenderUpdate();
    }

    //needed for gson
    public ClientConfig(@Nonnull RenderingConfig renderingCfg) {
        this.renderingCfg = register(renderingCfg);
    }
}
