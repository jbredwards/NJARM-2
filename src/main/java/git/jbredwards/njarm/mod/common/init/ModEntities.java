package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.entity.util.ISpawnableConfig;
import git.jbredwards.njarm.mod.common.entity.passive.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class ModEntities
{
    static int globalEntityId = 0;

    @Nonnull
    public static final NonNullList<EntityEntry> INIT = NonNullList.create();

    //========
    //entities
    //========

    @Nonnull
    public static final EntityEntry HIGHLAND_COO = register("highland_coo", builder(EntityHighlandCoo.class, 80, 3, true,
            ConfigHandler.entityCfg.highlandCooCfg, EnumCreatureType.CREATURE).egg(8606770, 10592673));

    @Nonnull
    public static final EntityEntry MOOBLOOM = register("moobloom", builder(EntityMoobloom.class, 80, 3, true,
            ConfigHandler.entityCfg.moobloomCfg, EnumCreatureType.CREATURE).egg(7951674, 6266677));

    //=======
    //helpers
    //=======

    @Nonnull
    static EntityEntryBuilder<?> builder(@Nonnull Class<? extends Entity> entity, int trackerRange, int trackerUpdateFrequency, boolean trackerSendVelocityUpdates) {
        return EntityEntryBuilder.create().entity(entity).tracker(trackerRange, trackerUpdateFrequency, trackerSendVelocityUpdates);
    }

    @Nonnull
    static EntityEntryBuilder<?> builder(@Nonnull Class<? extends Entity> entity, int trackerRange, int trackerUpdateFrequency, boolean trackerSendVelocityUpdates, @Nonnull ISpawnableConfig cfg, @Nonnull EnumCreatureType type) {
        final EntityEntryBuilder<?> builder = builder(entity, trackerRange, trackerUpdateFrequency, trackerSendVelocityUpdates);
        final ISpawnableConfig.Spawn[] spawnEntries = cfg.getSpawnData();
        for(ISpawnableConfig.Spawn entry : spawnEntries) {
            builder.spawn(type, entry.weight, entry.min, entry.max, entry.biomes);
        }

        return builder;
    }

    @Nonnull
    static EntityEntry register(@Nonnull String name, @Nonnull EntityEntryBuilder<?> builder) {
        final EntityEntry entry = builder.id(name, globalEntityId++).name(Constants.MODID + '.' + name).build();
        INIT.add(entry);
        return entry;
    }
}
