package git.jbredwards.njarm.mod.common.item.util;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.util.ItemStackUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.StatList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

/**
 * Implement this if the item should auto smelt drops upon breaking a block
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public interface IAutoSmelt
{
    default boolean doesAutoSmelt(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull ItemStack stack) { return true; }
    default void handleEffects(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull ItemStack stack, @Nonnull Random rand) {
        world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2 / rand.nextFloat() * 0.4f + 0.8f);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST) //handles block drop auto smelt logic
    static void tryAutoSmeltBlockDropsOnEvent(@Nonnull BlockEvent.HarvestDropsEvent event) {
        if(!event.isSilkTouching() && event.getHarvester() != null) {
            final ItemStack stack = event.getHarvester().getHeldItemMainhand();
            final BlockPos pos = event.getPos();
            final World world = event.getWorld();
            final IBlockState state = world.getBlockState(pos);

            if(stack.getItem() instanceof IAutoSmelt && ((IAutoSmelt)stack.getItem()).doesAutoSmelt(world, pos, state, stack)) {
                final NonNullList<ItemStack> smeltedDrops = NonNullList.create();

                for(Iterator<ItemStack> it = event.getDrops().iterator(); it.hasNext();) {
                    final ItemStack drop = it.next();
                    final ItemStack smelted = ItemStackUtils.copyStackWithScale(FurnaceRecipes.instance().getSmeltingResult(drop), drop.getCount());
                    if(!smelted.isEmpty()) {
                        //increment crafting stat
                        event.getHarvester().addStat(Objects.requireNonNull(StatList.getCraftStats(smelted.getItem())), smelted.getCount());

                        //get xp that normally results from smelting
                        float smeltedXp = FurnaceRecipes.instance().getSmeltingExperience(smelted);
                        int amount = smelted.getCount();
                        if(smeltedXp == 0) amount = 0;
                        else if(smeltedXp < 1) {
                            amount = MathHelper.floor(smelted.getCount() * smeltedXp);
                            if(amount < MathHelper.ceil(smelted.getCount() * smeltedXp) && Math.random() < (smelted.getCount() * smeltedXp - amount))
                                ++amount;
                        }

                        //spawn xp orbs
                        state.getBlock().dropXpOnBlockBreak(world, pos, amount);

                        //run smelting event
                        FMLCommonHandler.instance().firePlayerSmeltedEvent(event.getHarvester(), smelted);

                        //prevent individual drops from having too large a stack size
                        do smeltedDrops.add(smelted.splitStack(smelted.getMaxStackSize()));
                        while(!smelted.isEmpty());

                        it.remove();
                    }
                }

                ((IAutoSmelt)stack.getItem()).handleEffects(world, pos, state, stack, world.rand);
                event.getDrops().addAll(smeltedDrops);
            }
        }
    }
}
