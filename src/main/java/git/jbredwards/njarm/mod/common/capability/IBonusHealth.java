package git.jbredwards.njarm.mod.common.capability;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.item.BonusHeartConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 *
 * @author jbred
 *
 */
@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = Constants.MODID)
public interface IBonusHealth
{
    @CapabilityInject(IBonusHealth.class)
    @Nonnull Capability<IBonusHealth> CAPABILITY = null;
    @Nonnull Callable<IBonusHealth> NONE = () -> new IBonusHealth() {
        @Override
        public float getBonusHealth() { return 0; }

        @Override
        public void setBonusHealth(float amount) {}
    };

    float getBonusHealth();
    void setBonusHealth(float amount);
    default void incrementBonusHealth(float amount) {
        setBonusHealth(getBonusHealth() + amount);
    }

    @Nullable
    static IBonusHealth get(@Nullable ICapabilityProvider provider) {
        return provider.hasCapability(CAPABILITY, null) ? provider.getCapability(CAPABILITY, null) : null;
    }

    @SubscribeEvent
    static void attach(@Nonnull AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityPlayer)
            event.addCapability(new ResourceLocation(Constants.MODID, "max_health"),
                    new CapabilityProvider<>(CAPABILITY, new Impl((EntityPlayer)event.getObject())));
    }

    @SubscribeEvent
    static void keep(@Nonnull PlayerEvent.Clone event) {
        if(!event.isWasDeath() || BonusHeartConfig.keepOnDeath()) {
            final @Nullable IBonusHealth oldCap = get(event.getOriginal());
            if(oldCap != null) {
                final @Nullable IBonusHealth newCap = get(event.getEntity());
                if(newCap != null) newCap.setBonusHealth(oldCap.getBonusHealth());
            }
        }
    }

    class Impl implements IBonusHealth
    {
        @Nonnull
        public static final UUID ATTRIBUTE = UUID.fromString("63150761-ce7b-4115-8336-629ac3ebb3ad");

        @Nonnull
        protected final EntityPlayer entity;
        protected float bonusHealth;

        public Impl(@Nonnull EntityPlayer entity) {
            this.entity = entity;
        }

        @Override
        public float getBonusHealth() { return bonusHealth; }

        @Override
        public void setBonusHealth(float amount) {
            final IAttributeInstance attribute = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            Optional.ofNullable(attribute.getModifier(ATTRIBUTE)).ifPresent(attribute::removeModifier);

            attribute.applyModifier(new AttributeModifier(ATTRIBUTE, Constants.MODID + ":max_health", amount, 0));
            bonusHealth = amount;
        }
    }

    enum Storage implements Capability.IStorage<IBonusHealth>
    {
        INSTANCE;

        @Nonnull
        @Override
        public NBTBase writeNBT(@Nonnull Capability<IBonusHealth> capability, @Nonnull IBonusHealth instance, @Nullable EnumFacing side) {
            return new NBTTagFloat(instance.getBonusHealth());
        }

        @Override
        public void readNBT(@Nonnull Capability<IBonusHealth> capability, @Nonnull IBonusHealth instance, @Nullable EnumFacing side, @Nonnull NBTBase nbt) {
            instance.setBonusHealth(((NBTPrimitive)nbt).getFloat());
        }
    }
}
