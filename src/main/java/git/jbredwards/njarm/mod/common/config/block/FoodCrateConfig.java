package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.block.BlockFoodCrate;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public final class FoodCrateConfig implements IConfig
{
    @Config.LangKey("config.njarm.block.foodCrates.foodCrateStepEffects")
    @Nonnull public final String[] foodCrateStepEffects;

    @Config.LangKey("config.njarm.block.foodCrates.dropFullBlock")
    public final boolean dropFullBlock;
    public static boolean dropFullBlock() { return ConfigHandler.blockCfg.foodCrateCfg.dropFullBlock; }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onUpdate() {
        //reset old
        for(BlockFoodCrate.Type type : BlockFoodCrate.Type.values()) {
            type.ambientParticles = false;
            type.showParticles = false;
            type.effects.clear();
        }
        //apply new
        for(String nbtStr : foodCrateStepEffects) {
            final NBTTagCompound nbt = NBTUtils.getTagFromString(nbtStr);
            if(nbt.hasKey("Type", Constants.NBT.TAG_STRING)) {
                //gets the type
                final String typeName = nbt.getString("Type");
                @Nullable BlockFoodCrate.Type type = null;
                for(BlockFoodCrate.Type t : BlockFoodCrate.Type.values()) {
                    if(t.getName().equals(typeName)) {
                        type = t;
                        break;
                    }
                }

                if(type != null && nbt.hasKey("Effects", Constants.NBT.TAG_LIST)) {
                    final NBTTagList effectsNbt = nbt.getTagList("Effects", Constants.NBT.TAG_COMPOUND);
                    for(int i = 0; i < effectsNbt.tagCount(); i++) {
                        @Nullable PotionEffect effect = PotionEffect.readCustomPotionEffectFromNBT(effectsNbt.getCompoundTagAt(i));
                        if(effect != null) type.effects.add(effect);
                    }
                    //update particle render stats
                    type.particleColor = PotionUtils.getPotionColorFromEffectList(type.effects);
                    for(PotionEffect e : type.effects) {
                        if(e.getIsAmbient()) type.ambientParticles = true;
                        if(e.doesShowParticles()) type.showParticles = true;
                        if(type.ambientParticles && type.showParticles) break;
                    }
                }
            }
        }
    }

    //needed for gson
    public FoodCrateConfig(@Nonnull String[] foodCrateStepEffects, boolean dropFullBlock) {
        this.foodCrateStepEffects = foodCrateStepEffects;
        this.dropFullBlock = dropFullBlock;
    }
}
