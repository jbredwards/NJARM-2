package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.client.particle.util.ParticleProviders;
import git.jbredwards.njarm.mod.client.particle.util.ParticleUtils;
import git.jbredwards.njarm.mod.common.block.util.IEmissiveBlock;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.block.MagicOreConfig;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class BlockMagicOre extends BlockOre implements IEmissiveBlock
{
    public final boolean isLit;

    public BlockMagicOre(@Nonnull Material materialIn, boolean isLit) { this(materialIn, materialIn.getMaterialMapColor(), isLit); }
    public BlockMagicOre(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn, boolean isLit) {
        super(materialIn, mapColorIn);
        this.isLit = isLit;
        setTickRandomly(isLit);
    }

    @Nonnull
    public IBlockState getLit() { return ModBlocks.LIT_MAGIC_ORE.getDefaultState(); }

    @Nonnull
    public IBlockState getUnLit() { return ModBlocks.MAGIC_ORE.getDefaultState(); }

    @Nonnull
    @Override
    protected ItemStack getSilkTouchDrop(@Nonnull IBlockState state) { return new ItemStack(ModItems.MAGIC_ORE); }

    @Override
    public boolean isStickyBlock(@Nonnull IBlockState state) { return ConfigHandler.blockCfg.magicOreCfg.isSticky; }

    @Override
    public float getEnchantPowerBonus(@Nonnull World world, @Nonnull BlockPos pos) { return ConfigHandler.blockCfg.magicOreCfg.enchantPowerBonus; }

    @Override
    public boolean canEntityDestroy(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        return !(entity instanceof EntityDragon) && super.canEntityDestroy(state, world, pos, entity);
    }

    //==================================================================
    //mimic behavior of redstone ore (light up when clicked for example)
    //==================================================================

    @Override
    public int tickRate(@Nonnull World worldIn) { return 30; }

    @Override
    public void onBlockClicked(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EntityPlayer playerIn) {
        activate(worldIn, pos);
        super.onBlockClicked(worldIn, pos, playerIn);
    }

    @Override
    public void onEntityWalk(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Entity entityIn) {
        activate(worldIn, pos);
        super.onEntityWalk(worldIn, pos, entityIn);
        if(!worldIn.isRemote && entityIn instanceof EntityLivingBase && ConfigHandler.blockCfg.magicOreCfg.levitation)
            ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 60, 0, false, false));
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        activate(worldIn, pos);
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        if(!worldIn.isRemote && isLit) worldIn.setBlockState(pos, getUnLit());
    }

    public void activate(@Nonnull World world, @Nonnull BlockPos pos) {
        if(world.isRemote) spawnParticles(world, pos);
        else if(!isLit) world.setBlockState(pos, getLit());
    }

    @SideOnly(Side.CLIENT)
    protected void spawnParticles(@Nonnull World world, @Nonnull BlockPos pos) {
        ParticleUtils.spawnRedstoneParticles(world, pos, (x, y, z) -> {
            if(MagicOreConfig.emissive())
                ParticleProviders.LIT_REDSTONE.spawnClient(world, x, y, z, 252f/255, 157f/255, 250f/255);
            else world.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 252f/255, 157f/255, 250f/255);
        });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if(isLit) spawnParticles(worldIn, pos);
    }

    //===================================
    //spawn multi-layered block particles
    //===================================

    @Override
    public boolean addLandingEffects(@Nonnull IBlockState state, @Nonnull WorldServer world, @Nonnull BlockPos pos, @Nonnull IBlockState iblockstate, @Nonnull EntityLivingBase entity, int numberOfParticles) {
        return isLit && MagicOreConfig.emissive() && ParticleUtils.addLandingEffects(world, entity, numberOfParticles, ParticleProviders.MULTI_LAYER_BLOCK_DUST, getStateId(state));
    }

    @Override
    public boolean addRunningEffects(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        return isLit && MagicOreConfig.emissive() && ParticleUtils.addRunningParticles(world, entity, ParticleProviders.MULTI_LAYER_DIGGING, getStateId(state));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addHitEffects(@Nonnull IBlockState state, @Nonnull World world, @Nonnull RayTraceResult target, @Nonnull ParticleManager manager) {
        return isLit && MagicOreConfig.emissive() && ParticleUtils.addHitEffects(state, world, target, manager, ParticleProviders.MULTI_LAYER_DIGGING, getStateId(state));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addDestroyEffects(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ParticleManager manager, @Nonnull IBlockState state) {
        return isLit && MagicOreConfig.emissive() && ParticleUtils.addDestroyEffects(world, pos, manager, ParticleProviders.MULTI_LAYER_DIGGING, getStateId(state));
    }

    //========================================
    //light only the cutout layer of the block
    //========================================

    @SideOnly(Side.CLIENT)
    @Override
    public int getPackedLightmapCoords(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return isLightEmissive(state) ? 240 : super.getPackedLightmapCoords(state, source, pos);
    }

    @Override
    public int getLightValue(@Nonnull IBlockState state) {
        if(isLit && !MagicOreConfig.emissive()) return 9;
        return isLightEmissive(state) ? 1 : 0;
    }

    @Override
    public boolean canRenderInLayer(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || isLit && MagicOreConfig.emissive() && layer == BlockRenderLayer.CUTOUT;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isEmissive(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
        return isLit && MagicOreConfig.emissive() && layer == BlockRenderLayer.CUTOUT;
    }
}
