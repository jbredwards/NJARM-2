package git.jbredwards.njarm.mod.common.init;

import com.google.common.collect.ImmutableList;
import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.entity.util.ISpawnableConfig;
import git.jbredwards.njarm.mod.common.entity.passive.*;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import javax.annotation.Nonnull;
import java.util.HashSet;

/**
 *
 * @author jbred
 *
 */
public final class ModEntities
{
    static int globalEntityId = 0;

    @Nonnull
    public static final ImmutableList<EntityEntry> INIT = ImmutableList.<EntityEntry>builder()
            .add(register("highland_coo", EntityHighlandCoo.class, 80, 3, true, ConfigHandler.entityCfg.highlandCooCfg,
                    EnumCreatureType.CREATURE).egg(8606770, 10592673).build())
            .build();

    @Nonnull
    static <T extends Entity> EntityEntryBuilder<T> register(@Nonnull String name, @Nonnull Class<T> entity, int trackerRange, int trackerUpdateFrequency, boolean trackerSendVelocityUpdates) {
        return EntityEntryBuilder.<T>create().id(name, globalEntityId++).name(Constants.MODID + '.' + name).entity(entity).tracker(trackerRange, trackerUpdateFrequency, trackerSendVelocityUpdates);
    }

    @Nonnull
    static <T extends Entity> EntityEntryBuilder<T> register(@Nonnull String name, @Nonnull Class<T> entity, int trackerRange, int trackerUpdateFrequency, boolean trackerSendVelocityUpdates, @Nonnull ISpawnableConfig cfg, @Nonnull EnumCreatureType type) {
        final EntityEntryBuilder<T> builder = register(name, entity, trackerRange, trackerUpdateFrequency, trackerSendVelocityUpdates);
        if(cfg.minSpawnCount() != 0 && cfg.maxSpawnCount() >= cfg.minSpawnCount() && cfg.spawnWeight() != 0) {
            final Biome[] biomes = NBTUtils.gatherBiomesFromNBT(new HashSet<>(),
                    NBTUtils.getTagFromString(cfg.biomeData()))
                    .toArray(new Biome[0]);

            if(biomes.length > 0)
                builder.spawn(type, cfg.spawnWeight(), cfg.minSpawnCount(), cfg.maxSpawnCount(), biomes);
        }

        return builder;
    }
}
