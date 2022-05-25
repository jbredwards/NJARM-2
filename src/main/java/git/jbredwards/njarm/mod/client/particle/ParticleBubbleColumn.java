package git.jbredwards.njarm.mod.client.particle;

import git.jbredwards.fluidlogged_api.api.util.FluidloggedUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.ParticleBubble;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.vecmath.Vector2d;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class ParticleBubbleColumn extends ParticleBubble
{
    protected final Vector2d origin;
    protected final double radius;
    protected int degree = 0;

    public ParticleBubbleColumn(@Nonnull World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
        super(worldIn, x, y, z, 0, 0, 0);
        //removes RNG from the particle motion.
        motionX = xSpeed;
        motionY = ySpeed;
        motionZ = xSpeed;

        origin = new Vector2d(x, z);
        radius = zSpeed;

        if(args != null && args.length == 1) degree = args[0];

        setMaxAge(50);

        //prevents graphical bug when this particle initially spawns
        setNewPos();
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
    }

    @Override
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        degree++;
        setNewPos();
        if(particleMaxAge-- < 0) setExpired();
        else if(FluidloggedUtils.getFluidState(world, new BlockPos(posX, posY + 0.2, posZ)).getMaterial() != Material.WATER)
            setExpired();
    }

    protected void setNewPos() {
        final double x = radius * Math.sin(Math.toRadians(degree * motionX));
        final double z = radius * Math.cos(Math.toRadians(degree * motionZ));
        setPosition(origin.x + x, posY + motionY, origin.y + z);
    }
}
