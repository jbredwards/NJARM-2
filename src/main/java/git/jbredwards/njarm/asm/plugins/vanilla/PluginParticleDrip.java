package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

import javax.annotation.Nonnull;

/**
 * Water droplet particles keep the color set by this mod
 * @author jbred
 *
 */
public final class PluginParticleDrip implements IASMPlugin
{
    @Override
    public boolean isMethodValid(@Nonnull MethodNode method, boolean obfuscated) { return checkMethod(method, obfuscated ? "func_189213_a" : "onUpdate", "()V"); }

    @Override
    public boolean transform(@Nonnull InsnList instructions, @Nonnull MethodNode method, @Nonnull AbstractInsnNode insn, boolean obfuscated, int index) {
        if(insn.getOpcode() == IF_ACMPNE) {
            //gets each node that sets the rgb for the particle
            final AbstractInsnNode redNode = getNext(insn, 5);
            final AbstractInsnNode greenNode = getNext(redNode, 5);
            final AbstractInsnNode blueNode = getNext(greenNode, 5);
            //removes the red color change
            instructions.remove(redNode.getPrevious().getPrevious());
            instructions.remove(redNode.getPrevious());
            instructions.remove(redNode);
            //removes the green color change
            instructions.remove(greenNode.getPrevious().getPrevious());
            instructions.remove(greenNode.getPrevious());
            instructions.remove(greenNode);
            //removes the blue color change
            instructions.remove(blueNode.getPrevious().getPrevious());
            instructions.remove(blueNode.getPrevious());
            instructions.remove(blueNode);

            return true;
        }

        return false;
    }
}
