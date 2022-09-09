package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Use snowy state for any snow block instance
 * @author jbred
 *
 */
public final class PluginBlockGrass implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_176221_a" : "getActualState"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(checkMethod(insn, "valueOf")) {
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 4));
            instructions.insertBefore(insn, genMethodNode("fixGrassSnowyState", "(ZLnet/minecraft/block/Block;)Ljava/lang/Boolean;"));
            instructions.remove(insn);
            return true;
        }

        return false;
    }
}
