package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Swap hardcoded values for biome fog color
 * @author jbred
 *
 */
public final class PluginBlock implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals("getFogColor"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(insn.getOpcode() == NEW) {
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 1));
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 2));
            instructions.insertBefore(insn, new VarInsnNode(FLOAD, 7));
            instructions.insertBefore(insn, genMethodNode("betterWaterFogColor", "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;F)Lnet/minecraft/util/math/Vec3d;"));
            removeFrom(instructions, insn, 14);
            return true;
        }

        return false;
    }
}
