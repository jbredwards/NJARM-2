package git.jbredwards.njarm.mod.client.particle;

import git.jbredwards.njarm.mod.client.particle.util.ParticleLayer;
import net.minecraft.client.particle.ParticleBlockDust;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
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
public final class ParticleLayeredBlockDust extends ParticleBlockDust
{
    @Nonnull
    protected final ParticleLayer[] layers;
    protected int brightnessForRender;

    public ParticleLayeredBlockDust(@Nonnull World worldIn, double x, double y, double z, double xSpeedIn, double ySpeedIn, double zSpeedIn, @Nonnull ParticleLayer[] layers) {
        super(worldIn, x, y, z, xSpeedIn, ySpeedIn, zSpeedIn, Blocks.AIR.getDefaultState());
        this.layers = layers;
    }

    @Override
    public void renderParticle(@Nonnull BufferBuilder buffer, @Nonnull Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        for(ParticleLayer layer : layers) {
            particleTexture = layer.getSprite();
            brightnessForRender = layer.brightness < 0 ? super.getBrightnessForRender(partialTicks) : layer.brightness;
            super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        }
    }

    @Override
    public int getBrightnessForRender(float partialTick) { return brightnessForRender; }
}
