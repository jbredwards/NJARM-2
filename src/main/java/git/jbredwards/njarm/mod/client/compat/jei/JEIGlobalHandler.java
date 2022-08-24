package git.jbredwards.njarm.mod.client.compat.jei;

import com.google.common.collect.ImmutableList;
import git.jbredwards.njarm.mod.common.config.block.NetherCoreConfig;
import git.jbredwards.njarm.mod.common.config.item.ChargedSunstoneConfig;
import git.jbredwards.njarm.mod.common.init.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IVanillaRecipeFactory;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
@JEIPlugin
public final class JEIGlobalHandler implements IModPlugin
{
    @Override
    public void register(@Nonnull IModRegistry registry) {
        if(NetherCoreConfig.getAltReactorBehavior())
            registry.addIngredientInfo(new ItemStack(ModItems.GLOWING_OBSIDIAN), VanillaTypes.ITEM, "jei.njarm.desc.glowingObsidian");

        if(ChargedSunstoneConfig.fromLightning())
            registry.addIngredientInfo(new ItemStack(ModItems.CHARGED_SUNSTONE), VanillaTypes.ITEM, "jei.njarm.desc.sunstoneCharged");

        registry.addIngredientInfo(new ItemStack(ModItems.SUNSTONE), VanillaTypes.ITEM, "jei.njarm.desc.sunstone");
        registerRepairRecipes(registry);
    }

    static void registerRepairRecipes(@Nonnull IModRegistry registry) {
        final List<IRecipeWrapper> recipes = new ArrayList<>();

        generateRepairRecipe(recipes, registry.getJeiHelpers().getVanillaRecipeFactory(), ModItems.RUBY,
                ModItems.RUBY_HELMET, ModItems.RUBY_CHESTPLATE, ModItems.RUBY_LEGGINGS, ModItems.RUBY_BOOTS,
                ModItems.RUBY_AXE, ModItems.RUBY_HOE, ModItems.RUBY_PICKAXE, ModItems.RUBY_SHOVEL, ModItems.RUBY_SWORD);
        generateRepairRecipe(recipes, registry.getJeiHelpers().getVanillaRecipeFactory(), ModItems.SAPPHIRE,
                ModItems.SAPPHIRE_HELMET, ModItems.SAPPHIRE_CHESTPLATE, ModItems.SAPPHIRE_LEGGINGS, ModItems.SAPPHIRE_BOOTS,
                ModItems.SAPPHIRE_AXE, ModItems.SAPPHIRE_BOW, ModItems.SAPPHIRE_HOE, ModItems.SAPPHIRE_PICKAXE, ModItems.SAPPHIRE_SHOVEL, ModItems.SAPPHIRE_SWORD);
        generateRepairRecipe(recipes, registry.getJeiHelpers().getVanillaRecipeFactory(), ModItems.PLATINUM_INGOT,
                ModItems.PLATINUM_HELMET, ModItems.PLATINUM_CHESTPLATE, ModItems.PLATINUM_LEGGINGS, ModItems.PLATINUM_BOOTS,
                ModItems.PLATINUM_AXE, ModItems.PLATINUM_HOE, ModItems.PLATINUM_PICKAXE, ModItems.PLATINUM_SHOVEL, ModItems.PLATINUM_SWORD);
        generateRepairRecipe(recipes, registry.getJeiHelpers().getVanillaRecipeFactory(), ModItems.OBSIDIAN_INGOT,
                ModItems.OBSIDIAN_HELMET, ModItems.OBSIDIAN_CHESTPLATE, ModItems.OBSIDIAN_LEGGINGS, ModItems.OBSIDIAN_BOOTS,
                ModItems.OBSIDIAN_AXE, ModItems.OBSIDIAN_HOE, ModItems.OBSIDIAN_PICKAXE, ModItems.OBSIDIAN_SHOVEL, ModItems.OBSIDIAN_SWORD);
        generateRepairRecipe(recipes, registry.getJeiHelpers().getVanillaRecipeFactory(), ModItems.NETHERITE_INGOT,
                ModItems.NETHERITE_HELMET, ModItems.NETHERITE_CHESTPLATE, ModItems.NETHERITE_LEGGINGS, ModItems.NETHERITE_BOOTS,
                ModItems.NETHERITE_AXE, ModItems.NETHERITE_HOE, ModItems.NETHERITE_PICKAXE, ModItems.NETHERITE_SHOVEL, ModItems.NETHERITE_SWORD);
        generateRepairRecipe(recipes, registry.getJeiHelpers().getVanillaRecipeFactory(), Item.getItemFromBlock(Blocks.CACTUS),
                ModItems.CACTUS_HELMET, ModItems.CACTUS_CHESTPLATE, ModItems.CACTUS_LEGGINGS, ModItems.CACTUS_BOOTS,
                ModItems.CACTUS_AXE, ModItems.CACTUS_HOE, ModItems.CACTUS_PICKAXE, ModItems.CACTUS_SHOVEL, ModItems.CACTUS_SWORD);
        generateRepairRecipe(recipes, registry.getJeiHelpers().getVanillaRecipeFactory(), Item.getItemFromBlock(Blocks.PLANKS),
                ModItems.WOOD_HELMET, ModItems.WOOD_CHESTPLATE, ModItems.WOOD_LEGGINGS, ModItems.WOOD_BOOTS);
        generateRepairRecipe(recipes, registry.getJeiHelpers().getVanillaRecipeFactory(), Items.FEATHER, ModItems.FEATHER_BOOTS);
        generateRepairRecipe(recipes, registry.getJeiHelpers().getVanillaRecipeFactory(), Item.getItemFromBlock(Blocks.RED_FLOWER), ModItems.POPPY_SWORD);


        registry.addRecipes(recipes, VanillaRecipeCategoryUid.ANVIL);
    }

    static void generateRepairRecipe(@Nonnull List<IRecipeWrapper> recipes, @Nonnull IVanillaRecipeFactory factory, @Nonnull Item repairItem, @Nonnull Item... tools) {
        final ItemStack repairStack = new ItemStack(repairItem);

        for(Item tool : tools) {
            final ItemStack damaged1 = new ItemStack(tool, 1, tool.getMaxDamage());
            final ItemStack damaged2 = new ItemStack(tool, 1, tool.getMaxDamage() * 3 / 4);
            final ItemStack damaged3 = new ItemStack(tool, 1, tool.getMaxDamage() * 2 / 4);

            recipes.add(factory.createAnvilRecipe(damaged1, ImmutableList.of(repairStack), ImmutableList.of(damaged2)));
            recipes.add(factory.createAnvilRecipe(damaged2, ImmutableList.of(damaged2), ImmutableList.of(damaged3)));
        }
    }
}
