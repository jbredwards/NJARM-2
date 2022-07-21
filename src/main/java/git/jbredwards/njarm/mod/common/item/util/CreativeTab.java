package git.jbredwards.njarm.mod.common.item.util;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.init.ModEntities;
import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class CreativeTab extends CreativeTabs
{
    @Nonnull
    public static final CreativeTab INSTANCE = new CreativeTab(Constants.MODID);
    private CreativeTab(String label) { super(label); }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public ItemStack createIcon() { return new ItemStack(ModItems.RUBY); }

    @SideOnly(Side.CLIENT)
    @Override
    public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> items) {
        super.displayAllRelevantItems(items);
        //adds this mod's spawn eggs
        for(EntityEntry entry : ModEntities.INIT) {
            if(entry.getEgg() != null) {
                final ItemStack stack = new ItemStack(Items.SPAWN_EGG);
                ItemMonsterPlacer.applyEntityIdToItemStack(stack, entry.getEgg().spawnedID);
                items.add(stack);
            }
        }
        //TODO add potions & enchants
    }
}
