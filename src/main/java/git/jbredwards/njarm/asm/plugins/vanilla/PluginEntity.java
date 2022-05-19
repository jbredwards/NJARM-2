package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Fix MC-1691
 * @author jbred
 *
 */
public final class PluginEntity implements IASMPlugin
{
    @Override
    public int getMethodIndex(@Nonnull MethodNode method, boolean obfuscated) {
        if(method.name.equals(obfuscated ? "func_180429_a" : "playStepSound")) return 1;
        else if(method.name.equals(obfuscated ? "func_174808_Z" : "createRunningParticles")) return 2;
        return 0;
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        //fix block walking sound
        if(index == 1) {
            //save state above for later use
            if(instructions.getFirst() == insn.getPrevious()) {
                instructions.insertBefore(insn, new VarInsnNode(ALOAD, 0));
                instructions.insertBefore(insn, new FieldInsnNode(GETFIELD, "net/minecraft/entity/Entity", obfuscated ? "field_70170_p" : "world", "Lnet/minecraft/world/World;"));
                instructions.insertBefore(insn, new VarInsnNode(ALOAD, 1));
                instructions.insertBefore(insn, new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/util/math/BlockPos", obfuscated ? "func_177984_a" : "up", "()Lnet/minecraft/util/math/BlockPos;", false));
                instructions.insertBefore(insn, new MethodInsnNode(INVOKEINTERFACE, "net/minecraft/world/IBlockAccess", obfuscated ? "func_180495_p" : "getBlockState", "(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", true));
                instructions.insertBefore(insn, new VarInsnNode(ASTORE, 4));
            }
            //replace snow layer special case for IHasRunningEffects
            else if(checkField(insn, obfuscated ? "field_150431_aC" : "SNOW_LAYER")) {
                //change check to instanceof
                ((JumpInsnNode)insn.getNext()).setOpcode(IFEQ);
                instructions.insert(insn, new TypeInsnNode(INSTANCEOF, "git/jbredwards/njarm/mod/common/block/util/IHasRunningEffects"));
                instructions.insert(insn, new MethodInsnNode(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", obfuscated ? "func_177230_c" : "getBlock", "()Lnet/minecraft/block/Block;", true));
                instructions.insert(insn, new VarInsnNode(ALOAD, 4));
                //get SoundType from block above
                final InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 4));
                list.add(new MethodInsnNode(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", obfuscated ? "func_177230_c" : "getBlock", "()Lnet/minecraft/block/Block;", true));
                list.add(new VarInsnNode(ALOAD, 4));
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/Entity", obfuscated ? "field_70170_p" : "world", "Lnet/minecraft/world/World;"));
                list.add(new VarInsnNode(ALOAD, 1));
                list.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/util/math/BlockPos", obfuscated ? "func_177984_a" : "up", "()Lnet/minecraft/util/math/BlockPos;", false));
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/block/Block", "getSoundType", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)Lnet/minecraft/block/SoundType;", false));
                instructions.insert(getNext(insn, 8), list);
                //remove old
                removeFrom(instructions, getNext(insn, 7), 1);
                removeFrom(instructions, insn, -6);
                return true;
            }
        }
        //fix block walking particles
        else if(index == 2 && checkMethod(getPrevious(insn, 3), obfuscated ? "func_180495_p" : "getBlockState")) {
            final InsnList list = new InsnList();
            //get fixed state & pos
            list.add(new VarInsnNode(ALOAD, 0));
            list.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/Entity", obfuscated ? "field_70170_p" : "world", "Lnet/minecraft/world/World;"));
            list.add(new VarInsnNode(ALOAD, 5));
            list.add(new VarInsnNode(ALOAD, 4));
            list.add(genMethodNode("updateFallState", "(Lnet/minecraft/world/World;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;)Lorg/apache/commons/lang3/tuple/Pair;"));
            list.add(new VarInsnNode(ASTORE, 6));
            //set fixed state & pos
            list.add(new VarInsnNode(ALOAD, 6));
            list.add(new MethodInsnNode(INVOKEINTERFACE, "java/util/Map$Entry", "getKey", "()Ljava/lang/Object;", true));
            list.add(new TypeInsnNode(CHECKCAST, "net/minecraft/block/state/IBlockState"));
            list.add(new VarInsnNode(ASTORE, 5));
            list.add(new VarInsnNode(ALOAD, 6));
            list.add(new MethodInsnNode(INVOKEINTERFACE, "java/util/Map$Entry", "getValue", "()Ljava/lang/Object;", true));
            list.add(new TypeInsnNode(CHECKCAST, "net/minecraft/util/math/BlockPos"));
            list.add(new VarInsnNode(ASTORE, 4));
            instructions.insert(insn, list);
        }

        return false;
    }

    @Override
    public int addLocalVariables(@Nonnull MethodNode method, @Nonnull LabelNode start, @Nonnull LabelNode end, int index) {
        if(index == 1)
            method.localVariables.add(new LocalVariableNode("upState", "Lnet/minecraft/block/state/IBlockState;", null, start, end, 4));
        else if(index == 2)
            method.localVariables.add(new LocalVariableNode("pair", "Lorg/apache/commons/lang3/tuple/Pair;", "Lorg/apache/commons/lang3/tuple/Pair<Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;>;", start, end, 6));

        return 1;
    }
}
