package git.jbredwards.njarm.mod.common.message;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public abstract class AbstractMessage implements IMessage
{
    public boolean isValid;

    @Override
    public final void fromBytes(@Nonnull ByteBuf buf) {
        isValid = buf.readBoolean();
        if(isValid) read(buf);
    }

    @Override
    public final void toBytes(@Nonnull ByteBuf buf) {
        buf.writeBoolean(isValid);
        if(isValid) write(buf);
    }

    public abstract void read(@Nonnull ByteBuf buf);
    public abstract void write(@Nonnull ByteBuf buf);

    @Nonnull
    public int[] readArray(@Nonnull ByteBuf buf) {
        final int[] arr = new int[buf.readInt()];
        for(int i = 0; i < arr.length; i++) arr[i] = buf.readInt();
        return arr;
    }

    public void writeArray(@Nonnull ByteBuf buf, int[] arr) {
        buf.writeInt(arr.length);
        for(int i : arr) buf.writeInt(i);
    }
}
