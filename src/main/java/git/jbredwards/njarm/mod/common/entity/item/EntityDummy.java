package git.jbredwards.njarm.mod.common.entity.item;

import git.jbredwards.njarm.mod.common.init.ModItems;
import git.jbredwards.njarm.mod.common.util.SoundUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class EntityDummy extends EntityArmorStand
{
    @Nonnull static final DataParameter<Float> LAST_DAMAGE = EntityDataManager.createKey(EntityDummy.class, DataSerializers.FLOAT);
    @Nonnull static final DataParameter<Float> COMBO_DAMAGE = EntityDataManager.createKey(EntityDummy.class, DataSerializers.FLOAT);
    @Nonnull static final DataParameter<Integer> TICKS_TO_DISPLAY = EntityDataManager.createKey(EntityDummy.class, DataSerializers.VARINT);

    public EntityDummy(World worldIn) { super(worldIn); }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(LAST_DAMAGE, 0f);
        dataManager.register(COMBO_DAMAGE, 0f);
        dataManager.register(TICKS_TO_DISPLAY, 0);
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        if(!ForgeHooks.onLivingAttack(this, source, amount) || getIsInvulnerable()) return displayDamage(0);
        if(source.isFireDamage() && (isImmuneToFire || isPotionActive(MobEffects.FIRE_RESISTANCE)))
            return displayDamage(0);

        displayDamage(applyPotionDamageCalculations(source, applyArmorCalculations(source, amount)));
        if(world.getTotalWorldTime() - punchCooldown > 5) {
            world.setEntityState(this, (byte)32);
            punchCooldown = world.getTotalWorldTime();
        }

        return false;
    }

    protected boolean displayDamage(float amount) {
        if(!world.isRemote && world.getTotalWorldTime() - punchCooldown > 5) {
            setLastDamage(amount);
            setComboDamage(getComboDamage() + amount);
            setTicksToDisplay(20);
        }

        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        final int ticksToDisplay = getTicksToDisplay();
        if(ticksToDisplay > 0) setTicksToDisplay(ticksToDisplay - 1);
        if(ticksToDisplay <= 1) setComboDamage(0);
    }

    @Nonnull
    @Override
    public ItemStack getPickedResult(@Nonnull RayTraceResult target) { return new ItemStack(ModItems.DUMMY); }

    @Nonnull
    @Override
    public EnumActionResult applyPlayerInteraction(@Nonnull EntityPlayer player, @Nonnull Vec3d vec, @Nonnull EnumHand hand) {
        if(player.isSneaking()) {
            if(hand == EnumHand.MAIN_HAND && player.getHeldItem(hand).isEmpty()) {
                if(!player.isCreative()) {
                    Block.spawnAsEntity(world, new BlockPos(this), new ItemStack(ModItems.DUMMY));
                    dropContents();
                }
                //ensure breaking sound plays
                else if(!world.isRemote) SoundUtils.playSound(this, SoundEvents.ENTITY_ARMORSTAND_BREAK, 1, 1);

                if(world instanceof WorldServer) {
                    ((WorldServer)world).spawnParticle(EnumParticleTypes.BLOCK_DUST, posX, posY + height / 1.5, posZ, 20,
                            width / 4, height / 4, width / 4, 0.05, Block.getStateId(Blocks.HAY_BLOCK.getDefaultState()));
                }

                setDead();
                player.swingArm(hand);
                return EnumActionResult.SUCCESS;
            }

            return EnumActionResult.PASS;
        }

        return super.applyPlayerInteraction(player, vec, hand);
    }

    @Override
    public boolean getShowArms() { return true; }

    @Override
    public boolean canBeHitWithPotion() { return true; }

    @Override
    public void knockBack(@Nonnull Entity entityIn, float strength, double xRatio, double zRatio) {}

    public float getLastDamage() { return dataManager.get(LAST_DAMAGE); }
    public void setLastDamage(float lastDamage) { dataManager.set(LAST_DAMAGE, lastDamage); }

    public float getComboDamage() { return dataManager.get(COMBO_DAMAGE); }
    public void setComboDamage(float comboDamage) { dataManager.set(COMBO_DAMAGE, comboDamage); }

    public int getTicksToDisplay() { return dataManager.get(TICKS_TO_DISPLAY); }
    public void setTicksToDisplay(int ticksToDisplay) { dataManager.set(TICKS_TO_DISPLAY, ticksToDisplay); }
}
