package git.jbredwards.njarm.mod.common.config.entity.util;

import git.jbredwards.njarm.mod.common.util.NBTUtils;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jbred
 *
 */
public interface ILivingEntityConfig extends IEntityConfig
{
    int spawnWeight();
    int minSpawnCount();
    int maxSpawnCount();

    @Nonnull
    String[] biomeData();

    @Nonnull
    default Biome[] prepareBiomes() {
        final Set<Biome> biomeSet = new HashSet<>();
        for(String nbtString : biomeData()) NBTUtils.gatherBiomesFromNBT(
            biomeSet, NBTUtils.getTagFromString(nbtString)
        );

        return biomeSet.toArray(new Biome[0]);
    }
}
