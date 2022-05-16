package git.jbredwards.njarm.asm;

import com.google.common.collect.ImmutableMap;
import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import git.jbredwards.njarm.asm.plugins.forge.*;
import git.jbredwards.njarm.asm.plugins.vanilla.*;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Handles this mod's transformers
 * @author jbred
 *
 */
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.Name("NJARM 2 Plugin")
@IFMLLoadingPlugin.MCVersion("1.12.2")
public final class ASMHandler implements IFMLLoadingPlugin
{
    /**
     * True if this is being run from an obfuscated environment
     */
    public static boolean obfuscated;

    /**
     * This class exists because the launcher don't allow {@link IClassTransformer IClassTransformers}
     * to be the same class as {@link IFMLLoadingPlugin IFMLLoadingPlugins}
     */
    @SuppressWarnings("unused")
    public static final class Transformer implements IClassTransformer
    {
        /**
         * Indexes the plugins used by this mod based on the class they're transforming
         */
        @Nonnull
        private static final Map<String, IASMPlugin> PLUGINS = ImmutableMap.<String, IASMPlugin>builder()
                //vanilla
                .put("net.minecraft.client.particle.ParticleDrip", new PluginParticleDrip()) //Water droplet particles keep the color set by this mod
                .put("net.minecraft.client.renderer.entity.Render", new PluginRender()) //Bedrock edition entity shadow size parody
                .put("net.minecraft.block.BlockBasePressurePlate", new PluginIHasRunningEffects()) //Fix running effects for pressure plates
                .put("net.minecraft.block.BlockCauldron", new PluginBlockCauldron()) //Allows cauldrons to have transparent water
                .put("net.minecraft.block.BlockCarpet", new PluginIHasRunningEffects()) //Fix running effects for carpets
                .put("net.minecraft.block.BlockLilyPad", new PluginIHasRunningEffects()) //Fix running effects for lily pads
                .put("net.minecraft.block.BlockRailBase", new PluginIHasRunningEffects()) //Fix running effects for rails
                .put("net.minecraft.block.BlockRedstoneDiode", new PluginIHasRunningEffects()) //Fix running effects for repeaters & comparators
                .put("net.minecraft.block.BlockSnow", new PluginIHasRunningEffects()) //Fix running effects for snow layers
                .put("net.minecraft.block.BlockTrapDoor", new PluginIHasRunningEffects()) //Fix running effects for trapdoors
                .put("net.minecraft.entity.Entity", new PluginEntity()) //Fix MC-1691
                .put("net.minecraft.entity.EntityLivingBase", new PluginEntityLivingBase()) //Fix MC-1691
                //forge
                .put("net.minecraftforge.fluids.FluidRegistry", new PluginFluidRegistry()) //Changes the water textures to allow for better coloring
                .build();

        @Nonnull
        @Override
        public byte[] transform(@Nonnull String name, @Nonnull String transformedName, @Nonnull byte[] basicClass) {
            final @Nullable IASMPlugin plugin = PLUGINS.get(transformedName);
            return plugin != null ? plugin.transform(basicClass, obfuscated) : basicClass;
        }
    }

    @Override
    public void injectData(@Nonnull Map<String, Object> map) {
        obfuscated = (boolean)map.get("runtimeDeobfuscationEnabled");
    }

    @Nonnull
    @Override
    public String[] getASMTransformerClass() {
        return new String[] {"git.jbredwards.njarm.asm.ASMHandler$Transformer"};
    }

    @Nullable
    @Override
    public String getModContainerClass() { return null; }

    @Nullable
    @Override
    public String getSetupClass() { return null; }

    @Nullable
    @Override
    public String getAccessTransformerClass() { return null; }
}
