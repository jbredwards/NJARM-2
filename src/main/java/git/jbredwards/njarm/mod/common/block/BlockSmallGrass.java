package git.jbredwards.njarm.mod.common.block;

import com.google.common.collect.ImmutableList;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

/**
 *
 * @author jbred
 *
 */
public class BlockSmallGrass extends BlockBush implements IShearable
{
    @Nonnull
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.1, 0, 0.1, 0.9, 0.8, 0.9);

    @Nonnull
    public final List<SeedEntry> seeds = new ArrayList<>();

    @Nonnull
    public Predicate<IBlockState> canSustainBush = state ->
            state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND;

    public BlockSmallGrass() { super(); }
    public BlockSmallGrass(@Nonnull Material materialIn) { super(materialIn); }
    public BlockSmallGrass(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn) {
        super(materialIn, mapColorIn);
        seeds.add(new SeedEntry(Items.AIR, 10));
    }

    @Override
    protected boolean canSustainBush(@Nonnull IBlockState state) { return canSustainBush.test(state); }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) { return true; }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, int fortune) {
        return ImmutableList.of(new ItemStack(this));
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return AABB;
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return rand.nextInt(8) == 0 ? WeightedRandom.getRandomItem(rand, seeds).item : Items.AIR;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, @Nonnull Random random) { return  1 + random.nextInt(fortune * 2 + 1); }

    @Override
    public void dropBlockAsItemWithChance(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
        if(!worldIn.isRemote && this == ModBlocks.ENDER_GRASS && worldIn.rand.nextFloat() < 0.01 * chance) {
            final EntityEndermite entity = new EntityEndermite(worldIn);
            entity.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, worldIn.rand.nextFloat() * 360, 0);
            worldIn.spawnEntity(entity);
        }
    }

    public static class SeedEntry extends WeightedRandom.Item
    {
        @Nonnull
        public final Item item;
        public SeedEntry(@Nonnull Item item, int itemWeightIn) {
            super(itemWeightIn);
            this.item = item;
        }
    }
}
