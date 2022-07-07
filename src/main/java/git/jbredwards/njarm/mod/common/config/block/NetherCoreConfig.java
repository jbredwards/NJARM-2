package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public final class NetherCoreConfig implements IConfig
{
    @Config.RequiresMcRestart
    @Config.LangKey("config.njarm.block.netherCore.altReactorBehavior")
    public final boolean altReactorBehavior;
    public static boolean getAltReactorBehavior() { return ConfigHandler.blockCfg.netherCoreCfg.altReactorBehavior; }

    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.block.netherCore.range")
    public final int range;
    public static int getRange() { return ConfigHandler.blockCfg.netherCoreCfg.range; }

    @Config.RangeInt(min = 100)
    @Config.LangKey("config.njarm.block.netherCore.duration")
    public final int duration;
    public static int getDuration() { return ConfigHandler.blockCfg.netherCoreCfg.duration; }

    @Config.RangeInt(min = 1)
    @Config.LangKey("config.njarm.block.netherCore.pigmanCooldown")
    public final int pigmanCooldown;
    public static int getPigmanCooldown() { return ConfigHandler.blockCfg.netherCoreCfg.pigmanCooldown; }

    @Config.RangeInt(min = 1)
    @Config.LangKey("config.njarm.block.netherCore.itemCooldown")
    public final int itemCooldown;
    public static int getItemCooldown() { return ConfigHandler.blockCfg.netherCoreCfg.itemCooldown; }

    @Config.RangeInt(min = 1)
    @Config.LangKey("config.njarm.block.netherCore.itemCount")
    public final int itemCount;
    public static int getItemCount() { return ConfigHandler.blockCfg.netherCoreCfg.itemCount; }

    @Config.LangKey("config.njarm.block.netherCore.dynamicDifficulty")
    public final boolean dynamicDifficulty;
    public static boolean isDynamicDifficulty() { return ConfigHandler.blockCfg.netherCoreCfg.dynamicDifficulty; }

    @Nonnull
    @Config.LangKey("config.njarm.block.netherCore.entities")
    public final String[] entities;
    public static EntityEntry[] entries;
    public static EntityEntry getRandomEntry(@Nonnull Random rand) { return entries[rand.nextInt(entries.length)]; }

    @Override
    public void onUpdate() {
        final List<EntityEntry> list = new ArrayList<>();
        for(String entity : entities) {
            final @Nullable EntityEntry entry = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entity));
            if(entry != null) list.add(entry);
        }

        entries = list.toArray(new EntityEntry[0]);
    }

    //needed for gson
    public NetherCoreConfig(boolean altReactorBehavior, int range, int duration, int pigmanCooldown, int itemCooldown, int itemCount, boolean dynamicDifficulty, @Nonnull String[] entities) {
        this.altReactorBehavior = altReactorBehavior;
        this.range = range;
        this.duration = duration;
        this.pigmanCooldown = pigmanCooldown;
        this.itemCooldown = itemCooldown;
        this.itemCount = itemCount;
        this.dynamicDifficulty = dynamicDifficulty;
        this.entities = entities;
    }
}
