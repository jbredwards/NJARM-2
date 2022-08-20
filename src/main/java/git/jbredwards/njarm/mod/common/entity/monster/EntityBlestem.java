package git.jbredwards.njarm.mod.common.entity.monster;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.entity.ai.EntityAIBlestemAttack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class EntityBlestem extends EntityMob
{
    public EntityBlestem(@Nonnull World worldIn) {
        super(worldIn);
        experienceValue = 10;
    }

    @Override
    protected void initEntityAI() {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIWanderAvoidWater(this, 1));
        tasks.addTask(4, new EntityAIBlestemAttack(this));
        tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1));
        tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1, 0));
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8));
        tasks.addTask(8, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2);
    }

    @Override
    public void onLivingUpdate() {
        if(world.isRemote) for(int i = 0; i < 2; ++i)
            world.spawnParticle(EnumParticleTypes.PORTAL, posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height - 0.25, posZ + (rand.nextDouble() - 0.5) * width, (rand.nextDouble() - 0.5) * 2, -rand.nextDouble(), (rand.nextDouble() - 0.5) * 2);

        super.onLivingUpdate();
    }

    @Override
    public boolean canAttackClass(@Nonnull Class<? extends EntityLivingBase> cls) {
        return super.canAttackClass(cls) && !EntityDragon.class.isAssignableFrom(cls);
    }

    @Override
    public void fall(float distance, float damageMultiplier) { }

    @Override
    public void knockBack(@Nonnull Entity entityIn, float strength, double xRatio, double zRatio) { }

    @Nonnull
    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT; }

    @Nonnull
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) { return SoundEvents.ENTITY_ELDER_GUARDIAN_HURT; }

    @Nonnull
    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH; }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() { return new ResourceLocation(Constants.MODID, "entities/blestem"); }
}
