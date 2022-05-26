package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Add 1.13+ biome color blend slider functionality
 * @author jbred
 *
 */
public final class PluginBiomeColorHelper implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) {
        return checkMethod(method, obfuscated ? "func_180285_a" : "getColorAtPos", "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/biome/BiomeColorHelper$ColorResolver;)I");
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(insn.getPrevious() == instructions.getFirst()) {
            final InsnList list = new InsnList();
            list.add(genMethodNode("git/jbredwards/njarm/mod/common/config/client/RenderingConfig", "biomeColorBlendRadius", "()I"));
            list.add(new VarInsnNode(ISTORE, 9));
            instructions.insertBefore(insn, list);
        }
        //change start
        else if(insn.getOpcode() == ICONST_M1) {
            instructions.insertBefore(insn, new VarInsnNode(ILOAD, 9));
            instructions.insert(insn, new InsnNode(IMUL));
        }
        //change end
        else if(insn.getOpcode() == ICONST_1) {
            instructions.insert(insn, new VarInsnNode(ILOAD, 9));
            instructions.remove(insn);
        }
        //change blend
        else if(insn.getNext().getOpcode() == IRETURN) {
            final InsnList list = new InsnList();
            list.add(new VarInsnNode(ILOAD, 3));
            list.add(new VarInsnNode(ILOAD, 4));
            list.add(new VarInsnNode(ILOAD, 5));
            list.add(genMethodNode("doAccurateBiomeBlend", "(III)I"));
            instructions.insert(insn, list);
            removeFrom(instructions, insn, -20);
            return true;
        }

        return false;
    }

    @Override
    public int addLocalVariables(@Nonnull MethodNode method, @Nonnull LabelNode start, @Nonnull LabelNode end, int index) {
        method.localVariables.add(new LocalVariableNode("biomeBlendRadius", "I", null, start, end, 9));
        return 1;
    }
}
