package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.fluidlogged_api.api.block.IFluidloggable;
import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.util.SoundUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class BlockUndyingTotem extends BlockHorizontal implements IFluidloggable
{
    @Nonnull public static final AxisAlignedBB X = new AxisAlignedBB(0.4375, 0, 0.375, 0.5625, 0.875, 0.625);
    @Nonnull public static final AxisAlignedBB Z = new AxisAlignedBB(0.375, 0, 0.4375, 0.625, 0.875, 0.5625);

    public BlockUndyingTotem(@Nonnull Material materialIn) { this(materialIn, materialIn.getMaterialMapColor()); }
    public BlockUndyingTotem(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn) {
        super(materialIn, mapColorIn);
        EntityEnderman.setCarriable(this, true);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, FACING); }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) { return state.getValue(FACING).getHorizontalIndex(); }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Nonnull
    @Override
    public IBlockState getStateForPlacement(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @Nonnull EntityLivingBase placer) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

    @Nonnull
    @Override
    public IBlockState withRotation(@Nonnull IBlockState state, @Nonnull Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Nonnull
    @Override
    public IBlockState withMirror(@Nonnull IBlockState state, @Nonnull Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state) { return false; }

    @Override
    public boolean isFullCube(@Nonnull IBlockState state) { return false; }

    @Nonnull
    @Override
    public String getTranslationKey() { return "item.totem"; }

    @Nonnull
    @Override
    public CreativeTabs getCreativeTab() { return CreativeTabs.COMBAT; }

    @Nonnull
    @Override
    public BlockFaceShape getBlockFaceShape(@Nonnull IBlockAccess worldIn, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return Items.TOTEM_OF_UNDYING;
    }

    @Nonnull
    @Override
    public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return getSilkTouchDrop(state);
    }

    @Nonnull
    @Override
    protected ItemStack getSilkTouchDrop(@Nonnull IBlockState state) {
        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return state.getValue(FACING).getAxis() == EnumFacing.Axis.X ? X : Z;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        //particles
        if(Minecraft.getMinecraft().gameSettings.fancyGraphics) {
            final int radius = ConfigHandler.blockCfg.totemOfUndyingCfg.particleRadius;
            final double chance = ConfigHandler.blockCfg.totemOfUndyingCfg.particleFrequency;
            for(int x = -radius; x <= radius; x++) {
                for(int y = -radius; y <= radius; y++) {
                    for(int z = -radius; z <= radius; z++) {
                        if(rand.nextDouble() < chance) {
                            float posX = pos.getX() + x + rand.nextFloat();
                            float posY = pos.getY() + y + rand.nextFloat();
                            float posZ = pos.getZ() + z + rand.nextFloat();
                            worldIn.spawnParticle(EnumParticleTypes.TOTEM, posX, posY, posZ, 0, 0, 0);
                        }
                    }
                }
            }
        }
    }

    //resurrect entities that dye within the aoe of a totem of undying
    @SubscribeEvent
    public static void onEntityDeath(@Nonnull LivingHurtEvent event) {
        final EntityLivingBase entity = event.getEntityLiving();
        final boolean isPet = ConfigHandler.blockCfg.totemOfUndyingCfg.resurrectPets && entity instanceof IEntityOwnable && ((IEntityOwnable)entity).getOwner() instanceof EntityPlayer;
        if((isPet || entity instanceof EntityPlayer || entity instanceof IMerchant) && entity.getHealth() - event.getAmount() <= 0) {
            final BlockPos pos = findTotem(entity.world, new BlockPos(entity));
            if(pos != null) {
                SoundUtils.playSound(entity.world, pos, SoundEvents.ITEM_TOTEM_USE, SoundCategory.BLOCKS, 1, 1);
                performRes(entity.world, new Vec3d(pos), entity, Items.TOTEM_OF_UNDYING, true);
                entity.world.destroyBlock(pos, false);
                event.setCanceled(true);
            }
        }
        //check for endermen holding the block
        else if(entity instanceof EntityEnderman && entity.getHealth() - event.getAmount() <= 0) {
            final @Nullable IBlockState heldState = ((EntityEnderman)entity).getHeldBlockState();
            if(heldState != null && heldState.getBlock() == ModBlocks.TOTEM_OF_UNDYING) {
                SoundUtils.playSound(entity, SoundEvents.ITEM_TOTEM_USE, 1, 1);
                performRes(entity.world, new Vec3d(entity.posX, entity.posY, entity.posZ), entity, Items.TOTEM_OF_UNDYING, true);
                ((EntityEnderman)entity).setHeldBlockState(null);
                event.setCanceled(true);
            }
        }
    }

    //does the totem of undying effects
    public static void performRes(@Nonnull World world, @Nonnull Vec3d pos, @Nonnull EntityLivingBase entity, @Nonnull Item item, boolean particle) {
        if(!world.isRemote && world instanceof WorldServer) {
            entity.setHealth(1);
            entity.clearActivePotions();
            entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
            entity.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));

            if(particle) ((WorldServer)world).spawnParticle(EnumParticleTypes.TOTEM, pos.x, pos.y, pos.z, 100, 0, 0, 0, 0.5);
            if(entity instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP)entity;
                player.addStat(Objects.requireNonNull(StatList.getObjectUseStats(item)));
                CriteriaTriggers.USED_TOTEM.trigger(player, new ItemStack(item));
            }

            world.setEntityState(entity, (byte)35);
        }
    }

    //returns the nearest valid undying totem, null if none are found
    @Nullable
    public static BlockPos findTotem(@Nonnull IBlockAccess world, @Nonnull BlockPos origin) {
        final int radius = ConfigHandler.blockCfg.totemOfUndyingCfg.radius;
        for(int x = -radius; x <= radius; x++) {
            for(int y = -radius; y <= radius; y++) {
                for(int z = -radius; z <= radius; z++) {
                    BlockPos pos = origin.add(x, y, z);
                    if(world.getBlockState(pos).getBlock() instanceof BlockUndyingTotem)
                        return pos;
                }
            }
        }

        //none found
        return null;
    }

    //draws the aoe selection box around the totem
    private static float scale = 0;
    private static BlockPos prevPos = null;
    @SuppressWarnings("ConstantConditions")
    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onSelectBox(@Nonnull DrawBlockHighlightEvent event) {
        if(ConfigHandler.blockCfg.totemOfUndyingCfg.showAOEAABB) {
            final @Nullable RayTraceResult result = event.getTarget();
            final @Nullable EntityPlayer player = event.getPlayer();
            //so much null
            if(!event.isCanceled() && player != null && player.isSneaking() && result != null && result.getBlockPos() != null) {
                final BlockPos pos = result.getBlockPos();
                final IBlockState state = player.world.getBlockState(pos);

                //selected block is a totem
                if(state.getBlock() == ModBlocks.TOTEM_OF_UNDYING) {
                    if(!pos.equals(prevPos)) scale = 0;
                    scale = Math.min(scale + 0.01f, 1);
                    prevPos = pos;
                    //render offset
                    final double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
                    final double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
                    final double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();
                    //open gl start
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GlStateManager.glLineWidth(2);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    //totem aabb
                    RenderGlobal.drawSelectionBoundingBox(state.getSelectedBoundingBox(player.world, pos).grow(0.002).offset(-x, -y, -z), 0, 0, 0, 0.4f);
                    //aoe aabb
                    final float radius = ConfigHandler.blockCfg.totemOfUndyingCfg.radius * scale;
                    final AxisAlignedBB aoe = new AxisAlignedBB(new Vec3d(pos).subtract(radius, radius, radius), new Vec3d(pos).add(radius, radius, radius)).grow(0.002).offset(-x + 0.5, -y + 0.5, -z + 0.5);
                    RenderGlobal.renderFilledBox(aoe, 33f/255, 164f/255, 12f/255, 0.2f);
                    RenderGlobal.drawSelectionBoundingBox(aoe, 184f/255, 208f/255, 25f/255, 0.4f);
                    //open gl end
                    GlStateManager.depthMask(true);
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    //boxes were drawn, cancel the event
                    event.setCanceled(true);
                }
                else scale = 0;
            }
            else scale = 0;
        }
    }
}
