package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.ClassNode;

import javax.annotation.Nonnull;

/**
 * Nether brick slabs have the same nether brick sound as vanilla 1.16+
 * @author jbred
 *
 */
public final class PluginBlockStoneSlab implements IASMPlugin
{
    @Override
    public boolean transformClass(@Nonnull ClassNode classNode, boolean obfuscated) {
        addMethod(classNode, "getSoundType", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)Lnet/minecraft/block/SoundType;",
            "fixNetherBrickSlabSound", "(Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/SoundType;", generator -> {
                generator.visitVarInsn(ALOAD, 1);
                generator.visitMaxs(1, 0);
            }
        );

        return false;
    }
}
