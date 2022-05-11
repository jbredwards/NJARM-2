package git.jbredwards.njarm.asm.plugins.forge;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Changes the water textures to allow for better coloring
 * @author jbred
 *
 */
public final class PluginFluidRegistry implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return checkMethod(method, "<clinit>", "()V"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(insn.getOpcode() == LDC && insn instanceof LdcInsnNode) {
            final LdcInsnNode ldc = (LdcInsnNode)insn;

            if(ldc.cst.equals("blocks/water_still")) ldc.cst = "njarm:blocks/water_still";
            if(ldc.cst.equals("blocks/water_flow"))  ldc.cst = "njarm:blocks/water_flow";
            if(ldc.cst.equals("blocks/water_overlay")) {
                ldc.cst = "njarm:blocks/water_overlay";
                return true;
            }
        }

        return false;
    }
}
