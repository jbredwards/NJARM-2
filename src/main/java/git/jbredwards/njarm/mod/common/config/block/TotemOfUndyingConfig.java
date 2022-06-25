package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public class TotemOfUndyingConfig implements IConfig
{
    @Config.LangKey("config.njarm.block.totemOfUndying.placeable")
    public final boolean placeable;

    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.block.totemOfUndying.radius")
    public final int radius;

    @Config.LangKey("config.njarm.block.totemOfUndying.resurrectPets")
    public final boolean resurrectPets;

    @Config.LangKey("config.njarm.block.totemOfUndying.showAOEAABB")
    public final boolean showAOEAABB;

    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.block.totemOfUndying.particleRadius")
    public final int particleRadius;

    @Config.RangeDouble(min = 0, max = 1)
    @Config.LangKey("config.njarm.block.totemOfUndying.particleFrequency")
    public final double particleFrequency;

    public TotemOfUndyingConfig(boolean placeable, int radius, boolean resurrectPets, boolean showAOEAABB, int particleRadius, double particleFrequency) {
        this.placeable = placeable;
        this.radius = radius;
        this.resurrectPets = resurrectPets;
        this.showAOEAABB = showAOEAABB;
        this.particleRadius = particleRadius;
        this.particleFrequency = particleFrequency;
    }
}
