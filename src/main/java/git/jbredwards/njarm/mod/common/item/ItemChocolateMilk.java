package git.jbredwards.njarm.mod.common.item;

import net.darkhax.bookshelf.util.PotionUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.minecraftforge.fml.common.Optional;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.food.IEdible;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@Optional.Interface(modid = "applecore", iface = "squeek.applecore.api.food.IEdible")
public class ItemChocolateMilk extends ItemMilkStackable implements IEdible
{
    @Override
    protected boolean canRemoveEffect(@Nonnull PotionEffect effect) {
        return !PotionUtils.isBeneficial(effect.getPotion());
    }

    @Override
    protected void onPlayerConsume(@Nonnull EntityPlayer player) {
        player.getFoodStats().addStats(2, 0.2f);
        player.heal(2);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nonnull NBTTagCompound nbt) {
        return getContainerItem(stack).getItem() != Items.BUCKET ? null : new FluidBucketWrapper(stack) {
            @Override
            public boolean canFillFluidType(@Nonnull FluidStack fluidStack) {
                return fluidStack.getFluid().getName().equals("milk_chocolate");
            }

            @Nullable
            @Override
            public FluidStack getFluid() { return FluidRegistry.getFluidStack("milk_chocolate", Fluid.BUCKET_VOLUME); }
        };
    }

    @Optional.Method(modid = "applecore")
    @Nonnull
    @Override
    public FoodValues getFoodValues(@Nonnull ItemStack itemStack) { return new FoodValues(2, 0.2f); }
}
