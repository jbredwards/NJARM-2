package git.jbredwards.njarm.mod.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public interface IMusicCondition
{
    @Nonnull
    List<IMusicCondition> CONDITIONS = new ArrayList<>();

    @Nonnull
    static MusicTicker.MusicType getMusicToPlay(@Nonnull Minecraft mc) {
        for(IMusicCondition condition : CONDITIONS)
            if(condition.canPlay(mc)) return condition.getMusicType(mc);

        return MusicTicker.MusicType.MENU;
    }

    int getPriority();
    boolean canPlay(@Nonnull Minecraft mc);

    @Nonnull
    MusicTicker.MusicType getMusicType(@Nonnull Minecraft mc);
}
