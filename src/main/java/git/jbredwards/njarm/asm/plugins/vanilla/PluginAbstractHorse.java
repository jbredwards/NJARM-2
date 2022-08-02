package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Lets horses be ridden by holding a carrot on a stick
 * @author jbred
 *
 */
public final class PluginAbstractHorse implements IASMPlugin
{
    @Override
    public int getMethodIndex(@Nonnull MethodNode method, boolean obfuscated) {
        if(method.name.equals(obfuscated ? "func_70610_aX" : "isMovementBlocked")) return 1;
        else return method.name.equals(obfuscated ? "func_191986_a" : "travel") ? 2 : 0;
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        //replace saddle check with one that also accepts carrots on a stick
        if(checkMethod(insn, obfuscated ? "func_110257_ck" : "isHorseSaddled")) {
            instructions.insert(insn, genMethodNode("isHoldingCarrot", "(ZLnet/minecraft/entity/passive/AbstractHorse;)Z"));
            instructions.insert(insn, new VarInsnNode(ALOAD, 0));
            return index == 1;
        }

        //change travel speed if the user is holding a carrot on a stick
        else if(index == 2 && checkField(insn, obfuscated ? "field_191988_bg" : "moveForward")) {
            instructions.insert(insn, genMethodNode("setForwardSpeed", "(FLnet/minecraft/entity/passive/AbstractHorse;)F"));
            instructions.insert(insn, new VarInsnNode(ALOAD, 0));
            return true;
        }

        return false;
    }
}
