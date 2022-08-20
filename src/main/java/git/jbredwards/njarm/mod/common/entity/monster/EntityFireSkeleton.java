package git.jbredwards.njarm.mod.common.entity.monster;

import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

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
        isImmuneToFire = true;
    }

    @Override
    protected boolean isValidLightLevel() { return true; }

    @Override
    protected void setEnchantmentBasedOnDifficulty(@Nonnull DifficultyInstance difficulty) {
        super.setEnchantmentBasedOnDifficulty(difficulty);
        getHeldItemMainhand().addEnchantment(Enchantments.FLAME, 1);
    }

    @Nonnull
    @Override
    protected SoundEvent getStepSound() { return SoundEvents.ENTITY_SKELETON_STEP; }

    @Nonnull
    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_SKELETON_AMBIENT; }

    @Nonnull
    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_SKELETON_DEATH; }

    @Nonnull
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) { return SoundEvents.ENTITY_SKELETON_HURT; }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() { return LootTableList.ENTITIES_SKELETON; }
}
