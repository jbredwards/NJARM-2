package git.jbredwards.njarm.asm;

import com.google.common.collect.ImmutableMap;
import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import git.jbredwards.njarm.asm.plugins.vanilla.PluginParticleDrip;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Handles this mod's transformers
 * @author jbred
 *
 */
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.Name("NJARM2 Plugin")
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
                .put("net.minecraft.client.particle.ParticleDrip", new PluginParticleDrip())
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
        //handle mixins
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins." + Constants.MODID + ".forge.json");
        Mixins.addConfiguration("mixins." + Constants.MODID + ".vanilla.json");
        MixinEnvironment.getCurrentEnvironment().setObfuscationContext("searge");
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
