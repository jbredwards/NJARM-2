package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Lightning entities only create fire if they're added to the world
 * @author jbred
 *
 */
public final class PluginEntityLightningBolt implements IASMPlugin
{
    @Override
    public int getMethodIndex(@Nonnull MethodNode method, boolean obfuscated) {
        if(method.name.equals("<init>")) return 1;
        else return method.name.equals(obfuscated ? "func_70071_h_" : "onUpdate") ? 2 : 0;
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        //prevent the fire from the constructor by inserting a "!true" condition
        if(index == 1 && insn.getOpcode() == ILOAD && insn.getNext().getOpcode() == IFNE) {
            instructions.insert(insn, new InsnNode(ICONST_1));
            instructions.remove(insn);
            return true;
        }
        //put the fire stuff from the constructor into the "onUpdate" method
        else if(index == 2 && checkMethod(insn.getPrevious(), obfuscated ? "func_184148_a" : "playSound")) {
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 0));
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 0));
            instructions.insertBefore(insn, new FieldInsnNode(GETFIELD, "net/minecraft/entity/Entity", obfuscated ? "field_70146_Z" : "rand", "Ljava/util/Random;"));
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 0));
            instructions.insertBefore(insn, new FieldInsnNode(GETFIELD, "net/minecraft/entity/effect/EntityLightningBolt", obfuscated ? "field_184529_d" : "effectOnly", "Z"));
            instructions.insertBefore(insn, genMethodNode("fixLightningFire", "(Lnet/minecraft/entity/effect/EntityLightningBolt;Ljava/util/Random;Z)V"));
            return true;
        }

        return false;
    }
}
