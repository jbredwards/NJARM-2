package git.jbredwards.njarm.mod.common.config.item;

import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraft.item.Item;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 *
 * @author jbred
 *
 */
@SuppressWarnings("NotNullFieldNotInitialized")
public final class EquipmentConfig implements IConfig
{
    private static boolean initializeMaterials = true;

    @Config.RequiresMcRestart
    @Config.Comment("Change the stats of ruby tools.")
    @Nonnull public final String rubyToolMaterial;
    @Nonnull public static Item.ToolMaterial RUBY_TOOL_MATERIAL;

    private void initializeMaterials() throws NBTException {
        RUBY_TOOL_MATERIAL = toolMaterial("RUBY", rubyToolMaterial);
    }

    @Nonnull
    private Item.ToolMaterial toolMaterial(@Nonnull String name, @Nonnull String toolMaterial) throws NBTException {
        final NBTTagCompound nbt = JsonToNBT.getTagFromJson(toolMaterial);
        return Objects.requireNonNull(EnumHelper.addToolMaterial(name, nbt.getInteger("HarvestLevel"), nbt.getInteger("Durability"),
                nbt.getFloat("MiningSpeed"), nbt.getFloat("AttackDamage"), nbt.getInteger("Enchantability")));
    }

    //gson
    public EquipmentConfig(@Nonnull String rubyToolMaterial) {
        this.rubyToolMaterial = rubyToolMaterial;
        //only initialize materials on first load
        if(initializeMaterials) {
            initializeMaterials = false;
            try { initializeMaterials(); }
            catch(NBTException e) { e.printStackTrace();
                throw new IllegalStateException();
            }
        }
    }
}