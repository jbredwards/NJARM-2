package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.entity.util.ISpawnableConfig;
import git.jbredwards.njarm.mod.common.entity.item.*;
import git.jbredwards.njarm.mod.common.entity.monster.*;
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

    @Nonnull
    public static final EntityEntry CHOCOLATE_COW = register("chocolate_cow", builder(EntityChocolateCow.class, 80, 3, true,
            ConfigHandler.entityCfg.chocolateCowCfg, EnumCreatureType.CREATURE).egg(5580301, 10592673));

    @Nonnull
    public static final EntityEntry MUD_PIG = register("mud_pig", builder(EntityMudPig.class, 80, 3, true)
            .egg(15771042, 6898223));

    @Nonnull
    public static final EntityEntry CHARGED_SUNSTONE = register("charged_sunstone", builder(EntityChargedSunstone.class, 160, 3, true));

    @Nonnull
    public static final EntityEntry BLESTEM_ARROW = register("blestem_arrow", builder(EntityBlestemArrow.class, 160, 3, true));

    @Nonnull
    public static final EntityEntry BLESTEM = register("blestem", builder(EntityBlestem.class, 80, 3, true,
            ConfigHandler.entityCfg.blestemCfg, EnumCreatureType.MONSTER).egg(9725844, 15134893));

    @Nonnull
    public static final EntityEntry FIRE_SKELETON = register("fire_skeleton", builder(EntityFireSkeleton.class, 80, 3, true,
            ConfigHandler.entityCfg.fireSkeletonCfg, EnumCreatureType.MONSTER).egg(16152625, 14832128));

    @Nonnull
    public static final EntityEntry FIRE_CREEPER = register("fire_creeper", builder(EntityFireCreeper.class, 80, 3, true,
            ConfigHandler.entityCfg.fireCreeperCfg, EnumCreatureType.MONSTER).egg(6896957, 14646307));

    @Nonnull
    public static final EntityEntry SOUL_SKELETON = register("soul_skeleton", builder(EntitySoulSkeleton.class, 80, 3, true,
            ConfigHandler.entityCfg.soulSkeletonCfg, EnumCreatureType.MONSTER).egg(3679771, 4243391));

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
