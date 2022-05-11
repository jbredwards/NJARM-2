package git.jbredwards.njarm.asm.plugins;

import org.objectweb.asm.tree.MethodNode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Functions the same as its Fluidlogged API parent, but some things have been slightly tuned to function for this mod
 * @author jbred
 *
 */
public interface IASMPlugin extends git.jbredwards.fluidlogged_api.mod.asm.plugins.IASMPlugin
{
    @Override
    default void informConsole(@Nonnull String className, @Nullable MethodNode method) {
        if(method == null) System.out.printf("NJARM2 Plugin: transforming... %s%n", className);
        else System.out.printf("NJARM2 Plugin: transforming... %s.%s%s%n", className, method.name, method.desc);
    }

    @Nonnull
    @Override
    default String getHookClass() { return "git/jbredwards/njarm/asm/plugins/ASMHooks"; }
}
