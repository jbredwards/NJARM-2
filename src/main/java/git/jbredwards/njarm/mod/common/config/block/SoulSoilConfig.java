package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class SoulSoilConfig implements IConfig
{
    @Config.LangKey("config.njarm.block.soulSoil.useSoilForWither")
    public final boolean useSoilForWither;

    //needed for gson
    public SoulSoilConfig(boolean useSoilForWither) {
        this.useSoilForWither = useSoilForWither;
    }
}
