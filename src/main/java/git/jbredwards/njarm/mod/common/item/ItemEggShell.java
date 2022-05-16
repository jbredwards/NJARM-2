package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.item.EggShellsConfig;
import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class ItemEggShell extends ItemBonemeal
{
    //only allow bonemeal if enabled via config
    @Nonnull
    @Override
    public EnumActionResult applyBonemeal(@Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing) {
        return EggShellsConfig.actAsBonemeal() ? super.applyBonemeal(player, world, pos, hand, facing) : EnumActionResult.PASS;
    }

    //spawn the egg shell when an egg hits something
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onEggProjectileHit(@Nonnull ProjectileImpactEvent.Throwable event) {
        final @Nullable RayTraceResult result = event.getRayTraceResult();
        final EntityThrowable projectile = event.getThrowable();
        final World world = projectile.getEntityWorld();
        //ensure impact is valid
        if(result != null && (result.entityHit == null || result.entityHit != projectile.getThrower())) {
            if(EggShellsConfig.entities.containsKey(projectile.getClass())) {
                final boolean canSpawn = world.rand.nextFloat() * 100 < (projectile.getThrower() == null || projectile.getThrower() instanceof FakePlayer
                        ? EggShellsConfig.fromDispenserChance() : EggShellsConfig.fromThrownChance());

                if(canSpawn) {
                    //particles
                    if(world.isRemote) {
                        world.spawnParticle(EnumParticleTypes.ITEM_CRACK, projectile.posX, projectile.posY, projectile.posZ,
                                (world.rand.nextFloat() - 0.5) * 0.3,
                                (world.rand.nextFloat() - 0.5) * 0.3,
                                (world.rand.nextFloat() - 0.5) * 0.3,
                                Item.getIdFromItem(Items.EGG));
                    }
                    //spawn the item & play sound effect
                    else {
                        projectile.playSound(SoundEvents.ENTITY_WITHER_BREAK_BLOCK, 0.5f, 2.0f);
                        world.spawnEntity(new EntityItem(world, projectile.posX, projectile.posY, projectile.posZ, new ItemStack(ModItems.EGG_SHELL)));
                    }
                }
            }
        }
    }
}
