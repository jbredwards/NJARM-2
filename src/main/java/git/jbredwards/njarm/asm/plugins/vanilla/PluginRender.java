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
    public int getMethodIndex(@Nonnull MethodNode method, boolean obfuscated) {
        if(method.name.equals(obfuscated ? "func_188299_a" : "renderShadowSingle")) return 1;
        else if(method.name.equals(obfuscated ? "func_76979_b" : "doRenderShadowAndFire")) return 2;
        return 0;
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(index == 1 && insn.getOpcode() == DSTORE && ((VarInsnNode)insn).var == 26) {
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
        //render third person blue fire, not using RenderLivingEvent so non-living entities can also be rendered
        else if(index == 2 && checkMethod(insn, obfuscated ? "func_90999_ad" : "canRenderOnFire")) {
            final InsnList list = new InsnList();
            list.add(new VarInsnNode(DLOAD, 2));
            list.add(new VarInsnNode(DLOAD, 4));
            list.add(new VarInsnNode(DLOAD, 6));
            list.add(genMethodNode("renderBlueFire", "(Lnet/minecraft/entity/Entity;DDD)Z"));
            instructions.insert(insn, list);
            instructions.remove(insn);
            return true;
        }

        return false;
    }

    @Override
    public boolean recalcFrames(boolean obfuscated) { return true; }
}
