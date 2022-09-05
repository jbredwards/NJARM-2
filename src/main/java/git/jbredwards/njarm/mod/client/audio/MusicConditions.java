package git.jbredwards.njarm.mod.client.audio;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public enum MusicConditions implements IMusicCondition
{
    MENU(0, mc -> MusicTicker.MusicType.MENU, mc -> true),
    CREDITS(1000, mc -> MusicTicker.MusicType.CREDITS, mc -> mc.currentScreen instanceof GuiWinGame),
    CUSTOM(500, mc -> mc.world.provider.getMusicType(), mc -> mc.world.provider.getMusicType() != null),
    CREATIVE(100, mc -> MusicTicker.MusicType.CREATIVE, mc -> mc.player.isCreative() || mc.player.isSpectator()),
    SURVIVAL(100, mc -> MusicTicker.MusicType.GAME, mc -> !mc.player.isCreative() && !mc.player.isSpectator()),
    NETHER(300, mc -> MusicTicker.MusicType.NETHER, mc -> mc.world.provider instanceof WorldProviderHell),
    END(300, mc -> MusicTicker.MusicType.END, mc -> mc.world.provider instanceof WorldProviderEnd),
    END_DRAGON(400, mc -> MusicTicker.MusicType.END_BOSS, mc -> mc.world.provider instanceof WorldProviderEnd && mc.ingameGUI.getBossOverlay().shouldPlayEndBossMusic()),
    WATER(200, mc -> MusicConditionTicker.UNDERWATER, mc -> mc.player.isInsideOfMaterial(Material.WATER));

    @Nonnull final Function<Minecraft, MusicTicker.MusicType> type;
    @Nonnull final Predicate<Minecraft> condition;
    final int priority;

    MusicConditions(int priority, @Nonnull Function<Minecraft, MusicTicker.MusicType> type, @Nonnull Predicate<Minecraft> condition) {
        this.priority = priority;
        this.type = type;
        this.condition = condition;
    }

    public static void registerConditions() {
        CONDITIONS.addAll(Arrays.asList(values()));
        CONDITIONS.sort((e1, e2) -> e2.getPriority() - e1.getPriority());
    }

    @Override
    public int getPriority() { return priority; }

    @Override
    public boolean canPlay(@Nonnull Minecraft mc) {
        try { return condition.test(mc); }
        //usually catches while the game is loading
        catch(NullPointerException e) { return false; }
    }

    @Nonnull
    @Override
    public MusicTicker.MusicType getMusicType(@Nonnull Minecraft mc) { return type.apply(mc); }
}
