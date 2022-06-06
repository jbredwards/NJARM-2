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
    public final int brightness;

    public ParticleLayer(@Nonnull Supplier<String> texture, int brightness) {
        this.texture = texture;
        this.brightness = brightness;
        this.prevTexture = texture.get();
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getSprite() {
        //update sprite if supplier value is changed
        if(!prevTexture.equals(texture.get())) {
            prevTexture = texture.get();
            return sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(prevTexture);
        }

        if(sprite != null) return sprite;
        else return sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.get());
    }
}
