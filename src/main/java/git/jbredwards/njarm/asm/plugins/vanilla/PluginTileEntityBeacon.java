package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Backport the vanilla 1.13+ beacon sounds
 * @author jbred
 *
 */
public final class PluginTileEntityBeacon implements IASMPlugin
{
    @Override
    public int getMethodIndex(@Nonnull MethodNode method, boolean obfuscated) {
        if(method.name.equals(obfuscated ? "func_73660_a" : "update")) return 1;
        else return method.name.equals(obfuscated ? "func_174885_b" : "setField") ? 2 : 0;
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(index == 1) {
            //play activate & deactivate sounds
            if(insn == instructions.getFirst()) {
                final InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new FieldInsnNode(GETFIELD, "net/minecraft/tileentity/TileEntityBeacon", obfuscated ? "field_146015_k" : "isComplete", "Z"));
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new FieldInsnNode(GETFIELD, "net/minecraft/tileentity/TileEntityBeacon", "lastTickComplete", "Z"));
                list.add(genMethodNode("playBeaconUpdateSound", "(Lnet/minecraft/tileentity/TileEntity;ZZ)Z"));
                list.add(new FieldInsnNode(PUTFIELD, "net/minecraft/tileentity/TileEntityBeacon", "lastTickComplete", "Z"));
                instructions.insert(insn, list);
            }
            //play ambient sound
            else if(checkMethod(insn, obfuscated ? "func_174908_m" : "updateBeacon")) {
                final InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new FieldInsnNode(GETFIELD, "net/minecraft/tileentity/TileEntityBeacon", obfuscated ? "field_146015_k" : "isComplete", "Z"));
                list.add(genMethodNode("playBeaconAmbientSound", "(Lnet/minecraft/tileentity/TileEntity;Z)V"));
                instructions.insert(insn, list);
                if(method.maxStack < 3) method.maxStack = 3;
                return true;
            }
        }
        //play select sound
        else if(index == 2) {
            final InsnList list = new InsnList();
            list.add(new VarInsnNode(ALOAD, 0));
            list.add(new VarInsnNode(ALOAD, 0));
            list.add(new FieldInsnNode(GETFIELD, "net/minecraft/tileentity/TileEntityBeacon", obfuscated ? "field_146015_k" : "isComplete", "Z"));
            list.add(genMethodNode("playBeaconPowerSelectSound", "(Lnet/minecraft/tileentity/TileEntity;Z)V"));
            instructions.insert(insn, list);
            return true;
        }

        return false;
    }

    @Override
    public boolean transformClass(@Nonnull ClassNode classNode, boolean obfuscated) {
        classNode.fields.add(new FieldNode(ACC_PUBLIC, "lastTickComplete", "Z", null, null));
        return true;
    }
}
