package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

/**
 * Holds this mod's items
 * @author jbred
 *
 */
public final class ModItems
{
    @Nonnull public static final NonNullList<Item> INIT = NonNullList.create();

    //items
    @Nonnull public static final Item RUBY = register("ruby", new Item());
    @Nonnull public static final Item SAPPHIRE = register("sapphire", new Item());
    @Nonnull public static final ItemEggShell EGG_SHELL = register("egg_shell", new ItemEggShell());
    @Nonnull public static final ItemGlint MAGIC_DUST = register("magic_dust", new ItemGlint());

    //item blocks
    @Nonnull public static final ItemBlock RUBY_BLOCK = register("ruby_block", new ItemBlock(ModBlocks.RUBY_BLOCK));
    @Nonnull public static final ItemBlock SAPPHIRE_BLOCK = register("sapphire_block", new ItemBlock(ModBlocks.SAPPHIRE_BLOCK));

    //register ores
    public static void registerOres() {
        OreDictionary.registerOre("dustMagic", MAGIC_DUST);
        OreDictionary.registerOre("gemRuby", RUBY);
        OreDictionary.registerOre("gemSapphire", SAPPHIRE);
        OreDictionary.registerOre("blockRuby", RUBY_BLOCK);
        OreDictionary.registerOre("blockSapphire", SAPPHIRE_BLOCK);
    }

    //register item
    @Nonnull
    private static <T extends Item> T register(@Nonnull String name, @Nonnull T item) {
        INIT.add(item.setRegistryName(Constants.MODID, name)
                .setTranslationKey(Constants.MODID + "." + name)
                .setCreativeTab(CreativeTab.INSTANCE)
        );

        return item;
    }
}
