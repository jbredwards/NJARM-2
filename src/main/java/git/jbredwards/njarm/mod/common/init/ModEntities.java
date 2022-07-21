package git.jbredwards.njarm.mod.common.init;

import com.google.common.collect.ImmutableList;
import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.entity.util.IEntityConfig;
import git.jbredwards.njarm.mod.common.config.entity.util.ILivingEntityConfig;
import git.jbredwards.njarm.mod.common.entity.passive.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
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
    public static final ImmutableList<EntityEntry> INIT = ImmutableList.<EntityEntry>builder()
            .add(register("highland_coo", EntityHighlandCoo.class, ConfigHandler.entityCfg.highlandCooCfg,
                    EnumCreatureType.CREATURE).egg(8606770, 10592673).build())
            //done
            .build();

    @Nonnull
    static <T extends Entity> EntityEntryBuilder<T> register(@Nonnull String name, @Nonnull Class<T> entity, @Nonnull IEntityConfig cfg) {
        return EntityEntryBuilder.<T>create().id(name, globalEntityId++).name(Constants.MODID + '.' + name).entity(entity)
                .tracker(cfg.trackerRange(), cfg.trackerUpdateFrequency(), cfg.trackerSendVelocityUpdates());
    }

    @Nonnull
    static <T extends Entity> EntityEntryBuilder<T> register(@Nonnull String name, @Nonnull Class<T> entity, @Nonnull ILivingEntityConfig cfg, @Nonnull EnumCreatureType type) {
        final EntityEntryBuilder<T> builder = register(name, entity, cfg);
        if(cfg.minSpawnCount() != 0 && cfg.maxSpawnCount() >= cfg.minSpawnCount() && cfg.spawnWeight() != 0) {
            final Biome[] biomes = cfg.prepareBiomes();
            if(biomes.length > 0)
                builder.spawn(type, cfg.spawnWeight(), cfg.minSpawnCount(), cfg.maxSpawnCount(), biomes);
        }

        return builder;
    }
}
