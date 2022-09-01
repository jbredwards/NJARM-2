package git.jbredwards.njarm.mod.client.particle;

import git.jbredwards.njarm.mod.client.ClientEventHandler;
import net.minecraft.client.particle.Particle;
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
public class ParticleGlowSquidAura extends Particle
{
    public ParticleGlowSquidAura(@Nonnull World worldIn, double x, double y, double z, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, x, y, z, xSpeedIn, ySpeedIn, zSpeedIn);
        setParticleTexture(ClientEventHandler.GLOW_SQUID_AURA.getSprite());
        canCollide = false;

        if(worldIn.rand.nextBoolean()) {
            particleRed = 0.6f;
            particleGreen = 1;
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

    @Override
    public int getBrightnessForRender(float partialTick) { return 240; }

    @Override
    public int getFXLayer() { return 1; }

    @Override
    public void setParticleTextureIndex(int particleTextureIndex) { }
}
