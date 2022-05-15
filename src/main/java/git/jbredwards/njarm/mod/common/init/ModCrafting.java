package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Handles this mod's crafting table recipes
 * @author jbred
 *
 */
public final class ModCrafting
{
    private static IForgeRegistry<IRecipe> registry;

    //internal use only
    public static void registerAll(@Nonnull IForgeRegistry<IRecipe> event) {
        registry = event;

        //shapeless
        event.register(shapeless(9, ModItems.RUBY, ModItems.RUBY_BLOCK));
        event.register(shapeless(9, ModItems.SAPPHIRE, ModItems.SAPPHIRE_BLOCK));
        event.register(shapeless(4, Items.SNOWBALL, Blocks.SNOW));
        event.register(shapeless(2, Items.SNOWBALL, Blocks.SNOW_LAYER));

        //shaped
        event.register(shaped(1, ModItems.RUBY_BLOCK, "###", "###", "###", '#', "gemRuby"));
        event.register(shaped(1, ModItems.SAPPHIRE_BLOCK, "###", "###", "###", '#', "gemSapphire"));
        event.register(shaped(1, Blocks.SNOW_LAYER, "##", '#', Items.SNOWBALL));
    }

    //constructs a new shapeless recipe
    @Nonnull private static IRecipe shapeless(int amount, @Nonnull Item out, @Nonnull Object... in) { return shapeless(new ItemStack(out, amount), in); }
    @Nonnull private static IRecipe shapeless(int amount, @Nonnull Item out, int meta, @Nonnull Object... in) { return shapeless(new ItemStack(out, amount, meta), in); }
    @Nonnull private static IRecipe shapeless(int amount, @Nonnull Block out, @Nonnull Object... in) { return shapeless(new ItemStack(out, amount), in); }
    @Nonnull private static IRecipe shapeless(int amount, @Nonnull Block out, int meta, @Nonnull Object... in) { return shapeless(new ItemStack(out, amount, meta), in); }
    @Nonnull private static IRecipe shapeless(@Nonnull ItemStack out, @Nonnull Object... in) {
        return new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.NAME), out, in)
                .setRegistryName(generateName("shapeless", out.getItem().getRegistryName()));
    }

    //constructs a new shaped recipe
    @Nonnull private static IRecipe shaped(int amount, @Nonnull Item out, @Nonnull Object... in) { return shaped(new ItemStack(out, amount), in); }
    @Nonnull private static IRecipe shaped(int amount, @Nonnull Item out, int meta, @Nonnull Object... in) { return shaped(new ItemStack(out, amount, meta), in); }
    @Nonnull private static IRecipe shaped(int amount, @Nonnull Block out, @Nonnull Object... in) { return shaped(new ItemStack(out, amount), in); }
    @Nonnull private static IRecipe shaped(int amount, @Nonnull Block out, int meta, @Nonnull Object... in) { return shaped(new ItemStack(out, amount, meta), in); }
    @Nonnull private static IRecipe shaped(@Nonnull ItemStack out, @Nonnull Object... in) {
        return new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.NAME), out, in)
                .setRegistryName(generateName("shaped", out.getItem().getRegistryName()));
    }

    //generates an unused name for a recipe
    @Nonnull
    private static ResourceLocation generateName(@Nonnull String postfix, @Nullable ResourceLocation location) {
        if(location == null) throw new IllegalArgumentException("Attempted to register recipe with invalid item!");
        String name = location.getPath() + '.' + postfix;

        //resolves duplicate registry names
        if(registry.containsKey(new ResourceLocation(Constants.MODID, name))) {
            name += '.';

            int i = 0;
            do { i++; }
            while(registry.containsKey(new ResourceLocation(Constants.MODID, name + i)));

            name += i;
        }

        //returns an unused name
        return new ResourceLocation(Constants.MODID, name);
    }
}
