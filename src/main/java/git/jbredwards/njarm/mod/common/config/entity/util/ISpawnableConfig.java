package git.jbredwards.njarm.mod.common.config.entity.util;

import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
import java.util.HashSet;

/**
 *
 * @author jbred
 *
 */
public interface ISpawnableConfig extends IConfig
{
    @Nonnull
    String biomeData();

    int maxSpawnCount();
    int minSpawnCount();
    int spawnWeight();

    //**only called internally on entity registration**
    @Nonnull
    default Biome[] spawnBiomes() {
        return NBTUtils.gatherBiomesFromNBT(new HashSet<>(),
                NBTUtils.getTagFromString(biomeData()))
                .toArray(new Biome[0]);
    }
}
