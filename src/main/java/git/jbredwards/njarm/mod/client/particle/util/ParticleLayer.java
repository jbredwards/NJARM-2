package git.jbredwards.njarm.mod.client.particle.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Handles one layer of layered particles ({@link git.jbredwards.njarm.mod.client.particle.ParticleLayeredBlockDust ParticleLayeredBlockDust} &
 * {@link git.jbredwards.njarm.mod.client.particle.ParticleLayeredDigging ParticleLayeredDigging})
 * @author jbred
 *
 */
public class ParticleLayer
{
    @Nullable
    @SideOnly(Side.CLIENT)
    protected TextureAtlasSprite sprite;

    @Nonnull
    protected String prevTexture;

    @Nonnull
    protected final Supplier<String> texture;
    protected final int brightness;

    public ParticleLayer(@Nonnull Supplier<String> texture, int brightness) {
        this.texture = texture;
        this.brightness = brightness;
        this.prevTexture = texture.get();
    }

    public int getBrightness() { return brightness; }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getSprite() {
        //update sprite if supplier value is changed
        if(!prevTexture.equals(texture.get())) prevTexture = texture.get();
        else if(sprite != null) return sprite;

        return sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(prevTexture);
    }

    //used for actually rendering the layer
    @SideOnly(Side.CLIENT)
    public static class Stack
    {
        @Nonnull public final float[] colors = new float[3];
        @Nonnull protected final ParticleLayer layer;

        public Stack(@Nonnull ParticleLayer layer) { this.layer = layer; }
        public Stack(@Nonnull TextureAtlasSprite sprite) {
            this(new ParticleLayer(sprite::getIconName, -1));
            layer.sprite = sprite;
        }

        public int getBrightness() { return layer.getBrightness(); }

        @Nonnull
        public TextureAtlasSprite getSprite() { return layer.getSprite(); }
    }
}
