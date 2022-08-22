package git.jbredwards.njarm.mod.common.item.equipment;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.item.EquipmentConfig;
import git.jbredwards.njarm.mod.common.util.BlueFireUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class ItemSapphireBow extends ItemBow
{
    public ItemSapphireBow() {
        addPropertyOverride(new ResourceLocation(Constants.MODID, "blue_fire"),
            (stack, world, entity) -> EquipmentConfig.sapphireBowHasFlame() ? 1 : 0);
    }

    @Nonnull
    @Override
    public EntityArrow customizeArrow(@Nonnull EntityArrow arrow) {
        if(EquipmentConfig.sapphireBowHasFlame()) BlueFireUtils.setRemaining(arrow, 2);
        return arrow;
    }

    @Override
    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack, @Nonnull Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment)
                && !(enchantment == Enchantments.FLAME && EquipmentConfig.sapphireBowHasFlame());
    }
}
