package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * {@link git.jbredwards.njarm.mod.common.item.util.InvulnerableItem InvulnerableItem} implementation
 * @author jbred
 *
 */
public final class PluginEntityItem implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_70097_a" : "attackEntityFrom"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(checkMethod(insn, obfuscated ? "func_180431_b" : "isEntityInvulnerable")) {
            instructions.insert(insn, genMethodNode("isItemImmuneTo", "(Lnet/minecraft/entity/item/EntityItem;Lnet/minecraft/util/DamageSource;)Z"));
            instructions.remove(insn);
            return true;
        }

        return false;
    }
}
