package git.jbredwards.njarm.asm.plugins;

import git.jbredwards.njarm.mod.common.config.client.RenderingConfig;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import git.jbredwards.njarm.mod.common.util.BlueFireUtils;
import git.jbredwards.njarm.mod.common.util.IHasRunningEffects;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * class exists cause SpongeForge
 * @author jbred
 *
 */
@SuppressWarnings("unused")
public final class ASMHooks
{
    //PluginBlockCauldron
    public static boolean canCauldronRenderInLayer(@Nonnull Block block, @Nonnull BlockRenderLayer layer) {
        //only apply fix to vanilla cauldrons, as not to potentially ruin any modded ones
        return block == Blocks.CAULDRON && layer == BlockRenderLayer.TRANSLUCENT || block.getRenderLayer() == layer;
    }

    //PluginBlockCauldron
    @Nullable
    public static Boolean isEntityInsideCauldronMaterial(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Entity entity, double yToTest, @Nonnull Material materialIn, boolean testingHead) {
        if(state.getBlock() != Blocks.CAULDRON || materialIn != Material.WATER) return null;

        final int level = state.getValue(BlockCauldron.LEVEL);
        if(!testingHead) yToTest = entity.posY;

        return level > 0 && yToTest < pos.getY() + 0.375 + level * 0.1875;
    }

    //PluginBlockCauldron
    @Nullable
    public static Boolean isAABBInsideCauldronMaterial(@Nonnull Block block, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB boundingBox, @Nonnull Material materialIn) {
        return block != Blocks.CAULDRON || materialIn != Material.WATER ? null : block.isAABBInsideLiquid(world, pos, boundingBox);
    }

    //PluginBlockCauldron
    @Nonnull
    public static Boolean isAABBInsideCauldronLiquid(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB boundingBox) {
        final int level = world.getBlockState(pos).getValue(BlockCauldron.LEVEL);
        return level > 0 && boundingBox.minY < pos.getY() + 0.375 + level * 0.1875;
    }

    //PluginBlockFire
    @Nonnull
    public static IBlockState renderBlueOrNormalFire(@Nonnull BlockFire fire, @Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        if(shouldBeBlueFire(fire, world, pos)) return ModBlocks.BLUE_FIRE.getActualState(ModBlocks.BLUE_FIRE.getDefaultState(), world, pos);
        //run old code
        return world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) || fire.canCatchFire(world, pos.down(), EnumFacing.UP) ? state :
            state.withProperty(BlockFire.NORTH, fire.canCatchFire(world, pos.north(), EnumFacing.SOUTH))
                    .withProperty(BlockFire.EAST,  fire.canCatchFire(world, pos.east(), EnumFacing.WEST))
                    .withProperty(BlockFire.SOUTH, fire.canCatchFire(world, pos.south(), EnumFacing.NORTH))
                    .withProperty(BlockFire.WEST,  fire.canCatchFire(world, pos.west(), EnumFacing.EAST))
                    .withProperty(BlockFire.UPPER, fire.canCatchFire(world, pos.up(), EnumFacing.DOWN));
    }

    //PluginBlockFire
    public static void tryChangeToBlueFire(@Nonnull BlockFire fire, @Nonnull World world, @Nonnull BlockPos pos) {
        if(shouldBeBlueFire(fire, world, pos)) world.setBlockState(pos, ModBlocks.BLUE_FIRE.getDefaultState(), 2);
        //run old code
        else if(!fire.canPlaceBlockAt(world, pos)) world.setBlockToAir(pos);
    }

    //PluginBlockFire
    public static void tryConvertToBlueFire(@Nonnull BlockFire fire, @Nonnull World world, @Nonnull BlockPos pos) {
        if(world.provider.getDimensionType().getId() > 0 || !Blocks.PORTAL.trySpawnPortal(world, pos)) {
            if(shouldBeBlueFire(fire, world, pos)) world.setBlockState(pos, ModBlocks.BLUE_FIRE.getDefaultState(), 2);
            //run old code
            else if(!fire.canPlaceBlockAt(world, pos)) world.setBlockToAir(pos);
            else if(world.getGameRules().getBoolean("doFireTick"))
                world.scheduleUpdate(pos, fire, fire.tickRate(world) + world.rand.nextInt(10));
        }
    }

    //PluginBlockFire helper
    public static boolean shouldBeBlueFire(@Nonnull BlockFire fire, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        if(fire != Blocks.FIRE) return false; //only turn vanilla fire into blue fire
        else if(ModBlocks.BLUE_FIRE.canCatchFire(world, pos.down(), EnumFacing.UP)) return true;
        //check if normal fire can be at the sides lit here
        for(EnumFacing facing : EnumFacing.values())
            if(facing != EnumFacing.DOWN && fire.canCatchFire(world, pos.offset(facing), facing.getOpposite()))
                return false;

        //check if blue fire can be lit here
        for(EnumFacing facing : EnumFacing.values())
            if(facing != EnumFacing.DOWN && ModBlocks.BLUE_FIRE.canCatchFire(world, pos.offset(facing), facing.getOpposite()))
                return true;

        //default
        return false;
    }

    //PluginBlockStoneSlab
    @Nonnull
    public static SoundType fixNetherBrickSlabSound(@Nonnull IBlockState state) {
        return state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.NETHERBRICK
                ? ModSounds.NETHER_BRICKS : state.getBlock().getSoundType();
    }

    //PluginEntityLivingBase
    @Nonnull
    public static Pair<IBlockState, BlockPos> updateFallState(@Nonnull World world, @Nonnull IBlockState state, @Nonnull BlockPos pos) {
        final IBlockState upState = world.getBlockState(pos.up());
        return upState.getBlock() instanceof IHasRunningEffects ? Pair.of(upState, pos.up()) : Pair.of(state, pos);
    }

    //PluginEntityPlayer
    public static boolean fixPlayerBlueFireDamageSound(@Nonnull DamageSource source) {
        return source.isFireDamage() || source == BlueFireUtils.IN_BLUE_FIRE || source == BlueFireUtils.ON_BLUE_FIRE;
    }

    //PluginEntityRenderer
    public static int betterNightVision() { return RenderingConfig.nightVisionFlashing(); }

    //PluginItemRenderer
    @SideOnly(Side.CLIENT)
    public static boolean renderBlueFireFirstPerson(boolean ret) {
        if(!ret && BlueFireUtils.getRemaining(Minecraft.getMinecraft().player) > 0)
            BlueFireUtils.renderBlueFireInFirstPerson();

        return ret;
    }

    //PluginRender
    @SideOnly(Side.CLIENT)
    public static float bedrockShadowSize(float shadowSize, double shadowY, double renderY, double entityYDif) {
        if(!RenderingConfig.doBedrockShadowSize()) return shadowSize;
        final double entityY = renderY - entityYDif;
        final double blockY = (shadowY - 0.015625) - (entityYDif + renderY);
        return shadowSize / (float)(1 - renderY - (blockY - entityY));
    }
}
