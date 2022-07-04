package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.common.config.block.NetherCoreConfig;
import git.jbredwards.njarm.mod.common.tileentity.TileEntityNetherCore;
import git.jbredwards.njarm.mod.common.util.ArrayUtils;
import net.darkhax.bookshelf.block.BlockTileEntity;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public class BlockNetherCore extends BlockTileEntity
{
    @Nonnull
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockNetherCore(@Nonnull Material material) { this(material, material.getMaterialMapColor()); }
    public BlockNetherCore(@Nonnull Material material, @Nonnull MapColor color) { super(material, color); }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) { return new TileEntityNetherCore(); }

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
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(hand == EnumHand.MAIN_HAND && NetherCoreConfig.getAltReactorBehavior() && state.getValue(TYPE) == Type.NORMAL) {
            @Nullable final TileEntity te = worldIn.getTileEntity(pos);
            if(te instanceof TileEntityNetherCore) return ((TileEntityNetherCore)te).tryInitialize(playerIn);
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    public enum Type implements IStringSerializable
    {
        NORMAL("normal"),
        INITIALIZED("initialized"),
        FINISHED("finished");

        @Nonnull final String name;
        Type(@Nonnull String name) { this.name = name; }

        @Nonnull
        @Override
        public String getName() { return name; }
    }
}
