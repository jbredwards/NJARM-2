package git.jbredwards.njarm.mod.common.capability;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = Constants.MODID)
public interface IBubbleColumn
{
    @CapabilityInject(IBubbleColumn.class)
    @Nonnull
    Capability<IBubbleColumn> CAPABILITY = null;

    boolean isInBubbleColumn();
    void setInBubbleColumn(boolean isInBubbleColumnIn);

    @Nullable
    static IBubbleColumn get(@Nullable ICapabilityProvider provider) {
        return provider.hasCapability(CAPABILITY, null) ? provider.getCapability(CAPABILITY, null) : null;
    }

    @SubscribeEvent
    static void attach(@Nonnull AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(new ResourceLocation(Constants.MODID, "bubble_column"), new CapabilityProvider<>(CAPABILITY));
    }

    class Impl implements IBubbleColumn
    {
        protected boolean isInBubbleColumn;

        @Override
        public boolean isInBubbleColumn() { return isInBubbleColumn; }

        @Override
        public void setInBubbleColumn(boolean isInBubbleColumnIn) { isInBubbleColumn = isInBubbleColumnIn; }
    }

    enum Storage implements Capability.IStorage<IBubbleColumn>
    {
        INSTANCE;

        @Nonnull
        @Override
        public NBTBase writeNBT(@Nonnull Capability<IBubbleColumn> capability, @Nonnull IBubbleColumn instance, @Nullable EnumFacing side) {
            return new NBTTagByte(instance.isInBubbleColumn() ? (byte)1 : 0);
        }

        @Override
        public void readNBT(@Nonnull Capability<IBubbleColumn> capability, @Nonnull IBubbleColumn instance, @Nullable EnumFacing side, @Nonnull NBTBase nbt) {
            instance.setInBubbleColumn(((NBTTagByte)nbt).getByte() == 1);
        }
    }
}
