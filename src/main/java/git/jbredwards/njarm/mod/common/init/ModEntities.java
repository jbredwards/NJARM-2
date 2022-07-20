package git.jbredwards.njarm.mod.common.init;

import com.google.common.collect.ImmutableList;
import git.jbredwards.njarm.mod.common.entity.passive.*;
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
    @Nonnull
    public static final ImmutableList<EntityEntry> INIT = ImmutableList.<EntityEntry>builder()
            //highland coo
            .add(EntityEntryBuilder.create().id("highland_coo", 0)
                    .entity(EntityHighlandCoo.class)
                    .build())
            //done
            .build();
}
