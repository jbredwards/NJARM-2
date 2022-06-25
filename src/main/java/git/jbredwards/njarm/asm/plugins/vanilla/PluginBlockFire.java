package git.jbredwards.njarm.asm.plugins.vanilla;

import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import org.objectweb.asm.tree.ClassNode;

import javax.annotation.Nonnull;

/**
 * Turn fire into blue fire if it's on soul sand
 * @author jbred
 *
 */
public final class PluginBlockFire implements IASMPlugin
{
    @Override
    public boolean transformClass(@Nonnull ClassNode classNode, boolean obfuscated) {
        classNode.interfaces.add("git/jbredwards/njarm/mod/common/block/util/IHasWorldState");
        classNode.interfaces.add("git/jbredwards/njarm/mod/common/block/util/ICanFallThrough");
        //update to blue fire
        overrideMethod(classNode, method -> method.name.equals(obfuscated ? "func_189540_a" : "neighborChanged"),
            "tryChangeToBlueFire", "(Lnet/minecraft/block/BlockFire;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", generator -> {
                generator.visitVarInsn(ALOAD, 0);
                generator.visitVarInsn(ALOAD, 2);
                generator.visitVarInsn(ALOAD, 3);
            }
        );
        //update to blue fire
        addMethod(classNode, "getStateForWorld", "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;",
            "getBlueOrNormal", "(Lnet/minecraft/block/BlockFire;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;", generator -> {
                generator.visitVarInsn(ALOAD, 0);
                generator.visitVarInsn(ALOAD, 1);
                generator.visitVarInsn(ALOAD, 2);
                generator.visitVarInsn(ALOAD, 3);
            }
        );

        return false;
    }
}
