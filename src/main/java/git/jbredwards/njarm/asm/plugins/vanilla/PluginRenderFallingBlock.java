package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Fix rendering with ILayeredFallingBlock
 * @author jbred
 *
 */
public final class PluginRenderFallingBlock implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_76986_a" : "doRender"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(insn.getOpcode() == IF_ACMPEQ) {
            ((JumpInsnNode)insn).setOpcode(IFEQ);
            removeFrom(instructions, insn, -7);
            return true;
        }

        return false;
    }
}
