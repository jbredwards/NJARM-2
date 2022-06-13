package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.Main;
import git.jbredwards.njarm.mod.client.particle.util.ParticleProviders;
import git.jbredwards.njarm.mod.client.particle.util.ParticleUtils;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import net.minecraft.block.Block;
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
public class BlockExperienceOre extends BlockOre
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
        return getDefaultState().withProperty(TYPE, Type.values()[meta < Type.values().length ? meta : 0]);
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
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, @Nonnull NonNullList<ItemStack> items) {
        for(int meta = 0; meta < Type.values().length; meta++) items.add(new ItemStack(this, 1, meta));
    }

    //===================================
    //spawn multi-layered block particles
    //===================================

    @Override
    public boolean addLandingEffects(@Nonnull IBlockState state, @Nonnull WorldServer world, @Nonnull BlockPos pos, @Nonnull IBlockState iblockstate, @Nonnull EntityLivingBase entity, int numberOfParticles) {
        switch(state.getValue(TYPE)) {
            case OVERWORLD:
                return ParticleUtils.addLandingEffects(world, entity, numberOfParticles, ParticleProviders.XP_ORE_BLOCK_DUST, Block.getStateId(state), -1, 240);
            case NETHER:
                return ParticleUtils.addLandingEffects(world, entity, numberOfParticles, ParticleProviders.NETHER_XP_ORE_BLOCK_DUST, Block.getStateId(state), -1, 240);
            case END:
                return ParticleUtils.addLandingEffects(world, entity, numberOfParticles, ParticleProviders.END_XP_ORE_BLOCK_DUST, Block.getStateId(state), -1, 240);
        }

        return ParticleUtils.addLandingEffects(world, entity, numberOfParticles, ParticleProviders.XP_ORE_BLOCK_DUST, Block.getStateId(state), -1, 240);
    }

    @Override
    public boolean addRunningEffects(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        switch(state.getValue(TYPE)) {
            case OVERWORLD:
                return ParticleUtils.addRunningParticles(world, entity, ParticleProviders.XP_ORE_DIGGING, Block.getStateId(state), -1, 240);
            case NETHER:
                return ParticleUtils.addRunningParticles(world, entity, ParticleProviders.NETHER_XP_ORE_DIGGING, Block.getStateId(state), -1, 240);
            case END:
                return ParticleUtils.addRunningParticles(world, entity, ParticleProviders.END_XP_ORE_DIGGING, Block.getStateId(state), -1, 240);
        }

        return ParticleUtils.addRunningParticles(world, entity, ParticleProviders.XP_ORE_DIGGING, Block.getStateId(state), -1, 240);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addHitEffects(@Nonnull IBlockState state, @Nonnull World world, @Nonnull RayTraceResult target, @Nonnull ParticleManager manager) {
        switch(state.getValue(TYPE)) {
            case OVERWORLD:
                return ParticleUtils.addHitEffects(state, world, target, manager, ParticleProviders.XP_ORE_DIGGING, Block.getStateId(state), -1, 240);
            case NETHER:
                return ParticleUtils.addHitEffects(state, world, target, manager, ParticleProviders.NETHER_XP_ORE_DIGGING, Block.getStateId(state), -1, 240);
            case END:
                return ParticleUtils.addHitEffects(state, world, target, manager, ParticleProviders.END_XP_ORE_DIGGING, Block.getStateId(state), -1, 240);
        }

        return ParticleUtils.addHitEffects(state, world, target, manager, ParticleProviders.XP_ORE_DIGGING, Block.getStateId(state), -1, 240);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addDestroyEffects(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ParticleManager manager) {
        final IBlockState state = world.getBlockState(pos);
        switch(state.getValue(TYPE)) {
            case OVERWORLD:
                return ParticleUtils.addDestroyEffects(world, pos, manager, ParticleProviders.XP_ORE_DIGGING, Block.getStateId(state), -1, 240);
            case NETHER:
                return ParticleUtils.addDestroyEffects(world, pos, manager, ParticleProviders.NETHER_XP_ORE_DIGGING, Block.getStateId(state), -1, 240);
            case END:
                return ParticleUtils.addDestroyEffects(world, pos, manager, ParticleProviders.END_XP_ORE_DIGGING, Block.getStateId(state), -1, 240);
        }

        return ParticleUtils.addDestroyEffects(world, pos, manager, ParticleProviders.XP_ORE_DIGGING, Block.getStateId(state), -1, 240);
    }

    //========================================
    //light only the cutout layer of the block
    //========================================

    @SideOnly(Side.CLIENT)
    @Override
    public int getPackedLightmapCoords(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return Main.proxy.getLightForGlowingOre() ? 240 : super.getPackedLightmapCoords(state, source, pos);
    }

    @Override
    public int getLightValue(@Nonnull IBlockState state) { return Main.proxy.getLightForGlowingOre() ? 1 : 0; }

    @Override
    public boolean canRenderInLayer(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT;
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
