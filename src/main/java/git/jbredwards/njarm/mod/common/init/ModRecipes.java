package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class ModRecipes
{
    @SuppressWarnings("NotNullFieldNotInitialized")
    @Nonnull private static IForgeRegistry<IRecipe> registry;

    @SubscribeEvent
    public static void registerAll(@Nonnull RegistryEvent.Register<IRecipe> event) {
        registry = event.getRegistry();
        registerCrafting();
        registerSmelting();
    }

    private static void registerCrafting() {
        //shapeless
        registry.register(shapeless(9, Items.APPLE, new ItemStack(ModItems.FOOD_CRATE, 1, 6)));
        registry.register(shapeless(9, Items.BEETROOT, new ItemStack(ModItems.FOOD_CRATE, 1, 4)));
        registry.register(shapeless(9, Items.BEETROOT_SEEDS, new ItemStack(ModItems.FOOD_CRATE, 1, 5)));
        registry.register(shapeless(9, Items.CARROT, new ItemStack(ModItems.FOOD_CRATE, 1, 1)));
        registry.register(shapeless(9, Items.GOLDEN_APPLE, new ItemStack(ModItems.FOOD_CRATE, 1, 7)));
        registry.register(shapeless(9, Items.GOLDEN_CARROT, new ItemStack(ModItems.FOOD_CRATE, 1, 8)));
        registry.register(shapeless(9, 0, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 9)));
        registry.register(shapeless(9, 1, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 10)));
        registry.register(shapeless(9, 2, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 11)));
        registry.register(shapeless(9, 3, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 12)));
        registry.register(shapeless(16, ModItems.FRAGILE_ICE, Blocks.ICE));
        registry.register(shapeless(9, Items.POISONOUS_POTATO, new ItemStack(ModItems.FOOD_CRATE, 1, 3)));
        registry.register(shapeless(9, Items.POTATO, new ItemStack(ModItems.FOOD_CRATE, 1, 2)));
        registry.register(shapeless(9, ModItems.RUBY, ModItems.RUBY_BLOCK));
        registry.register(shapeless(9, ModItems.SAPPHIRE, ModItems.SAPPHIRE_BLOCK));
        registry.register(shapeless(4, Items.SNOWBALL, Blocks.SNOW));
        registry.register(shapeless(2, Items.SNOWBALL, Blocks.SNOW_LAYER));
        registry.register(shapeless(9, Items.WHEAT_SEEDS, new ItemStack(ModItems.FOOD_CRATE, 1, 0)));
        //shaped
        registry.register(shaped(1, 0, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.WHEAT_SEEDS));
        registry.register(shaped(1, 1, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.CARROT));
        registry.register(shaped(1, 2, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.POTATO));
        registry.register(shaped(1, 3, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.POISONOUS_POTATO));
        registry.register(shaped(1, 4, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.BEETROOT));
        registry.register(shaped(1, 5, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.BEETROOT_SEEDS));
        registry.register(shaped(1, 6, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.APPLE));
        registry.register(shaped(1, 8, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.GOLDEN_CARROT));
        registry.register(shaped(1, 7, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.GOLDEN_APPLE, 1, 0)));
        registry.register(shaped(1, 9, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.FISH, 1, 0)));
        registry.register(shaped(1, 10, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.FISH, 1, 1)));
        registry.register(shaped(1, 11, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.FISH, 1, 2)));
        registry.register(shaped(1, 12, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.FISH, 1, 3)));
        registry.register(shaped(1, ModItems.NETHER_REACTOR_CORE, "IDI", "IDI", "IDI", 'I', "ingotIron", 'D', "gemDiamond"));
        registry.register(shaped(1, ModItems.RUBY_AXE, "##", "#S", " S", '#', "gemRuby", 'S', "stickWood"));
        registry.register(shaped(1, ModItems.RUBY_BLOCK, "###", "###", "###", '#', "gemRuby"));
        registry.register(shaped(1, ModItems.RUBY_HOE, "##", " S", " S", '#', "gemRuby", 'S', "stickWood"));
        registry.register(shaped(1, ModItems.RUBY_PICKAXE, "###", " S ", " S ", '#', "gemRuby", 'S', "stickWood"));
        registry.register(shaped(1, ModItems.RUBY_SHOVEL, "#", "S", "S", '#', "gemRuby", 'S', "stickWood"));
        registry.register(shaped(1, ModItems.RUBY_SWORD, "#", "#", "S", '#', "gemRuby", 'S', "stickWood"));
        registry.register(shaped(1, ModItems.SAPPHIRE_BLOCK, "###", "###", "###", '#', "gemSapphire"));
        registry.register(shaped(1, Blocks.SNOW_LAYER, "##", '#', Items.SNOWBALL));
    }

    private static void registerSmelting() {
        GameRegistry.addSmelting(Items.APPLE, new ItemStack(ModItems.BAKED_APPLE), 0.35f);
        GameRegistry.addSmelting(ModItems.MAGIC_ORE, new ItemStack(ModItems.MAGIC_DUST), 0.7f);
        GameRegistry.addSmelting(ModItems.RUBY_ORE, new ItemStack(ModItems.RUBY), 1);
        GameRegistry.addSmelting(ModItems.SAPPHIRE_ORE, new ItemStack(ModItems.SAPPHIRE), 1);
    }

    @SubscribeEvent
    public static void registerFurnaceFuels(@Nonnull FurnaceFuelBurnTimeEvent event) {
        final @Nullable Item item = event.getItemStack().getItem();
        if(item == ModItems.SUNSTONE) event.setBurnTime(6400 * 200);
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

    //generates an unused name for a crafting recipe
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
