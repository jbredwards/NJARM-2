package git.jbredwards.njarm.mod.common.config.entity;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.entity.util.ISpawnableConfig;
import git.jbredwards.njarm.mod.common.util.ChatUtils;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 *
 * @author jbred
 *
 */
public final class HighlandCooConfig implements ISpawnableConfig
{
    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.entity.generic.biomeData")
    @Nonnull public final String biomeData;
    @Nonnull public String biomeData() { return biomeData; }
    //**only called internally on entity registration**
    @Nonnull static Biome[] spawnBiomes = new Biome[0];
    @Nonnull public Biome[] spawnBiomes() { return spawnBiomes = ISpawnableConfig.super.spawnBiomes(); }

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.entity.generic.maxSpawnCount")
    public final int maxSpawnCount;
    public int maxSpawnCount() { return maxSpawnCount; }

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.entity.generic.minSpawnCount")
    public final int minSpawnCount;
    public int minSpawnCount() { return minSpawnCount; }

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.entity.generic.spawnWeight")
    public final int spawnWeight;
    public int spawnWeight() { return spawnWeight; }

    @Config.LangKey("config.njarm.entity.highlandCoo.colorData")
    @Nonnull public final String[] colorData;
    @Nonnull public static final Map<Biome, List<ColorData>> colorDataMap = new HashMap<>();
    @Nonnull public static EnumDyeColor getRandColor(@Nonnull Random rand, @Nonnull Biome biome) {
        final @Nullable List<ColorData> colorData = colorDataMap.get(biome);
        return colorData != null ? WeightedRandom.getRandomItem(rand, colorData).color : EnumDyeColor.BROWN;
    }

    @Config.LangKey("config.njarm.entity.highlandCoo.dyeable")
    public final boolean dyeable;
    public static boolean isDyeable() { return ConfigHandler.entityCfg.highlandCooCfg.dyeable; }

    @Override
    public void onUpdate() {
        colorDataMap.clear();
        for(String data : colorData) {
            final NBTTagCompound nbt = NBTUtils.getTagFromString(data);
            if(nbt.hasKey("Color", Constants.NBT.TAG_INT)) {
                final int color = nbt.getInteger("Color");
                if(ChatUtils.getOrError(color > -1 && color < EnumDyeColor.values().length, "NJARM Config: Could not find color with index " + color)) {
                    final Biome[] biomes = NBTUtils.gatherBiomesFromNBT(new HashSet<>(), nbt).toArray(new Biome[0]);
                    final ColorData colorData = new ColorData(EnumDyeColor.values()[color],
                            Math.max(nbt.getInteger("Weight"), 1));

                    //if biome input is absent, assume all spawn biomes are valid
                    if(biomes.length == 0) for(Biome biome : spawnBiomes)
                        colorDataMap.computeIfAbsent(biome, biomeIn -> new ArrayList<>()).add(colorData);

                    //use biome input for color data
                    else for(Biome biome : biomes)
                        colorDataMap.computeIfAbsent(biome, biomeIn -> new ArrayList<>()).add(colorData);
                }
            }
        }
    }

    //needed for gson
    public HighlandCooConfig(int spawnWeight, int minSpawnCount, int maxSpawnCount, @Nonnull String biomeData, boolean dyeable, @Nonnull String[] colorData) {
        this.spawnWeight = spawnWeight;
        this.minSpawnCount = minSpawnCount;
        this.maxSpawnCount = maxSpawnCount;
        this.biomeData = biomeData;
        this.dyeable = dyeable;
        this.colorData = colorData;
    }

    public static class ColorData extends WeightedRandom.Item
    {
        @Nonnull
        protected final EnumDyeColor color;
        public ColorData(@Nonnull EnumDyeColor colorIn, int itemWeightIn) {
            super(itemWeightIn);
            color = colorIn;
        }
    }
}
