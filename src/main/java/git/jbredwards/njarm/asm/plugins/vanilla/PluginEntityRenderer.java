package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Color rain & remove the annoying night vision flashing
 * @author jbred
 *
 */
public final class PluginEntityRenderer implements IASMPlugin
{
    @Override
    public int getMethodIndex(@Nonnull MethodNode method, boolean obfuscated) {
        if(method.name.equals(obfuscated ? "func_180438_a" : "getNightVisionBrightness")) return 1;
        else return method.name.equals(obfuscated ? "func_78474_d" : "renderRainSnow") ? 2 : 0;
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        //remove the annoying night vision flashing
        if(index == 1 && insn.getOpcode() == SIPUSH) {
            instructions.insert(insn, genMethodNode("betterNightVision", "()I"));
            instructions.remove(insn);
            return true;
        }
        //color rain
        else if(index == 2 && insn.getOpcode() == ISTORE && ((VarInsnNode)insn).var == 45) {
            final InsnList colorData = new InsnList();
            //store water color
            colorData.add(new VarInsnNode(ALOAD, 5));
            colorData.add(new VarInsnNode(ALOAD, 21));
            colorData.add(genMethodNode("net/minecraft/world/biome/BiomeColorHelper", obfuscated ? "func_180288_c" : "getWaterColorAtPos", "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)I"));
            colorData.add(new VarInsnNode(ISTORE, 100));
            //get water red
            colorData.add(new VarInsnNode(ILOAD, 100));
            colorData.add(genMethodNode("git/jbredwards/njarm/asm/plugins/vanilla/PluginEntityRenderer", "getRed", "(I)F"));
            colorData.add(new VarInsnNode(FSTORE, 101));
            //get water green
            colorData.add(new VarInsnNode(ILOAD, 100));
            colorData.add(genMethodNode("git/jbredwards/njarm/asm/plugins/vanilla/PluginEntityRenderer", "getGreen", "(I)F"));
            colorData.add(new VarInsnNode(FSTORE, 102));
            //get water blue
            colorData.add(new VarInsnNode(ILOAD, 100));
            colorData.add(genMethodNode("git/jbredwards/njarm/asm/plugins/vanilla/PluginEntityRenderer", "getBlue", "(I)F"));
            colorData.add(new VarInsnNode(FSTORE, 103));

            //inject color data to rain render
            for(int i = 0; i < 4; i++) {
                final int offset = 35 * i;
                //replace red value
                final AbstractInsnNode redOld = getNext(insn, 27 + offset);
                instructions.insert(redOld, new VarInsnNode(FLOAD, 101));
                instructions.remove(redOld);
                //replace green value
                final AbstractInsnNode greenOld = getNext(insn, 28 + offset);
                instructions.insert(greenOld, new VarInsnNode(FLOAD, 102));
                instructions.remove(greenOld);
                //replace blue value
                final AbstractInsnNode blueOld = getNext(insn, 29 + offset);
                instructions.insert(blueOld, new VarInsnNode(FLOAD, 103));
                instructions.remove(blueOld);
            }

            instructions.insert(insn, colorData);
            return true;
        }

        return false;
    }

    @Override
    public boolean addLocalVariables(@Nonnull MethodNode method, @Nonnull LabelNode start, @Nonnull LabelNode end, int index) {
        if(index == 2) {
            method.localVariables.add(new LocalVariableNode("color", "I", null, start, end, 100));
            method.localVariables.add(new LocalVariableNode("red", "F", null, start, end, 101));
            method.localVariables.add(new LocalVariableNode("green", "F", null, start, end, 102));
            method.localVariables.add(new LocalVariableNode("blue", "F", null, start, end, 103));
            return true;
        }

        return false;
    }

    @SuppressWarnings("unused")
    public static float getRed(int color) {
        return ((color >> 16) & 0xFF) / 255f;
    }

    @SuppressWarnings("unused")
    public static float getGreen(int color) {
        return ((color >> 8) & 0xFF) / 255f;
    }

    @SuppressWarnings("unused")
    public static float getBlue(int color) {
        return (color & 0xFF) / 255f;
    }
}
