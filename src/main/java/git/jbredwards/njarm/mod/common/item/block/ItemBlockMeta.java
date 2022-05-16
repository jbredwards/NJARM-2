package git.jbredwards.njarm.mod.common.item.block;

import net.darkhax.bookshelf.item.ICustomModel;
import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Enhanced & safer version of bookshelf's class that takes in an IProperty instead
 * @author jbred
 *
 */
public class ItemBlockMeta extends ItemBlockBasic implements ICustomModel
{
    @Nonnull public final IProperty<?> property;

    public ItemBlockMeta(@Nonnull Block block, @Nonnull IProperty<? extends IStringSerializable> property) {
        super(block, property.getAllowedValues().stream().map(IStringSerializable::getName).toArray(String[]::new));
        this.property = property;
    }

    @Override
    public int getMetadata(int damage) { return damage > names.length ? 0 : damage; }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerMeshModels() {
        for(int meta = 0; meta < names.length; meta++) {
            String variant = property.getName() + "=" + names[meta];
            ModelLoader.setCustomModelResourceLocation(this, meta, new ModelResourceLocation(Objects.requireNonNull(getRegistryName()), variant));
        }
    }
}
