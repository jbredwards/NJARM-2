package git.jbredwards.njarm.mod.common.capability;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.util.BlueFireUtils;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = Constants.MODID)
public interface IBlueFire
{
    @CapabilityInject(IBlueFire.class)
    @Nonnull Capability<IBlueFire> CAPABILITY = null;

    //please use the more stable BlueFireUtils functions instead if possible
    int getRemaining();
    void setRemaining(int remainingIn);

    @Nullable
    static IBlueFire get(@Nullable ICapabilityProvider provider) {
        return provider != null && provider.hasCapability(CAPABILITY, null) ? provider.getCapability(CAPABILITY, null) : null;
    }

    @SubscribeEvent
    static void attach(@Nonnull AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(new ResourceLocation(Constants.MODID, "blue_fire"), new CapabilityProvider<>(CAPABILITY));
    }

    @SubscribeEvent
    static void sync(@Nonnull PlayerEvent.PlayerChangedDimensionEvent event) {
        final @Nullable IBlueFire cap = get(event.player);
        if(cap != null && cap.getRemaining() > 0)
            BlueFireUtils.syncBlueFire(event.player);
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
            instance.setRemaining(((NBTPrimitive)nbt).getInt());
        }
    }
}
