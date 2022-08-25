package git.jbredwards.njarm.mod.common.item.equipment;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import git.jbredwards.njarm.mod.common.config.entity.MummyConfig;
import git.jbredwards.njarm.mod.common.config.item.EquipmentConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
@Optional.InterfaceList(value = {
@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles"),
@Optional.Interface(iface = "baubles.api.render.IRenderBauble", modid = "baubles")})
public class ItemCrown extends ItemArmor implements IBauble, IRenderBauble
{
    public ItemCrown(@Nonnull ArmorMaterial materialIn) { super(materialIn, 0, EntityEquipmentSlot.HEAD); }

    @Override
    public boolean hasColor(@Nonnull ItemStack stack) { return true; }

    @Override
    public int getColor(@Nonnull ItemStack stack) { return PotionUtils.getColor(stack); }

    @Override
    public void onArmorTick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull ItemStack stack) {
        super.onArmorTick(world, player, stack);
        if(!world.isRemote) {
            //damages the crown while worn
            if(!player.isCreative() && EquipmentConfig.ticksPerCrownDurability() != 0 && player.ticksExisted != 0
                    && player.ticksExisted % EquipmentConfig.ticksPerCrownDurability() == 0) stack.damageItem(1, player);

            PotionUtils.getEffectsFromStack(stack).forEach(player::addPotionEffect);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        PotionUtils.addPotionTooltip(stack, tooltip, 0);
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        if(isInCreativeTab(tab)) MummyConfig.EFFECTS.getEntries().forEach(effect
                -> items.add(PotionUtils.appendEffects(new ItemStack(this),
                Collections.singleton(effect.getEntry()))));
    }

    @Optional.Method(modid = "baubles")
    @Override
    public BaubleType getBaubleType(@Nonnull ItemStack itemstack) { return BaubleType.HEAD; }

    @Optional.Method(modid = "baubles")
    @Override
    public void onWornTick(@Nonnull ItemStack itemstack, @Nonnull EntityLivingBase player) {
        if(player instanceof EntityPlayer) onArmorTick(player.world, (EntityPlayer)player, itemstack);
    }

    @Optional.Method(modid = "baubles")
    @Override
    public boolean canUnequip(@Nonnull ItemStack itemstack, @Nonnull EntityLivingBase player) {
        return !EnchantmentHelper.hasBindingCurse(itemstack);
    }

    @Optional.Method(modid = "baubles")
    @SideOnly(Side.CLIENT)
    @Override
    public void onPlayerBaubleRender(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull RenderType renderType, float v) {
        if(renderType == RenderType.HEAD) {
            //default head translations
            Helper.translateToHeadLevel(player);
            Helper.translateToFace();
            Helper.defaultTransforms();
            //translates the crown to a proper position
            GlStateManager.translate(0.03f, 0.5f, -0.07f);

            //renders the crown (not the helmet, so others know that it's a bauble)
            GlStateManager.pushMatrix();
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.HEAD);
            GlStateManager.popMatrix();
        }
    }
}
