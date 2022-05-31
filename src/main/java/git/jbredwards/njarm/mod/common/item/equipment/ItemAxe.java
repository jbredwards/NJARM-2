package git.jbredwards.njarm.mod.common.item.equipment;

import javax.annotation.Nonnull;

/**
 * Forge non-public constructor that can't become public via access transformer, gross...
 * @author jbred
 *
 */
public class ItemAxe extends net.minecraft.item.ItemAxe {
    public ItemAxe(@Nonnull ToolMaterial material) { super(material, material.getAttackDamage() + 5, -3); }
}
