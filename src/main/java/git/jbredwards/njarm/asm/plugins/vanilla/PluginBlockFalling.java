package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Allow falling blocks to fall through modded blocks
 * @author jbred
 *
 */
public final class PluginBlockFalling implements IASMPlugin
{
    @Override
    public boolean transformClass(@Nonnull ClassNode classNode, boolean obfuscated) {
        overrideMethod(classNode, method -> method.name.equals(obfuscated ? "func_185759_i" : "canFallThrough"),
            "canFallThrough", "(Lnet/minecraft/block/state/IBlockState;)Z",
                generator -> generator.visitVarInsn(ALOAD, 0));

        return false;
    }
}
