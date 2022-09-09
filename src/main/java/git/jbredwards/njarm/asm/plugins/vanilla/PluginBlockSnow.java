package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Snow layers have gravity
 * @author jbred
 *
 */
public final class PluginBlockSnow implements IASMPlugin
{
    @Override
    public boolean transformClass(@Nonnull ClassNode classNode, boolean obfuscated) {
        classNode.interfaces.add("git/jbredwards/njarm/mod/common/block/util/IHasRunningEffects");
        //add IHasWorldState functionality
        classNode.interfaces.add("git/jbredwards/njarm/mod/common/block/util/IHasWorldState");
        addMethod(classNode, "getStateForWorld", "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;",
            "getSnowStateForWorld", "(Lnet/minecraft/block/BlockSnow;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;", generator -> {
                generator.visitVarInsn(ALOAD, 0);
                generator.visitVarInsn(ALOAD, 1);
                generator.visitVarInsn(ALOAD, 2);
                generator.visitVarInsn(ALOAD, 3);
            }
        );
        //Add IFancyFallingBlock functionality
        classNode.interfaces.add("git/jbredwards/njarm/mod/common/block/util/gravity/IFancyFallingBlock");
        addMethod(classNode, "getHeightForFallingBlock", "(Lnet/minecraft/block/state/IBlockState;)F", null, null, generator -> {
            generator.visitVarInsn(ALOAD, 1);
            generator.visitFieldInsn(GETSTATIC, "net/minecraft/block/BlockSnow", obfuscated ? "field_176315_a" : "LAYERS", "Lnet/minecraft/block/properties/PropertyInteger;");
            generator.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", obfuscated ? "func_177229_b" : "getValue", "(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;", true);
            generator.visitTypeInsn(CHECKCAST, "java/lang/Integer");
            generator.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            generator.visitInsn(I2F);
            generator.visitLdcInsn(0.98f);
            generator.visitInsn(FMUL);
            generator.visitLdcInsn(8f);
            generator.visitInsn(FDIV);
        });
        //Add ILayeredFallingBlock functionality
        classNode.interfaces.add("git/jbredwards/njarm/mod/common/block/util/gravity/ILayeredFallingBlock");
        addMethod(classNode, "fallOnto", "(Lnet/minecraft/entity/item/EntityFallingBlock;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;)Z",
            "fallOnto", "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;)Z", generator -> {
                generator.visitVarInsn(ALOAD, 2);
                generator.visitVarInsn(ALOAD, 3);
                generator.visitVarInsn(ALOAD, 4);
                generator.visitVarInsn(ALOAD, 5);
            }
        );
        //move updateTick to randomTick
        for(MethodNode method : classNode.methods) {
            if(method.name.equals(obfuscated ? "func_180650_b" : "updateTick")) {
                method.name = obfuscated ? "func_180645_a" : "randomTick";
                break;
            }
        }
        //this falling block entity should not drop itself if it breaks when it lands
        addMethod(classNode, obfuscated ? "func_149829_a" : "onStartFalling", "(Lnet/minecraft/entity/item/EntityFallingBlock;)V", null, null, generator -> {
            generator.visitVarInsn(ALOAD, 1);
            generator.visitInsn(ICONST_0);
            generator.visitFieldInsn(PUTFIELD, "net/minecraft/entity/item/EntityFallingBlock", obfuscated ? "field_145813_c" : "shouldDropItem", "Z");
        });
        //fix falling dust particle color
        addMethod(classNode, obfuscated ? "func_189876_x" : "getDustColor", "(Lnet/minecraft/block/state/IBlockState;)I", null, null, generator -> generator.visitLdcInsn(16777215));
        //this plugin changes the super name, inform the console of a class transform
        informConsole(classNode.name, null);
        return true;
    }

    @Override
    public int getMethodIndex(@Nonnull MethodNode method, boolean obfuscated) {
        if(method.name.equals(obfuscated ? "func_189540_a" : "neighborChanged")) return 1;
        else if(method.name.equals(obfuscated ? "func_176196_c" : "canPlaceBlockAt")) return 2;
        else return 0;
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        //schedule block update
        if(index == 1 && insn.getOpcode() == RETURN) {
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 0));
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 1));
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 2));
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 3));
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 4));
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 5));
            instructions.insertBefore(insn, new MethodInsnNode(INVOKESPECIAL, "net/minecraft/block/BlockFalling", obfuscated ? "func_189540_a" : "neighborChanged", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;)V", false));
            return true;
        }
        else if(index == 2) {
            //snow layers can be placed on other snow layer instances
            if(insn.getOpcode() == ALOAD && ((VarInsnNode)insn).var == 0) {
                ((JumpInsnNode)insn.getNext()).setOpcode(IFEQ);
                instructions.insert(insn, new TypeInsnNode(INSTANCEOF, "net/minecraft/block/BlockSnow"));
                instructions.remove(insn);
            }
            //don't destroy snow layer if it can fall
            else if(insn.getOpcode() == ICONST_0) {
                instructions.insert(insn, genMethodNode("net/minecraft/block/BlockFalling", obfuscated ? "func_185759_i" : "canFallThrough", "(Lnet/minecraft/block/state/IBlockState;)Z"));
                instructions.insert(insn, new VarInsnNode(ALOAD, 3));
                instructions.remove(insn);
                return true;
            }
        }

        return false;
    }

    //change super class to BlockFalling
    @Override
    public byte[] transform(@Nonnull byte[] basicClass, boolean obfuscated) {
        final ClassWriter writer = new ClassWriter(0);
        new ClassReader(basicClass).accept(new ClassVisitor(ASM5, writer) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                super.visit(version, access, name, signature, "net/minecraft/block/BlockFalling", interfaces);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                return !name.equals("<init>") ? mv : new MethodVisitor(api, mv) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                        super.visitMethodInsn(opcode, name.equals("<init>") ? "net/minecraft/block/BlockFalling" : owner, name, desc, itf);
                    }
                };
            }
        }, 0);

        return IASMPlugin.super.transform(writer.toByteArray(), obfuscated);
    }
}
