package git.jbredwards.njarm.mod.common.entity.item;

import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class EntityBlestemArrow extends EntityArrow
{
    public EntityBlestemArrow(@Nonnull World worldIn) { super(worldIn); }
    public EntityBlestemArrow(@Nonnull World worldIn, double x, double y, double z) { super(worldIn, x, y, z); }
    public EntityBlestemArrow(@Nonnull World worldIn, @Nonnull EntityLivingBase shooter) { super(worldIn, shooter); }

    @Nonnull
    @Override
    protected ItemStack getArrowStack() { return new ItemStack(ModItems.BLESTEM_ARROW); }

    @Override
    protected void arrowHit(@Nonnull EntityLivingBase living) {
        living.hurtResistantTime = 0;
        living.attackEntityFrom(DamageSource.causeArrowDamage(this, shootingEntity != null ? shootingEntity : this),
                living instanceof EntityPlayer || !living.isNonBoss() ? (float)getDamage() : Float.MAX_VALUE);

        living.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 100));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(!inGround) for(int i = 0; i < 4; i++)
            world.spawnParticle(EnumParticleTypes.REDSTONE, posX + motionX * i / 4.0, posY + motionY * i / 4.0, posZ + motionZ * i / 4.0, 252f/255, 157f/255, 250f/255);
    }
}
