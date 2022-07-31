package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class MagicOreConfig implements IConfig
{
    @Config.LangKey("config.njarm.block.magicOre.emissive")
    public final boolean emissive;
    public static boolean emissive() { return ConfigHandler.blockCfg.magicOreCfg.emissive; }

    @Config.LangKey("config.njarm.block.magicOre.enchantPowerBonus")
    public final float enchantPowerBonus;

    @Config.LangKey("config.njarm.block.magicOre.isSticky")
    public final boolean isSticky;

    @Config.LangKey("config.njarm.block.magicOre.levitation")
    public final boolean levitation;

    //needed for gson
    public MagicOreConfig(boolean emissive, boolean levitation, float enchantPowerBonus, boolean isSticky) {
        this.emissive = emissive;
        this.levitation = levitation;
        this.enchantPowerBonus = enchantPowerBonus;
        this.isSticky = isSticky;
    }
}
