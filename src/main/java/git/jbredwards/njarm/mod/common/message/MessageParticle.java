package git.jbredwards.njarm.mod.common.message;

import git.jbredwards.njarm.mod.client.particle.util.ParticleProviders;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class MessageParticle extends AbstractMessage
{
    public ParticleProviders provider;
    public float x, y, z, xOffset, yOffset, zOffset, speed;
    public int numOfParticles;
    public int[] args;

    public MessageParticle() {}
    public MessageParticle(@Nonnull ParticleProviders provider, float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int numOfParticles, int... args) {
        this.isValid = true;
        this.provider = provider;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.speed = speed;
        this.numOfParticles = numOfParticles;
        this.args = args;
    }

    @Override
    public void read(@Nonnull ByteBuf buf) {
        provider = ParticleProviders.values()[buf.readInt()];
        //pos
        x = buf.readFloat();
        y = buf.readFloat();
        z = buf.readFloat();
        xOffset = buf.readFloat();
        yOffset = buf.readFloat();
        zOffset = buf.readFloat();
        //misc
        speed = buf.readFloat();
        numOfParticles = buf.readInt();
        //args
        args = readArray(buf);
    }

    @Override
    public void write(@Nonnull ByteBuf buf) {
        buf.writeInt(provider.ordinal());
        //pos
        buf.writeFloat(x);
        buf.writeFloat(y);
        buf.writeFloat(z);
        buf.writeFloat(xOffset);
        buf.writeFloat(yOffset);
        buf.writeFloat(zOffset);
        //misc
        buf.writeFloat(speed);
        buf.writeInt(numOfParticles);
        //args
        writeArray(buf, args);
    }

    public enum Handler implements IClientMessageHandler<MessageParticle>
    {
        INSTANCE;

        @SideOnly(Side.CLIENT)
        @Override
        public void handleMessage(@Nonnull MessageParticle message) {
            final WorldClient world = Minecraft.getMinecraft().world;
            //generates one particle
            if(message.numOfParticles == 0) {
                final double xSpeed = message.speed * message.xOffset;
                final double ySpeed = message.speed * message.yOffset;
                final double zSpeed = message.speed * message.zOffset;
                message.provider.spawnClient(world, message.x, message.y, message.z, xSpeed, ySpeed, zSpeed, message.args);
            }
            //generates all particles
            else for(int i = 0; i < message.numOfParticles; i++) {
                final double x = message.x + world.rand.nextGaussian() * message.xOffset;
                final double y = message.y + world.rand.nextGaussian() * message.yOffset;
                final double z = message.z + world.rand.nextGaussian() * message.zOffset;
                final double xSpeed = world.rand.nextGaussian() * message.speed;
                final double ySpeed = world.rand.nextGaussian() * message.speed;
                final double zSpeed = world.rand.nextGaussian() * message.speed;
                message.provider.spawnClient(world, x, y, z, xSpeed, ySpeed, zSpeed, message.args);
            }
        }
    }
}
