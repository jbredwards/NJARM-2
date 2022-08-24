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
    public int getMethodIndex(@Nonnull MethodNode method, boolean obfuscated) {
        if(method.name.equals(obfuscated ? "func_184601_bQ" : "getHurtSound")) return 1;
        else return method.name.equals(obfuscated ? "func_71059_n" : "attackTargetEntityWithCurrentItem") ? 2 : 0;
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(index == 1 && insn.getNext().getOpcode() == IF_ACMPNE) {
            ((JumpInsnNode)insn.getNext()).setOpcode(IFEQ);
            instructions.insert(insn, genMethodNode("fixPlayerBlueFireDamageSound", "(Lnet/minecraft/util/DamageSource;)Z"));
            instructions.remove(insn);
            return true;
        }

        else if(index == 2 && checkMethod(insn.getNext(), obfuscated ? "func_90036_a" : "getFireAspectModifier")) {
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 0));
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 1));
            instructions.insertBefore(insn, genMethodNode("updateAttackBlueFire", "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/Entity;)V"));
            return true;
        }

        return false;
    }
}
