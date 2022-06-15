package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.client.particle.util.ParticleUtils;
import git.jbredwards.njarm.mod.common.config.block.FragileIceConfig;
import git.jbredwards.njarm.mod.common.util.ArrayUtils;
import net.minecraft.block.BlockIce;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class BlockFragileIce extends BlockIce
{
    @Nonnull
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockFragileIce() {
        setTickRandomly(false);
        setDefaultState(getDefaultState().withProperty(TYPE, Type.FULL));
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

    @Override
    public void onBlockAdded(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if(FragileIceConfig.getMeltingTicks() > 0) worldIn.scheduleUpdate(pos, this, FragileIceConfig.getMeltingTicks());
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        worldIn.scheduleUpdate(pos, this, FragileIceConfig.getMeltingTicks());

        final int stage = state.getValue(TYPE).ordinal();

        if(stage < 3) {
            worldIn.playSound(null, pos, getSoundType(state, worldIn, pos, null).getHitSound(), SoundCategory.BLOCKS, 0.6f, 1.2f);
            worldIn.setBlockState(pos, getStateFromMeta(stage + 1));
        }
        else {
            turnIntoWater(worldIn, pos);
            worldIn.playEvent(2001, pos, getStateId(state));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if(rand.nextInt((4 - state.getValue(TYPE).ordinal()) << 1) == 0) ParticleUtils.spawnRedstoneParticles(worldIn, pos,
                (x, y, z) -> worldIn.spawnParticle(EnumParticleTypes.WATER_DROP, x, y, z, 0, 0, 0));
    }

    @Override
    public void turnIntoWater(@Nonnull World worldIn, @Nonnull BlockPos pos) { worldIn.setBlockToAir(pos); }

    //undo ice-to-water
    @Override
    public void harvestBlock(@Nonnull World worldIn, @Nonnull EntityPlayer player, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nullable TileEntity te, @Nonnull ItemStack stack) {
        player.addStat(Objects.requireNonNull(StatList.getBlockStats(this)));
        player.addExhaustion(0.005F);

        if(canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
            final List<ItemStack> items = new ArrayList<>();
            final ItemStack silkDrop = getSilkTouchDrop(state);
            if(!silkDrop.isEmpty()) items.add(silkDrop);

            ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1, true, player);
            for(ItemStack item : items) spawnAsEntity(worldIn, pos, item);
        }
        else {
            harvesters.set(player);
            dropBlockAsItem(worldIn, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));
            harvesters.set(null);
        }
    }

    public enum Type implements IStringSerializable
    {
        FULL("full"),
        CRACKED1("cracked1"),
        CRACKED2("cracked2"),
        CRACKED3("cracked3");

        @Nonnull final String name;
        Type(@Nonnull String name) { this.name = name; }

        @Nonnull
        @Override
        public String getName() { return name; }
    }
}
