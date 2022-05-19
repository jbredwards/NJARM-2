package git.jbredwards.njarm.mod.common.message;

import git.jbredwards.njarm.mod.common.util.BlueFireUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class BlueFireMessage implements IMessage
{
    public boolean isValid;
    public int entity;
    public boolean isOnBlueFire;

    public BlueFireMessage() {}
    public BlueFireMessage(int entityIn, boolean isOnBlueFireIn) {
        isValid = true;
        entity = entityIn;
        isOnBlueFire = isOnBlueFireIn;
    }

    @Override
    public void fromBytes(@Nonnull ByteBuf buf) {
        isValid = buf.readBoolean();
        if(isValid) {
            entity = buf.readInt();
            isOnBlueFire = buf.readBoolean();
        }
    }

    @Override
    public void toBytes(@Nonnull ByteBuf buf) {
        buf.writeBoolean(isValid);
        if(isValid) {
            buf.writeInt(entity);
            buf.writeBoolean(isOnBlueFire);
        }
    }

    public enum Handler implements IMessageHandler<BlueFireMessage, IMessage>
    {
        INSTANCE;

        @Override
        public IMessage onMessage(@Nonnull BlueFireMessage message, @Nonnull MessageContext ctx) {
            if(message.isValid && ctx.side.isClient())
                addTask(message.entity, message.isOnBlueFire);

            return null;
        }

        @SideOnly(Side.CLIENT)
        private void addTask(int entity, boolean isOnBlueFire) {
            Minecraft.getMinecraft().addScheduledTask(() -> BlueFireUtils.setRemaining(
                    Minecraft.getMinecraft().world.getEntityByID(entity), isOnBlueFire ? 1 : 0));
        }
    }
}
