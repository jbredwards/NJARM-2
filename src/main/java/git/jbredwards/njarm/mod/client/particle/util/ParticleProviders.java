package git.jbredwards.njarm.mod.client.particle.util;

import git.jbredwards.njarm.mod.Main;
import git.jbredwards.njarm.mod.client.particle.ParticleBlueFlame;
import git.jbredwards.njarm.mod.client.particle.ParticleLayeredBlockDust;
import git.jbredwards.njarm.mod.client.particle.ParticleLayeredDigging;
import git.jbredwards.njarm.mod.client.particle.ParticleLitRedstone;
import git.jbredwards.njarm.mod.common.message.MessageParticle;
import net.minecraft.block.Block;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public enum ParticleProviders implements IParticleProvider
{
    BLUE_FLAME(new IParticleProvider() {
        @Nonnull
        @SideOnly(Side.CLIENT)
        @Override
        public Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
            return new ParticleBlueFlame(world, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }),
    LIT_REDSTONE(new IParticleProvider() {
        @Nonnull
        @SideOnly(Side.CLIENT)
        @Override
        public Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
            return new ParticleLitRedstone(world, x, y, z, args.length == 2 ? args[1] : 1, (float)xSpeed, (float)ySpeed, (float)zSpeed, args.length > 0 ? args[0] : 240);
        }
    }),
    MULTI_LAYER_BLOCK_DUST(new IParticleProvider() {
        @Nonnull
        @SideOnly(Side.CLIENT)
        @Override
        public Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
            return new ParticleLayeredBlockDust(world, x, y, z, xSpeed, ySpeed, zSpeed, Block.getStateById(args[0])).init();
        }
    }),
    MULTI_LAYER_DIGGING(new IParticleProvider() {
        @Nonnull
        @SideOnly(Side.CLIENT)
        @Override
        public Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
            return new ParticleLayeredDigging(world, x, y, z, xSpeed, ySpeed, zSpeed, Block.getStateById(args[0])).init();
        }
    });

    @Nonnull final IParticleProvider internalProvider;
    ParticleProviders(@Nonnull IParticleProvider internalProvider) { this.internalProvider = internalProvider; }

    @Nullable
    @SideOnly(Side.CLIENT)
    @Override
    public Particle getParticle(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
        return internalProvider.getParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, args);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void spawnClient(@Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
        internalProvider.spawnClient(world, x, y, z, xSpeed, ySpeed, zSpeed, args);
    }

    @Override
    public void spawnServer(@Nonnull World world, float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int numOfParticles, int... args) {
        Main.wrapper.sendToAllAround(
                new MessageParticle(this, x, y, z, xOffset, yOffset, zOffset, speed, numOfParticles, args),
                new NetworkRegistry.TargetPoint(world.provider.getDimension(), x, y, z, 32)
        );
    }
}
