package git.jbredwards.njarm.mod.common.config;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.block.*;
import git.jbredwards.njarm.mod.common.config.client.*;
import git.jbredwards.njarm.mod.common.config.core.*;
import git.jbredwards.njarm.mod.common.config.item.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Config(modid = Constants.MODID)
@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class ConfigHandler
{
    @Nonnull
    private static final NonNullList<IConfig> CONFIGS = NonNullList.create();

    @Config.Name("blocks")
    @Config.Comment("This mod's blocks.")
    @Nonnull public static final BlockConfig blockCfg = register(new BlockConfig(
            new BlueFireConfig(new String[] {"minecraft:soul_sand"}, "Math.max(2, health / 4)", "Math.max(2, health / 32)", "Math.max(2, health / 4)", "Math.max(2, health / 32)", false),
            new FoodCrateConfig(new String[] {"{Type:\"poisonous_potato\",Effects:[{Id:19,Duration:65,Ambient:1b}]}", "{Type:\"golden_apple\",Effects:[{Id:10,Duration:65,Ambient:1b}]}", "{Type:\"golden_carrot\",Effects:[{Id:16,Duration:65,Ambient:1b}]}"}, false)
    ));

    @Config.Name("client")
    @Config.Comment("Client-side things, mostly rendering. Changes made to client settings are not seen by other players.")
    @Nonnull public static final ClientConfig clientCfg = register(new ClientConfig(
            new RenderingConfig(true, true, 60)
    ));

    @Config.Name("items")
    @Config.Comment("This mod's items.")
    @Nonnull public static final ItemConfig itemCfg = register(new ItemConfig(
            new EggShellsConfig(true, 0, 100, new String[] {"minecraft:egg"}, new String[] {"minecraft:egg"})
    ));

    //create a new config category while also adding it to the internal list
    @Nonnull
    private static <T extends IConfig> T register(@Nonnull T cfg) {
        CONFIGS.add(cfg);
        return cfg;
    }

    //initialize config categories
    public void onFMLInit() { CONFIGS.forEach(IConfig::onFMLInit); }

    //write in-game changes to disk & update config categories
    @SubscribeEvent
    public static void onUpdate(@Nonnull ConfigChangedEvent.OnConfigChangedEvent event) {
        if(Constants.MODID.equals(event.getModID())) {
            ConfigManager.sync(Constants.MODID, Config.Type.INSTANCE);
            CONFIGS.forEach(IConfig::onUpdate);
        }
    }
}
