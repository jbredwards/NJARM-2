package git.jbredwards.njarm.mod.client.particle;

import git.jbredwards.njarm.mod.client.ClientEventHandler;
import net.minecraft.client.particle.ParticleFlame;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ParticleBlueFlame extends ParticleFlame
{
    public ParticleBlueFlame(@Nonnull World worldIn, double x, double y, double z, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, x, y, z, xSpeedIn, ySpeedIn, zSpeedIn);
        setParticleTexture(ClientEventHandler.BLUE_FLAME.getSprite());
    }

    @Override
    public int getFXLayer() { return 1; }

    @Override
    public void setParticleTextureIndex(int particleTextureIndex) { }
}
