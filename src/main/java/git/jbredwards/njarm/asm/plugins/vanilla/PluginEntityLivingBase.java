package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Fix MC-1691 & fix fire damage sound
 * @author jbred
 *
 */
public final class PluginEntityLivingBase implements IASMPlugin
{
    @Override
    public int getMethodIndex(@Nonnull MethodNode method, boolean obfuscated) {
        if(method.name.equals(obfuscated ? "func_184231_a" : "updateFallState")) return 1;
        else if(method.name.equals(obfuscated ? "func_70097_a" : "attackEntityFrom")) return 2;
        return 0;
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        //fix fall damage block particles
        if(index == 1 && insn.getOpcode() == ISTORE) {
            final InsnList list = new InsnList();
            //get fixed state & pos
            list.add(new VarInsnNode(ALOAD, 0));
            list.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/Entity", obfuscated ? "field_70170_p" : "world", "Lnet/minecraft/world/World;"));
            list.add(new VarInsnNode(ALOAD, 4));
            list.add(new VarInsnNode(ALOAD, 5));
            list.add(genMethodNode("updateFallState", "(Lnet/minecraft/world/World;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;)Lorg/apache/commons/lang3/tuple/Pair;"));
            list.add(new VarInsnNode(ASTORE, 10));
            //set fixed state & pos
            list.add(new VarInsnNode(ALOAD, 10));
            list.add(new MethodInsnNode(INVOKEINTERFACE, "java/util/Map$Entry", "getKey", "()Ljava/lang/Object;", true));
            list.add(new TypeInsnNode(CHECKCAST, "net/minecraft/block/state/IBlockState"));
            list.add(new VarInsnNode(ASTORE, 4));
            list.add(new VarInsnNode(ALOAD, 10));
            list.add(new MethodInsnNode(INVOKEINTERFACE, "java/util/Map$Entry", "getValue", "()Ljava/lang/Object;", true));
            list.add(new TypeInsnNode(CHECKCAST, "net/minecraft/util/math/BlockPos"));
            list.add(new VarInsnNode(ASTORE, 5));
            instructions.insert(insn, list);
        }
        //fix fire damage sound
        else if(index == 2 && checkMethod(insn, obfuscated ? "func_76347_k" : "isFireDamage") && getNext(insn, 2).getOpcode() != ALOAD) {
            instructions.insert(insn, genMethodNode("fixPlayerBlueFireDamageSound", "(Lnet/minecraft/util/DamageSource;)Z"));
            instructions.remove(insn);
        }

        return false;
    }

    @Override
    public boolean addLocalVariables(@Nonnull MethodNode method, @Nonnull LabelNode start, @Nonnull LabelNode end, int index) {
        method.localVariables.add(new LocalVariableNode("pair", "Lorg/apache/commons/lang3/tuple/Pair;", "Lorg/apache/commons/lang3/tuple/Pair<Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;>;", start, end, 10));
        return true;
    }
}
