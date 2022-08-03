package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
                    case "rupee_shard": mapping.remap(ModItems.RUPEE); break;
                    case "heart": mapping.ignore(); break;
                }
            }
        });
    }
}
