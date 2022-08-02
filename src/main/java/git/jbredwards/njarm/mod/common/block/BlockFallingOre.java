package git.jbredwards.njarm.mod.common.block;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/**
 *
 * @author jbred
 *
 */
public class BlockFallingOre extends BlockFalling
{
    @Nonnull protected Supplier<Item> droppedItem = () -> Item.getItemFromBlock(this);
    @Nonnull protected ToIntFunction<Random> quantityDropped = r -> 1;
    @Nonnull protected ToIntFunction<Random> expDropped = r -> 0;
    @Nonnull protected final MapColor mapColor;
    protected boolean doesFortuneAdd;
    protected int damageDropped = 0;

    public BlockFallingOre(@Nonnull Material materialIn) { this(materialIn, materialIn.getMaterialMapColor()); }
    public BlockFallingOre(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn) {
        super(materialIn);
        mapColor = mapColorIn;
    }

    @Nonnull
    public BlockFallingOre setExpDropped(@Nonnull ToIntFunction<Random> expDroppedIn) {
        expDropped = expDroppedIn;
        return this;
    }

    @Nonnull
    public BlockFallingOre setItemDropped(@Nonnull Supplier<Item> droppedItemIn) {
        droppedItem = droppedItemIn;
        return this;
    }

    @Nonnull
    public BlockFallingOre setQuantityDropped(@Nonnull ToIntFunction<Random> quantityDroppedIn) {
        quantityDropped = quantityDroppedIn;
        return this;
    }

    @Nonnull
    public BlockFallingOre setDamageDropped(int damageDroppedIn) {
        damageDropped = damageDroppedIn;
        return this;
    }

    @Nonnull
    public BlockFallingOre setDoesFortuneAdd() {
        doesFortuneAdd = true;
        return this;
    }

    @Override
    public int getExpDrop(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, int fortune) {
        return expDropped.applyAsInt(world instanceof World ? ((World)world).rand : RANDOM);
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) { return droppedItem.get(); }

    @Override
    public int quantityDroppedWithBonus(int fortune, @Nonnull Random random) {
        return fortune > 0 ? (doesFortuneAdd ? random.nextInt(fortune + 1) + quantityDropped(random)
                : (random.nextInt(fortune + 1) + 1) * quantityDropped(random)) : quantityDropped(random);
    }

    @Override
    public int quantityDropped(@Nonnull Random random) { return quantityDropped.applyAsInt(random); }

    @Override
    public int damageDropped(@Nonnull IBlockState state) { return damageDropped; }

    @Nonnull
    @Override
    public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) { return getSilkTouchDrop(state); }

    @Nonnull
    @Override
    public MapColor getMapColor(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) { return mapColor; }

    @SideOnly(Side.CLIENT)
    @Override
    public int getDustColor(@Nonnull IBlockState state) { return mapColor.colorValue; }
}
