package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Initialize IHasDestroyEffects
 * @author jbred
 *
 */
public final class PluginParticleManager implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_180533_a" : "addBlockDestroyEffects"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(checkMethod(insn, "addDestroyEffects")) {
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 2));
            instructions.insertBefore(insn, genMethodNode("git/jbredwards/njarm/mod/common/block/util/IHasDestroyEffects", "addDestroyEffects",
                    "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/particle/ParticleManager;Lnet/minecraft/block/state/IBlockState;)Z"));
            instructions.remove(insn);
            return true;
        }

        return false;
    }
}
