package git.jbredwards.njarm.mod.common.item.equipment;

import com.google.common.collect.Multimap;
import git.jbredwards.njarm.mod.common.config.item.EquipmentConfig;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemPickaxe;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemCactusPickaxe extends ItemPickaxe
{
    public ItemCactusPickaxe(@Nonnull ToolMaterial material) { super(material); }

    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(@Nonnull EntityEquipmentSlot equipmentSlot) {
        final Multimap<String, AttributeModifier> modifiers = super.getItemAttributeModifiers(equipmentSlot);
        if(EquipmentConfig.cactusReach() && equipmentSlot == EntityEquipmentSlot.MAINHAND)
            modifiers.put(EntityPlayer.REACH_DISTANCE.getName(),
                    new AttributeModifier(EquipmentConfig.CACTUS_REACH_MODIFIER, "Weapon modifier", 1, 0));

        return modifiers;
    }
}
