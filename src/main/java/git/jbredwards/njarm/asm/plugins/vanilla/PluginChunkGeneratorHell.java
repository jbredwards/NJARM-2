package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Adds soul soil under soul sand during world gen
 * @author jbred
 *
 */
public final class PluginChunkGeneratorHell implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_185937_b" : "buildSurfaces"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(insn.getNext().getOpcode() == ASTORE && ((VarInsnNode)insn.getNext()).var == 14 && checkField(insn, obfuscated ? "field_185945_f" : "SOUL_SAND")) {
            instructions.insertBefore(insn, new FieldInsnNode(GETSTATIC, "git/jbredwards/njarm/mod/common/init/ModBlocks", "SOUL_SOIL", "Lnet/minecraft/block/Block;"));
            instructions.insertBefore(insn, new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/block/Block", obfuscated ? "func_176223_P" : "getDefaultState", "()Lnet/minecraft/block/state/IBlockState;", false));
            instructions.remove(insn);
            return true;
        }

        return false;
    }
}
