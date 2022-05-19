package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.config.IConfig;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public final class BlueFireConfig implements IConfig
{
    @Config.Comment("Blue fire will treat these blocks as soul sand.")
    @Nonnull public final String[] soulSandBlocks;

    @Config.Ignore
    public static final Object2BooleanMap<Block> SOUL_SAND = new Object2BooleanOpenHashMap<>();

    @Override
    public void onUpdate() {
        //generate the soul sand blocks
        SOUL_SAND.clear();
        for(String name : soulSandBlocks) {
            final @Nullable Block block = Block.getBlockFromName(name);
            if(block != null) SOUL_SAND.put(block, true);
        }
    }

    //needed for gson
    public BlueFireConfig(@Nonnull String[] soulSandBlocks) {
        this.soulSandBlocks = soulSandBlocks;
    }
}
