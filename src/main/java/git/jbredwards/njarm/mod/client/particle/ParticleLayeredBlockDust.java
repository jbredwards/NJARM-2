package git.jbredwards.njarm.mod.client.particle;

import git.jbredwards.fluidlogged_api.mod.asm.plugins.ASMHooks;
import git.jbredwards.njarm.mod.client.particle.util.ParticleLayer;
import git.jbredwards.njarm.mod.common.block.util.IEmissiveBlock;
import net.darkhax.bookshelf.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleBlockDust;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
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
public final class ParticleLayeredBlockDust extends ParticleBlockDust
{
    @Nonnull
    protected final ParticleLayer[] layers = new ParticleLayer[4];
    protected int brightnessForRender;

    public ParticleLayeredBlockDust(@Nonnull World worldIn, double x, double y, double z, double xSpeedIn, double ySpeedIn, double zSpeedIn, @Nonnull IBlockState state) {
        super(worldIn, x, y, z, xSpeedIn, ySpeedIn, zSpeedIn, state);
        final Block block = state.getBlock();

        for(BlockRenderLayer layer : BlockRenderLayer.values()) {
            if(ASMHooks.canRenderBlockInLayer(block, state, layer)) {
                ForgeHooksClient.setRenderLayer(layer);
                layers[layer.ordinal()] = new ParticleLayer(RenderUtils.getSprite(state),
                        block instanceof IEmissiveBlock && ((IEmissiveBlock)block).isEmissive(state, layer) ? 240 : -1);
            }
        }

        ForgeHooksClient.setRenderLayer(null);
    }

    @Override
    protected void multiplyColor(@Nullable BlockPos pos) {
        int index = 0;
        for(@Nullable ParticleLayer layer : layers) {
            if(layer != null) {
                final int color = Minecraft.getMinecraft().getBlockColors()
                        .colorMultiplier(sourceState, world, pos, index++);

                layer.colors[0] = (color >> 16 & 255) / 255f;
                layer.colors[1] = (color >> 8 & 255) / 255f;
                layer.colors[2] = (color & 255) / 255f;
            }
        }
    }

    @Override
    public void renderParticle(@Nonnull BufferBuilder buffer, @Nonnull Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        for(@Nullable ParticleLayer layer : layers) {
            if(layer != null) {
                particleTexture = layer.getSprite();
                particleRed = 0.6f * layer.colors[0];
                particleGreen = 0.6f * layer.colors[1];
                particleBlue = 0.6f * layer.colors[2];
                brightnessForRender = layer.brightness < 0 ? super.getBrightnessForRender(partialTicks) : layer.brightness;
                super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
            }
        }
    }

    @Override
    public int getBrightnessForRender(float partialTicks) { return brightnessForRender; }
}
