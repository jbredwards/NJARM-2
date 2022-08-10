package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Implement IHasWorldState functionality & fix snow layer placement
 * @author jbred
 *
 */
public final class PluginWorld implements IASMPlugin
{
    @Override
    public int getMethodIndex(@Nonnull MethodNode method, boolean obfuscated) {
        if(checkMethod(method, obfuscated ? "func_180501_a" : "setBlockState", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z")) return 1;
        else return method.name.equals("canSnowAtBody") ? 2 : 0;
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        //implement IHasWorldState functionality
        if(index == 1 && insn == instructions.getFirst()) {
            final InsnList list = new InsnList();
            list.add(new VarInsnNode(ALOAD, 0));
            list.add(new VarInsnNode(ALOAD, 1));
            list.add(new VarInsnNode(ALOAD, 2));
            list.add(genMethodNode("getStateForWorld", "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;"));
            list.add(new VarInsnNode(ASTORE, 2));
            instructions.insertBefore(insn, list);
            return true;
        }
        //fix snow layer placement
        else if(index == 2 && checkMethod(insn, obfuscated ? "func_176196_c" : "canPlaceBlockAt")) {
            instructions.insert(insn, genMethodNode("fixSnowLayerPlacement", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z"));
            instructions.remove(insn);
            return true;
        }

        return false;
    }
}
