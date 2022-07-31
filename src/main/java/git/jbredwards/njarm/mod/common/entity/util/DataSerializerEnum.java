package git.jbredwards.njarm.mod.common.entity.util;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;

import javax.annotation.Nonnull;

/**
 * A generic {@link DataSerializer} applicable to any Enum
 * @author jbred
 *
 */
public class DataSerializerEnum<T extends Enum<T>> implements DataSerializer<T>
{
    @Nonnull
    protected final Class<T> enumClass;
    public DataSerializerEnum(@Nonnull Class<T> enumClass) { this.enumClass = enumClass; }

    @Override
    public void write(@Nonnull PacketBuffer buf, @Nonnull T value) { buf.writeEnumValue(value); }

    @Nonnull
    @Override
    public T read(@Nonnull PacketBuffer buf) { return buf.readEnumValue(enumClass); }

    @Nonnull
    @Override
    public DataParameter<T> createKey(int id) { return new DataParameter<>(id, this); }

    @Nonnull
    @Override
    public T copyValue(@Nonnull T value) { return value; }
}
