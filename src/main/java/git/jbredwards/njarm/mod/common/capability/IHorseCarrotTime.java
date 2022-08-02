package git.jbredwards.njarm.mod.common.capability;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = Constants.MODID)
public interface IHorseCarrotTime
{
    @CapabilityInject(IHorseCarrotTime.class)
    @Nonnull Capability<IHorseCarrotTime> CAPABILITY = null;

    int getTime();
    int setTime(int timeIn);
    default int decrement() { return setTime(getTime() - 1); }

    @Nullable
    static IHorseCarrotTime get(@Nullable ICapabilityProvider provider) {
        return provider.hasCapability(CAPABILITY, null) ? provider.getCapability(CAPABILITY, null) : null;
    }

    @SubscribeEvent
    static void attach(@Nonnull AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityPlayer)
            event.addCapability(new ResourceLocation(Constants.MODID, "horse_carrot_time"),
                    new CapabilityProvider<>(CAPABILITY));
    }

    class Impl implements IHorseCarrotTime
    {
        protected int time;

        @Override
        public int getTime() { return time; }

        @Override
        public int setTime(int timeIn) { return time = timeIn; }
    }

    enum Storage implements Capability.IStorage<IHorseCarrotTime>
    {
        INSTANCE;

        @Nonnull
        @Override
        public NBTBase writeNBT(@Nonnull Capability<IHorseCarrotTime> capability, @Nonnull IHorseCarrotTime instance, @Nullable EnumFacing side) {
            return new NBTTagInt(instance.getTime());
        }

        @Override
        public void readNBT(@Nonnull Capability<IHorseCarrotTime> capability, @Nonnull IHorseCarrotTime instance, @Nullable EnumFacing side, @Nonnull NBTBase nbt) {
            instance.setTime(((NBTPrimitive)nbt).getInt());
        }
    }
}
