package git.jbredwards.njarm.mod.common.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class EntityFireSkeleton extends AbstractSkeleton
{
    public EntityFireSkeleton(@Nonnull World worldIn) {
        super(worldIn);
        setSize(0.65f, 2.2f);
        isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
    }

    @Override
    public float getEyeHeight() { return 2; }

    @Override
    protected boolean isValidLightLevel() { return true; }

    @Override
    public float getBrightness() { return 1; }

    @SideOnly(Side.CLIENT)
    @Override
    public int getBrightnessForRender() { return 15728880; }

    @Nonnull
    @Override
    protected EntityArrow getArrow(float distanceFactor) {
        final EntityArrow arrow = super.getArrow(distanceFactor);
        arrow.setFire(100);
        return arrow;
    }

    @Override
    public void onLivingUpdate() {
        if(world.isRemote)
            for(int i = 0; i < 2; ++i)
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0, 0, 0);

        super.onLivingUpdate();
    }

    @Nonnull
    @Override
    protected SoundEvent getStepSound() { return SoundEvents.ENTITY_WITHER_SKELETON_STEP; }

    @Nonnull
    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT; }

    @Nonnull
    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_WITHER_SKELETON_DEATH; }

    @Nonnull
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) { return SoundEvents.ENTITY_WITHER_SKELETON_HURT; }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() { return LootTableList.ENTITIES_SKELETON; }
}
