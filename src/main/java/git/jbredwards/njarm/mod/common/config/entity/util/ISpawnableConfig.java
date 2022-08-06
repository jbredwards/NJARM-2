package git.jbredwards.njarm.mod.common.config.entity.util;

import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jbred
 *
 */
public interface ISpawnableConfig extends IConfig
{
    @Nonnull
    String[] spawnData();

    @Nonnull
    Set<Biome> allSpawnBiomes();

    @Nonnull
    default Spawn[] getSpawnData() {
        final Set<Spawn> spawnData = new HashSet<>();
        for(String data : spawnData())
            spawnData.add(new Spawn(NBTUtils.getTagFromString(data), allSpawnBiomes()));

        return spawnData.toArray(new Spawn[0]);
    }

    class Spawn
    {
        @Nonnull
        public final Biome[] biomes;
        public final int min;
        public final int max;
        public final int weight;

        public Spawn(@Nonnull NBTTagCompound nbt, @Nonnull Set<Biome> allSpawnBiomes) {
            final Set<Biome> biomeSet = NBTUtils.gatherBiomesFromNBT(new HashSet<>(), nbt);
            allSpawnBiomes.addAll(biomeSet);

            biomes = biomeSet.toArray(new Biome[0]);
            min = Math.max(nbt.getInteger("Min"), 1);
            max = Math.max(nbt.getInteger("Max"), 1);
            weight = Math.max(nbt.getInteger("Weight"), 1);
        }
    }
}
