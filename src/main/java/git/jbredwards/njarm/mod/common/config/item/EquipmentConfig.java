package git.jbredwards.njarm.mod.common.config.item;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.StreamSupport;

/**
 *
 * @author jbred
 *
 */
@SuppressWarnings("NotNullFieldNotInitialized")
public final class EquipmentConfig implements IConfig
{
    static boolean initializeMaterials = true;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.rubyArmorMaterial")
    @Nonnull public final String rubyArmorMaterial;
    @Nonnull public static ItemArmor.ArmorMaterial RUBY_ARMOR_MATERIAL;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.sapphireArmorMaterial")
    @Nonnull public final String sapphireArmorMaterial;
    @Nonnull public static ItemArmor.ArmorMaterial SAPPHIRE_ARMOR_MATERIAL;

    @Config.LangKey("config.njarm.item.equipment.sapphireFireResist")
    public final boolean sapphireFireResist;
    public static boolean sapphireFireResist() { return ConfigHandler.itemCfg.equipmentCfg.sapphireFireResist; }

    @Config.LangKey("config.njarm.item.equipment.sapphireBlueFireResist")
    public final boolean sapphireBlueFireResist;
    public static boolean sapphireBlueFireResist() { return ConfigHandler.itemCfg.equipmentCfg.sapphireBlueFireResist; }

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.woodArmorMaterial")
    @Nonnull public final String woodArmorMaterial;
    @Nonnull public static ItemArmor.ArmorMaterial WOOD_ARMOR_MATERIAL;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.platinumArmorMaterial")
    @Nonnull public final String platinumArmorMaterial;
    @Nonnull public static ItemArmor.ArmorMaterial PLATINUM_ARMOR_MATERIAL;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.netheriteArmorMaterial")
    @Nonnull public final String netheriteArmorMaterial;
    @Nonnull public static ItemArmor.ArmorMaterial NETHERITE_ARMOR_MATERIAL;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.obsidianArmorMaterial")
    @Nonnull public final String obsidianArmorMaterial;
    @Nonnull public static ItemArmor.ArmorMaterial OBSIDIAN_ARMOR_MATERIAL;

    @Config.LangKey("config.njarm.item.equipment.obsidianResistKnockback")
    public final boolean obsidianResistKnockback;
    public static boolean obsidianResistKnockback() { return ConfigHandler.itemCfg.equipmentCfg.obsidianResistKnockback; }

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.bedrockArmorMaterial")
    @Nonnull public final String bedrockArmorMaterial;
    @Nonnull public static ItemArmor.ArmorMaterial BEDROCK_ARMOR_MATERIAL;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.featherArmorMaterial")
    @Nonnull public final String featherArmorMaterial;
    @Nonnull public static ItemArmor.ArmorMaterial FEATHER_ARMOR_MATERIAL;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.cactusArmorMaterial")
    @Nonnull public final String cactusArmorMaterial;
    @Nonnull public static ItemArmor.ArmorMaterial CACTUS_ARMOR_MATERIAL;

    @Config.RangeInt(min = 0, max = 3)
    @Config.LangKey("config.njarm.item.equipment.cactusThorns")
    public final int cactusThorns;
    public static int cactusThorns() { return ConfigHandler.itemCfg.equipmentCfg.cactusThorns; }

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.rubyToolMaterial")
    @Nonnull public final String rubyToolMaterial;
    @Nonnull public static Item.ToolMaterial RUBY_TOOL_MATERIAL;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.sapphireToolMaterial")
    @Nonnull public final String sapphireToolMaterial;
    @Nonnull public static Item.ToolMaterial SAPPHIRE_TOOL_MATERIAL;

    @Config.LangKey("config.njarm.item.equipment.sapphireBowDurability")
    public final int sapphireBowDurability;
    public static int sapphireBowDurability() { return ConfigHandler.itemCfg.equipmentCfg.sapphireBowDurability; }

    @Config.LangKey("config.njarm.item.equipment.sapphireBowHasFlame")
    public final boolean sapphireBowHasFlame;
    public static boolean sapphireBowHasFlame() { return ConfigHandler.itemCfg.equipmentCfg.sapphireBowHasFlame; }

    @Config.LangKey("config.njarm.item.equipment.sapphireToolsAutoSmelt")
    public final boolean sapphireToolsAutoSmelt;
    public static boolean sapphireToolsAutoSmelt() { return ConfigHandler.itemCfg.equipmentCfg.sapphireToolsAutoSmelt; }

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.platinumToolMaterial")
    @Nonnull public final String platinumToolMaterial;
    @Nonnull public static Item.ToolMaterial PLATINUM_TOOL_MATERIAL;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.netheriteToolMaterial")
    @Nonnull public final String netheriteToolMaterial;
    @Nonnull public static Item.ToolMaterial NETHERITE_TOOL_MATERIAL;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.poppyToolMaterial")
    @Nonnull public final String poppyToolMaterial;
    @Nonnull public static Item.ToolMaterial POPPY_TOOL_MATERIAL;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.obsidianToolMaterial")
    @Nonnull public final String obsidianToolMaterial;
    @Nonnull public static Item.ToolMaterial OBSIDIAN_TOOL_MATERIAL;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.bedrockToolMaterial")
    @Nonnull public final String bedrockToolMaterial;
    @Nonnull public static Item.ToolMaterial BEDROCK_TOOL_MATERIAL;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.equipment.cactusToolMaterial")
    @Nonnull public final String cactusToolMaterial;
    @Nonnull public static Item.ToolMaterial CACTUS_TOOL_MATERIAL;
    @Nonnull public static final UUID CACTUS_REACH_MODIFIER = UUID.fromString("01eab5f7-320b-4b7f-935c-881c5e4da5f0");

    @Config.LangKey("config.njarm.item.equipment.cactusReach")
    public final boolean cactusReach;
    public static boolean cactusReach() { return ConfigHandler.itemCfg.equipmentCfg.cactusReach; }

    void initializeMaterials() {
        RUBY_ARMOR_MATERIAL = armorMaterial("ruby", rubyArmorMaterial, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND);
        SAPPHIRE_ARMOR_MATERIAL = armorMaterial("sapphire", sapphireArmorMaterial, ModSounds.NETHERITE_EQUIP);
        WOOD_ARMOR_MATERIAL = armorMaterial("wood", woodArmorMaterial, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        PLATINUM_ARMOR_MATERIAL = armorMaterial("platinum", platinumArmorMaterial, SoundEvents.ITEM_ARMOR_EQUIP_GOLD);
        NETHERITE_ARMOR_MATERIAL = armorMaterial("netherite", netheriteArmorMaterial, ModSounds.NETHERITE_EQUIP);
        OBSIDIAN_ARMOR_MATERIAL = armorMaterial("obsidian", obsidianArmorMaterial, ModSounds.NETHERITE_EQUIP);
        BEDROCK_ARMOR_MATERIAL = armorMaterial("bedrock", bedrockArmorMaterial, ModSounds.NETHERITE_EQUIP);
        FEATHER_ARMOR_MATERIAL = armorMaterial("feather", featherArmorMaterial, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA);
        CACTUS_ARMOR_MATERIAL = armorMaterial("cactus", cactusArmorMaterial, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);

        RUBY_TOOL_MATERIAL = toolMaterial("ruby", rubyToolMaterial);
        SAPPHIRE_TOOL_MATERIAL = toolMaterial("sapphire", sapphireToolMaterial);
        PLATINUM_TOOL_MATERIAL = toolMaterial("platinum", platinumToolMaterial);
        NETHERITE_TOOL_MATERIAL = toolMaterial("netherite", netheriteToolMaterial);
        POPPY_TOOL_MATERIAL = toolMaterial("poppy", poppyToolMaterial);
        OBSIDIAN_TOOL_MATERIAL = toolMaterial("obsidian", obsidianToolMaterial);
        PLATINUM_TOOL_MATERIAL = toolMaterial("platinum", platinumToolMaterial);
        BEDROCK_TOOL_MATERIAL = toolMaterial("bedrock", bedrockToolMaterial);
        CACTUS_TOOL_MATERIAL = toolMaterial("cactus", cactusToolMaterial);
    }

    @Nonnull
    ItemArmor.ArmorMaterial armorMaterial(@Nonnull String name, @Nonnull String armorMaterial, @Nonnull SoundEvent equipSound) {
        final NBTTagCompound nbt = NBTUtils.getTagFromString(armorMaterial);
        if(!nbt.hasKey("Durability", NBT.TAG_INT)) throw new IllegalStateException(String.format("Missing \"Durability\" tag for %s armor in config!", name));
        else if(!nbt.hasKey("Enchantability", NBT.TAG_INT)) throw new IllegalStateException(String.format("Missing \"Enchantability\" tag for %s armor in config!", name));
        else if(!nbt.hasKey("Toughness", NBT.TAG_FLOAT)) throw new IllegalStateException(String.format("Missing \"Toughness\" tag for %s armor in config!", name));
        //armor values (use tag list cause nbt is stupid here for some reason and doesn't work with NBT.TAG_INT_ARRAY)
        else if(!nbt.hasKey("ArmorValues", NBT.TAG_LIST)) throw new IllegalStateException(String.format("Missing \"ArmorValues\" tag for %s armor in config!", name));
        final int[] armorValues = StreamSupport.stream(nbt.getTagList("ArmorValues", NBT.TAG_INT).spliterator(), false).mapToInt(tag -> ((NBTTagInt)tag).getInt()).toArray();
        if(armorValues.length != 4) throw new IllegalStateException(String.format("\"ArmorValues\" must have exactly four values for %s armor in config!", name));
        //equip sound (TODO)
        /*if(!nbt.hasKey("EquipSound", NBT.TAG_STRING)) throw new IllegalStateException(String.format("Missing \"EquipSound\" tag for %s armor in config!", name));
        final @Nullable SoundEvent equipSound = SoundEvent.REGISTRY.getObject(new ResourceLocation(nbt.getString("EquipSound")));
        if(equipSound == null) throw new IllegalStateException(String.format("Could not get sound from \"%s\" tag for %s armor in config!",
                new ResourceLocation(nbt.getString("EquipSound")), name));*/

        return Objects.requireNonNull(EnumHelper.addArmorMaterial(
                String.format("%s.%s", Constants.MODID, name.toUpperCase()), String.format("%s:%s", Constants.MODID, name),
                nbt.getInteger("Durability"), armorValues, nbt.getInteger("Enchantability"), equipSound, nbt.getFloat("Toughness")));
    }

    @Nonnull
    Item.ToolMaterial toolMaterial(@Nonnull String name, @Nonnull String toolMaterial) {
        final NBTTagCompound nbt = NBTUtils.getTagFromString(toolMaterial);
        if(!nbt.hasKey("HarvestLevel", NBT.TAG_INT)) throw new IllegalStateException(String.format("Missing \"HarvestLevel\" tag for %s tools in config!", name));
        else if(!nbt.hasKey("Durability", NBT.TAG_INT)) throw new IllegalStateException(String.format("Missing \"Durability\" tag for %s tools in config!", name));
        else if(!nbt.hasKey("MiningSpeed", NBT.TAG_FLOAT)) throw new IllegalStateException(String.format("Missing \"MiningSpeed\" tag for %s tools in config!", name));
        else if(!nbt.hasKey("AttackDamage", NBT.TAG_FLOAT)) throw new IllegalStateException(String.format("Missing \"AttackDamage\" tag for %s tools in config!", name));
        else if(!nbt.hasKey("Enchantability", NBT.TAG_INT)) throw new IllegalStateException(String.format("Missing \"Enchantability\" tag for %s tools in config!", name));
        else return Objects.requireNonNull(EnumHelper.addToolMaterial(String.format("%s.%s", Constants.MODID, name.toUpperCase()),
                nbt.getInteger("HarvestLevel"), nbt.getInteger("Durability"), nbt.getFloat("MiningSpeed"), nbt.getFloat("AttackDamage"), nbt.getInteger("Enchantability")));
    }

    //needed for gson
    public EquipmentConfig(@Nonnull String rubyArmorMaterial, @Nonnull String sapphireArmorMaterial, boolean sapphireFireResist, boolean sapphireBlueFireResist, @Nonnull String woodArmorMaterial, @Nonnull String platinumArmorMaterial, @Nonnull String netheriteArmorMaterial, @Nonnull String obsidianArmorMaterial, boolean obsidianResistKnockback, @Nonnull String bedrockArmorMaterial, @Nonnull String featherArmorMaterial, @Nonnull String cactusArmorMaterial, int cactusThorns, @Nonnull String rubyToolMaterial, @Nonnull String sapphireToolMaterial, int sapphireBowDurability, boolean sapphireBowHasFlame, boolean sapphireToolsAutoSmelt, @Nonnull String platinumToolMaterial, @Nonnull String netheriteToolMaterial, @Nonnull String poppyToolMaterial, @Nonnull String obsidianToolMaterial, @Nonnull String bedrockToolMaterial, @Nonnull String cactusToolMaterial, boolean cactusReach) {
        this.rubyArmorMaterial = rubyArmorMaterial;
        this.sapphireArmorMaterial = sapphireArmorMaterial;
        this.sapphireFireResist = sapphireFireResist;
        this.sapphireBlueFireResist = sapphireBlueFireResist;
        this.woodArmorMaterial = woodArmorMaterial;
        this.platinumArmorMaterial = platinumArmorMaterial;
        this.netheriteArmorMaterial = netheriteArmorMaterial;
        this.obsidianArmorMaterial = obsidianArmorMaterial;
        this.obsidianResistKnockback = obsidianResistKnockback;
        this.bedrockArmorMaterial = bedrockArmorMaterial;
        this.featherArmorMaterial = featherArmorMaterial;
        this.cactusArmorMaterial = cactusArmorMaterial;
        this.cactusThorns = cactusThorns;
        this.rubyToolMaterial = rubyToolMaterial;
        this.sapphireToolMaterial = sapphireToolMaterial;
        this.platinumToolMaterial = platinumToolMaterial;
        this.sapphireBowDurability = sapphireBowDurability;
        this.sapphireBowHasFlame = sapphireBowHasFlame;
        this.sapphireToolsAutoSmelt = sapphireToolsAutoSmelt;
        this.netheriteToolMaterial = netheriteToolMaterial;
        this.poppyToolMaterial = poppyToolMaterial;
        this.obsidianToolMaterial = obsidianToolMaterial;
        this.bedrockToolMaterial = bedrockToolMaterial;
        this.cactusToolMaterial = cactusToolMaterial;
        this.cactusReach = cactusReach;
        //only initialize materials on first load
        if(initializeMaterials) {
            initializeMaterials = false;
            initializeMaterials();
        }
    }
}
