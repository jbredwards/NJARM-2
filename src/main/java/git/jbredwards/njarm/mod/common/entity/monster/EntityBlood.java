package git.jbredwards.njarm.mod.common.entity.monster;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class EntityBlood extends AbstractSlime
{
    public EntityBlood(@Nonnull World worldIn) { super(worldIn); }

    @Nonnull
    @Override
    public AbstractSlime createInstance() { return new EntityBlood(world); }

    @Nonnull
    @Override
    public Item getItemForParticle() { return ModItems.BLOOD_BALL; }

    @Nonnull
    @Override
    public ResourceLocation getLootTableForSmall() { return new ResourceLocation(Constants.MODID, "entities/blood"); }

    @Override
    protected void applyEnchantments(@Nonnull EntityLivingBase self, @Nonnull Entity attackTarget) {
        super.applyEnchantments(self, attackTarget);
        if(!world.isRemote && isEntityAlive()) {
            final int size = getSlimeSize();
            if(size < 5 && rand.nextFloat() < 1f / size) {
                playSound(SoundEvents.ENTITY_WITCH_DRINK, 1, 2.0f / size);
                final float damageTaken = getMaxHealth() - getHealth();

                setSlimeSize(size + 1, false);
                setHealth(getMaxHealth() - damageTaken);
            }
        }
    }

    @Override
    protected boolean canDamagePlayer() { return true; }

    @Override
    protected boolean isValidLightLevel() { return true; }
}
