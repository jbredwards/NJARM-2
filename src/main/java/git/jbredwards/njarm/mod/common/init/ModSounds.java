package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

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
    @Nonnull public static final SoundEvent LODESTONE_PLACE = register("blocks.lodestone.place");

    //music
    @Nonnull public static final SoundEvent MUSIC_NETHER = register("music.raine_nether");
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
    @Nonnull public static final SoundType ANCIENT_DEBRIS = register("ancient_debris", 1, 1, NETHER_ORE.getStepSound());
    @Nonnull public static final SoundType LODESTONE = new SoundType(1, 1, SoundEvents.BLOCK_STONE_BREAK, SoundEvents.BLOCK_STONE_STEP, LODESTONE_PLACE, SoundEvents.BLOCK_STONE_HIT, SoundEvents.BLOCK_STONE_FALL);
    @Nonnull public static final SoundType CRATE = new SoundType(1, 1, SoundEvents.BLOCK_WOOD_BREAK, WET_GRASS.getStepSound(), SoundEvents.BLOCK_WOOD_PLACE, SoundEvents.BLOCK_WOOD_HIT, SoundEvents.BLOCK_WOOD_FALL);

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
    public static SoundType register(@Nonnull String name, float volume, float pitch) {
        return register(name, volume, pitch, register("blocks." + name + ".step"));
    }

    //register SoundType
    @Nonnull
    public static SoundType register(@Nonnull String name, float volume, float pitch, @Nonnull SoundEvent stepSound) {
        final SoundEvent breakSound = register("blocks." + name + ".break");
        return new SoundType(volume, pitch, breakSound, stepSound, breakSound, stepSound, stepSound);
    }

    //transfers sounds from one SoundEvent to another
    @SideOnly(Side.CLIENT)
    public static void combine(@Nonnull SoundEvent origin, @Nonnull SoundEvent contents) {
        final SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
        final @Nullable SoundEventAccessor originAccessor =  handler.getAccessor(Objects.requireNonNull(origin.getRegistryName()));
        final @Nullable SoundEventAccessor contentsAccessor = handler.getAccessor(Objects.requireNonNull(contents.getRegistryName()));

        //failed to get sound from origin
        if(originAccessor == null) {
            Constants.LOGGER.warn("Failed to get sound from " + origin.getSoundName());
            return;
        }

        //failed to get sound from contents
        if(contentsAccessor == null) {
            Constants.LOGGER.warn("Failed to get sound from " + contents.getSoundName());
            return;
        }

        //adds the sound
        originAccessor.addSound(contentsAccessor);
        Constants.LOGGER.info(String.format("Successfully added sounds from %s to %s", contents.getSoundName(), origin.getSoundName()));
    }
}
