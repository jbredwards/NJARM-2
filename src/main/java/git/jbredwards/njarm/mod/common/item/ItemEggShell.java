package git.jbredwards.njarm.mod.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public class ItemEggShell extends ItemBonemeal
{
    //TODO only allow bonemeal if enabled via config
    @Nonnull
    @Override
    public EnumActionResult applyBonemeal(@Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing) {
        return super.applyBonemeal(player, world, pos, hand, facing);
    }

    //spawn the egg shell when an egg hits something
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onEggProjectileHit(@Nonnull ProjectileImpactEvent.Throwable event) {
        final @Nullable RayTraceResult result = event.getRayTraceResult();
        final EntityThrowable projectile = event.getThrowable();
        final World world = projectile.getEntityWorld();
        //ensure impact is valid
        if(result != null && (result.entityHit == null || result.entityHit != projectile.getThrower())) {
            if(projectile.getClass() == EntityEgg.class && world.rand.nextFloat() < 0.2f) {

            }
        }
    }
}
