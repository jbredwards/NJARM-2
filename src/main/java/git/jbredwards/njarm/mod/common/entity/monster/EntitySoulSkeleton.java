package git.jbredwards.njarm.mod.common.entity.monster;

import git.jbredwards.njarm.mod.common.entity.util.IBlueFireproof;
import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
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
public class EntitySoulSkeleton extends AbstractSkeleton implements IBlueFireproof
{
    public EntitySoulSkeleton(@Nonnull World worldIn) {
        super(worldIn);
        isImmuneToFire = true;
    }

    @Override
    protected boolean isValidLightLevel() { return true; }

    @Override
    protected void setEquipmentBasedOnDifficulty(@Nonnull DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.SAPPHIRE_BOW));
    }

    @Nonnull
    @Override
    protected SoundEvent getStepSound() { return SoundEvents.ENTITY_STRAY_STEP; }

    @Nonnull
    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_STRAY_AMBIENT; }

    @Nonnull
    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_STRAY_DEATH; }

    @Nonnull
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) { return SoundEvents.ENTITY_STRAY_HURT; }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() { return LootTableList.ENTITIES_SKELETON; }
}
