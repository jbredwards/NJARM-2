package git.jbredwards.njarm.mod.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Common-side storage for a {@link TextureAtlasSprite}, automatically updates when supplier changes
 * @author jbred
 *
 */
public class SpriteStorage
{
    @Nullable
    @SideOnly(Side.CLIENT)
    protected TextureAtlasSprite sprite;

    @Nonnull
    protected String prevSprite;

    @Nonnull
    public final Supplier<String> supplier;

    public SpriteStorage(@Nonnull Supplier<String> supplier) {
        this.supplier = supplier;
        this.prevSprite = supplier.get();
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getSprite() {
        //update sprite if supplier value is changed
        if(!prevSprite.equals(supplier.get())) prevSprite = supplier.get();
        else if(sprite != null) return sprite;

        return setSprite(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(prevSprite));
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite setSprite(@Nonnull TextureAtlasSprite spriteIn) { return sprite = spriteIn; }
}
