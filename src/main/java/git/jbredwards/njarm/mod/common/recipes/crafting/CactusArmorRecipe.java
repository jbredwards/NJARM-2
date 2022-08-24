package git.jbredwards.njarm.mod.common.recipes.crafting;

import git.jbredwards.njarm.mod.common.config.item.EquipmentConfig;
import git.jbredwards.njarm.mod.common.util.ItemStackUtils;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;

/**
 * Dynamically changes the armor to be enchanted based on the config
 * @author jbred
 *
 */
public class CactusArmorRecipe extends ShapedOreRecipe
{
    public CactusArmorRecipe(@Nonnull ResourceLocation group, @Nonnull Item result, @Nonnull Object... recipe) {
        super(group, result, recipe);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        return EquipmentConfig.cactusThorns() > 0
                ? ItemStackUtils.addEnchantment(output.copy(), Enchantments.THORNS, EquipmentConfig.cactusThorns())
                : output.copy();
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return EquipmentConfig.cactusThorns() > 0
                ? ItemStackUtils.addEnchantment(output.copy(), Enchantments.THORNS, EquipmentConfig.cactusThorns())
                : output;
    }
}
