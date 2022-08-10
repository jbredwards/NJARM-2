package git.jbredwards.njarm.mod.common.entity.passive;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.entity.MudPigConfig;
import git.jbredwards.njarm.mod.common.util.EntityUtils;
import net.darkhax.bookshelf.util.StackUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class EntityMudPig extends EntityPig implements IShearable
{
    @Nonnull static final DataParameter<Byte> FLOWER = EntityDataManager.createKey(EntityMudPig.class, DataSerializers.BYTE);
    @Nonnull static final Supplier<Item> MUD_ITEM = Suppliers.memoize(() -> Item.getByNameOrId("biomesoplenty:mudball"));
    public int time;

    public EntityMudPig(@Nonnull World worldIn) {
        super(worldIn);
        if(MUD_ITEM.get() != null) time = rand.nextInt(6000) + 6000;
        setFlower(rand.nextInt(100) < MudPigConfig.flowerChance() ? (byte)(rand.nextInt(4) + 4) : (byte)0);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(FLOWER, (byte)0);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        tasks.taskEntries.removeIf(entry -> entry.action instanceof EntityAIPanic
                || entry.action instanceof EntityAIWanderAvoidWater);

        tasks.addTask(1, new EntityAIWanderAvoidWater(this, 1, 0));
        tasks.addTask(2, new EntityAIPanic(this, 1.25));
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) { return hasFlower(); }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1, 1);
        final byte flowerMeta = getFlower(); setFlower((byte)0);
        return ImmutableList.of(new ItemStack(Blocks.RED_FLOWER, 1, flowerMeta));
    }

    public byte getFlower() { return dataManager.get(FLOWER); }
    public void setFlower(byte flower) { dataManager.set(FLOWER, flower); }
    public boolean hasFlower() { return getFlower() != 0; }

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setByte("Flower", getFlower());
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if(compound.hasKey("Flower"))
            setFlower(compound.getByte("Flower"));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(!(isInWater() && turnToNormalPig()) && !world.isRemote && MUD_ITEM.get() != null && --time <= 0) {
            playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1);
            dropItem(MUD_ITEM.get(), 1);
            time = rand.nextInt(6000) + 6000;
        }
    }

    @Override
    public boolean processInteract(@Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        final ItemStack held = player.getHeldItem(hand);
        final @Nullable IFluidHandlerItem handler = FluidUtil.getFluidHandler(held);

        if(handler != null && handler.drain(new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME), false) != null && turnToNormalPig()) {
            if(!player.isCreative()) {
                handler.drain(new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME), true);
                final ItemStack container = StackUtils.copyStackWithSize(handler.getContainer(), 1);

                held.shrink(1);
                if(held.isEmpty()) player.setHeldItem(hand, container);
                else if(!player.inventory.addItemStackToInventory(container)) player.dropItem(container, false);
            }

            playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 1, 1);
            return true;
        }

        return super.processInteract(player, hand);
    }

    protected boolean turnToNormalPig() {
        if(!world.isRemote) {
            world.spawnEntity(EntityUtils.deserializeFromEntity(new EntityPig(world), this));
            world.setEntityState(this, (byte)1);
            setDead();
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if(id == 1) {
            final int stateId = Block.getStateId(Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(12));
            for(int j = 0; j < 4; ++j) {
                for(int k = 0; k < 4; ++k) {
                    for(int l = 0; l < 4; ++l) {
                        final double x = (j + 0.5) / 4 - 0.5;
                        final double y = (k + 0.5) / 4 + 0.1;
                        final double z = (l + 0.5) / 4 - 0.5;

                        world.spawnParticle(EnumParticleTypes.BLOCK_CRACK,
                                posX + x, posY + y, posZ + z, x, y - 0.6, z, stateId);
                    }
                }
            }
        }

        else super.handleStatusUpdate(id);
    }

    @SubscribeEvent
    public static void turnToMuddyPig(@Nonnull LivingSpawnEvent.CheckSpawn event) {
        if(!event.isSpawner() && event.getEntityLiving().getClass() == EntityPig.class) {
            final EntityPig entity = (EntityPig)event.getEntityLiving();
            if(entity.getRNG().nextInt(100) < MudPigConfig.spawnChance()) {
                final EntityMudPig newEntity = new EntityMudPig(event.getWorld());
                if(ForgeEventFactory.canEntitySpawn(newEntity, event.getWorld(), event.getX(), event.getY(), event.getZ(), null) != Event.Result.DENY) {
                    final byte flower = newEntity.getFlower();

                    EntityUtils.deserializeFromEntity(newEntity, entity);
                    newEntity.setLocationAndAngles(event.getX(), event.getY(), event.getZ(), entity.getRNG().nextFloat() * 360, 0);
                    newEntity.setFlower(flower);

                    event.getWorld().spawnEntity(newEntity);
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
