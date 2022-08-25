package git.jbredwards.njarm.mod.common.config.entity;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.entity.util.ISpawnableConfig;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import net.darkhax.bookshelf.lib.WeightedSelector;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
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
public final class MummyConfig implements ISpawnableConfig
{
    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.entity.generic.spawnData")
    @Nonnull public final String[] spawnData;
    @Nonnull public String[] spawnData() { return ConfigHandler.entityCfg.mummyCfg.spawnData; }
    @Nonnull public static final List<Biome> spawnBiomes = new ArrayList<>();
    @Nonnull public List<Biome> allSpawnBiomes() { return spawnBiomes; }

    @Nonnull
    @Config.LangKey("config.njarm.entity.mummy.potionData")
    public final String[] potionData;

    @Nonnull
    @Config.LangKey("config.njarm.entity.mummy.weaponData")
    public final String[] weaponData;

    @Nonnull
    public static final WeightedSelector<PotionEffect> EFFECTS = new WeightedSelector<>();

    @Nonnull
    public static final WeightedSelector<ItemStack> WEAPONS = new WeightedSelector<>();

    @Override
    public void onUpdate() {
        EFFECTS.getEntries().clear();
        EFFECTS.updateTotal();

        for(String data : potionData) {
            final NBTTagCompound nbt = NBTUtils.getTagFromString(data);
            EFFECTS.addEntry(nbt.getBoolean("Empty") ? new PotionEffect(MobEffects.GLOWING, 0) :
                    PotionEffect.readCustomPotionEffectFromNBT(nbt), Math.max(nbt.getInteger("Weight"), 1));
        }

        WEAPONS.getEntries().clear();
        WEAPONS.updateTotal();

        for(String data : weaponData) {
            final NBTTagCompound nbt = NBTUtils.getTagFromString(data);
            WEAPONS.addEntry(nbt.getBoolean("Empty") ? ItemStack.EMPTY :
                    new ItemStack(nbt), Math.max(nbt.getInteger("Weight"), 1));
        }
    }

    //needed for gson
    public MummyConfig(@Nonnull String[] spawnData, @Nonnull String[] potionData, @Nonnull String[] weaponData) {
        this.spawnData = spawnData;
        this.potionData = potionData;
        this.weaponData = weaponData;
    }
}
