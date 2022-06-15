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
    @Config.Comment("Players can place totems of undying as blocks.")
    public final boolean placeable;

    @Config.RangeInt(min = 0)
    @Config.Comment("While a totem of undying is placed, it will resurrect players that die within this radius around it.")
    public final int radius;

    @Config.Comment("Placed totems of undying can also resurrect pets that belong to players.")
    public final boolean resurrectPets;

    @Config.Comment("When the player looks at a totem of undying block, the area of effect bounding box will be revealed.")
    public final boolean showAOEAABB;

    @Config.RangeInt(min = 0)
    @Config.Comment("The radius around a totem of undying block that particles will spawn within.")
    public final int particleRadius;

    @Config.RangeDouble(min = 0, max = 1)
    @Config.Comment("For each block within the particle radius, a particle attempts to spawn, this is the chance the particle will successfully spawn.")
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
