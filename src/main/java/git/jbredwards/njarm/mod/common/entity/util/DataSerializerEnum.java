package git.jbredwards.njarm.mod.common.entity.util;

import git.jbredwards.njarm.mod.common.util.ArrayUtils;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * A generic {@link DataSerializer} applicable to any Enum
 * @author jbred
 *
 */
public class DataSerializerEnum<T extends Enum<T>> implements DataSerializer<T>
{
    @Nonnull
    protected final Supplier<T[]> values;
    public DataSerializerEnum(@Nonnull Supplier<T[]> values) { this.values = values; }

    @Override
    public void write(@Nonnull PacketBuffer buf, @Nonnull T value) { buf.writeByte(value.ordinal()); }

    @Nonnull
    @Override
    public T read(@Nonnull PacketBuffer buf) { return ArrayUtils.getSafe(values.get(), buf.readByte()); }

    @Nonnull
    @Override
    public DataParameter<T> createKey(int id) { return new DataParameter<>(id, this); }

    @Nonnull
    @Override
    public T copyValue(@Nonnull T value) { return value; }
}
