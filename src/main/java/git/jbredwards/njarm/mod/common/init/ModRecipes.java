package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.recipes.crafting.CactusArmorRecipe;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionHelper;
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
import net.minecraftforge.registries.IForgeRegistryModifiable;

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
        registerBrewing();
    }

    static void registerCrafting(@Nonnull IForgeRegistry<IRecipe> registry) {
        //shapeless
        registerCrafting(registry, shapeless(9, Items.APPLE, new ItemStack(ModItems.FOOD_CRATE, 1, 6)));
        registerCrafting(registry, shapeless(1, ModItems.BAGUETTE, Items.BREAD, Items.BREAD));
        registerCrafting(registry, shapeless(9, Items.BEETROOT, new ItemStack(ModItems.FOOD_CRATE, 1, 4)));
        registerCrafting(registry, shapeless(9, Items.BEETROOT_SEEDS, new ItemStack(ModItems.FOOD_CRATE, 1, 5)));
        registerCrafting(registry, shapeless(1, ModItems.CARAMEL_APPLE, Items.APPLE, Items.SUGAR, Items.STICK));
        registerCrafting(registry, shapeless(9, Items.CARROT, new ItemStack(ModItems.FOOD_CRATE, 1, 1)));
        registerCrafting(registry, shapeless(4, ModItems.ENDER_PLANKS, ModItems.ENDER_LOG));
        registerCrafting(registry, shapeless(9, 0, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 9)));
        registerCrafting(registry, shapeless(9, 1, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 10)));
        registerCrafting(registry, shapeless(9, 2, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 11)));
        registerCrafting(registry, shapeless(9, 3, Items.FISH, new ItemStack(ModItems.FOOD_CRATE, 1, 12)));
        registerCrafting(registry, shapeless(16, ModItems.FRAGILE_ICE, Blocks.ICE));
        registerCrafting(registry, shapeless(9, Items.GOLDEN_APPLE, new ItemStack(ModItems.FOOD_CRATE, 1, 7)));
        registerCrafting(registry, shapeless(9, Items.GOLDEN_CARROT, new ItemStack(ModItems.FOOD_CRATE, 1, 8)));
        registerCrafting(registry, shapeless(2, ModItems.MAGIC_DUST, ModItems.BLESTEM_ROD));
        registerCrafting(registry, shapeless(1, ModItems.MAGIC_INGOT, "dustMagicNJARM", "dustMica", "dustMagicNJARM", "dustMica", "ingotObsidianAlloy", "dustMica", "dustMagicNJARM", "dustMica", "dustMagicNJARM"));
        registerCrafting(registry, shapeless(9, ModItems.MAGIC_INGOT, ModItems.MAGIC_BLOCK));
        registerCrafting(registry, shapeless(9, ModItems.MAGIC_NUGGET, ModItems.MAGIC_INGOT));
        registerCrafting(registry, shapeless(9, ModItems.NETHERITE_INGOT, ModItems.NETHERITE_BLOCK));
        registerCrafting(registry, shapeless(1, ModItems.NETHERITE_INGOT, "scrapAncientDebris", "scrapAncientDebris", "scrapAncientDebris", "scrapAncientDebris", "ingotGold", "ingotGold", "ingotGold", "ingotGold"));
        registerCrafting(registry, shapeless(9, ModItems.NETHERITE_NUGGET, ModItems.NETHERITE_INGOT));
        registerCrafting(registry, shapeless(9, ModItems.OBSIDIAN_INGOT, ModItems.OBSIDIAN_BLOCK));
        registerCrafting(registry, shapeless(9, ModItems.OBSIDIAN_NUGGET, ModItems.OBSIDIAN_INGOT));
        registerCrafting(registry, shapeless(1, ModItems.OVERGROWN_DIRT, new ItemStack(Blocks.DIRT, 1, 0), "vine"));
        registerCrafting(registry, shapeless(1, ModItems.OVERGROWN_STONE, "stone", "vine"));
        registerCrafting(registry, shapeless(9, ModItems.PLATINUM_INGOT, ModItems.PLATINUM_BLOCK));
        registerCrafting(registry, shapeless(9, ModItems.PLATINUM_NUGGET, ModItems.PLATINUM_INGOT));
        registerCrafting(registry, shapeless(9, Items.POISONOUS_POTATO, new ItemStack(ModItems.FOOD_CRATE, 1, 3)));
        registerCrafting(registry, shapeless(9, Items.POTATO, new ItemStack(ModItems.FOOD_CRATE, 1, 2)));
        registerCrafting(registry, shapeless(9, Blocks.QUARTZ_BLOCK, ModItems.COMPRESSED_QUARTZ_BLOCK));
        registerCrafting(registry, shapeless(1, ModItems.RAW_EGG, Items.EGG));
        registerCrafting(registry, shapeless(9, Blocks.RED_SANDSTONE, ModItems.COMPRESSED_RED_SANDSTONE));
        registerCrafting(registry, shapeless(9, Blocks.RED_SANDSTONE, ModItems.CRACKED_COMPRESSED_RED_SANDSTONE));
        registerCrafting(registry, shapeless(9, ModItems.RUBY, ModItems.RUBY_BLOCK));
        registerCrafting(registry, shapeless(9, Blocks.SANDSTONE, ModItems.COMPRESSED_SANDSTONE));
        registerCrafting(registry, shapeless(9, Blocks.SANDSTONE, ModItems.CRACKED_COMPRESSED_SANDSTONE));
        registerCrafting(registry, shapeless(9, ModItems.SAPPHIRE, ModItems.SAPPHIRE_BLOCK));
        registerCrafting(registry, shapeless(4, Items.SNOWBALL, Blocks.SNOW));
        registerCrafting(registry, shapeless(2, Items.SNOWBALL, Blocks.SNOW_LAYER));
        registerCrafting(registry, shapeless(9, Blocks.STONE, ModItems.COMPRESSED_STONE));
        registerCrafting(registry, shapeless(1, ModItems.SUGAR_BAGUETTE, ModItems.BAGUETTE, Items.SUGAR));
        registerCrafting(registry, shapeless(1, ModItems.SUGAR_BAGUETTE, ModItems.SUGAR_BREAD, ModItems.SUGAR_BREAD));
        registerCrafting(registry, shapeless(1, ModItems.SUGAR_BREAD, Items.BREAD, Items.SUGAR));
        registerCrafting(registry, shapeless(1, ModItems.SUGAR_COOKIE, Items.COOKIE, Items.SUGAR));
        registerCrafting(registry, shapeless(9, Items.WHEAT_SEEDS, new ItemStack(ModItems.FOOD_CRATE, 1, 0)));
        //shaped
        registerCrafting(registry, shaped(1, ModItems.BAGUETTE, "###", "###", '#', "cropWheat"));
        registerCrafting(registry, shaped(3, ModItems.BLESTEM_ARROW, "IRI", "RAR", "ISI", 'I', "ingotMagicalAlloy", 'R', ModItems.BLESTEM_ROD, 'A', Items.ARROW, 'S', ModItems.ENDER_STAR));
        registerCrafting(registry, shaped(4, ModItems.BLACKSTONE_BRICKS, "##", "##", '#', ModItems.POLISHED_BLACKSTONE));
        registerCrafting(registry, shaped(1, ModItems.CACTUS_AXE, "##", "#S", " S", '#', "blockCactus", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.CACTUS_HOE, "##", " S", " S", '#', "blockCactus", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.CACTUS_PICKAXE, "###", " S ", " S ", '#', "blockCactus", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.CACTUS_SHOVEL, "#", "S", "S", '#', "blockCactus", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.CACTUS_SWORD, "#", "#", "S", '#', "blockCactus", 'S', "stickWood"));
        registerCrafting(registry, shaped(3, ModItems.CHAIN, "N", "I", "N", 'N', "nuggetIron", 'I', "ingotIron"));
        registerCrafting(registry, shaped(1, Items.CHAINMAIL_CHESTPLATE, "# #", "###", "###", '#', ModItems.FIRE));
        registerCrafting(registry, shaped(1, Items.CHAINMAIL_BOOTS, "# #", "# #", '#', ModItems.FIRE));
        registerCrafting(registry, shaped(1, Items.CHAINMAIL_HELMET, "###", "# #", '#', ModItems.FIRE));
        registerCrafting(registry, shaped(1, Items.CHAINMAIL_LEGGINGS, "###", "# #", "# #", '#', ModItems.FIRE));
        registerCrafting(registry, shaped(1, ModItems.COMPRESSED_QUARTZ_BLOCK, "###", "###", "###", '#', "blockQuartz"));
        registerCrafting(registry, shaped(1, ModItems.COMPRESSED_RED_SANDSTONE, "###", "###", "###", '#', Blocks.RED_SANDSTONE));
        registerCrafting(registry, shaped(1, ModItems.COMPRESSED_SANDSTONE, "###", "###", "###", '#', Blocks.SANDSTONE));
        registerCrafting(registry, shaped(1, ModItems.COMPRESSED_STONE, "###", "###", "###", '#', new ItemStack(Blocks.STONE, 1, 0)));
        registerCrafting(registry, shaped(1, ModItems.DIMENSIONAL_MAGIC_MIRROR, "DID", "IMI", "DID", 'D', "dustMagicNJARM", 'I', "ingotMagicalAlloy", 'M', ModItems.MAGIC_MIRROR));
        registerCrafting(registry, shaped(1, ModItems.DIMENSIONAL_MAGIC_MIRROR, "IDI", "DMD", "IDI", 'D', "dustMagicNJARM", 'I', "ingotMagicalAlloy", 'M', ModItems.MAGIC_MIRROR));
        registerCrafting(registry, shaped(1, Items.EMERALD, "###", "###", "###", '#', ModItems.RUPEE));
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
        registerCrafting(registry, shaped(1, ModItems.OBSIDIAN_AXE, "##", "#S", " S", '#', "ingotObsidianAlloy", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.OBSIDIAN_BLOCK, "###", "###", "###", '#', "ingotObsidianAlloy"));
        registerCrafting(registry, shaped(1, ModItems.OBSIDIAN_BOOTS, "# #", "# #", '#', "ingotObsidianAlloy"));
        registerCrafting(registry, shaped(1, ModItems.OBSIDIAN_CHESTPLATE, "# #", "###", "###", '#', "ingotObsidianAlloy"));
        registerCrafting(registry, shaped(16, ModItems.OBSIDIAN_GLASS_PANE, "###", "###", '#', ModItems.OBSIDIAN_GLASS));
        registerCrafting(registry, shaped(1, ModItems.OBSIDIAN_HELMET, "###", "# #", '#', "ingotObsidianAlloy"));
        registerCrafting(registry, shaped(1, ModItems.OBSIDIAN_HOE, "##", " S", " S", '#', "ingotObsidianAlloy", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.OBSIDIAN_INGOT, "###", "###", "###", '#', "nuggetObsidianAlloy"));
        registerCrafting(registry, shaped(1, ModItems.OBSIDIAN_LEGGINGS, "###", "# #", "# #", '#', "ingotObsidianAlloy"));
        registerCrafting(registry, shaped(1, ModItems.OBSIDIAN_PICKAXE, "###", " S ", " S ", '#', "ingotObsidianAlloy", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.OBSIDIAN_SHOVEL, "#", "S", "S", '#', "ingotObsidianAlloy", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.OBSIDIAN_SWORD, "#", "#", "S", '#', "ingotObsidianAlloy", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.MAGIC_BLOCK, "###", "###", "###", '#', "ingotMagicalAlloy"));
        registerCrafting(registry, shaped(1, ModItems.MAGIC_INGOT, "###", "###", "###", '#', "nuggetMagicalAlloy"));
        registerCrafting(registry, shaped(2, ModItems.MAGIC_MIRROR, "MPM", "PDP", "MPM", 'M', "dustMagicNJARM", 'P', "ingotPlatinum", 'D', "dustMica"));
        registerCrafting(registry, shaped(2, ModItems.MAGIC_MIRROR, "PMP", "MDM", "PMP", 'M', "dustMagicNJARM", 'P', "ingotPlatinum", 'D', "dustMica"));
        registerCrafting(registry, shaped(1, ModItems.NETHER_REACTOR_CORE, "IDI", "IDI", "IDI", 'I', "ingotIron", 'D', "gemDiamond"));
        registerCrafting(registry, shaped(1, ModItems.NETHERITE_AXE, "##", "#S", " S", '#', "ingotNetherite", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.NETHERITE_BLOCK, "###", "###", "###", '#', "ingotNetherite"));
        registerCrafting(registry, shaped(1, ModItems.NETHERITE_BOOTS, "# #", "# #", '#', "ingotNetherite"));
        registerCrafting(registry, shaped(1, ModItems.NETHERITE_CHESTPLATE, "# #", "###", "###", '#', "ingotNetherite"));
        registerCrafting(registry, shaped(1, ModItems.NETHERITE_HELMET, "###", "# #", '#', "ingotNetherite"));
        registerCrafting(registry, shaped(1, ModItems.NETHERITE_HOE, "##", " S", " S", '#', "ingotNetherite", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.NETHERITE_INGOT, "###", "###", "###", '#', "nuggetNetherite"));
        registerCrafting(registry, shaped(1, ModItems.NETHERITE_LEGGINGS, "###", "# #", "# #", '#', "ingotNetherite"));
        registerCrafting(registry, shaped(1, ModItems.NETHERITE_PICKAXE, "###", " S ", " S ", '#', "ingotNetherite", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.NETHERITE_SHOVEL, "#", "S", "S", '#', "ingotNetherite", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.NETHERITE_SWORD, "#", "#", "S", '#', "ingotNetherite", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.PLATINUM_AXE, "##", "#S", " S", '#', "ingotPlatinum", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.PLATINUM_BLOCK, "###", "###", "###", '#', "ingotPlatinum"));
        registerCrafting(registry, shaped(1, ModItems.PLATINUM_BOOTS, "# #", "# #", '#', "ingotPlatinum"));
        registerCrafting(registry, shaped(1, ModItems.PLATINUM_CHESTPLATE, "# #", "###", "###", '#', "ingotPlatinum"));
        registerCrafting(registry, shaped(1, ModItems.PLATINUM_HELMET, "###", "# #", '#', "ingotPlatinum"));
        registerCrafting(registry, shaped(1, ModItems.PLATINUM_HOE, "##", " S", " S", '#', "ingotPlatinum", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.PLATINUM_INGOT, "###", "###", "###", '#', "nuggetPlatinum"));
        registerCrafting(registry, shaped(1, ModItems.PLATINUM_LEGGINGS, "###", "# #", "# #", '#', "ingotPlatinum"));
        registerCrafting(registry, shaped(1, ModItems.PLATINUM_PICKAXE, "###", " S ", " S ", '#', "ingotPlatinum", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.PLATINUM_SHOVEL, "#", "S", "S", '#', "ingotPlatinum", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.PLATINUM_SWORD, "#", "#", "S", '#', "ingotPlatinum", 'S', "stickWood"));
        registerCrafting(registry, shaped(4, ModItems.POLISHED_BASALT, "##", "##", '#', ModItems.BASALT));
        registerCrafting(registry, shaped(4, ModItems.POLISHED_BLACKSTONE, "##", "##", '#', ModItems.BLACKSTONE));
        registerCrafting(registry, shaped(1, ModItems.POPPY_SWORD, "#", "#", "S", '#', new ItemStack(Blocks.RED_FLOWER, 1, 0), 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_AXE, "##", "#S", " S", '#', "gemRuby", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_BLOCK, "###", "###", "###", '#', "gemRuby"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_BOOTS, "# #", "# #", '#', "gemRuby"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_CHESTPLATE, "# #", "###", "###", '#', "gemRuby"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_HELMET, "###", "# #", '#', "gemRuby"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_HOE, "##", " S", " S", '#', "gemRuby", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_LEGGINGS, "###", "# #", "# #", '#', "gemRuby"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_PICKAXE, "###", " S ", " S ", '#', "gemRuby", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_SHOVEL, "#", "S", "S", '#', "gemRuby", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.RUBY_SWORD, "#", "#", "S", '#', "gemRuby", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.SAPPHIRE_AXE, "##", "#S", " S", '#', "gemSapphire", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.SAPPHIRE_BLOCK, "###", "###", "###", '#', "gemSapphire"));
        registerCrafting(registry, shaped(1, ModItems.SAPPHIRE_BOOTS, "# #", "# #", '#', "gemSapphire"));
        registerCrafting(registry, shaped(1, ModItems.SAPPHIRE_CHESTPLATE, "# #", "###", "###", '#', "gemSapphire"));
        registerCrafting(registry, shaped(1, ModItems.SAPPHIRE_HELMET, "###", "# #", '#', "gemSapphire"));
        registerCrafting(registry, shaped(1, ModItems.SAPPHIRE_HOE, "##", " S", " S", '#', "gemSapphire", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.SAPPHIRE_LEGGINGS, "###", "# #", "# #", '#', "gemSapphire"));
        registerCrafting(registry, shaped(1, ModItems.SAPPHIRE_PICKAXE, "###", " S ", " S ", '#', "gemSapphire", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.SAPPHIRE_SHOVEL, "#", "S", "S", '#', "gemSapphire", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, ModItems.SAPPHIRE_SWORD, "#", "#", "S", '#', "gemSapphire", 'S', "stickWood"));
        registerCrafting(registry, shaped(1, Blocks.SNOW_LAYER, "##", '#', Items.SNOWBALL));
        registerCrafting(registry, shaped(1, Blocks.SOUL_SAND, "##", "##", '#', ModItems.ASH_PILE));
        registerCrafting(registry, shaped(1, ModItems.WOOD_BOOTS, "# #", "# #", '#', "plankWood"));
        registerCrafting(registry, shaped(1, ModItems.WOOD_CHESTPLATE, "# #", "###", "###", '#', "plankWood"));
        registerCrafting(registry, shaped(1, ModItems.WOOD_HELMET, "###", "# #", '#', "plankWood"));
        registerCrafting(registry, shaped(1, ModItems.WOOD_LEGGINGS, "###", "# #", "# #", '#', "plankWood"));
        //custom recipes
        registerCrafting(registry, new CactusArmorRecipe(new ResourceLocation(Constants.MODID, Constants.NAME), ModItems.CACTUS_BOOTS, "# #", "# #", '#', "blockCactus"));
        registerCrafting(registry, new CactusArmorRecipe(new ResourceLocation(Constants.MODID, Constants.NAME), ModItems.CACTUS_CHESTPLATE, "# #", "###", "###", '#', "blockCactus"));
        registerCrafting(registry, new CactusArmorRecipe(new ResourceLocation(Constants.MODID, Constants.NAME), ModItems.CACTUS_HELMET, "###", "# #", '#', "blockCactus"));
        registerCrafting(registry, new CactusArmorRecipe(new ResourceLocation(Constants.MODID, Constants.NAME), ModItems.CACTUS_LEGGINGS, "###", "# #", "# #", '#', "blockCactus"));
        //edit vanilla recipes
        removeCrafting(registry, "end_rod", shaped(4, Blocks.END_ROD, "R", "C", 'R', ModItems.BLESTEM_ROD, 'C', Items.CHORUS_FRUIT_POPPED));
        removeCrafting(registry, "stone_slab", shaped(6, Blocks.STONE_SLAB, "###", '#', ModItems.COMPRESSED_STONE));
        Constants.LOGGER.info("^^^ These are intended overrides, done to either change the recipes, or to set their priorities further back");
    }

    static void registerCrafting(@Nonnull IForgeRegistry<IRecipe> registry, @Nonnull IRecipe recipe) {
        registry.register(recipe.setRegistryName(generateName(registry, recipe instanceof IShapedRecipe
                ? "shaped" : "shapeless", recipe.getRecipeOutput())));
    }

    static void removeCrafting(@Nonnull IForgeRegistry<IRecipe> registry, @Nonnull String idToRemove, @Nonnull IRecipe replacement) {
        if(registry instanceof IForgeRegistryModifiable) {
            ((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation(idToRemove));
            registry.register(replacement.setRegistryName(new ResourceLocation(idToRemove)));
        }
    }

    static void registerSmelting() {
        GameRegistry.addSmelting(Items.APPLE, new ItemStack(ModItems.BAKED_APPLE), 0.35f);
        GameRegistry.addSmelting(ModItems.BLACKSTONE_BRICKS, new ItemStack(ModItems.BLACKSTONE_CRACKED), 0.1f);
        GameRegistry.addSmelting(ModItems.BONE_ORE, new ItemStack(Items.BONE), 0.1f);
        GameRegistry.addSmelting(Items.EGG, new ItemStack(ModItems.COOKED_EGG), 0.1f);
        GameRegistry.addSmelting(ModItems.COMPRESSED_RED_SANDSTONE, new ItemStack(ModItems.CRACKED_COMPRESSED_RED_SANDSTONE), 0.1f);
        GameRegistry.addSmelting(ModItems.COMPRESSED_SANDSTONE, new ItemStack(ModItems.CRACKED_COMPRESSED_SANDSTONE), 0.1f);
        GameRegistry.addSmelting(Blocks.NETHER_BRICK, new ItemStack(ModItems.CRACKED_NETHER_BRICK), 0.1f);
        GameRegistry.addSmelting(ModItems.NETHER_DIAMOND_ORE, new ItemStack(Items.DIAMOND), 1);
        GameRegistry.addSmelting(ModItems.END_LAPIS_ORE, new ItemStack(Items.DYE, 1, 4), 0.2f);
        GameRegistry.addSmelting(ModItems.NETHER_EMERALD_ORE, new ItemStack(Items.EMERALD), 1);
        GameRegistry.addSmelting(ModItems.RAW_EGG, new ItemStack(ModItems.FRIED_EGG), 0.1f);
        GameRegistry.addSmelting(ModItems.GRAVEL_GOLD_ORE, new ItemStack(Items.GOLD_INGOT), 1);
        GameRegistry.addSmelting(ModItems.NETHER_GOLD_ORE, new ItemStack(Items.GOLD_INGOT), 1);
        GameRegistry.addSmelting(ModItems.GRAVEL_IRON_ORE, new ItemStack(Items.IRON_INGOT), 0.7f);
        GameRegistry.addSmelting(ModItems.MAGIC_ORE, new ItemStack(ModItems.MAGIC_DUST), 0.7f);
        GameRegistry.addSmelting(ModItems.MICA_ORE, new ItemStack(ModItems.MICA_DUST), 0.3f);
        GameRegistry.addSmelting(ModItems.ANCIENT_DEBRIS, new ItemStack(ModItems.NETHERITE_SCRAP), 2);
        GameRegistry.addSmelting(Blocks.OBSIDIAN, new ItemStack(ModItems.OBSIDIAN_GLASS), 0.1f);
        GameRegistry.addSmelting(ModItems.PLATINUM_ORE, new ItemStack(ModItems.PLATINUM_INGOT), 1);
        GameRegistry.addSmelting(ModItems.GRAVEL_QUARTZ_ORE, new ItemStack(Items.QUARTZ), 0.2f);
        GameRegistry.addSmelting(ModItems.QUARTZ_ORE, new ItemStack(Items.QUARTZ), 0.2f);
        GameRegistry.addSmelting(ModItems.RUBY_ORE, new ItemStack(ModItems.RUBY), 1);
        GameRegistry.addSmelting(ModItems.SAPPHIRE_ORE, new ItemStack(ModItems.SAPPHIRE), 1);
    }

    @SubscribeEvent
    public static void registerFurnaceFuels(@Nonnull FurnaceFuelBurnTimeEvent event) {
        final @Nullable Item item = event.getItemStack().getItem();
        if(item == ModItems.SUNSTONE) event.setBurnTime(6400 * 200);
    }

    static void registerBrewing() {
        PotionHelper.addMix(PotionTypes.WATER, Ingredient.fromItems(ModItems.ASH_PILE), PotionTypes.MUNDANE);
        PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromItems(ModItems.ASH_PILE), ModPotionTypes.BLUE_FIRE_RESISTANCE);
        PotionHelper.addMix(ModPotionTypes.BLUE_FIRE_RESISTANCE, Ingredient.fromItems(Items.REDSTONE), ModPotionTypes.LONG_BLUE_FIRE_RESISTANCE);
    }

    //constructs a new shapeless recipe
    @Nonnull static IRecipe shapeless(int amount, @Nonnull Item output, @Nonnull Object... recipe) { return shapeless(new ItemStack(output, amount), recipe); }
    @Nonnull static IRecipe shapeless(int amount, int meta, @Nonnull Item output, @Nonnull Object... recipe) { return shapeless(new ItemStack(output, amount, meta), recipe); }
    @Nonnull static IRecipe shapeless(int amount, @Nonnull Block output, @Nonnull Object... recipe) { return shapeless(new ItemStack(output, amount), recipe); }
    @Nonnull static IRecipe shapeless(int amount, int meta, @Nonnull Block output, @Nonnull Object... recipe) { return shapeless(new ItemStack(output, amount, meta), recipe); }
    @Nonnull static IRecipe shapeless(@Nonnull ItemStack output, @Nonnull Object... recipe) {
        return new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.NAME), output, recipe);
    }

    //constructs a new shaped recipe
    @Nonnull static IRecipe shaped(int amount, @Nonnull Item output, @Nonnull Object... recipe) { return shaped(new ItemStack(output, amount), recipe); }
    @Nonnull static IRecipe shaped(int amount, int meta, @Nonnull Item output, @Nonnull Object... recipe) { return shaped(new ItemStack(output, amount, meta), recipe); }
    @Nonnull static IRecipe shaped(int amount, @Nonnull Block output, @Nonnull Object... recipe) { return shaped(new ItemStack(output, amount), recipe); }
    @Nonnull static IRecipe shaped(int amount, int meta, @Nonnull Block output, @Nonnull Object... recipe) { return shaped(new ItemStack(output, amount, meta), recipe); }
    @Nonnull static IRecipe shaped(@Nonnull ItemStack output, @Nonnull Object... recipe) {
        return new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.NAME), output, recipe);
    }

    //generates an unused name for a crafting recipe
    @Nonnull
    public static ResourceLocation generateName(@Nonnull IForgeRegistry<IRecipe> registry, @Nonnull String postfix, @Nonnull ItemStack output) {
        final String baseRecipeID = output.getTranslationKey() + '.' + postfix + '.';

        //resolves duplicate registry names
        int i = 1;
        while(registry.containsKey(new ResourceLocation(Constants.MODID, baseRecipeID + i))) i++;

        //returns an unused name
        return new ResourceLocation(Constants.MODID, baseRecipeID + i);
    }
}
