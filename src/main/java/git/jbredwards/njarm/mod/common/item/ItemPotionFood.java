package git.jbredwards.njarm.mod.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemPotionFood extends ItemQuickFood
{
    @Nonnull
    public static final PotionEffect[] SPINACH = {
            new PotionEffect(MobEffects.RESISTANCE, 301*20, 1),
            new PotionEffect(MobEffects.FIRE_RESISTANCE, 301*20),
            new PotionEffect(MobEffects.WATER_BREATHING, 301*20),
            new PotionEffect(MobEffects.HASTE, 301*20, 1),
            new PotionEffect(MobEffects.SATURATION, 16*20),
            new PotionEffect(MobEffects.NIGHT_VISION, 301*20),
            new PotionEffect(MobEffects.STRENGTH, 301*20, 2),
            new PotionEffect(MobEffects.SPEED, 301*20, 3),
            new PotionEffect(MobEffects.JUMP_BOOST, 301*20, 1)
    };

    @Nonnull
    protected PotionEffect[] effects = new PotionEffect[0];

    public ItemPotionFood(int amount, boolean isWolfFood) { this(amount, 0.6f, isWolfFood); }
    public ItemPotionFood(int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        maxItemUseDuration = 32;
    }

    @Override
    protected void onFoodEaten(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);
        if(!worldIn.isRemote) for(PotionEffect effect : effects)
            player.addPotionEffect(new PotionEffect(effect));
    }

    @Nonnull
    public ItemPotionFood setEffects(@Nonnull PotionEffect... effects) {
        this.effects = effects;
        return this;
    }
}
