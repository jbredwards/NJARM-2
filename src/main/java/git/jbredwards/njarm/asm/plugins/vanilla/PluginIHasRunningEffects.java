package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.ClassNode;

import javax.annotation.Nonnull;

/**
 * Apply fixes for MC-1691 for certain block classes
 * @author jbred
 *
 */
public final class PluginIHasRunningEffects implements IASMPlugin
{
    @Override
    public boolean transformClass(@Nonnull ClassNode classNode, boolean obfuscated) {
        classNode.interfaces.add("git/jbredwards/njarm/mod/common/block/util/IHasRunningEffects");
        return false;
    }
}
