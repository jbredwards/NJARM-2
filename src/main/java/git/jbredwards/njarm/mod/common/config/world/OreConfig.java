package git.jbredwards.njarm.mod.common.config.world;

import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.world.generation.OreGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public final class OreConfig implements IConfig
{
    @Config.Comment("Add, remove, or change ore generation.")
    @Nonnull public final String[] ores;
    @Nonnull public static final NonNullList<OreGenerator.Data> ORES = NonNullList.create();

    @Override
    public void onUpdate() {
        ORES.clear();
        for(String ore : ores) {
            try {
                final NBTTagCompound nbt = JsonToNBT.getTagFromJson(ore);
                final NBTTagCompound oreNBT = nbt.getCompoundTag("Ore");
                final @Nullable Block oreBlock = Block.getBlockFromName(oreNBT.getString("block"));
                if(oreBlock != null) {
                    final NBTTagCompound stoneNBT = nbt.getCompoundTag("Stone");
                    final @Nullable Block stoneBlock = Block.getBlockFromName(stoneNBT.getString("block"));
                    if(stoneBlock != null) {
                        final int stoneMeta = stoneNBT.getInteger("meta");
                        final IBlockState stoneState = stoneBlock.getStateFromMeta(stoneMeta);
                        ORES.add(new OreGenerator.Data(oreBlock.getStateFromMeta(oreNBT.getInteger("meta")),
                                stoneMeta == OreDictionary.WILDCARD_VALUE ? stone -> Block.isEqualTo(stoneBlock, stone.getBlock()) : stoneState::equals,
                                nbt.getInteger("MinY"),
                                nbt.getInteger("MaxY"),
                                nbt.getInteger("ClumpSize"),
                                nbt.getInteger("PerChunk"),
                                nbt.getIntArray("Dimensions")));
                    }
                }
            }
            //oops
            catch(NBTException e) { e.printStackTrace(); }
        }
    }

    //needed for gson
    public OreConfig(@Nonnull String[] ores) {
        this.ores = ores;
    }
}
