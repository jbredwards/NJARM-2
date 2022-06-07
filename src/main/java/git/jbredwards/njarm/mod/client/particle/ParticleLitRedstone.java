package git.jbredwards.njarm.mod.client.particle;

import net.minecraft.client.particle.ParticleRedstone;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ParticleRedstone that has an optional brightness constant
 * @author jbred 
 * 
 */
@SideOnly(Side.CLIENT)
public class ParticleLitRedstone extends ParticleRedstone
{
    protected final int brightness;

    public ParticleLitRedstone(World worldIn, double x, double y, double z, float red, float green, float blue, int brightness) {
        this(worldIn, x, y, z, 1, red, green, blue, brightness);
    }

    public ParticleLitRedstone(World worldIn, double x, double y, double z, float scale, float red, float green, float blue, int brightness) {
        super(worldIn, x, y, z, scale, red, green, blue);
        this.brightness = brightness;
    }

    @Override
    public int getBrightnessForRender(float partialTicks) { return brightness < 0 ? super.getBrightnessForRender(partialTicks) : brightness; }
}
