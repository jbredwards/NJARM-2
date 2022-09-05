package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
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
                    case "fire_sponge": mapping.remap(ModBlocks.MAGIC_SPONGE); break;
                    //pots are being rethought, might be added back later
                    case "tall_pot":
                    case "tall_pot_nether":
                    case "tall_pot_spawner":
                    case "tall_pot_pryamid": //"pryamid"...
                    case "rupee_pot":
                    case "diamond_pot":
                    case "iron_pot":
                    case "gold_pot":
                        mapping.ignore(); break;
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
                    case "dark_arrow": mapping.remap(ModEntities.BLESTEM_ARROW); break;
                    case "pigman_njarm": mapping.remap(ModEntities.PIGMAN); break;
                    //replaced with asm, swap back to vanilla item entities
                    case "item_floating": case "item_netherite":
                        mapping.remap(ForgeRegistries.ENTITIES
                            .getValue(new ResourceLocation("item"))); break;
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
                    case "fire_sponge": mapping.remap(ModItems.MAGIC_SPONGE); break;

                    case "rupee_shard": mapping.remap(ModItems.RUPEE); break;
                    case "electricity": mapping.remap(ModItems.CHARGED_SUNSTONE); break;
                    case "light": mapping.remap(ModItems.ENDER_STAR); break;
                    case "dark": mapping.remap(ModItems.BLESTEM_ROD); break;
                    case "dark_arrow": mapping.remap(ModItems.BLESTEM_ARROW); break;
                    case "staff": mapping.remap(ModItems.STAFF_BASE); break;
                    case "sap": mapping.remap(ModItems.SAP_BALL); break;
                    case "blood": mapping.remap(ModItems.BLOOD_BALL); break;
                    case "undying_bauble": mapping.remap(Items.TOTEM_OF_UNDYING); break;

                    //removed
                    case "heart":
                    case "tall_pot":
                    case "tall_pot_nether":
                    case "tall_pot_spawner":
                    case "tall_pot_pryamid":
                    case "rupee_pot":
                    case "diamond_pot":
                    case "iron_pot":
                    case "gold_pot":
                        mapping.ignore();
                }
            }
        });
    }

    @SubscribeEvent
    public static void ignoreMissingSounds(@Nonnull RegistryEvent.MissingMappings<SoundEvent> event) {
        event.getAllMappings().forEach(mapping -> {
            switch(mapping.key.getNamespace()) {
                case "ruby_mod":
                case "njarm":
                    mapping.ignore();
            }
        });
    }
}
