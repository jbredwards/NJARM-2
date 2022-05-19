package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Plays the fire damage sound at the exact moment when the player takes damage from blue fire
 * @author jbred
 *
 */
public final class PluginEntityPlayer implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_184601_bQ" : "getHurtSound"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(insn.getNext().getOpcode() == IF_ACMPNE) {
            ((JumpInsnNode)insn.getNext()).setOpcode(IFEQ);
            instructions.insert(insn, genMethodNode("fixPlayerBlueFireDamageSound", "(Lnet/minecraft/util/DamageSource;)Z"));
            instructions.remove(insn);
            return true;
        }

        return false;
    }
}
