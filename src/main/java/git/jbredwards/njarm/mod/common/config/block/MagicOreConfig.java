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
    @Config.Comment("Mobs that stand on magic dust ores get levitation.")
    public final boolean levitation;

    @Config.Comment("Magic dust ores boost nearby enchanting tables by this much (set to 0 to disable).")
    public final float enchantPowerBonus;

    @Config.Comment("Magic dust ores stick to each other when moved with pistons.")
    public final boolean isSticky;

    //needed for gson
    public MagicOreConfig(boolean levitation, float enchantPowerBonus, boolean isSticky) {
        this.levitation = levitation;
        this.enchantPowerBonus = enchantPowerBonus;
        this.isSticky = isSticky;
    }
}
