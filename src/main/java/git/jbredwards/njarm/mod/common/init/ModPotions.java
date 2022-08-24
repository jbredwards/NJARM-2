package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.potion.PotionBase;
import git.jbredwards.njarm.mod.common.potion.util.PotionSupplier;
import net.minecraft.potion.Potion;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * Holds this mod's potion effects
 * @author jbred
 *
 */
public final class ModPotions
{
    @Nonnull public static final NonNullList<Potion> INIT = NonNullList.create();

    @Nonnull public static final PotionBase BLUE_FIRE_RESISTANCE = register("blue_fire_resistance", false, 4243391, PotionBase::new, Potion::setBeneficial);

    @Nonnull
    static <T extends PotionBase> T register(@Nonnull String name, boolean isBadEffect, int liquidColor, @Nonnull PotionSupplier<T> supplier) {
        return register(name, isBadEffect, liquidColor, supplier, potion -> {});
    }

    @Nonnull
    static <T extends PotionBase> T register(@Nonnull String name, boolean isBadEffect, int liquidColor, @Nonnull PotionSupplier<T> supplier, @Nonnull Consumer<T> consumer) {
        final T potion = supplier.get(new ResourceLocation(Constants.MODID, String.format("textures/potions/%s.png", name)), isBadEffect, liquidColor);
        INIT.add(potion.setRegistryName(Constants.MODID, name).setPotionName(String.format("%s.effect.%s", Constants.MODID, name)));
        consumer.accept(potion);
        return potion;
    }
}
