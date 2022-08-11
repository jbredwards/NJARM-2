package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.fluidlogged_api.api.block.IFluidloggable;
import git.jbredwards.fluidlogged_api.api.util.FluidloggedUtils;
import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.particle.ParticleBubbleColumn;
import git.jbredwards.njarm.mod.common.capability.IBubbleColumn;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import git.jbredwards.njarm.mod.common.util.SoundUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.darkhax.bookshelf.item.ICustomModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class BlockBubbleColumn extends Block implements IFluidloggable, ICustomModel
{
    @Nonnull public static final PropertyEnum<Drag> DRAG = PropertyEnum.create("drag", Drag.class);

    public BlockBubbleColumn(@Nonnull Material materialIn) { this(materialIn, materialIn.getMaterialMapColor()); }
    public BlockBubbleColumn(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn) {
        super(materialIn, mapColorIn);
        setDefaultState(getDefaultState().withProperty(DRAG, Drag.DOWN));
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, DRAG); }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) { return state.getValue(DRAG).ordinal(); }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(DRAG, Drag.values()[meta % Drag.values().length]);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerMeshModels() { ModelLoader.setCustomStateMapper(this, block -> new HashMap<>()); }

    @Nonnull
    @Override
    public EnumBlockRenderType getRenderType(@Nonnull IBlockState state) { return EnumBlockRenderType.INVISIBLE; }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(@Nonnull IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public int quantityDropped(@Nonnull IBlockState state, int fortune, @Nonnull Random random) { return 0; }

    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state) { return false; }

    @Override
    public boolean isFullCube(@Nonnull IBlockState state) { return false; }

    @Override
    public boolean canCollideCheck(@Nonnull IBlockState state, boolean hitIfLiquid) { return hitIfLiquid; }

    @Override //shouldn't be called, but exists just in case
    public int getLightOpacity(@Nonnull IBlockState state) { return Blocks.WATER.getDefaultState().getLightOpacity(); }

    @Nonnull
    @Override
    public EnumPushReaction getPushReaction(@Nonnull IBlockState state) { return EnumPushReaction.DESTROY; }

    @Nonnull
    @Override
    public BlockFaceShape getBlockFaceShape(@Nonnull IBlockAccess worldIn, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean isFluidValid(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Fluid fluid) {
        //use water material rather than water compatibility here, since that's what bubble particles use
        return fluid.canBePlacedInWorld() && fluid.getBlock().getDefaultState().getMaterial() == Material.WATER;
    }

    @Nonnull
    @Override
    public EnumActionResult onFluidDrain(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState here, int blockFlags) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), blockFlags);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void onBlockAdded(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) { worldIn.scheduleUpdate(pos, this, 1); }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        final IBlockState downState = worldIn.getBlockState(pos.down());
        final Drag drag = state.getValue(DRAG);

        if(drag.hasBlock(downState) || downState.getBlock() == this && drag == downState.getValue(DRAG))
            spreadToUp(worldIn, pos, state);

        else if(drag.getOpposite().hasBlock(downState) || downState.getBlock() == this && drag == downState.getValue(DRAG).getOpposite())
            worldIn.setBlockState(pos, state.withProperty(DRAG, drag.getOpposite()), 2);

        else worldIn.setBlockToAir(pos);
    }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos) {
        if(fromPos.equals(pos.up()) || fromPos.equals(pos.down())) worldIn.scheduleUpdate(pos, this, 1);
    }

    public void spreadToUp(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        final IBlockState upState = worldIn.getBlockState(pos.up());
        final @Nullable Fluid fluid = FluidloggedUtils.getFluidFromState(upState);
        if(fluid != null && isFluidValid(state, worldIn, pos.up(), fluid) && FluidloggedUtils.isFluidloggableFluid(upState, worldIn, pos.up()))
            worldIn.setBlockState(pos.up(), state, 2);
    }

    @Override
    public void onEntityCollision(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Entity entityIn) {
        final Drag drag = state.getValue(DRAG);
        if(drag == Drag.DOWN) entityIn.motionY = Math.max(-0.3, entityIn.motionY - 0.03);
        else {
            final IBlockState upState = worldIn.getBlockState(pos.up());
            if(upState.getBlock().isAir(upState, worldIn, pos.up()))
                entityIn.motionY = Math.min(1.8, entityIn.motionY + 0.1);
            else entityIn.motionY = Math.min(0.7, entityIn.motionY + 0.06);
        }

        if(!worldIn.isRemote && entityIn instanceof EntityLivingBase) {
            if(entityIn.isInsideOfMaterial(Material.WATER)) entityIn.setAir(300);
            //checks if the player was in a bubble column last tick
            final @Nullable IBubbleColumn cap = IBubbleColumn.get(entityIn);
            if(cap != null && !cap.isInBubbleColumn()) {
                cap.setInBubbleColumn(true);
                SoundUtils.playSound(worldIn, pos, drag == Drag.DOWN
                        ? ModSounds.BUBBLE_COLUMN_DOWN_INSIDE
                        : ModSounds.BUBBLE_COLUMN_UP_INSIDE,
                        SoundCategory.BLOCKS, 0.5f, 1.0f);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        final Drag drag = stateIn.getValue(DRAG);
        //down particles
        if(drag == Drag.DOWN) {
            Minecraft.getMinecraft().effectRenderer.addEffect(
                    new ParticleBubbleColumn(worldIn, pos.getX() + 0.5, pos.getY() + rand.nextFloat() - 0.125, pos.getZ() + 0.5, 12, -1.0 / 30, 0.4, rand.nextInt(360)));
        }
        //up particles
        else {
            worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0, 0.3, 0.0);
            worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 0.0, 0.3, 0.0);
        }
        //ambient sound effect
        if(rand.nextInt(200) == 0)
            SoundUtils.playSound(worldIn, pos, drag == Drag.DOWN
                    ? ModSounds.BUBBLE_COLUMN_DOWN_AMBIENT
                    : ModSounds.BUBBLE_COLUMN_UP_AMBIENT,
                    SoundCategory.BLOCKS, 0.2f + rand.nextFloat() * 0.2f,
                    0.9f + rand.nextFloat() * 0.15f);
    }

    @SubscribeEvent
    public static void updateIsInBubbleColumn(@Nonnull LivingEvent.LivingUpdateEvent event) {
        final EntityLivingBase entity = event.getEntityLiving();
        final @Nullable IBubbleColumn cap = IBubbleColumn.get(entity);
        if(cap != null && cap.isInBubbleColumn()) cap.setInBubbleColumn(entity.world.isMaterialInBB(
                entity.getEntityBoundingBox(), ModBlocks.BUBBLE_COLUMN_MATERIAL));
    }

    @SubscribeEvent
    public static void updateMagmaAndSoulSand(@Nonnull BlockEvent.NeighborNotifyEvent event) {
        if(event.getNotifiedSides().contains(EnumFacing.UP)) {
            final boolean isMagma = Drag.DOWN.hasBlock(event.getState());
            if(isMagma || Drag.UP.hasBlock(event.getState())) {
                ModBlocks.BUBBLE_COLUMN.spreadToUp(event.getWorld(), event.getPos(), ModBlocks.BUBBLE_COLUMN.getDefaultState()
                        .withProperty(DRAG, isMagma ? Drag.DOWN : Drag.UP));
                return;
            }
        }

        if(event.getNotifiedSides().contains(EnumFacing.DOWN)) {
            final IBlockState down = event.getWorld().getBlockState(event.getPos().down());
            final boolean isMagma = Drag.DOWN.hasBlock(down);

            if(isMagma || Drag.UP.hasBlock(down))
                ModBlocks.BUBBLE_COLUMN.spreadToUp(event.getWorld(), event.getPos().down(), ModBlocks.BUBBLE_COLUMN.getDefaultState()
                        .withProperty(DRAG, isMagma ? Drag.DOWN : Drag.UP));
        }
    }

    public enum Drag implements IStringSerializable
    {
        @Nonnull DOWN("down", 1),
        @Nonnull UP("up", 0);

        @Nonnull public final Map<Block, IntList> blocks = new HashMap<>();
        @Nonnull public final String name;
        public final int opposite;

        Drag(@Nonnull String name, int opposite) {
            this.name = name;
            this.opposite = opposite;
        }

        public boolean hasBlock(@Nonnull IBlockState state) {
            final @Nullable IntList meta = blocks.get(state.getBlock());
            return meta != null && (meta.isEmpty() || meta.contains(state.getBlock().getMetaFromState(state)));
        }

        public void addBlock(@Nonnull Block block, @Nonnull int[] meta) {
            blocks.put(block, IntArrayList.wrap(meta));
            //remove duplicate opposing entries
            final @Nullable IntList oppositeMeta = getOpposite().blocks.get(block);
            if(oppositeMeta != null) {
                if(oppositeMeta.isEmpty()) getOpposite().blocks.remove(block);
                else for(int i : meta) if(oppositeMeta.rem(i) && oppositeMeta.isEmpty()) {
                    getOpposite().blocks.remove(block);
                    break;
                }
            }
        }

        @Nonnull
        @Override
        public String getName() { return name; }

        @Nonnull
        public Drag getOpposite() { return values()[opposite]; }
    }
}
