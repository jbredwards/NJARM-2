package git.jbredwards.njarm.mod.client.particle.util;

import git.jbredwards.njarm.mod.client.util.SpriteStorage;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Handles one layer of layered particles ({@link git.jbredwards.njarm.mod.client.particle.ParticleLayeredBlockDust ParticleLayeredBlockDust} &
 * {@link git.jbredwards.njarm.mod.client.particle.ParticleLayeredDigging ParticleLayeredDigging})
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class ParticleLayer extends SpriteStorage
{
    @Nonnull public final float[] colors = new float[3];
    @Nonnull protected final SpriteStorage storage;
    public final int brightness;

    public ParticleLayer(@Nonnull SpriteStorage storage, int brightness) {
        super(storage.supplier);
        this.storage = storage;
        this.brightness = brightness;
    }

    public ParticleLayer(@Nonnull TextureAtlasSprite sprite, int brightness) {
        this(new SpriteStorage(sprite::getIconName), brightness);
        storage.setSprite(sprite);
    }

    @Nonnull
    @Override
    public TextureAtlasSprite getSprite() { return storage.getSprite(); }

    @Nonnull
    @Override
    public TextureAtlasSprite setSprite(@Nonnull TextureAtlasSprite spriteIn) { return storage.setSprite(spriteIn); }
}
