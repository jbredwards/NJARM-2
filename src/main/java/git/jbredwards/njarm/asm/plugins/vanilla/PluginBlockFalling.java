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
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_185759_i" : "canFallThrough"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(insn.getOpcode() == IF_ACMPEQ) {
            ((JumpInsnNode)insn).setOpcode(IF_ICMPEQ);
            instructions.remove(insn.getPrevious());
            instructions.insertBefore(insn, new TypeInsnNode(INSTANCEOF, "git/jbredwards/njarm/mod/common/block/util/ICanFallThrough"));
            instructions.insertBefore(insn, new InsnNode(ICONST_1));
            return true;
        }

        return false;
    }
}
