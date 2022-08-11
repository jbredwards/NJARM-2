package git.jbredwards.njarm.mod.common.config.item;

import git.jbredwards.njarm.mod.common.block.BlockSmallGrass;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;

/**
 *
 * @author jbred
 *
 */
public final class RupeeConfig implements IConfig
{
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    @Config.LangKey("config.njarm.item.rupee.seedWeight")
    public final int seedWeight;

    @Config.LangKey("config.njarm.item.rupee.sound")
    public final boolean sound;

    @Override
    public void onFMLInit() {
        if(seedWeight > 0) {
            MinecraftForge.addGrassSeed(new ItemStack(ModItems.RUPEE),
                    ConfigHandler.itemCfg.rupeeCfg.seedWeight);
            ModBlocks.ENDER_GRASS.seeds.add(new BlockSmallGrass.SeedEntry(ModItems.RUPEE,
                    ConfigHandler.itemCfg.rupeeCfg.seedWeight));
        }
    }

    //needed for gson
    public RupeeConfig(boolean sound, int seedWeight) {
        this.sound = sound;
        this.seedWeight = seedWeight;
    }
}
