package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

import javax.annotation.Nonnull;

/**
 * Fixes blue fire related entity rendering lighting bugs
 * @author jbred
 *
 */
public final class PluginRenderManager implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) {
        return method.name.equals(obfuscated ? "func_188388_a" : "renderEntityStatic") || method.name.equals(obfuscated ? "func_188389_a" : "renderMultipass");
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(checkMethod(insn, obfuscated ? "func_70027_ad" : "isBurning")) {
            instructions.insert(insn, genMethodNode("correctBlueFireBrightnessForRender", "(Lnet/minecraft/entity/Entity;)Z"));
            instructions.remove(insn);
            return true;
        }

        return false;
    }
}
