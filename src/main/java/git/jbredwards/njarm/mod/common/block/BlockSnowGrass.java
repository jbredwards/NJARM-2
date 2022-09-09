package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.block.util.gravity.ICanFallThrough;
import git.jbredwards.njarm.mod.common.block.util.gravity.ILayeredFallingBlock;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.util.SoundUtils;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class BlockSnowGrass extends BlockSnow implements ILayeredFallingBlock
{
    @Nonnull
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.1, 0, 0.1, 0.9, 0.8, 0.9);

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(@Nonnull IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return SNOW_AABB[blockState.getValue(LAYERS) - 1];
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBox(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos) {
        return (isLookingAtSnow(Minecraft.getMinecraft().player, pos, state)
                ? SNOW_AABB[state.getValue(LAYERS)] : AABB).offset(pos);
    }

    @Override
    public boolean canRenderInLayer(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Nonnull
    @Override
    public EnumOffsetType getOffsetType() {
        return MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT_MIPPED
                ? EnumOffsetType.XYZ : EnumOffsetType.NONE;
    }

    public static void dropGrassItem(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, @Nonnull ItemStack held) {
        Optional.ofNullable(StatList.getBlockStats(ModBlocks.SNOW_GRASS)).ifPresent(player::addStat);
        final boolean isShearable = held.getItem() instanceof ItemShears && Blocks.TALLGRASS.isShearable(held, world, pos);
        if(isShearable || EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, held) > 0) {
            spawnAsEntity(world, pos, new ItemStack(Blocks.TALLGRASS, 1, 1));
            if(isShearable) held.damageItem(1, player);
        }

        else Blocks.TALLGRASS.dropBlockAsItem(world, pos, Blocks.TALLGRASS.getStateFromMeta(1),
                EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, held));
    }

    @Override
    public boolean removedByPlayer(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, boolean willHarvest) {
        onBlockHarvested(world, pos, state, player);
        if(Blocks.TALLGRASS.canBlockStay(world, pos, Blocks.TALLGRASS.getStateFromMeta(1)))
            return world.setBlockState(pos, Blocks.TALLGRASS.getStateFromMeta(1), world.isRemote ? 11 : 3);

        if(!world.isRemote) {
            SoundUtils.playSound(world, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1, 0.8f);
            if(!player.isCreative()) dropGrassItem(world, pos, player, player.getHeldItemMainhand());
        }

        return world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
    }

    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(@Nonnull IBlockState blockState, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Vec3d start, @Nonnull Vec3d end) {
        start = start.subtract(pos.getX(), pos.getY(), pos.getZ());
        end = end.subtract(pos.getX(), pos.getY(), pos.getZ());

        final @Nullable RayTraceResult grass = AABB.calculateIntercept(start, end);
        final @Nullable RayTraceResult snow = SNOW_AABB[blockState.getValue(LAYERS)].calculateIntercept(start, end);
        if(grass == null && snow == null) return null;

        final RayTraceResult result;
        if(grass == null) result = snow;
        else if(snow == null) result = grass;
        else result = start.distanceTo(grass.hitVec) < (start.distanceTo(snow.hitVec)) ? grass : snow;

        return new RayTraceResult(result.hitVec.add(pos.getX(), pos.getY(), pos.getZ()), result.sideHit, pos);
    }

    public static boolean isLookingAtSnow(@Nonnull EntityPlayer player, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        final Vec3d start = player.getPositionEyes(1).subtract(pos.getX(), pos.getY(), pos.getZ());
        final Vec3d end = start.add(player.getLookVec().scale(
                player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue()));

        final @Nullable RayTraceResult grass = AABB.calculateIntercept(start, end);
        if(grass == null) return true;

        final @Nullable RayTraceResult snow = SNOW_AABB[state.getValue(LAYERS)].calculateIntercept(start, end);
        if(snow == null) return false;

        return start.distanceTo(grass.hitVec) >= start.distanceTo(snow.hitVec);
    }

    @Override
    public boolean fallOnto(@Nonnull EntityFallingBlock fallingBlock, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState fallingState, @Nonnull IBlockState replacedState, @Nonnull IBlockState hitState) {
        if(!(replacedState.getBlock() instanceof BlockSnow) || (replacedState.getBlock() instanceof ICanFallThrough
                && !((ICanFallThrough)replacedState.getBlock()).canFallThrough(replacedState))) return false;

        final int combinedLevel = replacedState.getValue(BlockSnow.LAYERS) + fallingState.getValue(BlockSnow.LAYERS);
        if(combinedLevel <= 8) world.setBlockState(pos, fallingState.withProperty(BlockSnow.LAYERS, combinedLevel));
        else {
            world.setBlockState(pos, replacedState.withProperty(BlockSnow.LAYERS, 8));
            if(world.mayPlace(fallingState.getBlock(), pos.up(), true, EnumFacing.UP, null))
                world.setBlockState(pos.up(), fallingState.withProperty(BlockSnow.LAYERS, combinedLevel - 8));
        }

        return true;
    }

    @SubscribeEvent
    public static void leftClickBlock(@Nonnull PlayerInteractEvent.LeftClickBlock event) {
        final IBlockState state = event.getWorld().getBlockState(event.getPos());
        if(state.getBlock() instanceof BlockSnowGrass) {
            if(!isLookingAtSnow(event.getEntityPlayer(), event.getPos(), state) && Blocks.SNOW_LAYER.canPlaceBlockAt(event.getWorld(), event.getPos())) {
                if(event.getWorld().isRemote) Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(event.getPos(), state);
                else {
                    if(!event.getEntityPlayer().isCreative())
                        dropGrassItem(event.getWorld(), event.getPos(), event.getEntityPlayer(), event.getEntityPlayer().getHeldItemMainhand());

                    SoundUtils.playSound(event.getWorld(), event.getPos(), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1, 0.8f);
                    event.getWorld().setBlockState(event.getPos(), Blocks.SNOW_LAYER.getDefaultState().withProperty(LAYERS, state.getValue(LAYERS)));
                }

                event.setCancellationResult(EnumActionResult.FAIL);
                event.setCanceled(true);
            }
        }
    }
}
