package git.jbredwards.njarm.mod.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.tileentity.TileEntity;
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
     * Sometimes the vanilla method for playing a sound doesn't work, this doesn't have that problem.
     */
    public static void playSound(@Nonnull Entity entity, @Nonnull SoundEvent sound, float volume, float pitch) {
        playSound(entity.world, entity.posX, entity.posY, entity.posZ, sound, entity.getSoundCategory(), volume, pitch);
    }

    /**
     * Sometimes the vanilla method for playing a sound doesn't work, this doesn't have that problem.
     */
    public static void playSound(@Nonnull TileEntity te, @Nonnull SoundEvent sound, float volume, float pitch) {
        playSound(te.getWorld(), te.getPos(), sound, SoundCategory.BLOCKS, volume, pitch);
    }

    /**
     * Sometimes the vanilla method for playing a sound doesn't work, this doesn't have that problem.
     */
    public static void playSound(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull SoundEvent sound, @Nonnull SoundCategory category, float volume, float pitch) {
        playSound(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, sound, category, volume, pitch);
    }

    /**
     * Sometimes the vanilla method for playing a sound doesn't work, this doesn't have that problem.
     */
    public static void playSound(@Nonnull World world, double x, double y, double z, @Nonnull SoundEvent sound, @Nonnull SoundCategory category, float volume, float pitch) {
        if(world.getMinecraftServer() == null) world.playSound(x, y, z, sound, category, volume, pitch, false);
        else world.getMinecraftServer().getPlayerList() //ensure the sound packets are sent
                .sendToAllNearExcept(null, x, y, z, volume > 1 ? 16 * volume : 16, world.provider.getDimension(),
                        new SPacketSoundEffect(sound, category, x, y, z, volume, pitch));
    }
}
