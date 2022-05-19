package git.jbredwards.njarm.mod.common.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@SuppressWarnings("ConstantConditions")
public interface IBlueFire
{
    @CapabilityInject(IBlueFire.class)
    @Nonnull Capability<IBlueFire> CAPABILITY = null;

    //please use the more stable BlueFireUtils functions instead if possible
    int getRemaining();
    void setRemaining(int remainingIn);

    @Nullable
    static IBlueFire get(@Nullable ICapabilityProvider provider) {
        return provider.hasCapability(CAPABILITY, null) ? provider.getCapability(CAPABILITY, null) : null;
    }

    class Impl implements IBlueFire
    {
        protected int remaining;

        @Override
        public int getRemaining() { return remaining; }

        @Override
        public void setRemaining(int remainingIn) { remaining = remainingIn; }
    }

    enum Storage implements Capability.IStorage<IBlueFire>
    {
        INSTANCE;

        @Nonnull
        @Override
        public NBTBase writeNBT(@Nonnull Capability<IBlueFire> capability, @Nonnull IBlueFire instance, @Nullable EnumFacing side) {
            return new NBTTagInt(instance.getRemaining());
        }

        @Override
        public void readNBT(@Nonnull Capability<IBlueFire> capability, @Nonnull IBlueFire instance, @Nullable EnumFacing side, @Nonnull NBTBase nbt) {
            instance.setRemaining(((NBTTagInt)nbt).getInt());
        }
    }
}
