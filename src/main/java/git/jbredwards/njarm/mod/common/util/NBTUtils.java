package git.jbredwards.njarm.mod.common.util;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
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
    public static Set<Biome> gatherBiomesFromNBT(@Nonnull NBTTagCompound nbt) {
        final Set<Biome> dest = new HashSet<>();
        //handle raw biome input
        nbt.getTagList("Biomes", Constants.NBT.TAG_STRING).forEach(subNbt -> {
            final @Nullable Biome biome = Biome.REGISTRY.getObject(
                    new ResourceLocation(((NBTTagString)subNbt).getString()));

            if(biome != null) dest.add(biome);
        });

        //handle biome tags
        nbt.getTagList("BiomeTags", Constants.NBT.TAG_STRING).forEach(subNbt -> {
            final String tag = ((NBTTagString)subNbt).getString();
            if(tag.equalsIgnoreCase("all")) Biome.REGISTRY.forEach(dest::add);
            else dest.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(tag)));
        });

        //remove unwanted biomes
        if(nbt.hasKey("ExcludeBiomes", Constants.NBT.TAG_COMPOUND))
            dest.removeAll(gatherBiomesFromNBT(nbt.getCompoundTag("ExcludeBiomes")));

        return dest;
    }

    public static void gatherBiomesFromData(@Nonnull Collection<Biome> biomes, @Nonnull String[] biomeData) {
        final Set<Biome> allBiomes = new HashSet<>();
        for(String data : biomeData) allBiomes.addAll(gatherBiomesFromNBT(getTagFromString(data)));

        biomes.clear();
        biomes.addAll(allBiomes);
    }
}
