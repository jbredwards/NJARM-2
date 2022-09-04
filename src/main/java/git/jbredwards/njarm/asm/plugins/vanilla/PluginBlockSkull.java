package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Use soul soil instead of soul sand for withers
 * @author jbred
 *
 */
public final class PluginBlockSkull implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) {
        return method.name.equals(obfuscated ? "func_176414_j" : "getWitherBasePattern") || method.name.equals(obfuscated ? "func_176416_l" : "getWitherPattern");
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(checkMethod(insn, obfuscated ? "func_177638_a" : "forBlock")) {
            instructions.insert(insn, genMethodNode("getWitherBaseBlock", "()Lcom/google/common/base/Predicate;"));
            removeFrom(instructions, insn, -1);
            return true;
        }

        return false;
    }
}
