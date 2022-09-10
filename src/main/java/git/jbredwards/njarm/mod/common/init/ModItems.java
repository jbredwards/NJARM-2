package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.block.BlockFoodCrate;
import git.jbredwards.njarm.mod.common.config.item.ChargedSunstoneConfig;
import git.jbredwards.njarm.mod.common.config.item.EquipmentConfig;
import git.jbredwards.njarm.mod.common.config.item.TeleportStaffConfig;
import git.jbredwards.njarm.mod.common.entity.item.EntityChargedSunstone;
import git.jbredwards.njarm.mod.common.item.*;
import git.jbredwards.njarm.mod.common.item.block.*;
import git.jbredwards.njarm.mod.common.item.equipment.ItemAxe;
import git.jbredwards.njarm.mod.common.item.equipment.*;
import git.jbredwards.njarm.mod.common.item.util.CreativeTab;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * Holds this mod's items
 * @author jbred
 *
 */
public final class ModItems
{
    @Nonnull public static final NonNullList<Item> INIT = NonNullList.create();

    //item blocks
    @Nonnull public static final ItemBlock RUBY_BLOCK = register("ruby_block", new ItemBlock(ModBlocks.RUBY_BLOCK));
    @Nonnull public static final ItemBlock SAPPHIRE_BLOCK = register("sapphire_block", new ItemBlock(ModBlocks.SAPPHIRE_BLOCK));
    @Nonnull public static final ItemBlockMeta FOOD_CRATE = register("food_crate", new ItemBlockMeta(ModBlocks.FOOD_CRATE, BlockFoodCrate.TYPE));
    @Nonnull public static final ItemBlock PLATINUM_BLOCK = register("platinum_block", new ItemBlock(ModBlocks.PLATINUM_BLOCK));
    @Nonnull public static final ItemBlock NETHERITE_BLOCK = register("netherite_block", new ItemBlock(ModBlocks.NETHERITE_BLOCK));
    @Nonnull public static final ItemBlock RUBY_ORE = register("ruby_ore", new ItemBlock(ModBlocks.RUBY_ORE));
    @Nonnull public static final ItemBlock SAPPHIRE_ORE = register("sapphire_ore", new ItemBlock(ModBlocks.SAPPHIRE_ORE));
    @Nonnull public static final ItemBlock ANCIENT_DEBRIS = register("ancient_debris", new ItemBlock(ModBlocks.ANCIENT_DEBRIS));
    @Nonnull public static final ItemBlock MAGIC_BLOCK = register("magic_block", new ItemBlock(ModBlocks.MAGIC_BLOCK));
    @Nonnull public static final ItemBlock MAGIC_ORE = register("magic_ore", new ItemBlock(ModBlocks.MAGIC_ORE));
    @Nonnull public static final ItemBlockMetaExpOre XP_ORE = register("xp_ore", new ItemBlockMetaExpOre(ModBlocks.XP_ORE));
    @Nonnull public static final ItemBlock FRAGILE_ICE = register("fragile_ice", new ItemBlock(ModBlocks.FRAGILE_ICE));
    @Nonnull public static final ItemBlock NETHER_REACTOR_CORE = register("nether_reactor_core", new ItemBlock(ModBlocks.NETHER_REACTOR_CORE));
    @Nonnull public static final ItemBlock GLOWING_OBSIDIAN = register("glowing_obsidian", new ItemBlock(ModBlocks.GLOWING_OBSIDIAN));
    @Nonnull public static final ItemBlock CHAIN = register("chain", new ItemBlock(ModBlocks.CHAIN));
    @Nonnull public static final ItemBlock SOUL_SOIL = register("soul_soil", new ItemBlock(ModBlocks.SOUL_SOIL));
    @Nonnull public static final ItemBlock BASALT = register("basalt", new ItemBlock(ModBlocks.BASALT));
    @Nonnull public static final ItemBlock POLISHED_BASALT = register("polished_basalt", new ItemBlock(ModBlocks.POLISHED_BASALT));
    @Nonnull public static final ItemBlock BLACKSTONE = register("blackstone", new ItemBlock(ModBlocks.BLACKSTONE));
    @Nonnull public static final ItemBlock POLISHED_BLACKSTONE = register("polished_blackstone", new ItemBlock(ModBlocks.POLISHED_BLACKSTONE));
    @Nonnull public static final ItemBlock BLACKSTONE_BRICKS = register("blackstone_bricks", new ItemBlock(ModBlocks.BLACKSTONE_BRICKS));
    @Nonnull public static final ItemBlock BLACKSTONE_CHISELED = register("blackstone_chiseled", new ItemBlock(ModBlocks.BLACKSTONE_CHISELED));
    @Nonnull public static final ItemBlock BLACKSTONE_CRACKED = register("blackstone_cracked", new ItemBlock(ModBlocks.BLACKSTONE_CRACKED));
    @Nonnull public static final ItemBlock ICE_GRASS = register("ice_grass", new ItemBlock(ModBlocks.ICE_GRASS));
    @Nonnull public static final ItemBlock ICE_MUSHROOM = register("ice_mushroom", new ItemBlock(ModBlocks.ICE_MUSHROOM));
    @Nonnull public static final ItemBlock MOSS = register("moss", new ItemBlock(ModBlocks.MOSS));
    @Nonnull public static final ItemBlock OBSIDIAN_BLOCK = register("obsidian_block", new ItemBlock(ModBlocks.OBSIDIAN_BLOCK));
    @Nonnull public static final ItemBlock OBSIDIAN_GLASS = register("obsidian_glass", new ItemBlock(ModBlocks.OBSIDIAN_GLASS));
    @Nonnull public static final ItemBlock OBSIDIAN_GLASS_PANE = register("obsidian_glass_pane", new ItemBlock(ModBlocks.OBSIDIAN_GLASS_PANE));
    @Nonnull public static final ItemBlock MICA_ORE = register("mica_ore", new ItemBlock(ModBlocks.MICA_ORE));
    @Nonnull public static final ItemBlock OVERGROWN_DIRT = register("overgrown_dirt", new ItemBlock(ModBlocks.OVERGROWN_DIRT));
    @Nonnull public static final ItemBlock OVERGROWN_STONE = register("overgrown_stone", new ItemBlock(ModBlocks.OVERGROWN_STONE));
    @Nonnull public static final ItemBlock QUARTZ_ORE = register("quartz_ore", new ItemBlock(ModBlocks.QUARTZ_ORE));
    @Nonnull public static final ItemBlock GRAVEL_GOLD_ORE = register("gravel_gold_ore", new ItemBlock(ModBlocks.GRAVEL_GOLD_ORE));
    @Nonnull public static final ItemBlock GRAVEL_IRON_ORE = register("gravel_iron_ore", new ItemBlock(ModBlocks.GRAVEL_IRON_ORE));
    @Nonnull public static final ItemBlockExpOre GRAVEL_XP_ORE = register("gravel_xp_ore", new ItemBlockExpOre(ModBlocks.GRAVEL_XP_ORE));
    @Nonnull public static final ItemBlock GRAVEL_QUARTZ_ORE = register("gravel_quartz_ore", new ItemBlock(ModBlocks.GRAVEL_QUARTZ_ORE));
    @Nonnull public static final ItemBlock BONE_ORE = register("bone_ore", new ItemBlock(ModBlocks.BONE_ORE));
    @Nonnull public static final ItemBlock PLATINUM_ORE = register("platinum_ore", new ItemBlock(ModBlocks.PLATINUM_ORE));
    @Nonnull public static final ItemBlock END_LAPIS_ORE = register("end_lapis_ore", new ItemBlock(ModBlocks.END_LAPIS_ORE));
    @Nonnull public static final ItemBlock NETHER_GOLD_ORE = register("nether_gold_ore", new ItemBlock(ModBlocks.NETHER_GOLD_ORE));
    @Nonnull public static final ItemBlock NETHER_DIAMOND_ORE = register("nether_diamond_ore", new ItemBlock(ModBlocks.NETHER_DIAMOND_ORE));
    @Nonnull public static final ItemBlock NETHER_EMERALD_ORE = register("nether_emerald_ore", new ItemBlock(ModBlocks.NETHER_EMERALD_ORE));
    @Nonnull public static final ItemBlock CRACKED_NETHER_BRICK = register("cracked_nether_brick", new ItemBlock(ModBlocks.CRACKED_NETHER_BRICK));
    @Nonnull public static final ItemBlock ENDER_PLANKS = register("ender_planks", new ItemBlock(ModBlocks.ENDER_PLANKS));
    @Nonnull public static final ItemBlock ENDER_LOG = register("ender_log", new ItemBlock(ModBlocks.ENDER_LOG));
    @Nonnull public static final ItemBlock ENDER_LEAVES = register("ender_leaves", new ItemBlock(ModBlocks.ENDER_LEAVES));
    @Nonnull public static final ItemBlock ENDER_GRASS = register("ender_grass", new ItemBlock(ModBlocks.ENDER_GRASS));
    @Nonnull public static final ItemBlock COMPRESSED_STONE = register("compressed_stone", new ItemBlock(ModBlocks.COMPRESSED_STONE));
    @Nonnull public static final ItemBlock COMPRESSED_SANDSTONE = register("compressed_sandstone", new ItemBlock(ModBlocks.COMPRESSED_SANDSTONE));
    @Nonnull public static final ItemBlock CRACKED_COMPRESSED_SANDSTONE = register("cracked_compressed_sandstone", new ItemBlock(ModBlocks.CRACKED_COMPRESSED_SANDSTONE));
    @Nonnull public static final ItemBlock COMPRESSED_RED_SANDSTONE = register("compressed_red_sandstone", new ItemBlock(ModBlocks.COMPRESSED_RED_SANDSTONE));
    @Nonnull public static final ItemBlock CRACKED_COMPRESSED_RED_SANDSTONE = register("cracked_compressed_red_sandstone", new ItemBlock(ModBlocks.CRACKED_COMPRESSED_RED_SANDSTONE));
    @Nonnull public static final ItemBlock COMPRESSED_QUARTZ_BLOCK = register("compressed_quartz_block", new ItemBlock(ModBlocks.COMPRESSED_QUARTZ_BLOCK));
    @Nonnull public static final ItemBlock MAGIC_SPONGE = register("magic_sponge", new ItemBlock(ModBlocks.MAGIC_SPONGE));
    @Nonnull public static final ItemBlock ENDER_LOG_CURSED = register("ender_log_cursed", new ItemBlock(ModBlocks.ENDER_LOG_CURSED));
    @Nonnull public static final ItemBlock ENDER_LEAVES_CURSED = register("ender_leaves_cursed", new ItemBlock(ModBlocks.ENDER_LEAVES_CURSED));
    @Nonnull public static final ItemBlock BLUE_ICE = register("blue_ice", new ItemBlock(ModBlocks.BLUE_ICE));
    @Nonnull public static final ItemBlock MYCELIUM_GRASS = register("mycelium_grass", new ItemBlock(ModBlocks.MYCELIUM_GRASS));
    @Nonnull public static final ItemBlock NETHER_GRASS = register("nether_grass", new ItemBlock(ModBlocks.NETHER_GRASS));
    @Nonnull public static final ItemSnow SNOW_GRASS = register("snow_grass", new ItemSnow(ModBlocks.SNOW_GRASS));
    @Nonnull public static final ItemBlock DRIED_KELP_BLOCK = register("dried_kelp_block", new ItemBlock(ModBlocks.DRIED_KELP_BLOCK));
    @Nonnull public static final ItemBlock ICE_STONE = register("ice_stone", new ItemBlock(ModBlocks.ICE_STONE));
    @Nonnull public static final ItemBlock LIGHTNING_ROD = register("lightning_rod", new ItemBlock(ModBlocks.LIGHTNING_ROD));

    //items
    @Nonnull public static final Item RUBY = register("ruby", new Item());
    @Nonnull public static final Item SAPPHIRE = register("sapphire", new Item());
    @Nonnull public static final ItemEggShell EGG_SHELL = register("egg_shell", new ItemEggShell());
    @Nonnull public static final ItemGlint MAGIC_DUST = register("magic_dust", new ItemGlint());
    @Nonnull public static final Item OBSIDIAN_INGOT = register("obsidian_ingot", new Item());
    @Nonnull public static final Item OBSIDIAN_NUGGET = register("obsidian_nugget", new Item());
    @Nonnull public static final Item CRUMBLING_BEDROCK = register("crumbling_bedrock", new Item());
    @Nonnull public static final Item SUNSTONE = register("sunstone", new Item());
    @Nonnull public static final ItemFood BAKED_APPLE = register("baked_apple", new ItemFood(8, 4.8f, false));
    @Nonnull public static final ItemFoodLeftovers CARAMEL_APPLE = register("caramel_apple", new ItemFoodLeftovers(4, 4.4f, false), item -> item.setPotionEffect(new PotionEffect(MobEffects.SPEED, 400, 1), 1).setAlwaysEdible().setContainerItem(Items.STICK));
    @Nonnull public static final ItemFood SUGAR_BREAD = register("sugar_bread", new ItemFood(5, 8, false), item -> item.setPotionEffect(new PotionEffect(MobEffects.SPEED, 400, 1), 1).setAlwaysEdible());
    @Nonnull public static final ItemFood BAGUETTE = register("baguette", new ItemFood(10, 12, false));
    @Nonnull public static final ItemFood SUGAR_BAGUETTE = register("sugar_baguette", new ItemFood(10, 14, false), item -> item.setPotionEffect(new PotionEffect(MobEffects.SPEED, 400, 1), 1).setAlwaysEdible());
    @Nonnull public static final ItemChocolateMilk CHOCOLATE_MILK_BUCKET = register("chocolate_milk_bucket", new ItemChocolateMilk(), item -> item.setContainerItem(Items.BUCKET));
    @Nonnull public static final ItemMilkStackable MILK_BOTTLE = register("milk_bottle", new ItemMilkStackable(), item -> item.setContainerItem(Items.GLASS_BOTTLE).setMaxStackSize(64));
    @Nonnull public static final ItemChocolateMilk CHOCOLATE_MILK_BOTTLE = register("chocolate_milk_bottle", new ItemChocolateMilk(), item -> item.setContainerItem(Items.GLASS_BOTTLE).setMaxStackSize(64));
    @Nonnull public static final ItemPotionFood CANNED_SPINACH = register("canned_spinach", new ItemPotionFood(20, false), item -> item.setEffects(ItemPotionFood.SPINACH).setAlwaysEdible());
    @Nonnull public static final ItemFood SUGAR_COOKIE = register("sugar_cookie", new ItemFood(2, 2.4f, false), item -> item.setPotionEffect(new PotionEffect(MobEffects.SPEED, 400, 2), 1).setAlwaysEdible());
    @Nonnull public static final ItemMagicMirror MAGIC_MIRROR = register("magic_mirror", new ItemMagicMirror(ItemMagicMirror.NORMAL));
    @Nonnull public static final ItemMagicMirror BEDROCK_MAGIC_MIRROR = register("bedrock_magic_mirror", new ItemMagicMirror(ItemMagicMirror.BEDROCK), item -> item.setMaxStackSize(1));
    @Nonnull public static final ItemMagicMirror DIMENSIONAL_MAGIC_MIRROR = register("dimensional_magic_mirror", new ItemMagicMirror(ItemMagicMirror.DIMENSIONAL));
    @Nonnull public static final ItemMagicMirror BEDROCK_DIMENSIONAL_MAGIC_MIRROR = register("bedrock_dimensional_magic_mirror", new ItemMagicMirror((byte)3), item -> item.setMaxStackSize(1));
    @Nonnull public static final Item NETHERITE_SCRAP = register("netherite_scrap", new Item());
    @Nonnull public static final Item NETHERITE_INGOT = register("netherite_ingot", new Item());
    @Nonnull public static final Item NETHERITE_NUGGET = register("netherite_nugget", new Item());
    @Nonnull public static final Item PLATINUM_INGOT = register("platinum_ingot", new Item());
    @Nonnull public static final Item PLATINUM_NUGGET = register("platinum_nugget", new Item());
    @Nonnull public static final ItemGlint MAGIC_INGOT = register("magic_ingot", new ItemGlint());
    @Nonnull public static final ItemGlint MAGIC_NUGGET = register("magic_nugget", new ItemGlint());
    @Nonnull public static final ItemFood RAW_EGG = register("raw_egg", new ItemFood(2, 1.2f, true), item -> item.setPotionEffect(new PotionEffect(MobEffects.NAUSEA, 600, 1), 0.3f));
    @Nonnull public static final ItemFood FRIED_EGG = register("fried_egg", new ItemFood(6, 7.2f, true));
    @Nonnull public static final ItemFood COOKED_EGG = register("cooked_egg", new ItemFood(6, 7.2f, true));
    @Nonnull public static final ItemChargedSunstone CHARGED_SUNSTONE = register("charged_sunstone", new ItemChargedSunstone(EntityChargedSunstone::new, ChargedSunstoneConfig::canThrow, EntityChargedSunstone::new, ChargedSunstoneConfig::canDispense));
    @Nonnull public static final Item MICA_DUST = register("mica_dust", new Item());
    @Nonnull public static final ItemBonusHeart BONUS_HEART = register("bonus_heart", new ItemBonusHeart(), item -> item.setMaxStackSize(1));
    @Nonnull public static final ItemRupee RUPEE = register("rupee", new ItemRupee());
    @Nonnull public static final ItemFire FIRE = register("fire", new ItemFire());
    @Nonnull public static final ItemGlint ENDER_STAR = register("ender_star", new ItemGlint());
    @Nonnull public static final Item BLESTEM_ROD = register("blestem_rod", new Item());
    @Nonnull public static final ItemBlestemArrow BLESTEM_ARROW = register("blestem_arrow", new ItemBlestemArrow());
    @Nonnull public static final Item STAFF_BASE = register("staff_base", new Item(), item -> item.setMaxStackSize(1));
    @Nonnull public static final ItemStaffTeleport TELEPORT_STAFF = register("teleport_staff", new ItemStaffTeleport(), item -> item.setMaxStackSize(1).setMaxDamage(TeleportStaffConfig.durability()));
    @Nonnull public static final ItemDummy DUMMY = register("dummy", new ItemDummy());
    @Nonnull public static final ItemQuickFood DRIED_KELP = register("dried_kelp", new ItemQuickFood(1, 0, false));
    @Nonnull public static final ItemFoodLeftovers MAGIC_CHORUS_FRUIT = register("magic_chorus_fruit", new ItemFoodLeftovers(5, 3.4f, false), item -> item.setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 120, 2), 1).setAlwaysEdible().setContainerItem(Items.LEAD));
    @Nonnull public static final ItemHeartRadish HEART_RADISH = register("heart_radish", new ItemHeartRadish(3, 1.2f, false), ItemFood::setAlwaysEdible);
    @Nonnull public static final ItemPotionFood VINE_FRUIT = register("vine_fruit", new ItemPotionFood(2, 2.4f, false), item -> item.setEffects(new PotionEffect(MobEffects.NIGHT_VISION, 15*20), new PotionEffect(MobEffects.GLOWING, 30*20)).setAlwaysEdible());
    @Nonnull public static final Item SAP_BALL = register("sap_ball", new Item());
    @Nonnull public static final Item BLOOD_BALL = register("blood_ball", new Item());

    @Nonnull public static final ItemHeartRadish GOLD_HEART_RADISH = register("gold_heart_radish", new ItemHeartRadish(3, 1.2f, false), item -> item.setPotionEffect(new PotionEffect(MobEffects.SATURATION, 40), 1).setAlwaysEdible());
    @Nonnull public static final Item ASH_PILE = register("ash_pile", new Item());

    //armor
    @Nonnull public static final ItemArmor RUBY_HELMET = register("ruby_helmet", new ItemArmor(EquipmentConfig.RUBY_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD), item -> EquipmentConfig.RUBY_ARMOR_MATERIAL.setRepairItem(new ItemStack(RUBY)));
    @Nonnull public static final ItemArmor RUBY_CHESTPLATE = register("ruby_chestplate", new ItemArmor(EquipmentConfig.RUBY_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST));
    @Nonnull public static final ItemArmor RUBY_LEGGINGS = register("ruby_leggings", new ItemArmor(EquipmentConfig.RUBY_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS));
    @Nonnull public static final ItemArmor RUBY_BOOTS = register("ruby_boots", new ItemArmor(EquipmentConfig.RUBY_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET));
    @Nonnull public static final ItemSapphireArmor SAPPHIRE_HELMET = register("sapphire_helmet", new ItemSapphireArmor(EquipmentConfig.SAPPHIRE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD), item -> EquipmentConfig.SAPPHIRE_ARMOR_MATERIAL.setRepairItem(new ItemStack(SAPPHIRE)));
    @Nonnull public static final ItemSapphireArmor SAPPHIRE_CHESTPLATE = register("sapphire_chestplate", new ItemSapphireArmor(EquipmentConfig.SAPPHIRE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST));
    @Nonnull public static final ItemSapphireArmor SAPPHIRE_LEGGINGS = register("sapphire_leggings", new ItemSapphireArmor(EquipmentConfig.SAPPHIRE_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS));
    @Nonnull public static final ItemSapphireArmor SAPPHIRE_BOOTS = register("sapphire_boots", new ItemSapphireArmor(EquipmentConfig.SAPPHIRE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET));
    @Nonnull public static final ItemArmor WOOD_HELMET = register("wood_helmet", new ItemArmor(EquipmentConfig.WOOD_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD), item -> EquipmentConfig.WOOD_ARMOR_MATERIAL.setRepairItem(new ItemStack(Blocks.PLANKS)));
    @Nonnull public static final ItemArmor WOOD_CHESTPLATE = register("wood_chestplate", new ItemArmor(EquipmentConfig.WOOD_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST));
    @Nonnull public static final ItemArmor WOOD_LEGGINGS = register("wood_leggings", new ItemArmor(EquipmentConfig.WOOD_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS));
    @Nonnull public static final ItemArmor WOOD_BOOTS = register("wood_boots", new ItemArmor(EquipmentConfig.WOOD_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET));
    @Nonnull public static final ItemArmor PLATINUM_HELMET = register("platinum_helmet", new ItemArmor(EquipmentConfig.PLATINUM_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD), item -> EquipmentConfig.PLATINUM_ARMOR_MATERIAL.setRepairItem(new ItemStack(PLATINUM_INGOT)));
    @Nonnull public static final ItemArmor PLATINUM_CHESTPLATE = register("platinum_chestplate", new ItemArmor(EquipmentConfig.PLATINUM_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST));
    @Nonnull public static final ItemArmor PLATINUM_LEGGINGS = register("platinum_leggings", new ItemArmor(EquipmentConfig.PLATINUM_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS));
    @Nonnull public static final ItemArmor PLATINUM_BOOTS = register("platinum_boots", new ItemArmor(EquipmentConfig.PLATINUM_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET));
    @Nonnull public static final ItemArmor NETHERITE_HELMET = register("netherite_helmet", new ItemArmor(EquipmentConfig.NETHERITE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD), item -> EquipmentConfig.NETHERITE_ARMOR_MATERIAL.setRepairItem(new ItemStack(NETHERITE_INGOT)));
    @Nonnull public static final ItemArmor NETHERITE_CHESTPLATE = register("netherite_chestplate", new ItemArmor(EquipmentConfig.NETHERITE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST));
    @Nonnull public static final ItemArmor NETHERITE_LEGGINGS = register("netherite_leggings", new ItemArmor(EquipmentConfig.NETHERITE_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS));
    @Nonnull public static final ItemArmor NETHERITE_BOOTS = register("netherite_boots", new ItemArmor(EquipmentConfig.NETHERITE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET));
    @Nonnull public static final ItemObsidianArmor OBSIDIAN_HELMET = register("obsidian_helmet", new ItemObsidianArmor(EquipmentConfig.OBSIDIAN_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD), item -> EquipmentConfig.OBSIDIAN_ARMOR_MATERIAL.setRepairItem(new ItemStack(OBSIDIAN_INGOT)));
    @Nonnull public static final ItemObsidianArmor OBSIDIAN_CHESTPLATE = register("obsidian_chestplate", new ItemObsidianArmor(EquipmentConfig.OBSIDIAN_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST));
    @Nonnull public static final ItemObsidianArmor OBSIDIAN_LEGGINGS = register("obsidian_leggings", new ItemObsidianArmor(EquipmentConfig.OBSIDIAN_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS));
    @Nonnull public static final ItemObsidianArmor OBSIDIAN_BOOTS = register("obsidian_boots", new ItemObsidianArmor(EquipmentConfig.OBSIDIAN_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET));
    @Nonnull public static final ItemArmor BEDROCK_HELMET = register("bedrock_helmet", new ItemArmor(EquipmentConfig.BEDROCK_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD));
    @Nonnull public static final ItemArmor BEDROCK_CHESTPLATE = register("bedrock_chestplate", new ItemArmor(EquipmentConfig.BEDROCK_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST));
    @Nonnull public static final ItemArmor BEDROCK_LEGGINGS = register("bedrock_leggings", new ItemArmor(EquipmentConfig.BEDROCK_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS));
    @Nonnull public static final ItemArmor BEDROCK_BOOTS = register("bedrock_boots", new ItemArmor(EquipmentConfig.BEDROCK_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET));
    @Nonnull public static final ItemFeatherArmor FEATHER_BOOTS = register("feather_boots", new ItemFeatherArmor(EquipmentConfig.FEATHER_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET));
    @Nonnull public static final ItemCactusArmor CACTUS_HELMET = register("cactus_helmet", new ItemCactusArmor(EquipmentConfig.CACTUS_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD), item -> EquipmentConfig.CACTUS_ARMOR_MATERIAL.setRepairItem(new ItemStack(Blocks.CACTUS)));
    @Nonnull public static final ItemCactusArmor CACTUS_CHESTPLATE = register("cactus_chestplate", new ItemCactusArmor(EquipmentConfig.CACTUS_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST));
    @Nonnull public static final ItemCactusArmor CACTUS_LEGGINGS = register("cactus_leggings", new ItemCactusArmor(EquipmentConfig.CACTUS_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS));
    @Nonnull public static final ItemCactusArmor CACTUS_BOOTS = register("cactus_boots", new ItemCactusArmor(EquipmentConfig.CACTUS_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET));
    @Nonnull public static final ItemShulkerArmor SHULKER_BOOTS = register("shulker_boots", new ItemShulkerArmor(EquipmentConfig.SHULKER_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET), item -> EquipmentConfig.SHULKER_ARMOR_MATERIAL.setRepairItem(new ItemStack(Items.SHULKER_SHELL)));
    @Nonnull public static final ItemCrown CROWN = register("crown", new ItemCrown(EquipmentConfig.CROWN_ARMOR_MATERIAL), item -> EquipmentConfig.CROWN_ARMOR_MATERIAL.setRepairItem(new ItemStack(Items.GOLDEN_APPLE, 1, 1)));

    //tools
    @Nonnull public static final ItemSword RUBY_SWORD = register("ruby_sword", new ItemSword(EquipmentConfig.RUBY_TOOL_MATERIAL), item -> EquipmentConfig.RUBY_TOOL_MATERIAL.setRepairItem(new ItemStack(RUBY)));
    @Nonnull public static final ItemSpade RUBY_SHOVEL = register("ruby_shovel", new ItemSpade(EquipmentConfig.RUBY_TOOL_MATERIAL));
    @Nonnull public static final ItemPickaxe RUBY_PICKAXE = register("ruby_pickaxe", new ItemPickaxe(EquipmentConfig.RUBY_TOOL_MATERIAL));
    @Nonnull public static final ItemAxe RUBY_AXE = register("ruby_axe", new ItemAxe(EquipmentConfig.RUBY_TOOL_MATERIAL));
    @Nonnull public static final ItemHoe RUBY_HOE = register("ruby_hoe", new ItemHoe(EquipmentConfig.RUBY_TOOL_MATERIAL));
    @Nonnull public static final ItemSapphireSword SAPPHIRE_SWORD = register("sapphire_sword", new ItemSapphireSword(EquipmentConfig.SAPPHIRE_TOOL_MATERIAL), item -> EquipmentConfig.SAPPHIRE_TOOL_MATERIAL.setRepairItem(new ItemStack(SAPPHIRE)));
    @Nonnull public static final ItemSapphireSpade SAPPHIRE_SHOVEL = register("sapphire_shovel", new ItemSapphireSpade(EquipmentConfig.SAPPHIRE_TOOL_MATERIAL));
    @Nonnull public static final ItemSapphirePickaxe SAPPHIRE_PICKAXE = register("sapphire_pickaxe", new ItemSapphirePickaxe(EquipmentConfig.SAPPHIRE_TOOL_MATERIAL));
    @Nonnull public static final ItemSapphireAxe SAPPHIRE_AXE = register("sapphire_axe", new ItemSapphireAxe(EquipmentConfig.SAPPHIRE_TOOL_MATERIAL));
    @Nonnull public static final ItemSapphireHoe SAPPHIRE_HOE = register("sapphire_hoe", new ItemSapphireHoe(EquipmentConfig.SAPPHIRE_TOOL_MATERIAL));
    @Nonnull public static final ItemSapphireBow SAPPHIRE_BOW = register("sapphire_bow", new ItemSapphireBow(), item -> item.setMaxDamage(EquipmentConfig.sapphireBowDurability()));
    @Nonnull public static final ItemSword PLATINUM_SWORD = register("platinum_sword", new ItemSword(EquipmentConfig.PLATINUM_TOOL_MATERIAL), item -> EquipmentConfig.PLATINUM_TOOL_MATERIAL.setRepairItem(new ItemStack(PLATINUM_INGOT)));
    @Nonnull public static final ItemSpade PLATINUM_SHOVEL = register("platinum_shovel", new ItemSpade(EquipmentConfig.PLATINUM_TOOL_MATERIAL));
    @Nonnull public static final ItemPickaxe PLATINUM_PICKAXE = register("platinum_pickaxe", new ItemPickaxe(EquipmentConfig.PLATINUM_TOOL_MATERIAL));
    @Nonnull public static final ItemAxe PLATINUM_AXE = register("platinum_axe", new ItemAxe(EquipmentConfig.PLATINUM_TOOL_MATERIAL));
    @Nonnull public static final ItemHoe PLATINUM_HOE = register("platinum_hoe", new ItemHoe(EquipmentConfig.PLATINUM_TOOL_MATERIAL));
    @Nonnull public static final ItemSword NETHERITE_SWORD = register("netherite_sword", new ItemSword(EquipmentConfig.NETHERITE_TOOL_MATERIAL), item -> EquipmentConfig.NETHERITE_TOOL_MATERIAL.setRepairItem(new ItemStack(NETHERITE_INGOT)));
    @Nonnull public static final ItemSpade NETHERITE_SHOVEL = register("netherite_shovel", new ItemSpade(EquipmentConfig.NETHERITE_TOOL_MATERIAL));
    @Nonnull public static final ItemPickaxe NETHERITE_PICKAXE = register("netherite_pickaxe", new ItemPickaxe(EquipmentConfig.NETHERITE_TOOL_MATERIAL));
    @Nonnull public static final ItemAxe NETHERITE_AXE = register("netherite_axe", new ItemAxe(EquipmentConfig.NETHERITE_TOOL_MATERIAL));
    @Nonnull public static final ItemHoe NETHERITE_HOE = register("netherite_hoe", new ItemHoe(EquipmentConfig.NETHERITE_TOOL_MATERIAL));
    @Nonnull public static final ItemSword POPPY_SWORD = register("poppy_sword", new ItemSword(EquipmentConfig.POPPY_TOOL_MATERIAL), item -> EquipmentConfig.POPPY_TOOL_MATERIAL.setRepairItem(new ItemStack(Blocks.RED_FLOWER)));
    @Nonnull public static final ItemSword OBSIDIAN_SWORD = register("obsidian_sword", new ItemSword(EquipmentConfig.OBSIDIAN_TOOL_MATERIAL), item -> EquipmentConfig.OBSIDIAN_TOOL_MATERIAL.setRepairItem(new ItemStack(OBSIDIAN_INGOT)));
    @Nonnull public static final ItemSpade OBSIDIAN_SHOVEL = register("obsidian_shovel", new ItemSpade(EquipmentConfig.OBSIDIAN_TOOL_MATERIAL));
    @Nonnull public static final ItemPickaxe OBSIDIAN_PICKAXE = register("obsidian_pickaxe", new ItemPickaxe(EquipmentConfig.OBSIDIAN_TOOL_MATERIAL));
    @Nonnull public static final ItemAxe OBSIDIAN_AXE = register("obsidian_axe", new ItemAxe(EquipmentConfig.OBSIDIAN_TOOL_MATERIAL));
    @Nonnull public static final ItemHoe OBSIDIAN_HOE = register("obsidian_hoe", new ItemHoe(EquipmentConfig.OBSIDIAN_TOOL_MATERIAL));
    @Nonnull public static final ItemSword BEDROCK_SWORD = register("bedrock_sword", new ItemSword(EquipmentConfig.BEDROCK_TOOL_MATERIAL));
    @Nonnull public static final ItemSpade BEDROCK_SHOVEL = register("bedrock_shovel", new ItemSpade(EquipmentConfig.BEDROCK_TOOL_MATERIAL));
    @Nonnull public static final ItemPickaxe BEDROCK_PICKAXE = register("bedrock_pickaxe", new ItemPickaxe(EquipmentConfig.BEDROCK_TOOL_MATERIAL));
    @Nonnull public static final ItemAxe BEDROCK_AXE = register("bedrock_axe", new ItemAxe(EquipmentConfig.BEDROCK_TOOL_MATERIAL));
    @Nonnull public static final ItemHoe BEDROCK_HOE = register("bedrock_hoe", new ItemHoe(EquipmentConfig.BEDROCK_TOOL_MATERIAL));
    @Nonnull public static final ItemCactusSword CACTUS_SWORD = register("cactus_sword", new ItemCactusSword(EquipmentConfig.CACTUS_TOOL_MATERIAL), item -> EquipmentConfig.CACTUS_TOOL_MATERIAL.setRepairItem(new ItemStack(Blocks.CACTUS)));
    @Nonnull public static final ItemCactusSpade CACTUS_SHOVEL = register("cactus_shovel", new ItemCactusSpade(EquipmentConfig.CACTUS_TOOL_MATERIAL));
    @Nonnull public static final ItemCactusPickaxe CACTUS_PICKAXE = register("cactus_pickaxe", new ItemCactusPickaxe(EquipmentConfig.CACTUS_TOOL_MATERIAL));
    @Nonnull public static final ItemCactusAxe CACTUS_AXE = register("cactus_axe", new ItemCactusAxe(EquipmentConfig.CACTUS_TOOL_MATERIAL));
    @Nonnull public static final ItemCactusHoe CACTUS_HOE = register("cactus_hoe", new ItemCactusHoe(EquipmentConfig.CACTUS_TOOL_MATERIAL));

    //register ores
    public static void registerOres() {
        OreDictionary.registerOre("blockGlass", OBSIDIAN_GLASS);
        OreDictionary.registerOre("blockGlassColorless", OBSIDIAN_GLASS);
        OreDictionary.registerOre("blockMagicalAlloy", MAGIC_BLOCK);
        OreDictionary.registerOre("blockNetherite", NETHERITE_BLOCK);
        OreDictionary.registerOre("blockObsidianAlloy", OBSIDIAN_BLOCK);
        OreDictionary.registerOre("blockPlatinum", PLATINUM_BLOCK);
        OreDictionary.registerOre("blockRuby", RUBY_BLOCK);
        OreDictionary.registerOre("blockSapphire", SAPPHIRE_BLOCK);
        OreDictionary.registerOre("bloodball", BLOOD_BALL);
        OreDictionary.registerOre("cobblestone", BLACKSTONE);
        OreDictionary.registerOre("dustMagicNJARM", MAGIC_DUST);
        OreDictionary.registerOre("dustMica", MICA_DUST);
        OreDictionary.registerOre("egg", RAW_EGG);
        OreDictionary.registerOre("enderStar", ENDER_STAR);
        OreDictionary.registerOre("gemRuby", RUBY);
        OreDictionary.registerOre("gemSapphire", SAPPHIRE);
        OreDictionary.registerOre("gemSunstone", CHARGED_SUNSTONE);
        OreDictionary.registerOre("gemSunstoneCharged", CHARGED_SUNSTONE);
        OreDictionary.registerOre("gemSunstone", SUNSTONE);
        OreDictionary.registerOre("ingotMagicalAlloy", MAGIC_INGOT);
        OreDictionary.registerOre("ingotNetherite", NETHERITE_INGOT);
        OreDictionary.registerOre("ingotObsidianAlloy", OBSIDIAN_INGOT);
        OreDictionary.registerOre("ingotPlatinum", PLATINUM_INGOT);
        OreDictionary.registerOre("logWood", ENDER_LOG);
        OreDictionary.registerOre("logWood", ENDER_LOG_CURSED);
        OreDictionary.registerOre("nuggetMagicalAlloy", MAGIC_NUGGET);
        OreDictionary.registerOre("nuggetNetherite", NETHERITE_NUGGET);
        OreDictionary.registerOre("nuggetObsidianAlloy", OBSIDIAN_NUGGET);
        OreDictionary.registerOre("nuggetPlatinum", PLATINUM_NUGGET);
        OreDictionary.registerOre("oreAncientDebris", ANCIENT_DEBRIS);
        OreDictionary.registerOre("oreBone", BONE_ORE);
        OreDictionary.registerOre("oreGold", GRAVEL_GOLD_ORE);
        OreDictionary.registerOre("oreGold", NETHER_GOLD_ORE);
        OreDictionary.registerOre("oreDiamond", NETHER_DIAMOND_ORE);
        OreDictionary.registerOre("oreEmerald", NETHER_EMERALD_ORE);
        OreDictionary.registerOre("oreIron", GRAVEL_IRON_ORE);
        OreDictionary.registerOre("oreLapis", END_LAPIS_ORE);
        OreDictionary.registerOre("oreMagicNJARM", MAGIC_ORE);
        OreDictionary.registerOre("oreMica", MICA_ORE);
        OreDictionary.registerOre("orePlatinum", PLATINUM_ORE);
        OreDictionary.registerOre("oreQuartz", GRAVEL_QUARTZ_ORE);
        OreDictionary.registerOre("oreQuartz", QUARTZ_ORE);
        OreDictionary.registerOre("oreRuby", RUBY_ORE);
        OreDictionary.registerOre("oreSapphire", SAPPHIRE_ORE);
        OreDictionary.registerOre("plankWood", ENDER_PLANKS);
        OreDictionary.registerOre("scrapAncientDebris", NETHERITE_SCRAP);
        OreDictionary.registerOre("slimeball", SAP_BALL);
        OreDictionary.registerOre("treeLeaves", ENDER_LEAVES);
        OreDictionary.registerOre("treeLeaves", ENDER_LEAVES_CURSED);
    }

    //register item
    @Nonnull
    static <T extends Item> T register(@Nonnull String name, @Nonnull T item) { return register(name, item, itemIn -> {}); }

    //register item
    @Nonnull
    static <T extends Item> T register(@Nonnull String name, @Nonnull T item, @Nonnull Consumer<T> consumer) {
        INIT.add(item.setRegistryName(Constants.MODID, name)
                .setTranslationKey(Constants.MODID + "." + name)
                .setCreativeTab(CreativeTab.INSTANCE)
        );

        consumer.accept(item);
        return item;
    }
}
