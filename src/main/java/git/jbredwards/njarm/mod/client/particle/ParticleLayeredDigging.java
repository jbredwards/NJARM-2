package git.jbredwards.njarm.mod.client.particle;

import git.jbredwards.njarm.mod.client.particle.util.ParticleLayer;
import git.jbredwards.njarm.mod.client.util.SpriteStorage;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public final class ParticleLayeredDigging extends ParticleDigging
{
    @Nonnull
    protected final ParticleLayer[] layers;
    protected int brightnessForRender;

    public ParticleLayeredDigging(@Nonnull World worldIn, double x, double y, double z, double xSpeedIn, double ySpeedIn, double zSpeedIn, @Nonnull SpriteStorage[] layers, @Nonnull int[] args) {
        super(worldIn, x, y, z, xSpeedIn, ySpeedIn, zSpeedIn, Block.getStateById(args[0]));
        this.layers = new ParticleLayer[layers.length + 1];
        this.layers[0] = new ParticleLayer(particleTexture, args[1]);

        for(int i = 1; i <= layers.length; i++)
            this.layers[i] = new ParticleLayer(layers[i - 1], args[i + 1]);
    }

    @Override
    protected void multiplyColor(@Nullable BlockPos pos) {
        for(int i = 0; i < layers.length; i++) {
            final int color = Minecraft.getMinecraft().getBlockColors()
                    .colorMultiplier(sourceState, world, pos, i);

            layers[i].colors[0] = (color >> 16 & 255) / 255f;
            layers[i].colors[1] = (color >> 8 & 255) / 255f;
            layers[i].colors[2] = (color & 255) / 255f;
        }
    }

    @Override
    public void renderParticle(@Nonnull BufferBuilder buffer, @Nonnull Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        for(ParticleLayer layer : layers) {
            particleTexture = layer.getSprite();
            particleRed = 0.6f * layer.colors[0];
            particleGreen = 0.6f * layer.colors[1];
            particleBlue = 0.6f * layer.colors[2];
            brightnessForRender = layer.brightness < 0 ? super.getBrightnessForRender(partialTicks) : layer.brightness;
            super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        }
    }

    @Override
    public int getBrightnessForRender(float partialTicks) { return brightnessForRender; }
}
