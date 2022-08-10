package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Add magma surrounding lava lakes
 * @author jbred
 *
 */
public class PluginWorldGenLakes implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_180709_b" : "generate"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(checkField(insn, obfuscated ? "field_150348_b" : "STONE")) {
            instructions.insert(insn, genMethodNode("getRandomBlockForSurroundingLava", "(Ljava/util/Random;I)Lnet/minecraft/block/Block;"));
            instructions.insert(insn, new VarInsnNode(ILOAD, 8));
            instructions.insert(insn, new VarInsnNode(ALOAD, 2));
            instructions.remove(insn);
            return true;
        }

        return false;
    }
}
