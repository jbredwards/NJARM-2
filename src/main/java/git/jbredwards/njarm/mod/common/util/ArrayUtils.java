package git.jbredwards.njarm.mod.common.util;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ArrayUtils
{
    @Nonnull
    public static <T> T getSafe(T[] values, int index) { return values[index < values.length ? index : 0]; }
}
