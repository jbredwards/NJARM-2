package git.jbredwards.njarm.mod.common.entity.ai;

import git.jbredwards.njarm.mod.common.entity.monster.EntityBlestem;
import net.darkhax.bookshelf.util.EntityUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.init.SoundEvents;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public class EntityAIBlestemAttack extends EntityAIBase
{
    @Nonnull
    protected final EntityBlestem entity;
    protected int attackTime;

    public EntityAIBlestemAttack(@Nonnull EntityBlestem entityIn) {
        entity = entityIn;
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        return entity.getAttackTarget() != null && entity.getAttackTarget().isEntityAlive()
                && entity.canAttackClass(entity.getAttackTarget().getClass());
    }

    @Override
    public void updateTask() {
        final @Nullable EntityLivingBase target = entity.getAttackTarget();
        if(target != null) {
            final double distance = entity.getDistanceSq(target);
            //use melee attack
            if(distance < 4) {
                if(attackTime <= 0) {
                    entity.attackEntityAsMob(target);
                    attackTime = 20;
                }

                entity.getMoveHelper().setMoveTo(target.posX, target.posY, target.posZ, 1);
            }
            //use ranged attack
            else if(distance < Math.pow(EntityUtils.getFollowRange(entity), 2)) {
                if(attackTime <= 0) {
                    attackTime = 100;

                    final EntityShulkerBullet bullet = new EntityShulkerBullet(entity.world, entity, target, null);
                    bullet.setPosition(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);

                    entity.world.spawnEntity(bullet);
                    entity.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2, (entity.getRNG().nextFloat() - entity.getRNG().nextFloat()) * 0.2f + 1);
                }

                entity.getLookHelper().setLookPositionWithEntity(target, 10, 10);
            }
            //wander into range
            else {
                entity.getNavigator().clearPath();
                entity.getMoveHelper().setMoveTo(target.posX, target.posY, target.posZ, 1);
            }
        }

        attackTime--;
        super.updateTask();
    }
}
