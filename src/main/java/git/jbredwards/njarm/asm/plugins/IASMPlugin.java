package git.jbredwards.njarm.asm.plugins;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import javax.annotation.Nonnull;

/**
 * Functions the same as its Fluidlogged API parent, but some things have been slightly tuned to function for this mod
 * @author jbred
 *
 */
public interface IASMPlugin extends git.jbredwards.fluidlogged_api.mod.asm.plugins.IASMPlugin
{
    @Override
    default void informConsole(@Nonnull ClassReader reader, @Nonnull MethodNode method) {
        System.out.printf("NJARM2 Plugin: transforming... %s.%s%s%n", reader.getClassName(), method.name, method.desc);
    }

    @Nonnull
    @Override
    default MethodInsnNode genMethodNode(@Nonnull String name, @Nonnull String desc) {
        return genMethodNode("git/jbredwards/njarm/asm/plugins/ASMHooks", name, desc);
    }
}
