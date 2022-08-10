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
    @Config.LangKey("config.njarm.entity.generic.spawnData")
    @Nonnull public final String[] spawnData;
    @Nonnull public String[] spawnData() { return ConfigHandler.entityCfg.highlandCooCfg.spawnData; }
    @Nonnull public static final List<Biome> spawnBiomes = new ArrayList<>();
    @Nonnull public List<Biome> allSpawnBiomes() { return spawnBiomes; }

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
                    final Biome[] biomes = NBTUtils.gatherBiomesFromNBT(nbt).toArray(new Biome[0]);
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
    public HighlandCooConfig(@Nonnull String[] spawnData, boolean dyeable, @Nonnull String[] colorData) {
        this.spawnData = spawnData;
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
