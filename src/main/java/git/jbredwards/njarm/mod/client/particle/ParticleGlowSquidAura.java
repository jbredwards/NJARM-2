package git.jbredwards.njarm.mod.client.particle;

import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class ParticleGlowSquidAura extends ParticleGlowing
{
    public ParticleGlowSquidAura(@Nonnull World worldIn, double x, double y, double z, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, x, y, z, xSpeedIn, ySpeedIn, zSpeedIn);
        if(worldIn.rand.nextBoolean()) {
            particleRed = 0.6f;
            particleBlue = 0.8f;
        }
        else {
            particleRed = 0.08f;
            particleGreen = 0.4f;
            particleBlue = 0.4f;
        }

        motionX *= 0.1;
        motionY *= 0.2;
        motionZ *= 0.1;

        setMaxAge((int)(8 / (world.rand.nextDouble() * 0.8 + 0.2)));
    }
}
