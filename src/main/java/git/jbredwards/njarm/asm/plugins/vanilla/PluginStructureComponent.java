package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Generate cracked nether bricks with nether fortresses
 * @author jbred
 *
 */
public final class PluginStructureComponent implements IASMPlugin
{
    @Override
    public int getMethodIndex(@Nonnull MethodNode method, boolean obfuscated) {
        if(method.name.equals(obfuscated ? "func_175804_a" : "fillWithBlocks")) return 1;
        return method.name.equals(obfuscated ? "func_175808_b" : "replaceAirAndLiquidDownwards") ? 2 : 0;
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(insn.getOpcode() == ALOAD && (index == 2 && ((VarInsnNode)insn).var == 2 || index == 1 && (((VarInsnNode)insn).var == 9 || ((VarInsnNode)insn).var == 10))) {
            instructions.insert(insn, genMethodNode("getNetherBrickOrCracked", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;)Lnet/minecraft/block/state/IBlockState;"));
            instructions.insert(insn, new VarInsnNode(ALOAD, 1));
            return ((VarInsnNode)insn).var != 10;
        }

        return false;
    }
}
