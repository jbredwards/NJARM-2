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
        event.register(shapeless(9, Items.APPLE, new ItemStack(ModItems.FOOD_CRATE, 1, 6)));
        event.register(shapeless(9, Items.BEETROOT, new ItemStack(ModItems.FOOD_CRATE, 1, 4)));
        event.register(shapeless(9, Items.BEETROOT_SEEDS, new ItemStack(ModItems.FOOD_CRATE, 1, 5)));
        event.register(shapeless(9, Items.CARROT, new ItemStack(ModItems.FOOD_CRATE, 1, 1)));
        event.register(shapeless(9, Items.GOLDEN_APPLE, new ItemStack(ModItems.FOOD_CRATE, 1, 7)));
        event.register(shapeless(9, Items.GOLDEN_CARROT, new ItemStack(ModItems.FOOD_CRATE, 1, 8)));
        event.register(shapeless(9, 0, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 9)));
        event.register(shapeless(9, 1, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 10)));
        event.register(shapeless(9, 2, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 11)));
        event.register(shapeless(9, 3, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 12)));
        event.register(shapeless(9, Items.POISONOUS_POTATO, new ItemStack(ModItems.FOOD_CRATE, 1, 3)));
        event.register(shapeless(9, Items.POTATO, new ItemStack(ModItems.FOOD_CRATE, 1, 2)));
        event.register(shapeless(9, ModItems.RUBY, ModItems.RUBY_BLOCK));
        event.register(shapeless(9, ModItems.SAPPHIRE, ModItems.SAPPHIRE_BLOCK));
        event.register(shapeless(4, Items.SNOWBALL, Blocks.SNOW));
        event.register(shapeless(2, Items.SNOWBALL, Blocks.SNOW_LAYER));
        event.register(shapeless(9, Items.WHEAT_SEEDS, new ItemStack(ModItems.FOOD_CRATE, 1, 0)));
        //shaped
        event.register(shaped(1, 0, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.WHEAT_SEEDS));
        event.register(shaped(1, 1, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.CARROT));
        event.register(shaped(1, 2, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.POTATO));
        event.register(shaped(1, 3, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.POISONOUS_POTATO));
        event.register(shaped(1, 4, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.BEETROOT));
        event.register(shaped(1, 5, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.BEETROOT_SEEDS));
        event.register(shaped(1, 6, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.APPLE));
        event.register(shaped(1, 8, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.GOLDEN_CARROT));
        event.register(shaped(1, 7, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.GOLDEN_APPLE, 1, 0)));
        event.register(shaped(1, 9, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.FISH, 1, 0)));
        event.register(shaped(1, 10, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.FISH, 1, 1)));
        event.register(shaped(1, 11, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.FISH, 1, 2)));
        event.register(shaped(1, 12, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.FISH, 1, 3)));
        event.register(shaped(1, ModItems.RUBY_BLOCK, "###", "###", "###", '#', "gemRuby"));
        event.register(shaped(1, ModItems.SAPPHIRE_BLOCK, "###", "###", "###", '#', "gemSapphire"));
        event.register(shaped(1, Blocks.SNOW_LAYER, "##", '#', Items.SNOWBALL));
    }

    //constructs a new shapeless recipe
    @Nonnull private static IRecipe shapeless(int amount, @Nonnull Item output, @Nonnull Object... recipe) { return shapeless(new ItemStack(output, amount), recipe); }
    @Nonnull private static IRecipe shapeless(int amount, int meta, @Nonnull Item output, @Nonnull Object... recipe) { return shapeless(new ItemStack(output, amount, meta), recipe); }
    @Nonnull private static IRecipe shapeless(int amount, @Nonnull Block output, @Nonnull Object... recipe) { return shapeless(new ItemStack(output, amount), recipe); }
    @Nonnull private static IRecipe shapeless(int amount, int meta, @Nonnull Block output, @Nonnull Object... recipe) { return shapeless(new ItemStack(output, amount, meta), recipe); }
    @Nonnull private static IRecipe shapeless(@Nonnull ItemStack output, @Nonnull Object... recipe) {
        return new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.NAME), output, recipe)
                .setRegistryName(generateName("shapeless", output));
    }

    //constructs a new shaped recipe
    @Nonnull private static IRecipe shaped(int amount, @Nonnull Item output, @Nonnull Object... recipe) { return shaped(new ItemStack(output, amount), recipe); }
    @Nonnull private static IRecipe shaped(int amount, int meta, @Nonnull Item output, @Nonnull Object... recipe) { return shaped(new ItemStack(output, amount, meta), recipe); }
    @Nonnull private static IRecipe shaped(int amount, @Nonnull Block output, @Nonnull Object... recipe) { return shaped(new ItemStack(output, amount), recipe); }
    @Nonnull private static IRecipe shaped(int amount, int meta, @Nonnull Block output, @Nonnull Object... recipe) { return shaped(new ItemStack(output, amount, meta), recipe); }
    @Nonnull private static IRecipe shaped(@Nonnull ItemStack output, @Nonnull Object... recipe) {
        return new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.NAME), output, recipe)
                .setRegistryName(generateName("shaped", output));
    }

    //generates an unused name for a recipe
    @Nonnull
    private static ResourceLocation generateName(@Nonnull String postfix, @Nonnull ItemStack output) {
        String name = output.getTranslationKey() + '.' + postfix;

        //resolves duplicate registry names
        if(registry.containsKey(new ResourceLocation(Constants.MODID, name))) {
            name += '.';

            int i = 1;
            do i++;
            while(registry.containsKey(new ResourceLocation(Constants.MODID, name + i)));

            name += i;
        }

        //returns an unused name
        return new ResourceLocation(Constants.MODID, name);
    }
}
