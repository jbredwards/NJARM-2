package git.jbredwards.njarm.asm.mixins.forge;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import javax.annotation.Nonnull;

/**
 * Changes the water textures to allow for better coloring
 * @author jbred
 *
 */
@Mixin(FluidRegistry.class)
public abstract class MixinFluidRegistry
{
    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/FluidRegistry$1;<init>(Ljava/lang/String;Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/util/ResourceLocation;)V", remap = false), remap = false)
    private static void replaceWaterTextures(@Nonnull Args args) {
        args.set(1, new ResourceLocation(Constants.MODID, "blocks/water_still"));
        args.set(2, new ResourceLocation(Constants.MODID, "blocks/water_flow"));
        args.set(3, new ResourceLocation(Constants.MODID, "blocks/water_overlay"));
    }
}
