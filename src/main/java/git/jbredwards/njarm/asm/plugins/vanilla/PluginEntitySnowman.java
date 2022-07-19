package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Snow golems cannot generate falling snow layers
 * @author jbred
 *
 */
public final class PluginEntitySnowman implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_70636_d" : "onLivingUpdate"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(checkField(insn, obfuscated ? "field_151579_a" : "AIR")) {
            final InsnList list = new InsnList();
            list.add(new VarInsnNode(ALOAD, 0));
            list.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/Entity", obfuscated ? "field_70170_p" : "world", "Lnet/minecraft/world/World;"));
            list.add(new VarInsnNode(ALOAD, 5));
            list.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/util/math/BlockPos", obfuscated ? "func_177977_b" : "down", "()Lnet/minecraft/util/math/BlockPos;", false));
            list.add(new MethodInsnNode(INVOKEINTERFACE, "net/minecraft/world/IBlockAccess", obfuscated ? "func_180495_p" : "getBlockState", "(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", true));
            list.add(genMethodNode("canFallThrough", "(Lnet/minecraft/block/state/IBlockState;)Z"));
            list.add(new JumpInsnNode(IFNE, ((JumpInsnNode)insn.getNext()).label));
            instructions.insert(insn.getNext(), list);
            return true;
        }

        return false;
    }
}
