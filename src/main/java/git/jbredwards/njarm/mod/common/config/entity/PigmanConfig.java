package git.jbredwards.njarm.mod.common.config.entity;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.entity.util.ISpawnableConfig;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import net.darkhax.bookshelf.util.StackUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
public final class PigmanConfig implements ISpawnableConfig
{
    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.entity.generic.spawnData")
    @Nonnull public final String[] spawnData;
    @Nonnull public String[] spawnData() { return ConfigHandler.entityCfg.soulSkeletonCfg.spawnData; }
    @Nonnull public static final List<Biome> spawnBiomes = new ArrayList<>();
    @Nonnull public List<Biome> allSpawnBiomes() { return spawnBiomes; }

    @Config.LangKey("config.njarm.entity.pigman.canTradeWhileMad")
    public final boolean canTradeWhileMad;
    public static boolean canTradeWhileMad() { return ConfigHandler.entityCfg.pigmanCfg.canTradeWhileMad; }

    @Config.LangKey("config.njarm.entity.pigman.armors")
    @Nonnull public final String[] armors;
    @Nonnull static final List<ItemStack> ARMORS = new ArrayList<>();
    public static boolean isArmorItem(@Nonnull ItemStack stackIn) {
        for(ItemStack stack : ARMORS)
            if(StackUtils.areStacksSimilarWithPartialNBT(stackIn, stack))
                return true;

        return false;
    }

    @Config.LangKey("config.njarm.entity.pigman.currencies")
    @Nonnull public final String[] currencies;
    @Nonnull static final List<ItemStack> CURRENCIES = new ArrayList<>();
    public static boolean isCurrencyItem(@Nonnull ItemStack stackIn) {
        for(ItemStack stack : CURRENCIES)
            if(StackUtils.areStacksSimilarWithPartialNBT(stackIn, stack))
                return true;

        return false;
    }

    @Override
    public void onUpdate() {
        ARMORS.clear();
        for(String data : armors) {
            final ItemStack stack = new ItemStack(NBTUtils.getTagFromString(data));
            if(!stack.isEmpty()) ARMORS.add(stack);
        }

        CURRENCIES.clear();
        for(String data : currencies) {
            final ItemStack stack = new ItemStack(NBTUtils.getTagFromString(data));
            if(!stack.isEmpty()) CURRENCIES.add(stack);
        }
    }

    //needed for gson
    public PigmanConfig(@Nonnull String[] spawnData, boolean canTradeWhileMad, @Nonnull String[] armors, @Nonnull String[] currencies) {
        this.spawnData = spawnData;
        this.canTradeWhileMad = canTradeWhileMad;
        this.armors = armors;
        this.currencies = currencies;
    }
}
