package git.jbredwards.njarm.mod.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class SoundUtils
{
    /**
     * plays a sound as an entity for all players in the area
     */
    public static void playServerSound(@Nonnull Entity entity, @Nonnull SoundEvent sound, float volume, float pitch) {
        playServerSound(entity.world, entity.posX, entity.posY, entity.posZ, sound, entity.getSoundCategory(), volume, pitch);
    }

    /**
     * plays a sound for all players in the area
     */
    public static void playServerSound(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull SoundEvent sound, @Nonnull SoundCategory category, float volume, float pitch) {
        playServerSound(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, sound, category, volume, pitch);
    }

    /**
     * plays a sound for all players in the area
     */
    public static void playServerSound(@Nonnull World world, double x, double y, double z, @Nonnull SoundEvent sound, @Nonnull SoundCategory category, float volume, float pitch) {
        if(world.getMinecraftServer() == null) world.playSound(x, y, z, sound, category, volume, pitch, false);
        else world.getMinecraftServer().getPlayerList() //don't call world.playSound in this case, sometimes it doesn't work...
                .sendToAllNearExcept(null, x, y, z, volume > 1 ? 16 * volume : 16, world.provider.getDimension(),
                        new SPacketSoundEffect(sound, category, x, y, z, volume, pitch));
    }
}
