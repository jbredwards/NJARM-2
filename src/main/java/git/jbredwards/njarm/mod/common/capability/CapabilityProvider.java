package git.jbredwards.njarm.mod.common.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public final class CapabilityProvider<T> implements ICapabilitySerializable<NBTBase>
{
    @Nonnull final Capability<T> capability;
    @Nullable final T instance;

    public CapabilityProvider(@Nonnull Capability<T> capabilityIn) {
        capability = capabilityIn; instance = capability.getDefaultInstance();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capabilityIn, @Nullable EnumFacing facing) {
        return capabilityIn == capability;
    }

    @Nullable
    @Override
    public <t> t getCapability(@Nonnull Capability<t> capabilityIn, @Nullable EnumFacing facing) {
        return capabilityIn == capability ? capability.cast(instance) : null;
    }

    @Nullable
    @Override
    public NBTBase serializeNBT() { return capability.writeNBT(instance, null); }

    @Override
    public void deserializeNBT(@Nonnull NBTBase nbt) { capability.readNBT(instance, null, nbt); }
}
