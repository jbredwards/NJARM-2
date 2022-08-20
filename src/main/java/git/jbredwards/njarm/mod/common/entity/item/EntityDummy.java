package git.jbredwards.njarm.mod.common.entity.item;

import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
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
    //used to color the text that displays the amount of damage
    @Nonnull
    public static final TextFormatting[] COLORS = new TextFormatting[] {
            TextFormatting.BLACK,
            TextFormatting.DARK_RED,
            TextFormatting.RED,
            TextFormatting.GOLD,
            TextFormatting.YELLOW,
            TextFormatting.GREEN,
            TextFormatting.DARK_GREEN,
            TextFormatting.DARK_AQUA,
            TextFormatting.AQUA,
            TextFormatting.BLUE,
            TextFormatting.DARK_BLUE,
            TextFormatting.LIGHT_PURPLE,
            TextFormatting.DARK_PURPLE
    };

    @Nonnull
    public String lastDamageString = "";
    public int ticksToDisplay;

    public EntityDummy(World worldIn) { super(worldIn); }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        if(!ForgeHooks.onLivingAttack(this, source, amount) || getIsInvulnerable()) return displayDamage(0);
        if(source.isFireDamage() && (isImmuneToFire || isPotionActive(MobEffects.FIRE_RESISTANCE)))
            return displayDamage(0);

        displayDamage(applyPotionDamageCalculations(source, applyArmorCalculations(source, amount)));
        ticksToDisplay = 20;

        if(world.getTotalWorldTime() - punchCooldown > 5) {
            world.setEntityState(this, (byte)32);
            punchCooldown = world.getTotalWorldTime();
        }

        return false;
    }

    protected boolean displayDamage(float amount) {
        lastDamageString = String.format("%s%.3f%s", COLORS[(int)amount <= 4 ? 0 : Math.min(MathHelper.log2((int)amount >> 2), 12)], amount, TextFormatting.RESET);
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        ticksToDisplay--;
    }

    @Nonnull
    @Override
    public ItemStack getPickedResult(@Nonnull RayTraceResult target) { return new ItemStack(ModItems.DUMMY); }

    @Override
    public boolean processInitialInteract(@Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        if(player.getHeldItem(hand).isEmpty()) {
            if(!player.isCreative()) {
                Block.spawnAsEntity(world, new BlockPos(this), new ItemStack(ModItems.DUMMY));
                dropContents();
            }

            if(world instanceof WorldServer)
                ((WorldServer)world).spawnParticle(EnumParticleTypes.BLOCK_DUST, posX, posY + height / 1.5, posZ, 10,
                        width / 4, height / 4, width / 4, 0.05, Block.getStateId(Blocks.HAY_BLOCK.getDefaultState()));

            setDead();
            return true;
        }

        return super.processInitialInteract(player, hand);
    }

    @Override
    public boolean canBeHitWithPotion() { return true; }

    @Override
    public void knockBack(@Nonnull Entity entityIn, float strength, double xRatio, double zRatio) {}
}
