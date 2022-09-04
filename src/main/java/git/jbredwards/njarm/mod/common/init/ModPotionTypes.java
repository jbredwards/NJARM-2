package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

/**
 * Stores this mod's potion effect wrappers
 * @author jbred
 *
 */
public final class ModPotionTypes
{
    @Nonnull public static final NonNullList<PotionType> INIT = NonNullList.create();

    @Nonnull public static final PotionType BLUE_FIRE_RESISTANCE = register("blue_fire_resistance", Type.NORMAL, new PotionEffect(ModPotions.BLUE_FIRE_RESISTANCE, 3600));
    @Nonnull public static final PotionType LONG_BLUE_FIRE_RESISTANCE = register("blue_fire_resistance", Type.LONG, new PotionEffect(ModPotions.BLUE_FIRE_RESISTANCE, 9600));
    @Nonnull public static final PotionType HEALTH_BOOST = register("health_boost", Type.NORMAL, new PotionEffect(MobEffects.HEALTH_BOOST, 3600));
    @Nonnull public static final PotionType LONG_HEALTH_BOOST = register("health_boost", Type.LONG, new PotionEffect(MobEffects.HEALTH_BOOST, 9600));
    @Nonnull public static final PotionType STRONG_HEALTH_BOOST = register("health_boost", Type.STRONG, new PotionEffect(MobEffects.HEALTH_BOOST, 1800, 1));

    @Nonnull
    static PotionType register(@Nonnull String name, @Nonnull Type type, @Nonnull PotionEffect... effects) {
        final PotionType potionType = new PotionType(Constants.MODID + '.' + name, effects)
                .setRegistryName(Constants.MODID, type.getName() + name);

        INIT.add(potionType);
        return potionType;
    }

    enum Type implements IStringSerializable
    {
        NORMAL(""),
        LONG("long_"),
        STRONG("string_");

        @Nonnull String name;
        Type(@Nonnull String nameIn) { name = nameIn; }

        @Nonnull
        @Override
        public String getName() { return name; }
    }
}
