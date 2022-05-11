package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.ClassNode;

import javax.annotation.Nonnull;

/**
 * Allows cauldrons to have transparent water
 * @author jbred
 *
 */
public final class PluginBlockCauldron implements IASMPlugin
{
    @Override
    public boolean transformClass(@Nonnull ClassNode classNode, boolean obfuscated) {
        addMethod(classNode, "canRenderInLayer", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z",
            "canCauldronRenderInLayer", "(Lnet/minecraft/block/Block;Lnet/minecraft/util/BlockRenderLayer;)Z", generator -> {
                generator.visitVarInsn(ALOAD, 0);
                generator.visitVarInsn(ALOAD, 2);
                generator.visitMaxs(2, 0);
            }
        );

        return false;
    }
}
