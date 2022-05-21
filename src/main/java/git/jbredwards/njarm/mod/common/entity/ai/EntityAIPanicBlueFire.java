package git.jbredwards.njarm.mod.common.entity.ai;

import git.jbredwards.njarm.mod.common.util.BlueFireUtils;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public class EntityAIPanicBlueFire extends EntityAIPanic
{
    public EntityAIPanicBlueFire(@Nonnull EntityCreature creature, double speedIn) {
        super(creature, speedIn);
    }

    @Override
    public boolean shouldExecute() {
        if(BlueFireUtils.getRemaining(creature) > 0) {
            final @Nullable BlockPos pos = getRandPos(creature.world, creature, 5, 4);
            if(pos != null) {
                randPosX = pos.getX();
                randPosY = pos.getY();
                randPosZ = pos.getZ();
                return true;
            }

            return findRandomPosition();
        }

        return false;
    }
}
