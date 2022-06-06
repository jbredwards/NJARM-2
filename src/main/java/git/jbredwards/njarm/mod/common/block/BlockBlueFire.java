package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.Main;
import git.jbredwards.njarm.mod.common.config.block.BlueFireConfig;
import git.jbredwards.njarm.mod.common.message.MessageBlueFire;
import git.jbredwards.njarm.mod.common.util.BlueFireUtils;
import git.jbredwards.njarm.mod.common.util.SoundUtils;
import net.darkhax.bookshelf.item.ICustomModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants.WorldEvents;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class BlockBlueFire extends BlockFire implements ICustomModel
{
    @Nonnull
    @Override
    public IBlockState getActualState(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return canCatchFire(worldIn, pos.down(), EnumFacing.UP) ? state :
                state.withProperty(NORTH, canCatchFire(worldIn, pos.north(), EnumFacing.SOUTH))
                        .withProperty(EAST,  canCatchFire(worldIn, pos.east(), EnumFacing.WEST))
                        .withProperty(SOUTH, canCatchFire(worldIn, pos.south(), EnumFacing.NORTH))
                        .withProperty(WEST,  canCatchFire(worldIn, pos.west(), EnumFacing.EAST))
                        .withProperty(UPPER, canCatchFire(worldIn, pos.up(), EnumFacing.DOWN));
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        if(worldIn.getGameRules().getBoolean("doFireTick")) {
            if(canDie(worldIn, pos) && rand.nextDouble() < 0.2 + state.getValue(AGE) * 0.03) worldIn.setBlockToAir(pos);
            else worldIn.scheduleUpdate(pos, this, tickRate(worldIn) + rand.nextInt(10));
        }
    }

    @Override
    public boolean canPlaceBlockAt(@Nonnull World worldIn, @Nonnull BlockPos pos) { return canNeighborCatchFire(worldIn, pos); }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos) {
        if(!canPlaceBlockAt(worldIn, pos)) worldIn.setBlockToAir(pos);
    }

    @Override
    public void onBlockAdded(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if(!canPlaceBlockAt(worldIn, pos)) worldIn.setBlockToAir(pos);
        else if(worldIn.getGameRules().getBoolean("doFireTick"))
            worldIn.scheduleUpdate(pos, this, tickRate(worldIn) + worldIn.rand.nextInt(10));
    }

    @Nonnull
    @Override
    public MapColor getMapColor(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) { return MapColor.LIGHT_BLUE; }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if(rand.nextInt(24) == 0)
            worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1 + rand.nextFloat(), rand.nextFloat() * 0.7f + 0.3f, false);
    }

    @Override
    public boolean canCatchFire(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing face) {
        return BlueFireConfig.SOUL_SAND.containsKey(world.getBlockState(pos).getBlock());
    }

    @Override
    public void onEntityCollision(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Entity entityIn) {
        final boolean isSoulItem = entityIn instanceof EntityItem && BlueFireConfig.SOUL_SAND.containsKey(getBlockFromItem(((EntityItem)entityIn).getItem().getItem()));
        if(!isSoulItem && BlueFireUtils.damageEntityIn(entityIn)) {
            if(entityIn.isWet()) SoundUtils.playSound(entityIn, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7f,
                    1.6f + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.4f);
            else if(BlueFireUtils.canBeLit(entityIn)) {
                Main.wrapper.sendToAllAround(
                        new MessageBlueFire(entityIn.getEntityId(), true),
                        new NetworkRegistry.TargetPoint(worldIn.provider.getDimension(), entityIn.posX, entityIn.posY, entityIn.posZ, 64));

                BlueFireUtils.setRemaining(entityIn, 5);
            }
        }
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) { return PathNodeType.LAVA; }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerMeshModels() { ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(AGE).build()); }

    @SubscribeEvent
    public static void extinguishOnLeftClick(@Nonnull PlayerInteractEvent.LeftClickBlock event) {
        final BlockPos pos = event.getFace() == null ? event.getPos() : event.getPos().offset(event.getFace());
        final World world = event.getWorld();

        if(world.getBlockState(pos).getBlock() instanceof BlockBlueFire) {
            if(world.isRemote) { //reset block breaking progress
                final boolean old = Minecraft.getMinecraft().playerController.isHittingBlock;
                Minecraft.getMinecraft().playerController.isHittingBlock = true;
                Minecraft.getMinecraft().playerController.resetBlockRemoving();
                Minecraft.getMinecraft().playerController.isHittingBlock = old;
            }

            else world.setBlockToAir(pos);
            world.playEvent(null, WorldEvents.FIRE_EXTINGUISH_SOUND, pos, 0);
            event.setCanceled(true);
        }
    }
}
