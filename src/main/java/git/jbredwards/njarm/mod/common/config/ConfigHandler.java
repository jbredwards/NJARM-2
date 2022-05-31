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
            new RenderingConfig(true, true, 60, 7, new String[] {
                    "{Biome:\"plains\",Surface:4501493,Fog:4501493}",
                    "{Biome:\"desert\",Surface:3319192,Fog:3319192}",
                    "{Biome:\"extreme_hills\",Surface:31735,Fog:31735}",
                    "{Biome:\"forest\",Surface:2004978,Fog:2004978}",
                    "{Biome:\"mutated_plains\",Surface:4501493,Fog:4501493}",
                    "{Biome:\"mutated_forest\",Surface:2139084,Fog:2139084}",
                    "{Biome:\"taiga\",Surface:2650242,Fog:2650242}",
                    "{Biome:\"taiga_hills\",Surface:1993602,Fog:1993602}",
                    "{Biome:\"swampland\",Surface:6388580,Fog:5006681}",
                    "{Biome:\"mutated_swampland\",Surface:6388580,Fog:5005654}",
                    "{Biome:\"river\",Surface:34047,Fog:34047}",
                    "{Biome:\"hell\",Surface:9460055,Fog:9460055}",
                    "{Biome:\"sky\",Surface:6443678,Fog:6443678}",
                    "{Biome:\"frozen_river\",Surface:1594256,Fog:1594256}",
                    "{Biome:\"ice_flats\",Surface:1332635,Fog:1332635}",
                    "{Biome:\"mutated_ice_flats\",Surface:1332635,Fog:1332635}",
                    "{Biome:\"ice_mountains\",Surface:1136295,Fog:1136295}",
                    "{Biome:\"mushroom_island\",Surface:9079191,Fog:9079191}",
                    "{Biome:\"mushroom_island_shore\",Surface:8487315,Fog:8487315}",
                    "{Biome:\"beaches\",Surface:1408171,Fog:1408171}",
                    "{Biome:\"desert_hills\",Surface:1735329,Fog:1735329}",
                    "{Biome:\"forest_hills\",Surface:355281,Fog:355281}",
                    "{Biome:\"taiga_hills\",Surface:2319747,Fog:2319747}",
                    "{Biome:\"smaller_extreme_hills\",Surface:285909,Fog:285909}",
                    "{Biome:\"jungle\",Surface:1352389,Fog:1352389}",
                    "{Biome:\"jungle_hills\",Surface:1810136,Fog:1810136}",
                    "{Biome:\"mutated_jungle\",Surface:1810136,Fog:1810136}",
                    "{Biome:\"jungle_edge\",Surface:887523,Fog:887523}",
                    "{Biome:\"mutated_jungle_edge\",Surface:887523,Fog:887523}",
                    "{Biome:\"stone_beach\",Surface:878523,Fog:878523}",
                    "{Biome:\"cold_beach\",Surface:1336229,Fog:1336229}",
                    "{Biome:\"birch_forest\",Surface:423886,Fog:423886}",
                    "{Biome:\"mutated_birch_forest\",Surface:423886,Fog:423886}",
                    "{Biome:\"birch_forest_hills\",Surface:685252,Fog:685252}",
                    "{Biome:\"mutated_birch_forest_hills\",Surface:685252,Fog:685252}",
                    "{Biome:\"roofed_forest\",Surface:3894481,Fog:3894481}",
                    "{Biome:\"mutated_roofed_forest\",Surface:3894481,Fog:3894481}",
                    "{Biome:\"taiga_cold\",Surface:2121347,Fog:2121347}",
                    "{Biome:\"mutated_taiga_cold\",Surface:2121347,Fog:2121347}",
                    "{Biome:\"taiga_cold_hills\",Surface:2382712,Fog:2382712}",
                    "{Biome:\"redwood_taiga\",Surface:2977143,Fog:2977143}",
                    "{Biome:\"mutated_redwood_taiga\",Surface:2977143,Fog:2977143}",
                    "{Biome:\"mutated_redwood_taiga_hills\",Surface:2977143,Fog:2977143}",
                    "{Biome:\"redwood_taiga_hills\",Surface:2646904,Fog:2646904}",
                    "{Biome:\"extreme_hills_with_trees\",Surface:943019,Fog:943019}",
                    "{Biome:\"mutated_extreme_hills\",Surface:943019,Fog:943019}",
                    "{Biome:\"mutated_extreme_hills_with_trees\",Surface:943019,Fog:943019}",
                    "{Biome:\"savanna\",Surface:2919324,Fog:2919324}",
                    "{Biome:\"savanna_rock\",Surface:2461864,Fog:2461864}",
                    "{Biome:\"mutated_savanna\",Surface:2461864,Fog:2461864}",
                    "{Biome:\"mutated_savanna_rock\",Surface:2461864,Fog:2461864}",
                    "{Biome:\"mesa\",Surface:5144449,Fog:5144449}",
                    "{Biome:\"mutated_mesa\",Surface:4816793,Fog:4816793}",
                    "{Biome:\"mesa_rock\",Surface:4816793,Fog:4816793}",
                    "{Biome:\"mesa_clear_rock\",Surface:5603486,Fog:5603486}",
                    "{Biome:\"mutated_mesa_rock\",Surface:5603486,Fog:5603486}",
                    "{Biome:\"mutated_mesa_clear_rock\",Surface:5603486,Fog:5603486}",
                    "{Biome:\"ocean\",Surface:1542100,Fog:1140144}",
                    "{Biome:\"deep_ocean\",Surface:1542100,Fog:1336229}",
                    "{Biome:\"frozen_ocean\",Surface:2453685,Fog:1526149}",
                    "{Biome:\"mutated_desert\",Surface:2004978,Fog:2004978}",
                    "{Biome:\"mutated_taiga\",Surface:2650242,Fog:2650242}"
            })
    ));

    @Config.Name("items")
    @Config.Comment("This mod's items.")
    @Nonnull public static final ItemConfig itemCfg = register(new ItemConfig(
            new EggShellsConfig(true, 0, 100, new String[] {"minecraft:egg"}, new String[] {"minecraft:egg"}),
            new EquipmentConfig(
                    "{HarvestLevel:3,Durability:3122,MiningSpeed:8.0f,AttackDamage:3.0f,Enchantability:10}"
            )
    ));

    //create a new config category while also adding it to the internal list
    @Nonnull
    private static <T extends IConfig> T register(@Nonnull T cfg) {
        CONFIGS.add(cfg);
        return cfg;
    }

    //turn config data into game data
    public static void onFMLInit() { CONFIGS.forEach(IConfig::onFMLInit); }

    //write in-game changes to disk & update config categories
    @SubscribeEvent
    public static void onUpdate(@Nonnull ConfigChangedEvent.OnConfigChangedEvent event) {
        if(Constants.MODID.equals(event.getModID())) {
            ConfigManager.sync(Constants.MODID, Config.Type.INSTANCE);
            CONFIGS.forEach(IConfig::onUpdate);
        }
    }
}
