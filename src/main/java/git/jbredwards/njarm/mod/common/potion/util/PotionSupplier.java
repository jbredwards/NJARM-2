package git.jbredwards.njarm.mod.common.potion.util;

import git.jbredwards.njarm.mod.common.potion.PotionBase;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@FunctionalInterface
public interface PotionSupplier<T extends PotionBase>
{
    @Nonnull
    T get(@Nonnull ResourceLocation texture, boolean isBadEffect, int liquidColor);
}
