package git.jbredwards.njarm.mod.common.item.equipment;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.item.EquipmentConfig;
import git.jbredwards.njarm.mod.common.init.ModItems;
import git.jbredwards.njarm.mod.common.item.util.IBlueFireWeapon;
import git.jbredwards.njarm.mod.common.util.BlueFireUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class ItemSapphireBow extends ItemBow implements IBlueFireWeapon
{
    public ItemSapphireBow() {
        addPropertyOverride(new ResourceLocation(Constants.MODID, "blue_fire"),
            (stack, world, entity) -> EquipmentConfig.sapphireBowHasFlame() ? 1 : 0);
    }

    @Override
    public boolean getIsRepairable(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return OreDictionary.itemMatches(new ItemStack(ModItems.SAPPHIRE), repair, false);
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack stack, @Nonnull EntityLivingBase target, @Nonnull EntityLivingBase attacker) {
        stack.damageItem(2, attacker);
        return super.hitEntity(stack, target, attacker);
    }

    @Nonnull
    @Override
    public EntityArrow customizeArrow(@Nonnull EntityArrow arrow) {
        if(EquipmentConfig.sapphireBowHasFlame()) {
            if(arrow.isBurning()) arrow.setFire(0);
            BlueFireUtils.setRemaining(arrow, 2);
        }

        return arrow;
    }

    @Override
    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack, @Nonnull Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) && !(EquipmentConfig.sapphireBowHasFlame()
                && (enchantment == Enchantments.FLAME || enchantment == Enchantments.FIRE_ASPECT));
    }
}
