package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Implement IFancyFallingBlock functionality
 * @author jbred
 *
 */
public final class PluginEntityFallingBlock implements IASMPlugin
{
    @Override
    public int getMethodIndex(@Nonnull MethodNode method, boolean obfuscated) {
        if(method.name.equals("<init>")) return 1;
        return method.name.equals(obfuscated ? "func_70037_a" : "readEntityFromNBT") ? 2 : 0;
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        //update constructor with correct width & height values
        if(index == 1) {
            if(insn.getOpcode() == INVOKEVIRTUAL && insn.getPrevious().getOpcode() == LDC) {
                removeFrom(instructions, insn.getPrevious(), -1);
                instructions.insertBefore(insn, new VarInsnNode(ALOAD, 8));
                instructions.insertBefore(insn, genMethodNode("getWidthForFallingBlock", "(Lnet/minecraft/block/state/IBlockState;)F"));
                instructions.insertBefore(insn, new VarInsnNode(ALOAD, 8));
                instructions.insertBefore(insn, genMethodNode("getHeightForFallingBlock", "(Lnet/minecraft/block/state/IBlockState;)F"));
            }
            //set position to x, y + 0.02, z (no longer dependent on hardcoded height value)
            else if(insn.getOpcode() == DLOAD && insn.getPrevious().getOpcode() == DLOAD) {
                removeFrom(instructions, insn.getNext(), 7);
                instructions.insert(insn, new InsnNode(DADD));
                instructions.insert(insn, new LdcInsnNode(0.02));
                return true;
            }
        }
        //update width & height when read from nbt
        else if(index == 2 && checkMethod(insn.getNext(), obfuscated ? "func_177230_c" : "getBlock")) {
            final InsnList list = new InsnList();
            list.add(new VarInsnNode(ALOAD, 0));
            list.add(new VarInsnNode(ALOAD, 0));
            list.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/item/EntityFallingBlock", obfuscated ? "field_175132_d" : "fallTile", "Lnet/minecraft/block/state/IBlockState;"));
            list.add(genMethodNode("getWidthForFallingBlock", "(Lnet/minecraft/block/state/IBlockState;)F"));
            list.add(new VarInsnNode(ALOAD, 0));
            list.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/item/EntityFallingBlock", obfuscated ? "field_175132_d" : "fallTile", "Lnet/minecraft/block/state/IBlockState;"));
            list.add(genMethodNode("getHeightForFallingBlock", "(Lnet/minecraft/block/state/IBlockState;)F"));
            list.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/entity/Entity", obfuscated ? "func_70105_a" : "setSize", "(FF)V", false));
            instructions.insert(insn, list);
            return true;
        }

        return false;
    }
}
