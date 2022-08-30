package git.jbredwards.njarm.mod.common.entity.monster;

import com.google.common.collect.ImmutableSet;
import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.entity.PigmanConfig;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import git.jbredwards.njarm.mod.common.util.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
public class EntityPigman extends EntityPigZombie
{
    @Nonnull
    static final DataParameter<Integer>
            VARIANT = EntityDataManager.createKey(EntityPigman.class, DataSerializers.VARINT), //variants are TODO
            TRADING_TICKS = EntityDataManager.createKey(EntityPigman.class, DataSerializers.VARINT);

    public int ticksInOverworld;
    protected boolean isBeingTempt;

    public EntityPigman(@Nonnull World worldIn) { super(worldIn); }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        tasks.addTask(3, new EntityAITempt(this, 1.2, false, ImmutableSet.of()) {
            @Override
            public boolean shouldExecute() {
                return EntityPigman.this.isBeingTempt = !EntityPigman.this.isTrading()
                        && EntityPigman.this.canTradeWith()
                        && super.shouldExecute();
            }

            @Override
            protected boolean isTempting(@Nonnull ItemStack stack) { return PigmanConfig.isCurrencyItem(stack); }
        });
    }

    @Override
    protected void applyEntityAI() {
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, true) {
            @Override
            protected void setEntityAttackTarget(@Nonnull EntityCreature creatureIn, @Nonnull EntityLivingBase target) {
                super.setEntityAttackTarget(creatureIn, target);
                if(creatureIn instanceof EntityPigman) ((EntityPigman)creatureIn).becomeAngryAt(target);
            }
        });

        targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && (EntityPigman.this.isAngry() || !isWearingGold(targetEntity));
            }
        });
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(VARIANT, 0);
        dataManager.register(TRADING_TICKS, 0);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if(!isAngry() && isWearingGold(getAttackTarget()))
            setAttackTarget(null);

        if(isTrading()) {
            if(canTradeWith()) navigator.clearPath();
            else resetTrade();
        }
    }

    @Override
    public void setRevengeTarget(@Nullable EntityLivingBase livingBase) {
        super.setRevengeTarget(livingBase);
        if(livingBase != null) resetTrade();
    }

    @Override
    public boolean attackEntityAsMob(@Nonnull Entity entityIn) {
        swingArm(EnumHand.MAIN_HAND);
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(canTradeWith() && !isTrading()) {
            final List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class,
                    getEntityBoundingBox().grow(15, 3, 15),
                    entity -> !entity.cannotPickup() && PigmanConfig.isCurrencyItem(entity.getItem()));

            if(items.size() > 0) {
                EntityItem item = items.get(0);
                double distance = Double.MAX_VALUE;

                //find the closest valid item if multiple are present
                for(EntityItem testItem : items) {
                    final double distanceToTest = testItem.getDistance(this);
                    if(distanceToTest < distance) {
                        distance = distanceToTest;
                        item = testItem;
                    }
                }

                navigator.tryMoveToEntityLiving(item, 1.5);
                if(!world.isRemote && distance < 1.5)
                    startTrade(item.getItem());
            }
        }

        updateOrPerformTrade();
        zombifyIfPossible();
    }

    @Override
    public boolean processInteract(@Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        if(!isTrading() && canTradeWith()) {
            final ItemStack held = player.getHeldItem(hand);
            if(PigmanConfig.isCurrencyItem(held)) {
                startTrade(held);
                player.swingArm(hand);
                return true;
            }
        }

        return super.processInteract(player, hand);
    }

    //drops an item with the same motion as a player
    public void tossItem() {
        if(world instanceof WorldServer) {
            final List<ItemStack> items = world.getLootTableManager()
                    .getLootTableFromLocation(new ResourceLocation(Constants.MODID, "gameplay/pigman_trade"))
                    .generateLootForPools(rand, new LootContext.Builder((WorldServer)world).build());

            for(ItemStack stack : items) {
                final EntityItem item = new EntityItem(world, posX, posY - 0.3 + getEyeHeight(), posZ, stack);
                item.setPickupDelay(40);

                double speedMultiplier = 0.3;
                item.motionX = -Math.sin(rotationYawHead * 0.0175) * Math.cos(rotationPitch * 0.0175) * speedMultiplier;
                item.motionZ = Math.cos(rotationYawHead * 0.0175) * Math.cos(rotationPitch * 0.0175) * speedMultiplier;
                item.motionY = -Math.sin(rotationPitch * 0.0175) * speedMultiplier + 0.1;

                final double inaccuracy = rand.nextFloat() * (Math.PI * 2);
                speedMultiplier = 0.02 * rand.nextFloat();

                item.motionX += Math.cos(inaccuracy) * speedMultiplier;
                item.motionY += ((rand.nextFloat() - rand.nextFloat()) * 0.1);
                item.motionZ += Math.sin(inaccuracy) * speedMultiplier;

                world.spawnEntity(item);
            }
        }
    }

    public void zombifyIfPossible() {
        if(dimension == 0 && ++ticksInOverworld > 100) {
            resetTrade();

            final EntityPigZombie zombie = EntityUtils.deserializeFromEntity(new EntityPigZombie(world), this);
            zombie.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200));
            setDead();

            if(!world.isRemote) world.spawnEntity(zombie);
            zombie.playSound(ModSounds.PIGMAN_COVERT, 0.5f, 1);
        }
    }

    public void startTrade(@Nonnull ItemStack currency) {
        isBeingTempt = false;

        playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1, 1);
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND,
                ItemHandlerHelper.copyStackWithSize(currency, 1));

        currency.shrink(1);
        setTradingTicks(50 + rand.nextInt(50));
    }

    protected void updateOrPerformTrade() {
        if(isTrading()) {
            setTradingTicks(getTradingTicks() - 1);
            if(!isTrading()) {
                if(!world.isRemote) tossItem();
                else for(int i = 0; i < 5; i++)
                    world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
                            posX + rand.nextFloat() * width * 2 - width,
                            posY + rand.nextFloat() * height + 1,
                            posZ + rand.nextFloat() * width * 2 - width,
                            rand.nextGaussian() * 0.02,
                            rand.nextGaussian() * 0.02,
                            rand.nextGaussian() * 0.02);

                swingArm(EnumHand.OFF_HAND);
                resetTrade();
            }
        }
    }

    public void resetTrade() {
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
        setTradingTicks(0);
    }

    public static boolean isWearingGold(@Nullable EntityLivingBase entity) {
        if(entity != null) for(ItemStack stack : entity.getArmorInventoryList())
            if(PigmanConfig.isArmorItem(stack)) return true;

        return false;
    }

    public boolean isTrading() { return getTradingTicks() > 0; }
    public int getTradingTicks() { return dataManager.get(TRADING_TICKS); }
    public void setTradingTicks(int tradingTicks) { dataManager.set(TRADING_TICKS, tradingTicks); }

    public int getVariant() { return dataManager.get(VARIANT); }
    public void setVariant(int variant) { dataManager.set(VARIANT, variant); }

    public boolean canTradeWith() {
        return PigmanConfig.canTradeWhileMad() || !(isAngry()
                || getAttackTarget() != null || getRevengeTarget() != null);
    }

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("OverworldTicks", ticksInOverworld);
        compound.setInteger("tradingTicks", getTradingTicks());
        compound.setInteger("Variant", getVariant());
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if(compound.hasKey("OverworldTicks", NBT.TAG_INT))
            ticksInOverworld = compound.getInteger("OverworldTicks");

        setTradingTicks(compound.getInteger("tradingTicks"));
        setVariant(compound.getInteger("Variant"));
    }

    @Nonnull
    @Override
    protected SoundEvent getAmbientSound() {
        if(isTrading()) return ModSounds.PIGMAN_ADMIRE;
        else if(isAngry()) return ModSounds.PIGMAN_ANGRY;
        else if(isBeingTempt) return ModSounds.PIGMAN_JEALOUS;
        else return ModSounds.PIGMAN_AMBIENT;
    }

    @Nonnull
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) { return ModSounds.PIGMAN_HURT; }

    @Nonnull
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.PIGMAN_DEATH; }

    @Nonnull
    @Override
    protected SoundEvent getStepSound() { return ModSounds.PIGMAN_STEP; }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() { return new ResourceLocation(Constants.MODID, "entities/pigman"); }

    @Nonnull
    @Override
    public EnumCreatureAttribute getCreatureAttribute() { return EnumCreatureAttribute.UNDEFINED; }

    //band-aid fix for wrong pigman angry sound that doesn't require me to rewrite a whole method
    @Override
    public void playSound(@Nonnull SoundEvent soundIn, float volume, float pitch) {
        if(soundIn == SoundEvents.ENTITY_ZOMBIE_PIG_ANGRY) soundIn = ModSounds.PIGMAN_ANGRY;
        super.playSound(soundIn, volume, pitch);
    }
}
