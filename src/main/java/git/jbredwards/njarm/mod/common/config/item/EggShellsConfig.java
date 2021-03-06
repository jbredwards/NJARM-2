package git.jbredwards.njarm.mod.common.config.item;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.init.ModItems;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public final class EggShellsConfig implements IConfig
{
    @Config.LangKey("config.njarm.item.eggShells.actAsBonemeal")
    public final boolean actAsBonemeal;
    public static boolean actAsBonemeal() { return ConfigHandler.itemCfg.eggShellCfg.actAsBonemeal; }

    @Config.SlidingOption
    @Config.RangeInt(min = 0, max = 100)
    @Config.LangKey("config.njarm.item.eggShells.fromDispenserChance")
    public final int fromDispenserChance;
    public static int fromDispenserChance() { return ConfigHandler.itemCfg.eggShellCfg.fromDispenserChance; }

    @Config.SlidingOption
    @Config.RangeInt(min = 0, max = 100)
    @Config.LangKey("config.njarm.item.eggShells.fromThrownChance")
    public final int fromThrownChance;
    public static int fromThrownChance() { return ConfigHandler.itemCfg.eggShellCfg.fromThrownChance; }

    @Config.LangKey("config.njarm.item.eggShells.fromEntityList")
    @Nonnull public final String[] fromEntityList;

    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.item.eggShells.fromCraftingResult")
    @Nonnull public final String[] fromCraftingResult;

    @Config.Ignore
    public static final Object2BooleanMap<Class<?>> ENTITIES = new Object2BooleanOpenHashMap<>();

    @Override
    public void onUpdate() {
        //update dispenser action to reflect config
        if(actAsBonemeal) ModItems.EGG_SHELL.addDispenserBehavior();
        else BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.registryObjects.remove(ModItems.EGG_SHELL);
        //generate valid entities
        ENTITIES.clear();
        for(String entity : fromEntityList) {
            final @Nullable EntityEntry entry = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entity));
            if(entry != null) ENTITIES.put(entry.getEntityClass(), true);
        }
    }

    @Override
    public void onFMLInit() {
        IConfig.super.onFMLInit();
        //generate the crafting results
        for(String name : fromCraftingResult) {
            final @Nullable Item item = Item.getByNameOrId(name);
            if(item != null) item.setContainerItem(ModItems.EGG_SHELL);
        }
    }

    //needed for gson
    public EggShellsConfig(boolean actAsBonemeal, int fromDispenserChance, int fromThrownChance, @Nonnull String[] fromEntityList, @Nonnull String[] fromCraftingResult) {
        this.actAsBonemeal = actAsBonemeal;
        this.fromDispenserChance = fromDispenserChance;
        this.fromThrownChance = fromThrownChance;
        this.fromEntityList = fromEntityList;
        this.fromCraftingResult = fromCraftingResult;
    }
}
