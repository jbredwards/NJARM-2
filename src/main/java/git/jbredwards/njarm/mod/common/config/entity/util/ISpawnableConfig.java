package git.jbredwards.njarm.mod.common.config.entity.util;

import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    List<Biome> allSpawnBiomes();

    @Nonnull
    default Spawn[] getSpawnData() {
        final List<Spawn> spawnData = new ArrayList<>();
        final Set<Biome> allBiomes = new HashSet<>();

        for(String data : spawnData()) {
            final NBTTagCompound nbt = NBTUtils.getTagFromString(data);
            final Set<Biome> biomeSet = NBTUtils.gatherBiomesFromNBT(nbt);

            spawnData.add(new Spawn(nbt, biomeSet));
            allBiomes.addAll(biomeSet);
        }

        allSpawnBiomes().addAll(allBiomes);
        return spawnData.toArray(new Spawn[0]);
    }

    class Spawn
    {
        @Nonnull
        public final Biome[] biomes;
        public final int min;
        public final int max;
        public final int weight;

        public Spawn(@Nonnull NBTTagCompound nbt, @Nonnull Set<Biome> biomeSet) {
            biomes = biomeSet.toArray(new Biome[0]);
            min = Math.max(nbt.getInteger("Min"), 1);
            max = Math.max(nbt.getInteger("Max"), 1);
            weight = Math.max(nbt.getInteger("Weight"), 1);
        }
    }
}
