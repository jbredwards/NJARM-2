package git.jbredwards.njarm.mod.common.config;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.block.*;
import git.jbredwards.njarm.mod.common.config.client.*;
import git.jbredwards.njarm.mod.common.config.core.*;
import git.jbredwards.njarm.mod.common.config.entity.*;
import git.jbredwards.njarm.mod.common.config.item.*;
import git.jbredwards.njarm.mod.common.config.world.*;
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
    static final NonNullList<IConfig> CONFIGS = NonNullList.create();

    @Config.LangKey("config.njarm.cfg.block")
    @Nonnull public static final BlockConfig blockCfg = register(new BlockConfig(
            new BlueFireConfig(new String[] {"soul_sand", "njarm:soul_soil"}, "Math.max(2, health / 4)", "Math.max(2, health / 32)", "Math.max(2, health / 4)", "Math.max(2, health / 32)", false),
            new BubbleColumnConfig(new String[] {"{blockId:\"minecraft:magma\"}"}, new String[] {"{blockId:\"minecraft:soul_sand\"}"}),
            new ChainConfig(true),
            new FoodCrateConfig(new String[] {"{Type:\"poisonous_potato\",Effects:[{Id:19,Duration:65,Ambient:1b}]}", "{Type:\"golden_apple\",Effects:[{Id:10,Duration:65,Ambient:1b}]}", "{Type:\"golden_carrot\",Effects:[{Id:16,Duration:65,Ambient:1b}]}"}, false),
            new FragileIceConfig(400),
            new MagicOreConfig(true, true, 1, true),
            new NetherCoreConfig(true, 17, 900, 40, 60, false, new String[] {"minecraft:zombie_pigman"},
                    "{\"type\":\"block\",\"pools\":[{\"rolls\":10,\"entries\":[{\"type\":\"item\",\"name\":\"quartz\",\"weight\":30},{\"type\":\"item\",\"name\":\"glowstone_dust\",\"weight\":30},{\"type\":\"item\",\"name\":\"cactus\",\"weight\":10},{\"type\":\"item\",\"name\":\"red_mushroom\",\"weight\":12},{\"type\":\"item\",\"name\":\"brown_mushroom\",\"weight\":12},{\"type\":\"item\",\"name\":\"pumpkin_seeds\",\"weight\":10},{\"type\":\"item\",\"name\":\"melon_seeds\",\"weight\":10},{\"type\":\"item\",\"name\":\"bow\",\"weight\":8,\"functions\":[{\"function\":\"set_damage\",\"damage\":{\"min\":0,\"max\":1}}]},{\"type\":\"item\",\"name\":\"bow\",\"weight\":2,\"functions\":[{\"function\":\"set_damage\",\"damage\":{\"min\":0,\"max\":1}},{\"function\":\"enchant_with_levels\",\"treasure\":true,\"levels\":{\"min\":0,\"max\":25}}]}]}]}"),
            new TotemOfUndyingConfig(true, 16, true, true, 16, 0.000625)));

    @Config.LangKey("config.njarm.cfg.client")
    @Nonnull public static final ClientConfig clientCfg = register(new ClientConfig(
            new RenderingConfig(true, true, 60, 4, new String[] {
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

    @Config.LangKey("config.njarm.cfg.entity")
    @Nonnull public static final EntityConfig entityCfg = register(new EntityConfig(
            new BlestemConfig(new String[] {"{Weight:10,Min:1,Max:3,BiomeTags:[end]}"}),
            new ChocolateCowConfig(new String[] {}),
            new FireCreeperConfig(new String[] {"{Weight:20,Min:1,Max:3,BiomeTags:[nether]}"}),
            new FireSkeletonConfig(new String[] {"{Weight:20,Min:1,Max:3,BiomeTags:[nether]}"}),
            new HighlandCooConfig(
                    new String[] {"{Weight:10,Min:1,Max:5,BiomeTags:[plains],ExcludeBiomes:{BiomeTags:[hot]}}"}, true,
                    new String[] {"{Color:0,BiomeTags:[cold]}"}),
            new MoobloomConfig(-1, -1, true, true,
                    new String[] {
                            "{Weight:30,Min:1,Max:3,Biomes:[mutated_forest]}",
                            "{Weight:2,Min:1,Max:1,Biomes:[plains,forest,swampland]}"
                    },
                    new String[] {
                            "{Item:\"dye\",Meta:15}", "{Item:\"njarm:egg_shell\"}"
                    },
                    new String[] {
                            "{Flower:{Name:air},Weight:5,Biomes:[plains,forest,swampland]}",
                            "{Flower:{Name:yellow_flower},Weight:30,Biomes:[plains]}",
                            "{Flower:{Name:red_flower,Properties:{type:orange_tulip}},Weight:3,Biomes:[plains]}",
                            "{Flower:{Name:red_flower,Properties:{type:red_tulip}},Weight:3,Biomes:[plains]}",
                            "{Flower:{Name:red_flower,Properties:{type:pink_tulip}},Weight:3,Biomes:[plains]}",
                            "{Flower:{Name:red_flower,Properties:{type:white_tulip}},Weight:3,Biomes:[plains]}",
                            "{Flower:{Name:red_flower,Properties:{type:poppy}},Weight:20,Biomes:[plains]}",
                            "{Flower:{Name:red_flower,Properties:{type:houstonia}},Weight:20,Biomes:[plains]}",
                            "{Flower:{Name:red_flower,Properties:{type:oxeye_daisy}},Weight:20,Biomes:[plains]}",
                            "{Flower:{Name:yellow_flower},Weight:20,Biomes:[forest]}",
                            "{Flower:{Name:red_flower,Properties:{type:poppy}},Weight:10,Biomes:[forest]}",
                            "{Flower:{Name:red_flower,Properties:{type:blue_orchid}},Weight:10,Biomes:[swampland]}",
                            "{Flower:{Name:yellow_flower},Weight:20,Biomes:[mutated_forest]}",
                            "{Flower:{Name:red_flower,Properties:{type:poppy}},Weight:20,Biomes:[mutated_forest]}",
                            "{Flower:{Name:red_flower,Properties:{type:allium}},Weight:10,Biomes:[mutated_forest]}",
                            "{Flower:{Name:red_flower,Properties:{type:houstonia}},Weight:10,Biomes:[mutated_forest]}",
                            "{Flower:{Name:red_flower,Properties:{type:red_tulip}},Weight:10,Biomes:[mutated_forest]}",
                            "{Flower:{Name:red_flower,Properties:{type:orange_tulip}},Weight:10,Biomes:[mutated_forest]}",
                            "{Flower:{Name:red_flower,Properties:{type:white_tulip}},Weight:10,Biomes:[mutated_forest]}",
                            "{Flower:{Name:red_flower,Properties:{type:pink_tulip}},Weight:10,Biomes:[mutated_forest]}",
                            "{Flower:{Name:red_flower,Properties:{type:oxeye_daisy}},Weight:10,Biomes:[mutated_forest]}"
                    },
                    new String[] {
                            "{Name:yellow_flower}",
                            "{Name:red_flower,Properties:{type:poppy}}",
                            "{Name:red_flower,Properties:{type:allium}}",
                            "{Name:red_flower,Properties:{type:houstonia}}",
                            "{Name:red_flower,Properties:{type:red_tulip}}",
                            "{Name:red_flower,Properties:{type:orange_tulip}}",
                            "{Name:red_flower,Properties:{type:white_tulip}}",
                            "{Name:red_flower,Properties:{type:pink_tulip}}",
                            "{Name:red_flower,Properties:{type:oxeye_daisy}}"
                    }
            ),
            new MudPigConfig(30, 10),
            new MummyConfig(
                    new String[] {"{Weight:200,Min:1,Max:3,Biomes:[desert,desert_hills,mutated_desert]}"},
                    new String[] {"{Weight:72,Empty:true}","{Id:11,Duration:200}","{Id:12,Duration:200}","{Id:10,Duration:200}","{Id:13,Duration:200}","{Id:26,Duration:200}","{Id:3,Duration:200}","{Id:8,Duration:200}","{Id:16,Duration:200}"},
                    new String[] {"{Weight:1,Empty:true}","{Weight:4,id:\"bow\",Count:1}","{Weight:4,id:\"golden_sword\",Count:1}","{Weight:1,id:\"diamond_sword\",Count:1}","{Weight:1,id:\"njarm:ruby_sword\",Count:1}","{Weight:1,id:\"njarm:sapphire_sword\",Count:1}"}
            ),
            new SoulSkeletonConfig(new String[] {"{Weight:10,Min:1,Max:3,BiomeTags:[nether]}"}))
    );

    @Config.LangKey("config.njarm.cfg.item")
    @Nonnull public static final ItemConfig itemCfg = register(new ItemConfig(
            new BonusHeartConfig(2, true, true),
            new ChargedSunstoneConfig(true, true, false, true, true, true, 3, true),
            new EggShellsConfig(true, 0, 100, new String[] {"minecraft:egg"}, new String[] {"minecraft:egg", "njarm:cooked_egg"}),
            new EquipmentConfig(
                    "{Durability:66,ArmorValues:[3,6,8,3],Enchantability:10,Toughness:2f}", //ruby
                    "{Durability:33,ArmorValues:[3,6,8,3],Enchantability:10,Toughness:2f}", true, false, //sapphire
                    "{Durability:8,ArmorValues:[1,2,3,1],Enchantability:5,Toughness:0f}", //wood
                    "{Durability:33,ArmorValues:[3,6,8,3],Enchantability:25,Toughness:4f}", //platinum
                    "{Durability:37,ArmorValues:[3,6,8,3],Enchantability:15,Toughness:3f}", //netherite
                    "{Durability:37,ArmorValues:[3,6,8,3],Enchantability:10,Toughness:4f}", true, //obsidian
                    "{Durability:-1,ArmorValues:[3,6,8,3],Enchantability:25,Toughness:4f}", //bedrock
                    "{Durability:7,ArmorValues:[1,1,1,1],Enchantability:10,Toughness:0f}", //feather
                    "{Durability:15,ArmorValues:[1,2,3,1],Enchantability:5,Toughness:0f}", 1, //cactus
                    "{Durability:7,ArmorValues:[0,0,0,0],Enchantability:10,Toughness:0f}", 40, //crown
                    "{HarvestLevel:4,Durability:3122,MiningSpeed:8f,AttackDamage:3f,Enchantability:10}", //ruby
                    "{HarvestLevel:3,Durability:1561,MiningSpeed:8f,AttackDamage:3f,Enchantability:10}", 384, true, true, //sapphire
                    "{HarvestLevel:3,Durability:781,MiningSpeed:11f,AttackDamage:3f,Enchantability:25}", //platinum
                    "{HarvestLevel:4,Durability:2031,MiningSpeed:9f,AttackDamage:4f,Enchantability:15}", //netherite
                    "{HarvestLevel:0,Durability:29,MiningSpeed:6f,AttackDamage:2f,Enchantability:9999}", //poppy
                    "{HarvestLevel:4,Durability:1041,MiningSpeed:10f,AttackDamage:5f,Enchantability:15}", //obsidian
                    "{HarvestLevel:4,Durability:-1,MiningSpeed:12f,AttackDamage:5f,Enchantability:25}", //bedrock
                    "{HarvestLevel:0,Durability:131,MiningSpeed:5f,AttackDamage:2f,Enchantability:5}", true), //cactus
            new ResistantItemsConfig(
                    new String[] {"njarm:ash_pile", "soul_sand", "njarm:soul_soil"},
                    new String[] {"njarm:charged_sunstone", "njarm:sunstone"},
                    new String[] {"njarm:ancient_debris", "njarm:charged_sunstone", "njarm:netherite_axe", "njarm:netherite_block", "njarm:netherite_boots", "njarm:netherite_chestplate", "njarm:netherite_helmet", "njarm:netherite_hoe", "njarm:netherite_ingot", "njarm:netherite_leggings", "njarm:netherite_nugget", "njarm:netherite_pickaxe", "njarm:netherite_shovel", "njarm:netherite_sword", "njarm:netherite_scrap", "njarm:sapphire", "njarm:sapphire_block", "njarm:sapphire_ore", "njarm:sunstone"},
                    new String[] {"njarm:bedrock_axe", "njarm:bedrock_boots", "njarm:bedrock_chestplate", "njarm:bedrock_helmet", "njarm:bedrock_hoe", "njarm:bedrock_leggings", "njarm:bedrock_pickaxe", "njarm:bedrock_shovel", "njarm:bedrock_sword", "njarm:crumbling_bedrock"}
            ),
            new RupeeConfig(true, 1),
            new TeleportStaffConfig(1, 40, 384, 500)));

    @Config.LangKey("config.njarm.cfg.world")
    @Nonnull public static final WorldConfig worldCfg = register(new WorldConfig(
            new OreConfig(new String[] {
                    "{Ore:{Name:\"njarm:ruby_ore\"},Stone:{Name:stone},MinY:0,MaxY:16,ClumpSize:8,PerChunk:1}",
                    "{Ore:{Name:\"njarm:quartz_ore\"},Stone:{Name:stone},MinY:0,MaxY:256,ClumpSize:14,PerChunk:8}",
                    "{Ore:{Name:\"njarm:xp_ore\"},Stone:{Name:stone},MinY:0,MaxY:70,ClumpSize:8,PerChunk:4}",
                    "{Ore:{Name:\"njarm:xp_ore\",Properties:{type:nether}},Stone:{Name:netherrack},MinY:0,MaxY:128,ClumpSize:8,PerChunk:8,Dimensions:[-1]}",
                    "{Ore:{Name:\"njarm:xp_ore\",Properties:{type:end}},Stone:{Name:end_stone},MinY:0,MaxY:256,ClumpSize:8,PerChunk:70,Dimensions:[1]}",
                    "{Ore:{Name:\"njarm:mica_ore\"},Stone:{Name:sand},MinY:40,MaxY:80,ClumpSize:8,PerChunk:10,BiomeTags:[beach]}",
                    "{Ore:{Name:\"njarm:bone_ore\"},Stone:{Name:dirt},MinY:0,MaxY:256,ClumpSize:6,PerChunk:34}",
                    "{Ore:{Name:\"njarm:platinum_ore\"},Stone:{Name:stone},MinY:0,MaxY:16,ClumpSize:8,PerChunk:2}",
                    "{Ore:{Name:\"njarm:gravel_gold_ore\"},Stone:{Name:gravel},MinY:0,MaxY:256,ClumpSize:8,PerChunk:15,BiomeTags:[all],ExcludeBiomes:{BiomeTags:[water]}}",
                    "{Ore:{Name:\"njarm:gravel_iron_ore\"},Stone:{Name:gravel},MinY:0,MaxY:256,ClumpSize:8,PerChunk:15,BiomeTags:[all],ExcludeBiomes:{BiomeTags:[water]}}",
                    "{Ore:{Name:\"njarm:gravel_xp_ore\"},Stone:{Name:gravel},MinY:0,MaxY:256,ClumpSize:8,PerChunk:15,BiomeTags:[all],ExcludeBiomes:{BiomeTags:[water]}}",
                    "{Ore:{Name:\"njarm:gravel_quartz_ore\"},Stone:{Name:gravel},MinY:0,MaxY:256,ClumpSize:14,PerChunk:15,BiomeTags:[all],ExcludeBiomes:{BiomeTags:[water]}}",
                    "{Ore:{Name:\"njarm:magic_ore\"},Stone:{Name:end_stone},MinY:0,MaxY:256,ClumpSize:4,PerChunk:100,Dimensions:[1]}",
                    "{Ore:{Name:\"njarm:end_lapis_ore\"},Stone:{Name:end_stone},MinY:0,MaxY:256,ClumpSize:8,PerChunk:60,Dimensions:[1]}",
                    "{Ore:{Name:\"njarm:nether_gold_ore\"},Stone:{Name:netherrack},MinY:0,MaxY:128,ClumpSize:8,PerChunk:30,Dimensions:[-1]}",
                    "{Ore:{Name:\"njarm:nether_diamond_ore\"},Stone:{Name:netherrack},MinY:0,MaxY:48,ClumpSize:8,PerChunk:3,Dimensions:[-1]}",
                    "{Ore:{Name:\"njarm:nether_emerald_ore\"},Stone:{Name:netherrack},MinY:0,MaxY:128,ClumpSize:1,PerChunk:15,Dimensions:[-1]}",
                    "{Ore:{Name:\"njarm:sapphire_ore\"},Stone:{Name:netherrack},MinY:0,MaxY:48,ClumpSize:4,PerChunk:4,Dimensions:[-1]}",
                    "{Ore:{Name:\"njarm:blackstone\",Properties:{axis:y}},Stone:{Name:netherrack},MinY:121,MaxY:126,ClumpSize:33,PerChunk:16,Biomes:[hell]}",
                    "{Ore:{Name:\"njarm:blackstone\",Properties:{axis:y}},Stone:{Name:netherrack},MinY:1,MaxY:5,ClumpSize:33,PerChunk:16,Biomes:[hell]}",
                    "{Ore:{Name:\"njarm:ancient_debris\",Properties:{axis:y}},Stone:{Name:netherrack},MinY:0,MaxY:48,ClumpSize:2,PerChunk:2,Dimensions:[-1]}"
            })
    ));

    //create a new config category while also adding it to the internal list
    @Nonnull
    static <T extends IConfig> T register(@Nonnull T cfg) {
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
