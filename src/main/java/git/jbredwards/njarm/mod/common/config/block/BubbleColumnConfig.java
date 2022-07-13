package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.block.BlockBubbleColumn;
import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.util.ChatUtils;
import net.minecraft.block.Block;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public final class BubbleColumnConfig implements IConfig
{
    @Config.LangKey("config.njarm.block.bubbleColumn.magma")
    @Nonnull public final String[] magmaBlocks;

    @Config.LangKey("config.njarm.block.bubbleColumn.soulSand")
    @Nonnull public final String[] soulSandBlocks;

    @Override
    public void onUpdate() {
        //setup magma blocks
        BlockBubbleColumn.Drag.DOWN.blocks.clear();
        for(String str : magmaBlocks) {
            try {
                final NBTTagCompound nbt = JsonToNBT.getTagFromJson(str);
                if(ChatUtils.getOrError(nbt.hasKey("blockId", Constants.NBT.TAG_STRING), "Missing blockId for string: " + str)) {
                    final String blockName = nbt.getString("blockId");
                    final @Nullable Block block = Block.getBlockFromName(blockName);
                    if(ChatUtils.getOrError(block != null, "Could not find block for blockId: " + blockName)) {
                        BlockBubbleColumn.Drag.DOWN.addBlock(block, nbt.hasKey("metadata", Constants.NBT.TAG_INT_ARRAY)
                                ? nbt.getIntArray("metadata") : new int[0]);
                    }
                }
            }
            //oops
            catch(NBTException e) { e.printStackTrace(); }
        }

        //setup soul sand blocks
        BlockBubbleColumn.Drag.UP.blocks.clear();
        for(String str : soulSandBlocks) {
            try {
                final NBTTagCompound nbt = JsonToNBT.getTagFromJson(str);
                if(ChatUtils.getOrError(nbt.hasKey("blockId", Constants.NBT.TAG_STRING), "Missing blockId for string: " + str)) {
                    final String blockName = nbt.getString("blockId");
                    final @Nullable Block block = Block.getBlockFromName(blockName);
                    if(ChatUtils.getOrError(block != null, "Could not find block for blockId: " + blockName)) {
                        BlockBubbleColumn.Drag.UP.addBlock(block, nbt.hasKey("metadata", Constants.NBT.TAG_INT_ARRAY)
                                ? nbt.getIntArray("metadata") : new int[0]);
                    }
                }
            }
            //oops
            catch(NBTException e) { e.printStackTrace(); }
        }
    }

    //needed for gson
    public BubbleColumnConfig(@Nonnull String[] magmaBlocks, @Nonnull String[] soulSandBlocks) {
        this.magmaBlocks = magmaBlocks;
        this.soulSandBlocks = soulSandBlocks;
    }
}
