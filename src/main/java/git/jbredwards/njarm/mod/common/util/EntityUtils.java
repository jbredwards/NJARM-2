package git.jbredwards.njarm.mod.common.util;

import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 *
 * @author jbred
 *
 */
public final class EntityUtils
{
    @Nonnull
    public static <T extends Entity> T deserializeFromEntity(@Nonnull T to, @Nonnull Entity from) {
        final UUID id = to.getUniqueID();
        to.deserializeNBT(from.serializeNBT());
        to.setUniqueId(id);
        return to;
    }
}
