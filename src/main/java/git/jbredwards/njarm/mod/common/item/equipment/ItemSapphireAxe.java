package git.jbredwards.njarm.mod.common.item.equipment;

import git.jbredwards.njarm.mod.client.particle.util.ParticleProviders;
import git.jbredwards.njarm.mod.common.config.item.EquipmentConfig;
import git.jbredwards.njarm.mod.common.config.item.ResistantItemsConfig;
import git.jbredwards.njarm.mod.common.item.util.IAutoSmelt;
import git.jbredwards.njarm.mod.common.item.util.IBlueFireWeapon;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class ItemSapphireAxe extends ItemAxe implements IAutoSmelt, IBlueFireWeapon
{
    public ItemSapphireAxe(@Nonnull ToolMaterial material) { super(material); }

    @Override
    public boolean doesAutoSmelt(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull ItemStack stack) {
        return EquipmentConfig.sapphireToolsAutoSmelt();
    }

    @Override
    public boolean canAutoSmeltDrop(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull ItemStack stack, @Nonnull ItemStack drop) {
        return !ResistantItemsConfig.BLUE_FIRE.contains(drop.getItem());
    }

    @Override
    public void handleEffects(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull ItemStack stack, @Nonnull Random rand) {
        ((WorldServer)world).spawnParticle(EnumParticleTypes.CLOUD, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 5, 0.225, 0, 0.25, 0.0);
        ParticleProviders.BLUE_FLAME.spawnServer(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 0.25f, 0.25f, 0.25f, 0, 10);
        IAutoSmelt.super.handleEffects(world, pos, state, stack, rand);
    }

    @Override
    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack, @Nonnull Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment)
                && !(EquipmentConfig.sapphireBowHasFlame() && enchantment == Enchantments.FIRE_ASPECT);
    }
}
