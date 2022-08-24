package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * When a mob attacks with a blue fire weapon, the target is lit on blue fire
 * @author jbred
 *
 */
public final class PluginEntityMob implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_70652_k" : "attackEntityAsMob"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(checkMethod(insn.getNext(), obfuscated ? "func_90036_a" : "getFireAspectModifier")) {
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 0));
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 1));
            instructions.insertBefore(insn, genMethodNode("updateAttackBlueFire", "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/Entity;)V"));
            return true;
        }

        return false;
    }
}
