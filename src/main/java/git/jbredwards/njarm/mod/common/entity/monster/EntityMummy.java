package git.jbredwards.njarm.mod.common.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public class EntityMummy extends EntityHusk implements IRangedAttackMob
{
    @Nonnull final EntityAIAttackRangedBow<EntityMummy> aiArrowAttack = new EntityAIAttackRangedBow<>(this, 1, 20, 15);
    @Nonnull final EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2, false);

    public EntityMummy(@Nonnull World worldIn) {
        super(worldIn);
        setCombatTask();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2);
    }

    @Override
    public void setItemStackToSlot(@Nonnull EntityEquipmentSlot slotIn, @Nonnull ItemStack stack) {
        super.setItemStackToSlot(slotIn, stack);
        if(slotIn == EntityEquipmentSlot.MAINHAND)
            setCombatTask();
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setCombatTask();
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(@Nonnull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        setCombatTask();
        return livingdata;
    }

    public void setCombatTask() {
        if(world != null && !world.isRemote) {
            tasks.removeTask(aiAttackOnCollide);
            tasks.removeTask(aiArrowAttack);

            if(getHeldItemMainhand().getItem() instanceof ItemBow) {
                aiArrowAttack.setAttackCooldown(world.getDifficulty() == EnumDifficulty.HARD ? 40 : 20);
                tasks.addTask(4, aiArrowAttack);
            }

            else tasks.addTask(4, aiAttackOnCollide);
        }
    }

    @Override
    public void attackEntityWithRangedAttack(@Nonnull EntityLivingBase target, float distanceFactor) {
        final EntityTippedArrow arrow = new EntityTippedArrow(world, this);
        arrow.setEnchantmentEffectsFromEntity(this, distanceFactor);

        final ItemStack held = getHeldItemMainhand();
        if(held.getItem() instanceof ItemBow) ((ItemBow)held.getItem()).customizeArrow(arrow);

        final double x = target.posX - posX;
        final double y = target.getEntityBoundingBox().minY + (target.height / 3) - posY;
        final double z = target.posZ - posZ;
        final double dist = MathHelper.sqrt(x * x + z * z);

        arrow.shoot(x, y + dist * 0.2, z, 1.6f, 14 - world.getDifficulty().getId() * 4);
        playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1, 1 / (rand.nextFloat() * 0.4f + 0.8f));
        world.spawnEntity(arrow);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {}
}
