package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Render blue fire without "quirkiness"
 * @author jbred
 *
 */
public final class PluginItemRenderer implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_78447_b" : "renderOverlays"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(checkMethod(insn, obfuscated ? "func_70027_ad" : "isBurning")) {
            instructions.insert(insn, genMethodNode("renderBlueFireFirstPerson", "(Z)Z"));
            return true;
        }

        return false;
    }
}
