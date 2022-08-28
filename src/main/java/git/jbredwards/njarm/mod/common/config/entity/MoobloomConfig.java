package git.jbredwards.njarm.mod.common.config.entity;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.entity.util.ISpawnableConfig;
import git.jbredwards.njarm.mod.common.util.ChatUtils;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import net.darkhax.bookshelf.lib.WeightedSelector;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 *
 * @author jbred
 *
 */
public final class MoobloomConfig implements ISpawnableConfig
{
    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.entity.generic.spawnData")
    @Nonnull public final String[] spawnData;
    @Nonnull public String[] spawnData() { return ConfigHandler.entityCfg.moobloomCfg.spawnData; }
    @Nonnull public static final List<Biome> spawnBiomes = new ArrayList<>();
    @Nonnull public List<Biome> allSpawnBiomes() { return spawnBiomes; }

    @Config.LangKey("config.njarm.entity.moobloom.bonemeal")
    @Nonnull public final String[] bonemeal;
    @Nonnull public static final List<Pair<Item, Integer>> bonemealItems = new ArrayList<>();

    @Config.LangKey("config.njarm.entity.moobloom.flowerData")
    @Nonnull public final String[] flowerData;
    @Nonnull public static final Map<Biome, WeightedSelector<Optional<IBlockState>>> flowerDataMap = new HashMap<>();
    @Nonnull public static Optional<IBlockState> getRandFlower(@Nonnull Biome biome) {
        final @Nullable WeightedSelector<Optional<IBlockState>> flowerData = flowerDataMap.get(biome);
        return flowerData != null ? flowerData.getRandomEntry().getEntry() : Optional.empty();
    }

    @Config.LangKey("config.njarm.entity.moobloom.flowers")
    @Nonnull public final String[] flowers;
    @Nonnull public static final List<IBlockState> flowerStates = new ArrayList<>();

    @Config.LangKey("config.njarm.entity.moobloom.maxPlantTime")
    public final int maxPlantTime;
    public static int maxPlantTime() { return ConfigHandler.entityCfg.moobloomCfg.maxPlantTime; }

    @Config.LangKey("config.njarm.entity.moobloom.minPlantTime")
    public final int minPlantTime;
    public static int minPlantTime() { return ConfigHandler.entityCfg.moobloomCfg.minPlantTime; }

    @Config.LangKey("config.njarm.entity.moobloom.shearable")
    public final boolean shearable;
    public static boolean shearable() { return ConfigHandler.entityCfg.moobloomCfg.shearable; }

    @Config.LangKey("config.njarm.entity.moobloom.weather")
    public final boolean weather;
    public static boolean weather() { return ConfigHandler.entityCfg.moobloomCfg.weather; }

    @Override
    public void onUpdate() {
        bonemealItems.clear();
        final Set<Pair<Item, Integer>> bonemealSet = new HashSet<>();
        for(String data : bonemeal) {
            final NBTTagCompound nbt = NBTUtils.getTagFromString(data);
            if(ChatUtils.getOrError(nbt.hasKey("Item", Constants.NBT.TAG_STRING), "Bonemeal item in config does not have \"Item\" arg!")) {
                final @Nullable Item item = Item.getByNameOrId(nbt.getString("Item"));
                if(ChatUtils.getOrError(item != null, "Could not resolve item in config: " + data))
                    bonemealSet.add(Pair.of(item, nbt.getInteger("Meta")));
            }
        }

        flowerDataMap.clear();
        for(String data : flowerData) {
            final NBTTagCompound nbt = NBTUtils.getTagFromString(data);
            if(nbt.hasKey("Flower", Constants.NBT.TAG_COMPOUND)) {
                final Biome[] biomes = NBTUtils.gatherBiomesFromNBT(nbt).toArray(new Biome[0]);
                final int weight = Math.max(nbt.getInteger("Weight"), 1);

                final IBlockState flower = NBTUtil.readBlockState(nbt.getCompoundTag("Flower"));
                final Optional<IBlockState> flowerData = flower.getBlock() == Blocks.AIR
                        ? Optional.empty() : Optional.of(flower);

                //if biome input is absent, assume all spawn biomes are valid
                if(biomes.length == 0) for(Biome biome : spawnBiomes)
                    flowerDataMap.computeIfAbsent(biome, biomeIn -> new WeightedSelector<>()).addEntry(flowerData, weight);

                //use biome input for color data
                else for(Biome biome : biomes)
                    flowerDataMap.computeIfAbsent(biome, biomeIn -> new WeightedSelector<>()).addEntry(flowerData, weight);
            }
        }

        flowerStates.clear();
        final Set<IBlockState> flowerSet = new HashSet<>();
        for(String data : flowers) {
            final IBlockState flower = NBTUtil.readBlockState(NBTUtils.getTagFromString(data));
            if(flower.getBlock() != Blocks.AIR) flowerSet.add(flower);
        }

        bonemealItems.addAll(bonemealSet);
        flowerStates.addAll(flowerSet);
    }

    //needed for gson
    public MoobloomConfig(int maxPlantTime, int minPlantTime, boolean shearable, boolean weather, @Nonnull String[] spawnData, @Nonnull String[] bonemeal, @Nonnull String[] flowerData, @Nonnull String[] flowers) {
        this.bonemeal = bonemeal;
        this.spawnData = spawnData;
        this.flowerData = flowerData;
        this.flowers = flowers;
        this.maxPlantTime = maxPlantTime;
        this.minPlantTime = minPlantTime;
        this.shearable = shearable;
        this.weather = weather;
    }
}
