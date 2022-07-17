package git.jbredwards.njarm.asm.plugins.modded;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Apply the 1.16 sounds to hardcoded BOP blocks
 * @author jbred
 *
 */
public final class PluginBiomesOPlenty implements IASMPlugin
{
    @Override
    public boolean transformClass(@Nonnull ClassNode classNode, boolean obfuscated) {
        overrideMethod(classNode, method -> method.name.equals("getSoundType"), "fixBOPBlockSounds", "(I)Lnet/minecraft/block/SoundType;", generator -> {
            generator.visitVarInsn(ALOAD, 1);
            generator.visitFieldInsn(GETSTATIC, "biomesoplenty/common/block/BlockBOPGrass", "VARIANT", "Lnet/minecraft/block/properties/PropertyEnum;");
            generator.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", "getValue", "(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;", true);
            generator.visitTypeInsn(CHECKCAST, "biomesoplenty/common/block/BlockBOPGrass$BOPGrassType");
            generator.visitMethodInsn(INVOKEVIRTUAL, "biomesoplenty/common/block/BlockBOPGrass$BOPGrassType", "ordinal", "()I", false);
        });

        return false;
    }
}
