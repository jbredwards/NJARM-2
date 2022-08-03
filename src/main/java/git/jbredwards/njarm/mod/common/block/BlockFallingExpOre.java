package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.client.particle.util.ParticleProviders;
import git.jbredwards.njarm.mod.client.particle.util.ParticleUtils;
import git.jbredwards.njarm.mod.common.block.util.IEmissiveBlock;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
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
public class BlockFallingExpOre extends BlockFallingOre implements IEmissiveBlock
{
    public BlockFallingExpOre(@Nonnull Material materialIn) { super(materialIn); }
    public BlockFallingExpOre(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn) { super(materialIn, mapColorIn); }

    @Override
    public int quantityDroppedWithBonus(int fortune, @Nonnull Random random) {
        return random.nextInt(10 - Math.min(fortune, 3) * 3) == 0 ? 1 : 0;
    }

    @Override
    public int quantityDropped(@Nonnull Random random) { return random.nextInt(10) == 0 ? 1 : 0; }

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
}
