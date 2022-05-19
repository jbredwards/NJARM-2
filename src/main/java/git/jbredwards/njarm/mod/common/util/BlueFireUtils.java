package git.jbredwards.njarm.mod.common.util;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.Main;
import git.jbredwards.njarm.mod.common.capability.CapabilityProvider;
import git.jbredwards.njarm.mod.common.capability.IBlueFire;
import git.jbredwards.njarm.mod.common.message.BlueFireMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

/**
 * Utility class for handling blue fire
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class BlueFireUtils
{
    @Nonnull public static final DamageSource IN_BLUE_FIRE = new DamageSource("inBlueFire").setDamageBypassesArmor();
    @Nonnull public static final DamageSource ON_BLUE_FIRE = new DamageSource("onBlueFire").setDamageBypassesArmor();

    //if clientside returns 1 or 0, if serverside returns the amount of seconds remaining
    public static int getRemaining(@Nullable ICapabilityProvider provider) {
        final @Nullable IBlueFire cap = IBlueFire.get(provider);
        return cap != null ? cap.getRemaining() : 0;
    }

    //note that you'll most likely have to call a packet alongside this function
    public static void setRemaining(@Nullable ICapabilityProvider provider, int remainingIn) {
        final @Nullable IBlueFire cap = IBlueFire.get(provider);
        if(cap != null) cap.setRemaining(remainingIn);
    }

    public static boolean canBeLit(@Nonnull Entity entity) {
        return !((entity instanceof EntityPlayer && ((EntityPlayer)entity).isCreative()) || entity.isWet()
                || entity.isBurning() || entity.world.isFlammableWithin(entity.getEntityBoundingBox().shrink(0.001)));
    }

    public static boolean damageEntityIn(@Nonnull Entity entity) {
        return entity.attackEntityFrom(IN_BLUE_FIRE, entity instanceof EntityLivingBase ?
                Math.max(2, ((EntityLivingBase)entity).getHealth() / (entity.isNonBoss() ? 4 : 32)) : 2);
    }

    public static boolean damageEntityOn(@Nonnull Entity entity) {
        return entity.attackEntityFrom(ON_BLUE_FIRE, entity instanceof EntityLivingBase ?
                Math.max(2, ((EntityLivingBase)entity).getHealth() / (entity.isNonBoss() ? 4 : 32)) : 2);
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void attach(@Nonnull AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(new ResourceLocation(Constants.MODID, "blue_fire"),
                new CapabilityProvider<>(IBlueFire.CAPABILITY));
    }

    @SubscribeEvent
    public static void tick(@Nonnull LivingEvent.LivingUpdateEvent event) {
        final EntityLivingBase entity = event.getEntityLiving();
        if(!entity.world.isRemote) {
            final @Nullable IBlueFire cap = IBlueFire.get(entity);
            if(cap != null && cap.getRemaining() > 0) {
                //extinguish if the entity shouldn't be on fire
                if(!canBeLit(entity)) {
                    //play extinguish sound if wet
                    if(entity.isWet()) SoundUtils.playServerSound(entity, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7f,
                            1.6f + (entity.getRNG().nextFloat() - entity.getRNG().nextFloat()) * 0.4f);

                    cap.setRemaining(0);
                    Main.wrapper.sendToAllAround(
                            new BlueFireMessage(entity.getEntityId(), false),
                            new NetworkRegistry.TargetPoint(entity.world.provider.getDimension(), entity.posX, entity.posY, entity.posZ, 64));
                }
                //deals the damage & shrinks the time left
                else if(entity.world.getTotalWorldTime() % 20 == 0) {
                    cap.setRemaining(cap.getRemaining() - 1);
                    damageEntityOn(entity);

                    if(cap.getRemaining() == 0) Main.wrapper.sendToAllAround(
                            new BlueFireMessage(entity.getEntityId(), false),
                            new NetworkRegistry.TargetPoint(entity.world.provider.getDimension(), entity.posX, entity.posY, entity.posZ, 64));
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void smeltLoot(@Nonnull LivingDropsEvent event) {
        //don't smelt the loot of players, that's just cruel
        if(!(event.getEntityLiving() instanceof EntityPlayer)) {
            final EntityLivingBase entity = event.getEntityLiving();
            if(getRemaining(entity) > 0 && canBeLit(entity)) {
                final NonNullList<EntityItem> smeltedDrops = NonNullList.create();
                for(Iterator<EntityItem> it = event.getDrops().iterator(); it.hasNext();) {
                    final ItemStack drop = it.next().getItem();
                    final ItemStack smelted = ItemStackUtils.copyStackWithScale(FurnaceRecipes.instance().getSmeltingResult(drop), drop.getCount());
                    if(!smelted.isEmpty()) {
                        //prevent individual drops from having too large a stack size
                        do smeltedDrops.add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, smelted.splitStack(smelted.getMaxStackSize())));
                        while(!smelted.isEmpty());

                        it.remove();
                    }
                }

                event.getDrops().addAll(smeltedDrops);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderBlueFireInThirdPerson(@Nonnull RenderLivingEvent.Post<?> event) {
        final EntityLivingBase entity = event.getEntity();
        if(getRemaining(entity) > 0 && canBeLit(entity)) {

        }
    }

    //copied from ItemRenderer#renderFireInFirstPerson
    @SideOnly(Side.CLIENT)
    public static void renderBlueFireInFirstPerson() {
        final TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("njarm:blocks/blue_fire_layer_1");
        final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        GlStateManager.color(1, 1, 1, 0.9f);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        for(int i = 0; i < 2; ++i) {
            GlStateManager.pushMatrix();
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            GlStateManager.translate((-(i * 2 - 1)) * 0.24, -0.3, 0);
            GlStateManager.rotate((i * 2 - 1) * 10, 0, 1, 0);
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(-0.5, -0.5, -0.5).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
            buffer.pos(0.5, -0.5, -0.5).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
            buffer.pos(0.5, 0.5, -0.5).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
            buffer.pos(-0.5, 0.5, -0.5).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
            Tessellator.getInstance().draw();
            GlStateManager.popMatrix();
        }

        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
    }
}
