package git.jbredwards.njarm.mod.common.config.world;

import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.util.ChatUtils;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import git.jbredwards.njarm.mod.common.world.gen.OreGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 *
 * @author jbred
 *
 */
public final class OreConfig implements IConfig
{
    @Config.LangKey("config.njarm.world.oreGen.ores")
    @Nonnull public final String[] ores;
    @Nonnull public static final NonNullList<OreGenerator.Data> ORES = NonNullList.create();

    @Override
    public void onUpdate() {
        ORES.clear();
        for(String ore : ores) {
            final NBTTagCompound nbt = NBTUtils.getTagFromString(ore);
            final IBlockState oreState = NBTUtil.readBlockState(nbt.getCompoundTag("Ore"));
            if(ChatUtils.getOrError(oreState.getBlock() != Blocks.AIR, "Could not get valid ore id from: " + ore)) {
                final IBlockState stoneState = NBTUtil.readBlockState(nbt.getCompoundTag("Stone"));
                if(ChatUtils.getOrError(stoneState.getBlock() != Blocks.AIR, "Could not get valid stone id from: " + ore))
                    ORES.add(new OreGenerator.Data(oreState, stoneState::equals,
                        new ArrayList<>(NBTUtils.gatherBiomesFromNBT(nbt)),
                        nbt.getInteger("MinY"),
                        nbt.getInteger("MaxY"),
                        nbt.getInteger("ClumpSize"),
                        nbt.getInteger("PerChunk"),
                        nbt.getIntArray("Dimensions"))
                );
            }
        }
    }

    //needed for gson
    public OreConfig(@Nonnull String[] ores) { this.ores = ores; }
}
