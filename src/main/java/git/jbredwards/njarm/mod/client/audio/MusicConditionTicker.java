package git.jbredwards.njarm.mod.client.audio;

import git.jbredwards.njarm.mod.common.init.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class MusicConditionTicker extends MusicTicker
{
    @Nonnull
    public static final MusicType UNDERWATER = Objects.requireNonNull(EnumHelper.addEnum(MusicType.class, "UNDERWATER",
            new Class[] {SoundEvent.class, int.class, int.class}, ModSounds.MUSIC_WATER, 12000, 24000));

    @Nonnull protected final Minecraft mc = Minecraft.getMinecraft();
    @Nonnull protected final Random rand = new Random();
    @Nullable protected ISound currentMusic;
    protected int timeUntilNextMusic = 100;

    public MusicConditionTicker() { super(Minecraft.getMinecraft()); }

    //copied from MusicTicker, except now gets the music type from IMusicCondition instead.
    @Override
    public void update() {
        final MusicType type = IMusicCondition.getMusicToPlay(mc);
        //fixes vanilla issue that lets music play while it's disabled in-game.
        final float volume = mc.gameSettings.getSoundLevel(SoundCategory.MUSIC);

        if(currentMusic != null) {
            if(volume <= 0 || !type.getMusicLocation().getSoundName().equals(currentMusic.getSoundLocation())) {
                mc.getSoundHandler().stopSound(currentMusic);
                timeUntilNextMusic = MathHelper.getInt(rand, 0, type.getMinDelay() / 2);
            }

            if(!mc.getSoundHandler().isSoundPlaying(currentMusic)) {
                currentMusic = null;
                timeUntilNextMusic = Math.min(MathHelper.getInt(rand, type.getMinDelay(), type.getMaxDelay()), timeUntilNextMusic);
            }
        }

        timeUntilNextMusic = Math.min(timeUntilNextMusic, type.getMaxDelay());
        if(volume > 0 && currentMusic == null && timeUntilNextMusic-- <= 0) playMusic(type);
    }

    //copied from MusicTicker.
    @Override
    public void playMusic(@Nonnull MusicType requestedMusicType) {
        currentMusic = PositionedSoundRecord.getMusicRecord(requestedMusicType.getMusicLocation());
        mc.getSoundHandler().playSound(currentMusic);
        timeUntilNextMusic = Integer.MAX_VALUE;
    }
}
