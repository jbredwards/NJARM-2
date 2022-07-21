package git.jbredwards.njarm.mod.common.util;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 *
 * @author jbred
 *
 */
public final class NBTUtils
{
    //alternative to JsonToNBT that doesn't throw an error
    @Nonnull
    public static NBTTagCompound getTagFromString(@Nonnull String nbt) {
        try { return JsonToNBT.getTagFromJson(nbt); }
        catch(NBTException e) {
            e.printStackTrace();
            return new NBTTagCompound();
        }
    }

    @Nonnull
    public static Set<Biome> gatherBiomesFromNBT(@Nonnull Set<Biome> dest, @Nonnull NBTTagCompound nbt) {
        //handle raw biome input
        nbt.getTagList("Biomes", Constants.NBT.TAG_STRING).forEach(subNbt -> {
            final @Nullable Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(subNbt.toString()));
            if(biome != null) dest.add(biome);
        });
        //handle biome tags
        nbt.getTagList("BiomeTags", Constants.NBT.TAG_STRING).forEach(subNbt -> dest.addAll(
                BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(subNbt.toString()))));

        return dest;
    }
}
