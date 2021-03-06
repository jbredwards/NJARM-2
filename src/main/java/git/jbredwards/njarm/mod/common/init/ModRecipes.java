package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
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
    @SubscribeEvent
    public static void registerAll(@Nonnull RegistryEvent.Register<IRecipe> event) {
        registerCrafting(event.getRegistry());
        registerSmelting();
    }

    private static void registerCrafting(@Nonnull IForgeRegistry<IRecipe> registry) {
        //shapeless
        registerCrafting(registry, shapeless(9, Items.APPLE, new ItemStack(ModItems.FOOD_CRATE, 1, 6)));
        registerCrafting(registry, shapeless(9, Items.BEETROOT, new ItemStack(ModItems.FOOD_CRATE, 1, 4)));
        registerCrafting(registry, shapeless(9, Items.BEETROOT_SEEDS, new ItemStack(ModItems.FOOD_CRATE, 1, 5)));
        registerCrafting(registry, shapeless(9, Items.CARROT, new ItemStack(ModItems.FOOD_CRATE, 1, 1)));
        registerCrafting(registry, shapeless(9, Items.GOLDEN_APPLE, new ItemStack(ModItems.FOOD_CRATE, 1, 7)));
        registerCrafting(registry, shapeless(9, Items.GOLDEN_CARROT, new ItemStack(ModItems.FOOD_CRATE, 1, 8)));
        registerCrafting(registry, shapeless(9, 0, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 9)));
        registerCrafting(registry, shapeless(9, 1, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 10)));
        registerCrafting(registry, shapeless(9, 2, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 11)));
        registerCrafting(registry, shapeless(9, 3, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 12)));
        registerCrafting(registry, shapeless(16, ModItems.FRAGILE_ICE, Blocks.ICE));
        registerCrafting(registry, shapeless(9, ModItems.OBSIDIAN_INGOT, ModItems.OBSIDIAN_BLOCK));
        registerCrafting(registry, shapeless(9, Items.POISONOUS_POTATO, new ItemStack(ModItems.FOOD_CRATE, 1, 3)));
        registerCrafting(registry, shapeless(9, Items.POTATO, new ItemStack(ModItems.FOOD_CRATE, 1, 2)));
        registerCrafting(registry, shapeless(9, ModItems.RUBY, ModItems.RUBY_BLOCK));
        registerCrafting(registry, shapeless(9, ModItems.SAPPHIRE, ModItems.SAPPHIRE_BLOCK));
        registerCrafting(registry, shapeless(4, Items.SNOWBALL, Blocks.SNOW));
        registerCrafting(registry, shapeless(2, Items.SNOWBALL, Blocks.SNOW_LAYER));
        registerCrafting(registry, shapeless(9, Items.WHEAT_SEEDS, new ItemStack(ModItems.FOOD_CRATE, 1, 0)));
        //shaped
        registerCrafting(registry, shaped(4, ModItems.BLACKSTONE_BRICKS, "##", "##", '#', ModItems.POLISHED_BLACKSTONE));
        registerCrafting(registry, shaped(3, ModItems.CHAIN, "N", "I", "N", 'N', "nuggetIron", 'I', "ingotIron"));
        registerCrafting(registry, shaped(1, 0, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.WHEAT_SEEDS));
        registerCrafting(registry, shaped(1, 1, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.CARROT));
        registerCrafting(registry, shaped(1, 2, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.POTATO));
        registerCrafting(registry, shaped(1, 3, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.POISONOUS_POTATO));
        registerCrafting(registry, shaped(1, 4, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.BEETROOT));
        registerCrafting(registry, shaped(1, 5, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.BEETROOT_SEEDS));
        registerCrafting(registry, shaped(1, 6, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.APPLE));
        registerCrafting(registry, shaped(1, 8, ModItems.FOOD_CRATE, "###", "###", "###", '#', Items.GOLDEN_CARROT));
        registerCrafting(registry, shaped(1, 7, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.GOLDEN_APPLE, 1, 0)));
        registerCrafting(registry, shaped(1, 9, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.FISH, 1, 0)));
        registerCrafting(registry, shaped(1, 10, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.FISH, 1, 1)));
        registerCrafting(registry, shaped(1, 11, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.FISH, 1, 2)));
        registerCrafting(registry, shaped(1, 12, ModItems.FOOD_CRATE, "###", "###", "###", '#', new ItemStack(Items.FISH, 1, 3)));
        registerCrafting(registry, shaped(1, Blocks.ICE, "###", "###", "###", '#', ModItems.ICE_MUSHROOM));
        registerCrafting(registry, shaped(1, ModItems.OBSIDIAN_BLOCK, "###", "###", "###", '#', "ingotObsidianAlloy"));
        registerCrafting(registry, shaped(1, ModItems.NETHER_REACTOR_CORE, "IDI", "IDI", "IDI", 'I', "ingotIron", 'D', "gemDiamond"));
        registerCrafting(registry, shaped(4, ModItems.POLISHED_BASALT, "##", "##", '#', ModItems.BASALT));
        registerCrafting(registry, shaped(4, ModItems.POLISHED_BLACKSTONE, "##", "##", '#', ModItems.BLACKSTONE));
        registerCrafting(registry, shaped(1, ModItems.RUBY_AXE, "##", "#S", " S", '#', "gemRuby", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_BLOCK, "###", "###", "###", '#', "gemRuby"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_HOE, "##", " S", " S", '#', "gemRuby", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_PICKAXE, "###", " S ", " S ", '#', "gemRuby", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_SHOVEL, "#", "S", "S", '#', "gemRuby", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_SWORD, "#", "#", "S", '#', "gemRuby", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.SAPPHIRE_BLOCK, "###", "###", "###", '#', "gemSapphire"));
        registerCrafting(registry, shaped(1, Blocks.SNOW_LAYER, "##", '#', Items.SNOWBALL));
    }

    private static void registerCrafting(@Nonnull IForgeRegistry<IRecipe> registry, @Nonnull IRecipe recipe) {
        registry.register(recipe.setRegistryName(generateName(registry, recipe instanceof IShapedRecipe
                ? "shaped" : "shapeless", recipe.getRecipeOutput())));
    }

    private static void registerSmelting() {
        GameRegistry.addSmelting(Items.APPLE, new ItemStack(ModItems.BAKED_APPLE), 0.35f);
        GameRegistry.addSmelting(ModItems.BLACKSTONE_BRICKS, new ItemStack(ModItems.BLACKSTONE_CRACKED), 0.1f);
        GameRegistry.addSmelting(ModItems.MAGIC_ORE, new ItemStack(ModItems.MAGIC_DUST), 0.7f);
        GameRegistry.addSmelting(Blocks.OBSIDIAN, new ItemStack(ModItems.OBSIDIAN_GLASS), 0.1f);
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
        return new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.NAME), output, recipe);
    }

    //constructs a new shaped recipe
    @Nonnull private static IRecipe shaped(int amount, @Nonnull Item output, @Nonnull Object... recipe) { return shaped(new ItemStack(output, amount), recipe); }
    @Nonnull private static IRecipe shaped(int amount, int meta, @Nonnull Item output, @Nonnull Object... recipe) { return shaped(new ItemStack(output, amount, meta), recipe); }
    @Nonnull private static IRecipe shaped(int amount, @Nonnull Block output, @Nonnull Object... recipe) { return shaped(new ItemStack(output, amount), recipe); }
    @Nonnull private static IRecipe shaped(int amount, int meta, @Nonnull Block output, @Nonnull Object... recipe) { return shaped(new ItemStack(output, amount, meta), recipe); }
    @Nonnull private static IRecipe shaped(@Nonnull ItemStack output, @Nonnull Object... recipe) {
        return new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.NAME), output, recipe);
    }

    //generates an unused name for a crafting recipe
    @Nonnull
    public static ResourceLocation generateName(@Nonnull IForgeRegistry<IRecipe> registry, @Nonnull String postfix, @Nonnull ItemStack output) {
        String name = output.getTranslationKey() + '.' + postfix + '.';

        //resolves duplicate registry names
        int i = 1;
        do i++;
        while(registry.containsKey(new ResourceLocation(Constants.MODID, name + i)));

        //returns an unused name
        return new ResourceLocation(Constants.MODID, name + i);
    }
}
