package git.jbredwards.njarm.mod.common.entity.monster;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public class EntityFireCreeper extends EntityCreeper
{
    public EntityFireCreeper(@Nonnull World worldIn) {
        super(worldIn);
        isImmuneToFire = true;
    }

    @Override
    public void explode() {
        if(!world.isRemote) {
            world.newExplosion(this, posX, posY, posZ, explosionRadius * (getPowered() ? 2 : 1),
                    true, ForgeEventFactory.getMobGriefingEvent(world, this));

            setDead();
            spawnLingeringCloud();
        }
    }

    @Override
    protected boolean isValidLightLevel() {
        return true;
    }

    //temporary fix for this dropping normal creeper heads
    @Nullable
    @Override
    public EntityItem entityDropItem(@Nonnull ItemStack stack, float offsetY) {
        return stack.getItem() == Items.SKULL ? null : super.entityDropItem(stack, offsetY);
    }
}
