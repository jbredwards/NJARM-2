package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.entity.util.DataSerializerEnum;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.DataSerializerEntry;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class ModDataSerializers
{
    @Nonnull public static final NonNullList<DataSerializerEntry> INIT = NonNullList.create();

    @Nonnull public static final DataSerializerEnum<EnumDyeColor> DYE_COLOR = register("dye_color",
            new DataSerializerEnum<>(EnumDyeColor.class));

    @Nonnull
    static <T extends DataSerializer<?>> T register(@Nonnull String name, T serializer) {
        INIT.add(new DataSerializerEntry(serializer).setRegistryName(Constants.MODID, name));
        return serializer;
    }
}
