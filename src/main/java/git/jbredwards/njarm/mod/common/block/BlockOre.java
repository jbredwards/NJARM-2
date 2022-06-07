package git.jbredwards.njarm.mod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/**
 *
 * @author jbred
 *
 */
public class BlockOre extends Block
{
    @Nonnull protected Supplier<Item> droppedItem = () -> Item.getItemFromBlock(this);
    @Nonnull protected ToIntFunction<Random> quantityDropped = r -> 1;
    @Nonnull protected ToIntFunction<Random> expDropped = r -> 0;
    protected boolean doesFortuneAdd;
    protected int damageDropped = 0;

    public BlockOre(@Nonnull Material materialIn) { this(materialIn, materialIn.getMaterialMapColor()); }
    public BlockOre(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn) { super(materialIn, mapColorIn); }

    @SuppressWarnings("unchecked")
    public <T extends BlockOre> T setExpDropped(@Nonnull ToIntFunction<Random> expDroppedIn) {
        expDropped = expDroppedIn;
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockOre> T setItemDropped(@Nonnull Supplier<Item> droppedItemIn) {
        droppedItem = droppedItemIn;
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockOre> T setQuantityDropped(@Nonnull ToIntFunction<Random> quantityDroppedIn) {
        quantityDropped = quantityDroppedIn;
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockOre> T setDamageDropped(int damageDroppedIn) {
        damageDropped = damageDroppedIn;
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockOre> T setDoesFortuneAdd() {
        doesFortuneAdd = true;
        return (T)this;
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
}
