package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.particle.util.ParticleProviders;
import git.jbredwards.njarm.mod.common.config.block.LightningRodConfig;
import git.jbredwards.njarm.mod.common.util.AABBUtils;
import net.darkhax.bookshelf.data.Blockstates;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class BlockLightningRod extends Block
{
    @Nonnull protected static final AxisAlignedBB AABB = AABBUtils.of(6, 0, 6, 10, 16, 10);

    public BlockLightningRod(@Nonnull Material materialIn) { this(materialIn, materialIn.getMaterialMapColor()); }
    public BlockLightningRod(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn) {
        super(materialIn, mapColorIn);
        setTickRandomly(true);
        setDefaultState(getDefaultState().withProperty(Blockstates.POWERED, false));
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, Blockstates.POWERED); }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) { return state.getValue(Blockstates.POWERED) ? 1 : 0; }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) { return getDefaultState().withProperty(Blockstates.POWERED, meta > 0); }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return AABB;
    }

    @Nonnull
    @Override
    public BlockFaceShape getBlockFaceShape(@Nonnull IBlockAccess worldIn, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean isFullCube(@Nonnull IBlockState state) { return false; }

    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state) { return false; }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        final boolean isStatePowered = state.getValue(Blockstates.POWERED);
        if(isStatePowered != worldIn.isBlockPowered(pos))
            worldIn.setBlockState(pos, state.withProperty(Blockstates.POWERED, !isStatePowered),
                    net.minecraftforge.common.util.Constants.BlockFlags.SEND_TO_CLIENTS);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);
        if(worldIn.isThundering() && canRedirect(worldIn, pos, stateIn)) {
            final double x, y, z;
            switch(EnumFacing.Axis.values()[rand.nextInt(3)]) {
                case X:
                    x = (rand.nextBoolean() ? 7 : 9) / 15.5;
                    y = MathHelper.getInt(rand, 12, 16) / 15.5;
                    z = MathHelper.getInt(rand, 7, 9) / 15.5;
                    break;
                case Y:
                    x = MathHelper.getInt(rand, 7, 9) / 15.5;
                    y = 16 / 15.5;
                    z = MathHelper.getInt(rand, 7, 9) / 15.5;
                    break;
                default:
                    x = MathHelper.getInt(rand, 7, 9) / 15.5;
                    y = MathHelper.getInt(rand, 12, 16) / 15.5;
                    z = (rand.nextBoolean() ? 7 : 9) / 15.5;
            }

            ParticleProviders.ELECTRIC_SPARK.spawnClient(worldIn, pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0, 0, 0);
        }
    }

    @Override
    public void randomTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random random) {
        if(!worldIn.isRemote && worldIn.isThundering() && canRedirect(worldIn, pos, state))
            worldIn.addWeatherEffect(new EntityLightningBolt(worldIn, pos.getX(), pos.getY(), pos.getZ(), false));
    }

    public boolean canRedirect(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return !state.getValue(Blockstates.POWERED) && worldIn.canSeeSky(pos);
    }

    @SubscribeEvent
    public static void redirectLightning(@Nonnull EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof EntityLightningBolt && !event.getWorld().isRemote) {
            final EntityLightningBolt bolt = (EntityLightningBolt)event.getEntity();
            final World world = event.getWorld();
            final List<BlockPos> positions = new ArrayList<>();
            for(int x = -LightningRodConfig.radius(); x <= LightningRodConfig.radius(); x++)
                for(int y = -LightningRodConfig.radius(); y <= LightningRodConfig.radius(); y++)
                    for(int z = -LightningRodConfig.radius(); z <= LightningRodConfig.radius(); z++)
                        positions.add(bolt.getPosition().add(x, y, z));

            Collections.shuffle(positions, world.rand);
            for(BlockPos pos : positions) {
                if(world.isBlockLoaded(pos)) {
                    final IBlockState state = world.getBlockState(pos);
                    if(state.getBlock() instanceof BlockLightningRod && ((BlockLightningRod)state.getBlock()).canRedirect(world, pos, state)) {
                        bolt.setPosition(pos.getX(), pos.getY(), pos.getZ());
                        return;
                    }
                }
            }
        }
    }
}
