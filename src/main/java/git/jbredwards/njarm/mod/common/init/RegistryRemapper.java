package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class RegistryRemapper
{
    @SubscribeEvent
    public static void remapBlocks(@Nonnull RegistryEvent.MissingMappings<Block> event) {
        event.getAllMappings().forEach(mapping -> {
            //njarm1
            if(mapping.key.getNamespace().equals("ruby_mod")) {
                switch(mapping.key.getPath()) {
                    case "ruby_ore": mapping.remap(ModBlocks.RUBY_ORE); break;
                    case "ruby_block": mapping.remap(ModBlocks.RUBY_BLOCK); break;
                    case "obsidian_block": mapping.remap(ModBlocks.OBSIDIAN_BLOCK); break;
                }
            }

            //njarm2
            else if(mapping.key.getNamespace().equals("njarm")) {
                switch(mapping.key.getPath()) {
                    case "lapis_ore": mapping.remap(ModBlocks.END_LAPIS_ORE); break;
                    case "gold_ore": mapping.remap(ModBlocks.NETHER_GOLD_ORE); break;
                    case "diamond_ore": mapping.remap(ModBlocks.NETHER_DIAMOND_ORE); break;
                    case "emerald_ore": mapping.remap(ModBlocks.NETHER_EMERALD_ORE); break;
                    case "sunstone_ore": mapping.remap(Blocks.QUARTZ_ORE); break;
                    case "nether_brick": mapping.remap(ModBlocks.CRACKED_NETHER_BRICK); break;
                }
            }
        });
    }

    @SubscribeEvent
    public static void remapEntities(@Nonnull RegistryEvent.MissingMappings<EntityEntry> event) {
        event.getAllMappings().forEach(mapping -> {
            //njarm1
            if(mapping.key.getNamespace().equals("ruby_mod")) {

            }

            //njarm2
            else if(mapping.key.getNamespace().equals("njarm")) {
                switch(mapping.key.getPath()) {
                    case "item_netherite": mapping.remap(ForgeRegistries.ENTITIES.getValue(new ResourceLocation("item"))); break;
                }
            }
        });
    }

    @SubscribeEvent
    public static void remapItems(@Nonnull RegistryEvent.MissingMappings<Item> event) {
        event.getAllMappings().forEach(mapping -> {
            //njarm1
            if(mapping.key.getNamespace().equals("ruby_mod")) {
                switch(mapping.key.getPath()) {
                    case "ruby": mapping.remap(ModItems.RUBY); break;
                }
            }

            //njarm2
            else if(mapping.key.getNamespace().equals("njarm")) {
                switch(mapping.key.getPath()) {
                    case "lapis_ore": mapping.remap(ModItems.END_LAPIS_ORE); break;
                    case "gold_ore": mapping.remap(ModItems.NETHER_GOLD_ORE); break;
                    case "diamond_ore": mapping.remap(ModItems.NETHER_DIAMOND_ORE); break;
                    case "emerald_ore": mapping.remap(ModItems.NETHER_EMERALD_ORE); break;
                    case "sunstone_ore": mapping.remap(Item.getItemFromBlock(Blocks.QUARTZ_ORE)); break;
                    case "nether_brick": mapping.remap(ModItems.CRACKED_NETHER_BRICK); break;

                    case "rupee_shard": mapping.remap(ModItems.RUPEE); break;
                    case "heart": mapping.ignore(); break;
                    case "electricity": mapping.remap(ModItems.CHARGED_SUNSTONE); break;
                    case "light": mapping.remap(ModItems.ENDER_STAR); break;
                    case "dark": mapping.remap(ModItems.BLESTEM_HEART); break;
                }
            }
        });
    }
}
