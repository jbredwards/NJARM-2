package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Bedrock edition entity shadow size parody
 * @author jbred
 *
 */
public final class PluginRender implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return method.name.equals(obfuscated ? "func_188299_a" : "renderShadowSingle"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(insn.getOpcode() == DSTORE && ((VarInsnNode)insn).var == 26) {
            final InsnList list = new InsnList();
            list.add(new VarInsnNode(FLOAD, 10));
            list.add(new VarInsnNode(DLOAD, 26));
            list.add(new VarInsnNode(DLOAD, 4));
            list.add(new VarInsnNode(DLOAD, 13));
            list.add(genMethodNode("bedrockShadowSize", "(FDDD)F"));
            list.add(new VarInsnNode(FSTORE, 10));
            instructions.insert(insn, list);
            return true;
        }

        return false;
    }
}
