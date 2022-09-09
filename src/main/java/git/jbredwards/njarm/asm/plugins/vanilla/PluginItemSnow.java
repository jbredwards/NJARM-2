package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Fix snow layer placement check for snow layers & snow grass
 * @author jbred
 *
 */
public final class PluginItemSnow implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_180614_a" : "onItemUse"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(checkMethod(insn, obfuscated ? "func_177230_c" : "getBlock")) {
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 0));
            instructions.insertBefore(insn, new FieldInsnNode(GETFIELD, "net/minecraft/item/ItemBlock", obfuscated ? "field_150939_a" : "block", "Lnet/minecraft/block/Block;"));
            instructions.insertBefore(insn, genMethodNode("fixItemSnowCheck", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/Block;)Lnet/minecraft/block/Block;"));
            instructions.remove(insn);
        }

        else if(checkMethod(getNext(insn, 6), obfuscated ? "func_177226_a" : "withProperty")) {
            instructions.insertBefore(insn, new VarInsnNode(ALOAD, 11));
            instructions.insert(insn, genMethodNode("fixItemSnowPlacement", "(Lnet/minecraft/block/Block;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;"));
            return true;
        }

        return false;
    }
}
