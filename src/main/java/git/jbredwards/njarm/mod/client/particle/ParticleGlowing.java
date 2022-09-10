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
public abstract class ParticleGlowing extends Particle
{
    public ParticleGlowing(@Nonnull World worldIn, double x, double y, double z, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, x, y, z, xSpeedIn, ySpeedIn, zSpeedIn);
        setParticleTexture(ClientEventHandler.GLOW.getSprite());
        canCollide = false;
    }

    @Override
    public int getBrightnessForRender(float partialTick) { return 240; }

    @Override
    public int getFXLayer() { return 1; }

    @Override
    public void setParticleTextureIndex(int particleTextureIndex) { }
}
