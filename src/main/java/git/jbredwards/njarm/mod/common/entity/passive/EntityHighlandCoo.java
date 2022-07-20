package git.jbredwards.njarm.mod.common.entity.passive;

import git.jbredwards.njarm.mod.common.entity.util.DataSerializerEnum;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author jbred
 *
 */
public class EntityHighlandCoo extends EntityCow implements IShearable
{
    @Nonnull static final DataParameter<Boolean> SHEARED = EntityDataManager.createKey(EntityHighlandCoo.class, DataSerializers.BOOLEAN);
    @Nonnull static final DataParameter<EnumDyeColor> DYE_COLOR = EntityDataManager.createKey(EntityHighlandCoo.class,
            new DataSerializerEnum<>(EnumDyeColor::values));

    protected int sheepTimer;
    public EntityHighlandCoo(@Nonnull World worldIn) { super(worldIn); }

    @Override
    protected void initEntityAI() {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIPanic(this, 2));
        tasks.addTask(2, new EntityAIMate(this, 1));
        tasks.addTask(3, new EntityAITempt(this, 1.25, Items.WHEAT, false));
        tasks.addTask(4, new EntityAIFollowParent(this, 1.25));
        tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1));
        tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6));
        tasks.addTask(8, new EntityAILookIdle(this));
        tasks.addTask(5, new EntityAIEatGrass(this) {
            @Override
            public void updateTask() {
                super.updateTask();
                sheepTimer = getEatingGrassTimer();
            }
        });
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(DYE_COLOR, EnumDyeColor.WHITE);
        dataManager.register(SHEARED, false);
    }

    @Override
    public void onLivingUpdate() {
        if(world.isRemote && sheepTimer > 0) sheepTimer--;
        super.onLivingUpdate();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if(id == 10) sheepTimer = 40;
        else super.handleStatusUpdate(id);
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationPointY(float partialTicks) {
        if(sheepTimer >= 4 && sheepTimer <= 36) return 13;
        else return sheepTimer < 4 ? (sheepTimer - partialTicks) * 2.25f + 4 :
                (partialTicks - sheepTimer + 40) * 2.25f + 4;
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationAngleX(float partialTicks) {
        if(sheepTimer > 4 && sheepTimer <= 36) {
            float f = (sheepTimer - 4 - partialTicks) / 32;
            return ((float)Math.PI / 5) + (float)Math.PI * 7 / 100 * MathHelper.sin(f * 28.7f);
        }

        return sheepTimer > 0 ? (float)Math.PI / 5 : rotationPitch * 0.0175f;
    }

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Sheared", getSheared());
        compound.setByte("Color", (byte)getFleeceColor().getMetadata());
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setSheared(compound.getBoolean("Sheared"));
        setFleeceColor(EnumDyeColor.byMetadata(compound.getByte("Color")));
    }

    @Nonnull
    public EnumDyeColor getFleeceColor() { return dataManager.get(DYE_COLOR); }
    public void setFleeceColor(@Nonnull EnumDyeColor color) { dataManager.set(DYE_COLOR, color); }

    public boolean getSheared() { return dataManager.get(SHEARED); }
    public void setSheared(boolean sheared) { dataManager.set(SHEARED, sheared); }

    @Override
    public void eatGrassBonus() {
        if(isChild()) addGrowth(60);
        setSheared(false);
    }

    @Override
    public EntityHighlandCoo createChild(@Nonnull EntityAgeable other) {
        final EntityHighlandCoo child = new EntityHighlandCoo(world);
        child.setFleeceColor(mixWith(((EntityHighlandCoo)other).getFleeceColor()));
        return child;
    }

    @Nonnull
    protected EnumDyeColor mixWith(@Nonnull EnumDyeColor other) {
        final InventoryCrafting internalCrafter = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
                return false;
            }
        }, 2, 1);

        internalCrafter.setInventorySlotContents(0, new ItemStack(Items.DYE, 1, getFleeceColor().getMetadata()));
        internalCrafter.setInventorySlotContents(1, new ItemStack(Items.DYE, 1, other.getMetadata()));
        final ItemStack result = CraftingManager.findMatchingResult(internalCrafter, world);

        return result.getItem() == Items.DYE ? EnumDyeColor.byDyeDamage(result.getMetadata())
                : rand.nextBoolean() ? getFleeceColor() : other;
    }

    @Override
    public boolean processInteract(@Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        final ItemStack held = player.getHeldItem(hand);
        if(held.getItem() instanceof ItemDye) {
            final EnumDyeColor color = EnumDyeColor.byDyeDamage(held.getMetadata());
            if(color != getFleeceColor()) {
                setFleeceColor(color);
                held.shrink(1);
            }
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return !getSheared() && !isChild();
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, int fortune) {
        setSheared(true);
        playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1, 1);

        final ItemStack wool = new ItemStack(Blocks.WOOL, 1, getFleeceColor().getMetadata());
        return Stream.generate(wool::copy).limit(rand.nextInt(3) + 1).collect(Collectors.toList());
    }
}
