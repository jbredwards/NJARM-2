package git.jbredwards.njarm.mod.common;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.entity.passive.EntityChocolateCow;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class EventHandler
{
    @SubscribeEvent
    public static void onBonemeal(@Nonnull BonemealEvent event) {
        if(event.getBlock() == Blocks.END_STONE.getDefaultState()) {
            bonemealGrass(event.getWorld(), event.getPos().up(), event.getBlock(), ModBlocks.ENDER_GRASS);
            event.setResult(Event.Result.ALLOW);
        }
    }

    static void bonemealGrass(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Block grass) {
        for(int i = 0; i < 128; i++) {
            BlockPos offset = pos;
            int attempts = 0;

            while(true) {
                if(attempts >= i / 16) {
                    if(world.rand.nextInt(8) != 0 && world.isAirBlock(offset) && grass.canPlaceBlockAt(world, offset))
                        world.setBlockState(offset, grass.getDefaultState());

                    break;
                }

                offset = offset.add(world.rand.nextInt(3) - 1, (world.rand.nextInt(3) - 1) * world.rand.nextInt(3) / 2, world.rand.nextInt(3) - 1);
                if(world.getBlockState(offset.down()) != state || world.getBlockState(offset).isNormalCube())
                    break;

                attempts++;
            }
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(@Nonnull PlayerInteractEvent.EntityInteract event) {
        bottlesMilkCows(event.getTarget(), event.getEntityPlayer(), event.getHand());
    }

    static void bottlesMilkCows(@Nonnull Entity entity, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        if(entity instanceof EntityCow && !((EntityCow)entity).isChild() && !player.isCreative()) {
            final ItemStack held = player.getHeldItem(hand);
            if(held.getItem() == Items.GLASS_BOTTLE) {
                player.playSound(SoundEvents.ENTITY_COW_MILK, 1, 1);
                held.shrink(1);

                final ItemStack milkBottle = new ItemStack(entity instanceof EntityChocolateCow
                        ? ModItems.CHOCOLATE_MILK_BOTTLE : ModItems.MILK_BOTTLE);

                if(held.isEmpty()) player.setHeldItem(hand, milkBottle);
                else if(!player.inventory.addItemStackToInventory(milkBottle)) player.dropItem(milkBottle, false);
            }
        }
    }
}
