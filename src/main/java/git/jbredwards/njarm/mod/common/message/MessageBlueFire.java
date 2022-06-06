package git.jbredwards.njarm.mod.common.message;

import git.jbredwards.njarm.mod.common.util.BlueFireUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class MessageBlueFire extends AbstractMessage
{
    public int entity;
    public boolean isOnBlueFire;

    public MessageBlueFire() {}
    public MessageBlueFire(int entityIn, boolean isOnBlueFireIn) {
        isValid = true;
        entity = entityIn;
        isOnBlueFire = isOnBlueFireIn;
    }

    @Override
    public void write(@Nonnull ByteBuf buf) {
        entity = buf.readInt();
        isOnBlueFire = buf.readBoolean();
    }

    @Override
    public void read(@Nonnull ByteBuf buf) {
        buf.writeInt(entity);
        buf.writeBoolean(isOnBlueFire);
    }

    public enum Handler implements IClientMessageHandler<MessageBlueFire>
    {
        INSTANCE;

        @SideOnly(Side.CLIENT)
        @Override
        public void handleMessage(@Nonnull MessageBlueFire message) {
            BlueFireUtils.setRemaining(Minecraft.getMinecraft().world.getEntityByID(message.entity), message.isOnBlueFire ? 1 : 0);
        }
    }
}
