package git.jbredwards.njarm.mod.common.entity.passive;

import com.google.common.base.Optional;
import git.jbredwards.njarm.mod.common.config.entity.MoobloomConfig;
import net.darkhax.bookshelf.util.StackUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
@SuppressWarnings("Guava")
public class EntityMoobloom extends EntityCow implements IShearable
{
    @Nonnull
    static final DataParameter<Optional<IBlockState>> FLOWER = EntityDataManager.createKey(EntityMoobloom.class, DataSerializers.OPTIONAL_BLOCK_STATE);

    public EntityMoobloom(@Nonnull World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(FLOWER, Optional.absent());
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!world.isRemote && !getSheared() && (!MoobloomConfig.weather() || world.isRainingAt(getPosition()))) {
            final int daytime = (int)(world.getWorldTime() % 24000);
            if(daytime >= MoobloomConfig.minPlantTime() && daytime <= MoobloomConfig.maxPlantTime()) {
                final IBlockState flower = getFlower();
                if(world.mayPlace(flower.getBlock(), getPosition(), true, EnumFacing.UP, this))
                    world.setBlockState(getPosition(), flower);
            }
        }
    }

    @Override
    public boolean processInteract(@Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        final ItemStack held = player.getHeldItem(hand);
        //plant flower
        if(getSheared()) {
            final @Nullable IBlockState flower = StackUtils.getStateFromStack(held);
            if(flower != null && MoobloomConfig.flowerStates.contains(flower)) {
                playSound(SoundEvents.BLOCK_GRASS_PLACE, 1, 1);
                setFlower(flower);

                if(!player.isCreative()) held.shrink(1);
            }
        }

        //bonemeal
        else if(MoobloomConfig.bonemealItems.contains(Pair.of(held.getItem(), held.getMetadata()))) {
            if(!world.isRemote) {
                final IBlockState flower = getFlower();
                flower.getBlock().getDrops(world, getPosition(), flower, 0).forEach(
                        stack -> entityDropItem(stack, getEyeHeight()));

                if(!player.isCreative()) held.shrink(1);
            }

            else for(int i = 0; i < 15; i++) {
                final double x = posX + (rand.nextDouble() * 1.2 - 0.6);
                final double y = posY + rand.nextDouble() * 1.5;
                final double z = posZ + (rand.nextDouble() * 1.2 - 0.6);

                final double xSpeed = rand.nextGaussian() * 0.02;
                final double ySpeed = rand.nextGaussian() * 0.02;
                final double zSpeed = rand.nextGaussian() * 0.02;

                world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x, y, z, xSpeed, ySpeed, zSpeed);
            }
        }

        return super.processInteract(player, hand);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(@Nonnull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        final IEntityLivingData data = super.onInitialSpawn(difficulty, livingdata);
        final @Nullable Optional<IBlockState> flower = Optional.fromJavaUtil(
                MoobloomConfig.getRandFlower(rand, world.getBiomeForCoordsBody(getPosition())));

        if(flower != null && flower.isPresent()) setFlower(flower.get());
        return data;
    }

    @Override
    public EntityMoobloom createChild(@Nonnull EntityAgeable ageable) {
        final EntityMoobloom other = (EntityMoobloom)ageable;
        final Optional<IBlockState> flower;
        if(!getSheared()) flower = Optional.of(getFlower());
        else flower = other.getSheared() ? Optional.absent() : Optional.of(other.getFlower());

        final EntityMoobloom child = new EntityMoobloom(world);
        if(flower.isPresent()) child.setFlower(flower.get());
        return child;
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return MoobloomConfig.shearable() && !getSheared();
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, int fortune) {
        final IBlockState flower = getFlower();
        playSound(SoundEvents.BLOCK_GRASS_BREAK, 1, 1);
        setSheared();

        return flower.getBlock().getDrops(world, pos, flower, fortune);
    }

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if(!getSheared()) compound.setTag("Flower", NBTUtil.writeBlockState(new NBTTagCompound(), getFlower()));
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if(compound.hasKey("Flower", Constants.NBT.TAG_COMPOUND))
            setFlower(NBTUtil.readBlockState(compound.getCompoundTag("Flower")));

        //legacy compat
        else if(compound.hasKey("Meta", Constants.NBT.TAG_INT)) {
            if(compound.getBoolean("Sheared")) return;
            final @Nullable Block flower =  Block.getBlockFromName(compound.getString("Flower"));
            if(flower != null) setFlower(flower.getStateFromMeta(compound.getInteger("Meta")));
        }
    }

    @Nonnull
    public IBlockState getFlower() { return dataManager.get(FLOWER).or(Blocks.AIR.getDefaultState()); }
    public void setFlower(@Nonnull IBlockState flower) { dataManager.set(FLOWER, Optional.of(flower)); }

    public boolean getSheared() { return !dataManager.get(FLOWER).isPresent(); }
    public void setSheared() { dataManager.set(FLOWER, Optional.absent()); }
}
