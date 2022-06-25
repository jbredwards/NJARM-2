package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class MagicOreConfig implements IConfig
{
    @Config.LangKey("config.njarm.block.magicOre.levitation")
    public final boolean levitation;

    @Config.LangKey("config.njarm.block.magicOre.enchantPowerBonus")
    public final float enchantPowerBonus;

    @Config.LangKey("config.njarm.block.magicOre.isSticky")
    public final boolean isSticky;

    //needed for gson
    public MagicOreConfig(boolean levitation, float enchantPowerBonus, boolean isSticky) {
        this.levitation = levitation;
        this.enchantPowerBonus = enchantPowerBonus;
        this.isSticky = isSticky;
    }
}
