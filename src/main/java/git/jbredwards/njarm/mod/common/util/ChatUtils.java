package git.jbredwards.njarm.mod.common.util;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class ChatUtils
{
    public static boolean getOrError(boolean condition, @Nonnull String message) {
        if(condition) return true;
        LogManager.getLogger(Constants.NAME).warn(message);

        return false;
    }

    public static boolean getOrError(@Nonnull EntityPlayer player, boolean condition, @Nonnull String message) {
        if(condition) return true;
        else if(player.world.isRemote) player.sendMessage(new TextComponentTranslation(message));

        return false;
    }
}
