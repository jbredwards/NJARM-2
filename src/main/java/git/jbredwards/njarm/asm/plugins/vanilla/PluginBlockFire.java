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
        //render blue fire if it should be here, the world is updated later to actually have blue fire here
        overrideMethod(classNode, method -> method.name.equals(obfuscated ? "func_176221_a" : "getActualState"),
            "renderBlueOrNormalFire", "(Lnet/minecraft/block/BlockFire;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", generator -> {
                generator.visitVarInsn(ALOAD, 0);
                generator.visitVarInsn(ALOAD, 1);
                generator.visitVarInsn(ALOAD, 2);
                generator.visitVarInsn(ALOAD, 3);
                generator.visitMaxs(4, 0);
            }
        );

        //update to blue fire
        overrideMethod(classNode, method -> method.name.equals(obfuscated ? "func_189540_a" : "neighborChanged"),
            "tryChangeToBlueFire", "(Lnet/minecraft/block/BlockFire;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", generator -> {
                generator.visitVarInsn(ALOAD, 0);
                generator.visitVarInsn(ALOAD, 2);
                generator.visitVarInsn(ALOAD, 3);
                generator.visitMaxs(3, 0);
            }
        );

        //when fire is added to the world, check if it should become blue fire
        overrideMethod(classNode, method -> method.name.equals(obfuscated ? "func_176213_c" : "onBlockAdded"),
            "tryConvertToBlueFire", "(Lnet/minecraft/block/BlockFire;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", generator -> {
                generator.visitVarInsn(ALOAD, 0);
                generator.visitVarInsn(ALOAD, 1);
                generator.visitVarInsn(ALOAD, 2);
                generator.visitMaxs(3, 0);
            }
        );

        return false;
    }
}
