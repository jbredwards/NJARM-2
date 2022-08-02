package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.client.particle.util.IParticleProvider;
import git.jbredwards.njarm.mod.client.particle.util.ParticleUtils;
import git.jbredwards.njarm.mod.common.block.util.IHasDestroyEffects;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
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
public class BlockOvergrown extends BlockOre implements IHasDestroyEffects
{
    @Nullable
    protected IParticleProvider digging, dust;
    protected final byte layers;

    public BlockOvergrown(@Nonnull Material materialIn, @Nonnull BlockRenderLayer... layers) {
        this(materialIn, materialIn.getMaterialMapColor(), layers);
    }

    public BlockOvergrown(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn, @Nonnull BlockRenderLayer... layersIn) {
        super(materialIn, mapColorIn);

        byte layerSum = 0;
        for(BlockRenderLayer layer : layersIn)
            layerSum |= 1 << layer.ordinal();

        layers = layerSum;
    }

    @Override
    public boolean canRenderInLayer(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
        return (layers & (1 << layer.ordinal())) != 0;
    }

    @Nonnull
    public BlockOvergrown setParticles(@Nonnull IParticleProvider diggingIn, @Nonnull IParticleProvider dustIn) {
        digging = diggingIn;
        dust = dustIn;
        return this;
    }

    @Override
    public boolean addLandingEffects(@Nonnull IBlockState state, @Nonnull WorldServer world, @Nonnull BlockPos pos, @Nonnull IBlockState iblockstate, @Nonnull EntityLivingBase entity, int numberOfParticles) {
        return dust != null && ParticleUtils.addLandingEffects(world, entity, numberOfParticles, dust, getStateId(state), -1, -1);
    }

    @Override
    public boolean addRunningEffects(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        return digging != null && ParticleUtils.addRunningParticles(world, entity, digging, getStateId(state), -1, -1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addHitEffects(@Nonnull IBlockState state, @Nonnull World world, @Nonnull RayTraceResult target, @Nonnull ParticleManager manager) {
        return digging != null && ParticleUtils.addHitEffects(state, world, target, manager, digging, getStateId(state), -1, -1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addDestroyEffects(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ParticleManager manager, @Nonnull IBlockState state) {
        return digging != null && ParticleUtils.addDestroyEffects(world, pos, manager, digging, getStateId(state), -1, -1);
    }
}
