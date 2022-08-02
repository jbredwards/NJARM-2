package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
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
                    case "rupee_shard": mapping.remap(ModItems.RUPEE); break;
                    case "heart": mapping.ignore(); break;
                }
            }
        });
    }
}
