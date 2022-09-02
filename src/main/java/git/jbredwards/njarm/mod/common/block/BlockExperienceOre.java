package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.client.particle.util.ParticleProviders;
import git.jbredwards.njarm.mod.client.particle.util.ParticleUtils;
import git.jbredwards.njarm.mod.common.block.util.IEmissiveBlock;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import git.jbredwards.njarm.mod.common.util.ArrayUtils;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public class BlockExperienceOre extends BlockOre implements IEmissiveBlock
{
    @Nonnull
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockExperienceOre(@Nonnull Material materialIn) { this(materialIn, materialIn.getMaterialMapColor()); }
    public BlockExperienceOre(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn) {
        super(materialIn, mapColorIn);
        setDefaultState(getDefaultState().withProperty(TYPE, Type.OVERWORLD));
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, TYPE); }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) { return state.getValue(TYPE).ordinal(); }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, ArrayUtils.getSafe(Type.values(), meta));
    }

    @Nonnull
    @Override
    public SoundType getSoundType(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nullable Entity entity) {
        return state.getValue(TYPE) == Type.NETHER ? ModSounds.NETHER_ORE : super.getSoundType(state, world, pos, entity);
    }

    @Nonnull
    @Override
    public MapColor getMapColor(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        final Type type = state.getValue(TYPE);
        switch(type) {
            case OVERWORLD: return Blocks.STONE.getDefaultState().getMapColor(worldIn, pos);
            case NETHER: return Blocks.NETHERRACK.getDefaultState().getMapColor(worldIn, pos);
            case END: return Blocks.END_STONE.getDefaultState().getMapColor(worldIn, pos);
        }

        return super.getMapColor(state, worldIn, pos);
    }

    @Override
    public boolean canEntityDestroy(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        return !(entity instanceof EntityDragon && state.getValue(TYPE) == Type.END) && super.canEntityDestroy(state, world, pos, entity);
    }

    @Override
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, @Nonnull NonNullList<ItemStack> items) {
        for(int meta = 0; meta < Type.values().length; meta++) items.add(new ItemStack(this, 1, meta));
    }

    @Override
    public boolean addLandingEffects(@Nonnull IBlockState state, @Nonnull WorldServer world, @Nonnull BlockPos pos, @Nonnull IBlockState iblockstate, @Nonnull EntityLivingBase entity, int numberOfParticles) {
        return ParticleUtils.addLandingEffects(world, entity, numberOfParticles, ParticleProviders.MULTI_LAYER_BLOCK_DUST, getStateId(state));
    }

    @Override
    public boolean addRunningEffects(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        return ParticleUtils.addRunningParticles(world, entity, ParticleProviders.MULTI_LAYER_DIGGING, getStateId(state));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addHitEffects(@Nonnull IBlockState state, @Nonnull World world, @Nonnull RayTraceResult target, @Nonnull ParticleManager manager) {
        return ParticleUtils.addHitEffects(state, world, target, manager, ParticleProviders.MULTI_LAYER_DIGGING, getStateId(state));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addDestroyEffects(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ParticleManager manager, @Nonnull IBlockState state) {
        return ParticleUtils.addDestroyEffects(world, pos, manager, ParticleProviders.MULTI_LAYER_DIGGING, getStateId(state));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getPackedLightmapCoords(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return isLightEmissive(state) ? 240 : super.getPackedLightmapCoords(state, source, pos);
    }

    @Override
    public int getLightValue(@Nonnull IBlockState state) { return isLightEmissive(state) ? 1 : 0; }

    @Override
    public boolean canRenderInLayer(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isEmissive(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
        return layer == BlockRenderLayer.CUTOUT;
    }

    public enum Type implements IStringSerializable
    {
        OVERWORLD("overworld"),
        NETHER("nether"),
        END("end");

        @Nonnull private final String name;

        Type(@Nonnull String name) { this.name = name; }

        @Nonnull
        @Override
        public String getName() { return name; }
    }
}
