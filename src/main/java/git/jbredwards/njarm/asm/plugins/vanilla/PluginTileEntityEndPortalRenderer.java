package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;

/**
 * Render the bottom of end portals
 * @author jbred
 *
 */
public final class PluginTileEntityEndPortalRenderer implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) {
        return checkMethod(method, obfuscated ? "func_192841_a" : "render", "(Lnet/minecraft/tileentity/TileEntityEndPortal;DDDFIF)V");
    }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(checkMethod(insn, obfuscated ? "func_184313_a" : "shouldRenderFace") && checkField(insn.getPrevious(), "DOWN")) {
            final InsnList list = new InsnList();
            list.add(new VarInsnNode(ALOAD, 20));
            list.add(new VarInsnNode(FLOAD, 14));
            //get x, y, z
            list.add(new VarInsnNode(DLOAD, 2));
            list.add(new VarInsnNode(DLOAD, 4));
            list.add(new VarInsnNode(DLOAD, 6));
            //get f1, f2, f3
            list.add(new VarInsnNode(FLOAD, 21));
            list.add(new VarInsnNode(FLOAD, 22));
            list.add(new VarInsnNode(FLOAD, 23));
            //add new code
            list.add(genMethodNode("fixEndPortalBottomRender", "(Lnet/minecraft/tileentity/TileEntityEndPortal;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/client/renderer/BufferBuilder;FDDDFFF)Z"));
            instructions.insert(insn, list);
            instructions.remove(insn);
            return true;
        }

        return false;
    }
}
