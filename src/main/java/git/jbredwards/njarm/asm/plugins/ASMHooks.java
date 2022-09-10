package git.jbredwards.njarm.asm.plugins;

import com.google.common.base.Predicate;
import git.jbredwards.njarm.mod.common.block.BlockSnowGrass;
import git.jbredwards.njarm.mod.common.block.BlockUndyingTotem;
import git.jbredwards.njarm.mod.common.block.util.gravity.ICanFallThrough;
import git.jbredwards.njarm.mod.common.block.util.gravity.IFancyFallingBlock;
import git.jbredwards.njarm.mod.common.block.util.IHasWorldState;
import git.jbredwards.njarm.mod.common.capability.IHorseCarrotTime;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.block.BlueFireConfig;
import git.jbredwards.njarm.mod.common.config.client.RenderingConfig;
import git.jbredwards.njarm.mod.common.config.item.ResistantItemsConfig;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import git.jbredwards.njarm.mod.common.item.ItemUndyingTotem;
import git.jbredwards.njarm.mod.common.item.util.IBlueFireWeapon;
import git.jbredwards.njarm.mod.common.util.BlueFireUtils;
import git.jbredwards.njarm.mod.common.block.util.IHasRunningEffects;
import git.jbredwards.njarm.mod.common.util.SoundUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

/**
 * class exists cause SpongeForge
 * @author jbred
 *
 */
@SuppressWarnings("unused")
public final class ASMHooks
{
    //PluginAbstractHorse
    public static boolean isHoldingCarrot(boolean skipCheck, @Nonnull AbstractHorse entity) {
        if(skipCheck) return true;
        else if(!entity.isTame()) return false;

        final @Nullable Entity rider = entity.getControllingPassenger();
        return rider instanceof EntityLivingBase
                && (((EntityLivingBase)rider).getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK
                || ((EntityLivingBase)rider).getHeldItemOffhand().getItem() == Items.CARROT_ON_A_STICK);
    }

    //PluginAbstractHorse
    public static float setForwardSpeed(float forward, @Nonnull AbstractHorse entity) {
        if(isHoldingCarrot(false, entity)) {
            //damage carrot on a stick over time
            if(!entity.world.isRemote) {
                final @Nullable Entity rider = entity.getControllingPassenger();
                if(rider instanceof EntityLivingBase) {
                    final @Nullable IHorseCarrotTime cap = IHorseCarrotTime.get(rider);
                    if(cap != null && cap.decrement() <= 0) {
                        final EntityLivingBase livingRider = (EntityLivingBase)rider;
                        final EnumHand hand = livingRider.getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK
                                ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;

                        livingRider.getHeldItem(hand).damageItem(1, livingRider);
                        if(livingRider.getHeldItem(hand).isEmpty()) livingRider
                                .setHeldItem(hand, new ItemStack(Items.FISHING_ROD));

                        cap.setTime(entity.world.rand.nextInt(100) + 100);
                    }
                }
            }

            //if the rider is using both a saddle & carrot on a stick, increase overall speed
            if(entity.isHorseSaddled()) {
                if(!entity.onGround) return Math.max(forward, 0.5f);
                else {
                    entity.motionX -= MathHelper.sin(entity.rotationYaw * 0.0175f) * 0.075f;
                    entity.motionZ += MathHelper.cos(entity.rotationYaw * 0.0175f) * 0.075f;
                    return Math.max(forward, 0.4f);
                }
            }

            //without a saddle, cap speed
            else return MathHelper.clamp(forward, 0.4f, 0.8f);
        }

        return forward;
    }

    //PluginBlock
    @Nonnull
    public static Vec3d betterWaterFogColor(@Nonnull World world, @Nonnull BlockPos origin, float modifier) {
        final float[] components = new Color(BiomeColorHelper.getColorAtPos(world, origin, (biome, pos) ->
                RenderingConfig.FOG_COLORS.containsKey(biome) ? RenderingConfig.FOG_COLORS.get(biome) : 20827)).getColorComponents(new float[3]);
        return new Vec3d(Math.min(1, components[0] + modifier), Math.min(1, components[1] + modifier), Math.min(1, components[2] + modifier));
    }

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

    //PluginBlockFalling
    public static boolean canFallThrough(@Nonnull IBlockState state) {
        if(state.getBlock() instanceof ICanFallThrough) return ((ICanFallThrough)state.getBlock()).canFallThrough(state);
        else return state.getMaterial().isReplaceable() && !state.isTopSolid();
    }

    //PluginBlockFire
    public static void tryChangeToBlueFire(@Nonnull BlockFire fire, @Nonnull World world, @Nonnull BlockPos pos) {
        if(shouldBeBlueFire(fire, world, pos)) world.setBlockState(pos, ModBlocks.BLUE_FIRE.getDefaultState());
        //run old code
        else if(!fire.canPlaceBlockAt(world, pos)) world.setBlockToAir(pos);
    }

    //PluginBlockFire
    @Nonnull
    public static IBlockState getBlueOrNormal(@Nonnull BlockFire fire, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return shouldBeBlueFire(fire, world, pos) ? ModBlocks.BLUE_FIRE.getDefaultState().withProperty(BlockFire.AGE, state.getValue(BlockFire.AGE)) : state;
    }

    //PluginBlockFire helper
    public static boolean shouldBeBlueFire(@Nonnull BlockFire fire, @Nonnull World world, @Nonnull BlockPos pos) {
        if(fire != Blocks.FIRE) return false; //only turn vanilla fire into blue fire
        else if(ModBlocks.BLUE_FIRE.canCatchFire(world, pos.down(), EnumFacing.UP)) return true;

        //check if normal fire can be placed here
        final IBlockState down = world.getBlockState(pos.down());
        if(down.getBlock().isFireSource(world, pos.down(), EnumFacing.UP) || down.isTopSolid())
            return false;

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

    //PluginBlockGrass
    @Nonnull
    public static Boolean fixGrassSnowyState(boolean old, @Nonnull Block block) {
        return block instanceof BlockSnow || block instanceof BlockSnowBlock;
    }

    //PluginBlockSkull
    @SuppressWarnings("Guava")
    @Nonnull
    public static Predicate<IBlockState> getWitherBaseBlock() {
        return state -> Block.isEqualTo(state.getBlock(), ConfigHandler.blockCfg.soulSoilCfg.useSoilForWither ? ModBlocks.SOUL_SOIL : Blocks.SOUL_SAND);
    }

    //PluginBlockSnow
    public static boolean fallOnto(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState fallingState, @Nonnull IBlockState replacedState) {
        //special case where snow layers can fall on grass to create snow grass
        if(replacedState.getBlock() == Blocks.TALLGRASS && replacedState.getValue(BlockTallGrass.TYPE) == BlockTallGrass.EnumType.GRASS) {
            fallingState = ModBlocks.SNOW_GRASS.getDefaultState().withProperty(BlockSnow.LAYERS, fallingState.getValue(BlockSnow.LAYERS));
            if(world.mayPlace(fallingState.getBlock(), pos, true, EnumFacing.UP, null)) world.setBlockState(pos, fallingState);
            return true;
        }

        //snow layers can only fall onto other snow layers
        else if(!(replacedState.getBlock() instanceof BlockSnow) || (replacedState.getBlock() instanceof ICanFallThrough
                && !((ICanFallThrough)replacedState.getBlock()).canFallThrough(replacedState))) return false;

        final int combinedLevel = replacedState.getValue(BlockSnow.LAYERS) + fallingState.getValue(BlockSnow.LAYERS);
        if(combinedLevel <= 8) world.setBlockState(pos, replacedState.withProperty(BlockSnow.LAYERS, combinedLevel));
        else {
            world.setBlockState(pos, replacedState.withProperty(BlockSnow.LAYERS, 8));
            if(world.mayPlace(replacedState.getBlock(), pos.up(), true, EnumFacing.UP, null))
                world.setBlockState(pos.up(), replacedState.withProperty(BlockSnow.LAYERS, combinedLevel - 8));
        }

        return true;
    }

    //PluginBlockSnow
    @Nonnull
    public static IBlockState getSnowStateForWorld(@Nonnull BlockSnow block, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        //let snow be placed on grass (in a way that should work with most mods, hence why the RightClickBlock event isn't being used)
        if(block == Blocks.SNOW_LAYER) {
            final IBlockState old = world.getBlockState(pos);
            if(old.getBlock() == Blocks.TALLGRASS && old.getValue(BlockTallGrass.TYPE) == BlockTallGrass.EnumType.GRASS)
                return ModBlocks.SNOW_GRASS.getDefaultState().withProperty(BlockSnow.LAYERS, state.getValue(BlockSnow.LAYERS));
        }

        //don't remove the grass part when placed onto a vanilla snow layer
        else if(block instanceof BlockSnowGrass) {
            final IBlockState old = world.getBlockState(pos);
            if(old.getBlock() == Blocks.SNOW_LAYER)
                return block.getDefaultState().withProperty(BlockSnow.LAYERS, state.getValue(BlockSnow.LAYERS));
        }

        return state;
    }

    //PluginBlockStoneSlab
    @Nonnull
    public static SoundType fixNetherBrickSlabSound(@Nonnull IBlockState state) {
        return state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.NETHERBRICK
                ? Blocks.NETHER_BRICK.getSoundType() : state.getBlock().getSoundType();
    }

    //PluginEntityFallingBlock
    public static float getHeightForFallingBlock(@Nonnull IBlockState state) {
        return state.getBlock() instanceof IFancyFallingBlock
                ? ((IFancyFallingBlock)state.getBlock()).getHeightForFallingBlock(state)
                : 0.98f;
    }

    //PluginEntityFallingBlock
    public static float getWidthForFallingBlock(@Nonnull IBlockState state) {
        return state.getBlock() instanceof IFancyFallingBlock
                ? ((IFancyFallingBlock)state.getBlock()).getWidthForFallingBlock(state)
                : 0.98f;
    }

    //PluginEntityItem
    public static boolean isItemImmuneTo(@Nonnull EntityItem entity, @Nonnull DamageSource source) {
        if(entity.isEntityInvulnerable(source)) return true;
        final ItemStack stack = entity.getItem();

        if(ResistantItemsConfig.INVULNERABLE.contains(stack.getItem())) return true;
        else if(source.isFireDamage()) return ResistantItemsConfig.FIRE.contains(stack.getItem());
        else if(source.isExplosion()) return ResistantItemsConfig.EXPLODE.contains(stack.getItem());
        else return false;
    }

    //PluginEntityLightningBolt
    public static void fixLightningFire(@Nonnull EntityLightningBolt bolt, @Nonnull Random rand, boolean isEffect) {
        if(!isEffect && !bolt.world.isRemote
                && (bolt.world.getDifficulty() == EnumDifficulty.NORMAL
                || bolt.world.getDifficulty() == EnumDifficulty.HARD)
                && bolt.world.getGameRules().getBoolean("doFireTick")
                && bolt.world.isAreaLoaded(new BlockPos(bolt), 10)) {
            final BlockPos pos = new BlockPos(bolt);
            if(bolt.world.getBlockState(pos).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(bolt.world, pos))
                bolt.world.setBlockState(pos, Blocks.FIRE.getDefaultState());

            for(int i = 0; i < 4; i++) {
                final BlockPos offset = pos.add(
                        MathHelper.getInt(rand, -1, 1),
                        MathHelper.getInt(rand, -1, 1),
                        MathHelper.getInt(rand, -1, 1));

                if(bolt.world.getBlockState(offset).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(bolt.world, offset))
                    bolt.world.setBlockState(offset, Blocks.FIRE.getDefaultState());
            }
        }
    }

    //PluginEntityLivingBase
    @Nonnull
    public static Pair<IBlockState, BlockPos> updateFallState(@Nonnull World world, @Nonnull IBlockState state, @Nonnull BlockPos pos) {
        final IBlockState upState = world.getBlockState(pos.up());
        return upState.getBlock() instanceof IHasRunningEffects ? Pair.of(upState, pos.up()) : Pair.of(state, pos);
    }

    //PluginEntityMob & PluginEntityPlayer
    public static void updateAttackBlueFire(@Nonnull EntityLivingBase attacker, @Nonnull Entity target) {
        if(!attacker.world.isRemote && attacker.getHeldItemMainhand().getItem() instanceof IBlueFireWeapon && BlueFireUtils.canBeLit(target)) {
            BlueFireUtils.setRemaining(target, 2);
            BlueFireUtils.syncBlueFire(target);
        }
    }

    //PluginEntityPlayer
    public static boolean fixPlayerBlueFireDamageSound(@Nonnull DamageSource source) {
        return source.isFireDamage() || source == BlueFireUtils.IN_BLUE_FIRE || source == BlueFireUtils.ON_BLUE_FIRE;
    }

    //PluginEntityRenderer
    public static int betterNightVision() { return RenderingConfig.nightVisionFlashing(); }

    //PluginItem helper
    @Nonnull public static Supplier<BlockUndyingTotem> TOTEM_BLOCK = () -> null;

    //PluginItem
    @Nonnull
    public static Item genTotemOfUndying(@Nonnull Map<Item, Block> map) {
        final BlockUndyingTotem totemBlock = new BlockUndyingTotem(Material.GLASS, MapColor.GOLD);
        TOTEM_BLOCK = () -> totemBlock;

        final ItemUndyingTotem totem = new ItemUndyingTotem(totemBlock);
        map.put(totem, totemBlock);
        return totem;
    }

    //PluginItemRenderer
    @SideOnly(Side.CLIENT)
    public static boolean renderBlueFireFirstPerson(boolean ret) {
        if(!ret && BlueFireUtils.getRemaining(Minecraft.getMinecraft().player) > 0)
            BlueFireUtils.renderBlueFireInFirstPerson();

        return ret;
    }

    //PluginItemSnow
    @Nonnull
    public static Block fixItemSnowCheck(@Nonnull IBlockState state, @Nonnull Block block) {
        if(block == Blocks.SNOW_LAYER && state.getBlock() instanceof BlockSnowGrass) return block;
        else if(block instanceof BlockSnowGrass && state.getBlock() == Blocks.SNOW_LAYER) return block;
        else return state.getBlock();
    }

    //PluginItemSnow
    @Nonnull
    public static IBlockState fixItemSnowPlacement(@Nonnull Block block, @Nonnull IBlockState state) {
        return block instanceof BlockSnowGrass ? block.getDefaultState() : state;
    }

    //PluginRender
    @SideOnly(Side.CLIENT)
    public static float bedrockShadowSize(float shadowSize, double shadowY, double renderY, double entityYDif) {
        if(!RenderingConfig.doBedrockShadowSize()) return shadowSize;
        final double entityY = renderY - entityYDif;
        final double blockY = (shadowY - 0.015625) - (entityYDif + renderY);
        return shadowSize / (float)(1 - renderY - (blockY - entityY));
    }

    //PluginRender
    @SideOnly(Side.CLIENT)
    public static boolean renderBlueFire(@Nonnull Entity entity, double x, double y, double z) {
        if(BlueFireUtils.getRemaining(entity) > 0 && BlueFireUtils.canBeLit(entity)) {
            if(BlueFireConfig.quarkBlueFireRender()) BlueFireUtils.renderQuarkBlueFireInThirdPerson(entity, x, y, z);
            else BlueFireUtils.renderBlueFireInThirdPerson(entity, x, y, z);
            return false;
        }

        //return old
        return entity.canRenderOnFire() && !(entity instanceof EntityItem
                && isItemImmuneTo((EntityItem)entity, DamageSource.ON_FIRE));
    }

    //PluginRenderManager
    public static boolean correctBlueFireBrightnessForRender(@Nonnull Entity entity) {
        return entity.isBurning() || BlueFireUtils.getRemaining(entity) > 0 && BlueFireUtils.canBeLit(entity);
    }

    //PluginStructureComponent
    @Nonnull
    public static IBlockState getNetherBrickOrCracked(@Nonnull IBlockState defaultState, @Nonnull World world) {
        return defaultState.getBlock() == Blocks.NETHER_BRICK && world.rand.nextFloat() < 0.3f
                ? ModBlocks.CRACKED_NETHER_BRICK.getDefaultState() : defaultState;
    }

    //PluginTileEntityBeacon
    public static void playBeaconAmbientSound(@Nonnull TileEntity te, boolean isComplete) {
        if(te.hasWorld() && !te.getWorld().isRemote && isComplete) SoundUtils.playSound(te, ModSounds.BEACON_AMBIENT, 1, 1);
    }

    //PluginTileEntityBeacon
    public static boolean playBeaconUpdateSound(@Nonnull TileEntity te, boolean isComplete, boolean lastTickComplete) {
        if(te.hasWorld() && !te.getWorld().isRemote && isComplete != lastTickComplete) {
            SoundUtils.playSound(te, isComplete ? ModSounds.BEACON_ACTIVATE : ModSounds.BEACON_DEACTIVATE, 1, 1);
            return isComplete;
        }

        return lastTickComplete;
    }

    //PluginTileEntityBeacon
    public static void playBeaconPowerSelectSound(@Nonnull TileEntity te, boolean isComplete) {
        if(te.hasWorld() && !te.getWorld().isRemote && isComplete) SoundUtils.playSound(te, ModSounds.BEACON_POWER_SELECT, 1, 1);
    }

    //PluginTileEntityEndPortalRenderer
    @SideOnly(Side.CLIENT)
    public static boolean fixEndPortalBottomRender(@Nonnull TileEntityEndPortal te, @Nonnull EnumFacing facing, @Nonnull BufferBuilder buffer, float offset, double x, double y, double z, float r, float g, float b) {
        if(te.shouldRenderFace(facing)) return true;
        else if(te.getClass() == TileEntityEndPortal.class) {
            buffer.pos(x, y + offset, z).color(r, g, b, 1).endVertex();
            buffer.pos(x + 1, y + offset, z).color(r, g, b, 1).endVertex();
            buffer.pos(x + 1, y + offset, z + 1).color(r, g, b, 1).endVertex();
            buffer.pos(x, y + offset, z + 1).color(r, g, b, 1).endVertex();
        }

        return false;
    }

    //PluginWorld
    @Nonnull
    public static IBlockState getStateForWorld(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return state.getBlock() instanceof IHasWorldState ? ((IHasWorldState)state.getBlock()).getStateForWorld(world, pos, state) : state;
    }

    //PluginWorld
    public static boolean fixSnowLayerPlacement(@Nonnull Block snow, @Nonnull World world, @Nonnull BlockPos pos) {
        return snow.canPlaceBlockAt(world, pos) && !canFallThrough(world.getBlockState(pos.down()));
    }

    //PluginWorldGenLakes
    @Nonnull
    public static Block getRandomBlockForSurroundingLava(@Nonnull Random rand, int y) {
        return y < 4 && rand.nextFloat() < 0.4f ? Blocks.MAGMA : Blocks.STONE;
    }

    //PluginBiomeColorHelper
    public static int doAccurateBiomeBlend(int r, int g, int b) {
        final int biomeBlendRadius = RenderingConfig.biomeColorBlendRadius();
        final int a = (biomeBlendRadius * 2 + 1) * (biomeBlendRadius * 2 + 1);
        return (r / a & 255) << 16 | (g / a & 255) << 8 | b / a & 255;
    }

    //MODDED

    //PluginBiomesOPlenty
    @Nonnull
    public static SoundType fixBOPBlockSounds(int grassType) {
        switch(grassType) {
            case 6:
            case 8:
                return Blocks.NETHERRACK.getSoundType();
            case 0:
            case 1:
                return Blocks.STONE.getSoundType();
            default:
                return Blocks.GRASS.getSoundType();
        }
    }
}
