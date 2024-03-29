package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.block.SoundType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;

/**
 * Holds this mod's sounds
 * @author jbred
 *
 */
public final class ModSounds
{
    @Nonnull public static final NonNullList<SoundEvent> INIT = NonNullList.create();

    //sounds
    @Nonnull public static final SoundEvent PARTY_HORN = register("misc.party_horn");
    @Nonnull public static final SoundEvent ELECTRIC_EXPLOSION = register("misc.electric_explosion");
    @Nonnull public static final SoundEvent NETHER_REACTOR_CORE_AMBIENT = register("blocks.nether_reactor_core.ambient");
    @Nonnull public static final SoundEvent NETHER_REACTOR_CORE_ACTIVATE = register("blocks.nether_reactor_core.activate");
    @Nonnull public static final SoundEvent NETHER_REACTOR_CORE_DEACTIVATE = register("blocks.nether_reactor_core.deactivate");
    @Nonnull public static final SoundEvent RUPEE_PICKUP = register("items.rupee_pickup");
    @Nonnull public static final SoundEvent NETHERITE_EQUIP = register("items.netherite_equip");
    @Nonnull public static final SoundEvent BARREL_CLOSE = register("blocks.barrel.close");
    @Nonnull public static final SoundEvent BARREL_OPEN = register("blocks.barrel.open");
    @Nonnull public static final SoundEvent BUBBLE_COLUMN_UP_AMBIENT = register("blocks.bubble_column.up.ambient");
    @Nonnull public static final SoundEvent BUBBLE_COLUMN_DOWN_AMBIENT = register("blocks.bubble_column.down.ambient");
    @Nonnull public static final SoundEvent BUBBLE_COLUMN_UP_INSIDE = register("blocks.bubble_column.up.inside");
    @Nonnull public static final SoundEvent BUBBLE_COLUMN_DOWN_INSIDE = register("blocks.bubble_column.down.inside");
    @Nonnull public static final SoundEvent LODESTONE_LOCK = register("blocks.lodestone.lock");
    @Nonnull public static final SoundEvent BEACON_AMBIENT = register("blocks.beacon.ambient");
    @Nonnull public static final SoundEvent BEACON_ACTIVATE = register("blocks.beacon.activate");
    @Nonnull public static final SoundEvent BEACON_DEACTIVATE = register("blocks.beacon.deactivate");
    @Nonnull public static final SoundEvent BEACON_POWER_SELECT = register("blocks.beacon.power_select");
    @Nonnull public static final SoundEvent PIGMAN_ADMIRE = register("entity.pigman.admire");
    @Nonnull public static final SoundEvent PIGMAN_AMBIENT = register("entity.pigman.ambient");
    @Nonnull public static final SoundEvent PIGMAN_ANGRY = register("entity.pigman.angry");
    @Nonnull public static final SoundEvent PIGMAN_COVERT = register("entity.pigman.convert");
    @Nonnull public static final SoundEvent PIGMAN_DEATH = register("entity.pigman.death");
    @Nonnull public static final SoundEvent PIGMAN_HURT = register("entity.pigman.hurt");
    @Nonnull public static final SoundEvent PIGMAN_JEALOUS = register("entity.pigman.jealous");
    @Nonnull public static final SoundEvent PIGMAN_STEP = register("entity.pigman.step");
    @Nonnull public static final SoundEvent GLOW_SQUID_AMBIENT = register("entity.glow_squid.ambient");
    @Nonnull public static final SoundEvent GLOW_SQUID_DEATH = register("entity.glow_squid.death");
    @Nonnull public static final SoundEvent GLOW_SQUID_HURT = register("entity.glow_squid.hurt");

    //music
    @Nonnull public static final SoundEvent MUSIC_WATER = register("music.water_ambient");

    //sound types
    @Nonnull public static final SoundType BONE = register("bone", 1, 1);
    @Nonnull public static final SoundType CHAIN = register("chain", 1, 1);
    @Nonnull public static final SoundType CORAL = register("coral", 1, 1);
    @Nonnull public static final SoundType BASALT = register("basalt", 1, 1);
    @Nonnull public static final SoundType WET_GRASS = register("wet_grass", 1, 1);
    @Nonnull public static final SoundType NETHERWART = register("netherwart", 1, 1);
    @Nonnull public static final SoundType NETHERRACK = register("netherrack", 1, 1);
    @Nonnull public static final SoundType NETHERITE = register("netherite", 1, 1);
    @Nonnull public static final SoundType NETHER_ORE = register("nether_ore", 1, 1);
    @Nonnull public static final SoundType SOUL_SAND = register("soul_sand", 1, 1);
    @Nonnull public static final SoundType SOUL_SOIL = register("soul_soil", 1, 1);
    @Nonnull public static final SoundType SOUL_WOOD = register("soul_wood", 1, 1);
    @Nonnull public static final SoundType NETHER_BRICKS = register("nether_bricks", 1, 1);
    @Nonnull public static final SoundType ANCIENT_DEBRIS = register("ancient_debris", 1, 1);
    @Nonnull public static final SoundType LODESTONE = register("lodestone", 1, 1);

    //register sound
    @Nonnull
    private static SoundEvent register(@Nonnull String name) {
        final ResourceLocation id = new ResourceLocation(Constants.MODID, name);
        final SoundEvent soundEvent = new SoundEvent(id).setRegistryName(id);

        INIT.add(soundEvent);
        return soundEvent;
    }

    //register SoundType
    @Nonnull
    private static SoundType register(@Nonnull String name, float volume, float pitch) {
        return new SoundType(volume, pitch,
                register("blocks." + name + ".break"),
                register("blocks." + name + ".step"),
                register("blocks." + name + ".place"),
                register("blocks." + name + ".hit"),
                register("blocks." + name + ".fall"));
    }
}
