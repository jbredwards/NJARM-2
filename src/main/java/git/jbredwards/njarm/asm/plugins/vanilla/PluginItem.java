package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Overrides the totem of undying item
 * @author jbred
 *
 */
public final class PluginItem implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_150900_l" : "registerItems"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(insn.getOpcode() == LDC && ((LdcInsnNode)insn).cst.equals("totem")) {
            final InsnList list = new InsnList();
            list.add(new FieldInsnNode(GETSTATIC, "net/minecraft/item/Item", "BLOCK_TO_ITEM", "Ljava/util/Map;"));
            list.add(genMethodNode("genTotemOfUndying", "(Ljava/util/Map;)Lnet/minecraft/item/Item;"));

            removeFrom(instructions, insn.getPrevious(), -2);
            instructions.insertBefore(insn, list);
            return true;
        }

        return false;
    }
}
