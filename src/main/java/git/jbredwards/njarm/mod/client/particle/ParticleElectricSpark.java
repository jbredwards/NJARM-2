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
public class ParticleElectricSpark extends ParticleGlowing
{
    public ParticleElectricSpark(@Nonnull World worldIn, double x, double y, double z, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, x, y, z, xSpeedIn, ySpeedIn, zSpeedIn);
        motionX = xSpeedIn * 0.25;
        motionY = ySpeedIn * 0.25;
        motionZ = zSpeedIn * 0.25;
        particleGreen = 0.9f;

        setMaxAge(world.rand.nextInt(2) + 2);
    }
}
