package git.jbredwards.njarm.mod.client.audio;

import git.jbredwards.njarm.mod.common.init.ModSounds;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class UnderwaterMusicTicker extends MusicTicker
{
    @Nonnull
    public static final MusicType UNDERWATER = Objects.requireNonNull(EnumHelper.addEnum(MusicType.class, "UNDERWATER",
            new Class[] {SoundEvent.class, int.class, int.class}, ModSounds.MUSIC_WATER, 12000, 24000));

    @Nonnull protected final Minecraft mc = Minecraft.getMinecraft();
    @Nonnull protected final Random rand = new Random();
    @Nullable protected ISound currentMusic;
    protected int timeUntilNextMusic = 100;

    public UnderwaterMusicTicker() { super(Minecraft.getMinecraft()); }

    //copied from MusicTicker, except now gets the music type from MusicCondition instead.
    @Override
    public void update() {
        final MusicType type = MusicCondition.getNext(mc);
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

    public enum MusicCondition
    {
        MENU(0, mc -> MusicType.MENU, mc -> true),
        CREDITS(1000, mc -> MusicType.CREDITS, mc -> mc.currentScreen instanceof GuiWinGame),
        CUSTOM(500, mc -> mc.world.provider.getMusicType(), mc -> mc.world.provider.getMusicType() != null),
        CREATIVE(100, mc -> MusicType.CREATIVE, mc -> mc.player.isCreative() || mc.player.isSpectator()),
        SURVIVAL(100, mc -> MusicType.GAME, mc -> !mc.player.isCreative() && !mc.player.isSpectator()),
        NETHER(300, mc -> MusicType.NETHER, mc -> mc.world.provider instanceof WorldProviderHell),
        END(300, mc -> MusicType.END, mc -> mc.world.provider instanceof WorldProviderEnd),
        END_DRAGON(400, mc -> MusicType.END_BOSS, mc -> mc.world.provider instanceof WorldProviderEnd && mc.ingameGUI.getBossOverlay().shouldPlayEndBossMusic()),
        WATER(200, mc -> UNDERWATER, mc -> mc.player.isInsideOfMaterial(Material.WATER));
        @Nullable static List<MusicCondition> sortedValues = null;
        @Nonnull final Function<Minecraft, MusicType> type;
        @Nonnull final Predicate<Minecraft> condition;
        final int priority;

        MusicCondition(int priority, @Nonnull Function<Minecraft, MusicType> type, @Nonnull Predicate<Minecraft> condition) {
            this.priority = priority;
            this.type = type;
            this.condition = condition;
        }

        @Nonnull
        public static MusicType getNext(@Nonnull Minecraft mc) {
            //generates the sorted list
            if(sortedValues == null) {
                sortedValues = Arrays.asList(values());
                sortedValues.sort((e1, e2) -> e2.priority - e1.priority);
            }
            //goes though each music condition to find which one should play.
            for(MusicCondition music : sortedValues) {
                try { if(music.condition.test(mc)) return music.type.apply(mc); }
                catch(Exception ignored) {}
            }

            return MENU.type.apply(mc);
        }
    }
}
