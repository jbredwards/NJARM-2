package git.jbredwards.njarm.mod.common.util;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.Main;
import git.jbredwards.njarm.mod.common.capability.IBlueFire;
import git.jbredwards.njarm.mod.common.config.block.BlueFireConfig;
import git.jbredwards.njarm.mod.common.entity.ai.EntityAIPanicBlueFire;
import git.jbredwards.njarm.mod.common.entity.util.IBlueFireproof;
import git.jbredwards.njarm.mod.common.init.ModPotions;
import git.jbredwards.njarm.mod.common.message.MessageBlueFire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.script.ScriptException;
import java.util.Iterator;

/**
 * Utility class for handling blue fire
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class BlueFireUtils
{
    @Nonnull public static final DamageSource IN_BLUE_FIRE = new DamageSource("njarm.inBlueFire").setDamageBypassesArmor().setDamageIsAbsolute();
    @Nonnull public static final DamageSource ON_BLUE_FIRE = new DamageSource("njarm.onBlueFire").setDamageBypassesArmor().setDamageIsAbsolute();

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
        return !(entity instanceof EntityPlayer && ((EntityPlayer)entity).isCreative() || entity instanceof IBlueFireproof
                || entity.isWet() || entity.isBurning()
                || entity.world.isFlammableWithin(entity.getEntityBoundingBox().shrink(0.001))
                || (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(ModPotions.BLUE_FIRE_RESISTANCE)));
    }

    public static boolean damageEntityIn(@Nonnull Entity entity) {
        try { return entity.attackEntityFrom(IN_BLUE_FIRE, BlueFireConfig.getDamageDealt(entity, false)); }
        //oops, return default
        catch(ScriptException e) {
            e.printStackTrace();
            return entity.attackEntityFrom(IN_BLUE_FIRE, entity instanceof EntityLivingBase ?
                    Math.max(2, ((EntityLivingBase)entity).getHealth() / (entity.isNonBoss() ? 4 : 32)) : 2);
        }
    }

    public static boolean damageEntityOn(@Nonnull Entity entity) {
        try { return entity.attackEntityFrom(ON_BLUE_FIRE, BlueFireConfig.getDamageDealt(entity, true)); }
        //oops, return default
        catch(ScriptException e) {
            e.printStackTrace();
            return entity.attackEntityFrom(ON_BLUE_FIRE, entity instanceof EntityLivingBase ?
                    Math.max(2, ((EntityLivingBase)entity).getHealth() / (entity.isNonBoss() ? 4 : 32)) : 2);
        }
    }

    //syncs the blue fire of an entity
    public static void syncBlueFire(@Nonnull Entity entity) {
        if(!entity.world.isRemote) {
            final MessageBlueFire message = new MessageBlueFire(entity.getEntityId(), getRemaining(entity) > 0);
            if(entity instanceof EntityPlayerMP) Main.wrapper.sendTo(message, (EntityPlayerMP)entity);
            Main.wrapper.sendToAllTracking(message, entity);
        }
    }

    @SubscribeEvent
    public static void syncBlueFireEntities(@Nonnull PlayerEvent.StartTracking event) {
        if(event.getEntityPlayer() instanceof EntityPlayerMP) Main.wrapper.sendTo(
                new MessageBlueFire(event.getTarget().getEntityId(), getRemaining(event.getTarget()) > 0),
                (EntityPlayerMP)event.getEntityPlayer());
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
                    if(entity.isWet()) SoundUtils.playSound(entity, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7f,
                            1.6f + (entity.getRNG().nextFloat() - entity.getRNG().nextFloat()) * 0.4f);

                    cap.setRemaining(0);
                    syncBlueFire(entity);
                }
                //deals the damage & shrinks the time left
                else if(entity.ticksExisted % 20 == 0) {
                    cap.setRemaining(cap.getRemaining() - 1);
                    damageEntityOn(entity);

                    if(cap.getRemaining() == 0)
                        syncBlueFire(entity);
                }
            }
        }
    }

    @SubscribeEvent
    public static void blueFireArrows(@Nonnull ProjectileImpactEvent.Arrow event) {
        if(!event.getArrow().world.isRemote) {
            final int remaining = getRemaining(event.getArrow());
            if(remaining > 0 && event.getRayTraceResult() != null) {
                final @Nullable Entity entityHit = event.getRayTraceResult().entityHit;
                if(entityHit != null && !(entityHit instanceof EntityEnderman) && !(entityHit instanceof EntityLivingBase && ((EntityLivingBase)entityHit).isActiveItemStackBlocking()) && canBeLit(entityHit)) {
                    setRemaining(entityHit, remaining);
                    syncBlueFire(entityHit);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void panicIfBlueFire(@Nonnull EntityJoinWorldEvent event) {
        final @Nonnull Entity entity = event.getEntity();
        if(entity instanceof EntityCreature) {
            final EntityCreature creature = (EntityCreature)entity;
            for(EntityAITasks.EntityAITaskEntry task : creature.tasks.taskEntries) {
                if(task.action instanceof EntityAIPanic) {
                    creature.tasks.addTask(task.priority, new EntityAIPanicBlueFire(creature, ((EntityAIPanic)task.action).speed));
                    return;
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void smeltLoot(@Nonnull LivingDropsEvent event) {
        //don't smelt the loot of players, that's just cruel
        if(!(event.getEntityLiving() instanceof EntityPlayer)) {
            final EntityLivingBase entity = event.getEntityLiving();

            if(getRemaining(entity) > 0 && canBeLit(entity) || event.getSource() == IN_BLUE_FIRE || event.getSource() == ON_BLUE_FIRE) {
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

    //copied from Render#renderEntityOnFire
    @SideOnly(Side.CLIENT)
    public static void renderBlueFireInThirdPerson(@Nonnull Entity entity, double x, double y, double z) {
        GlStateManager.disableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        x = 0.5;
        y = entity.posY - entity.getEntityBoundingBox().minY;
        z = 0;

        final TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("njarm:blocks/blue_fire_layer_0");
        final TextureAtlasSprite sprite1 = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("njarm:blocks/blue_fire_layer_1");
        final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

        final float size = entity.width * 1.4f;
        float height = entity.height / size;

        GlStateManager.scale(size, size, size);
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0, 1, 0);
        GlStateManager.translate(0, 0, -0.3 + (int)height * 0.02);
        GlStateManager.color(1, 1, 1, 1);

        int i = 0;
        while(height > 0) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            final TextureAtlasSprite fire = (i & 1) == 0 ? sprite : sprite1;
            float minU = fire.getMinU();
            float maxU = fire.getMaxU();

            if((i & 3) == 0) {
                final float f10 = maxU;
                maxU = minU;
                minU = f10;
            }

            buffer.pos(x, -y, z).tex(maxU, fire.getMaxV()).endVertex();
            buffer.pos(-x, -y, z).tex(minU, fire.getMaxV()).endVertex();
            buffer.pos(-x, 1.4 - y, z).tex(minU, fire.getMinV()).endVertex();
            buffer.pos(x, 1.4 - y, z).tex(maxU, fire.getMinV()).endVertex();
            height -= 0.45;
            x *= 0.9;
            y -= 0.45;
            z += 0.03;
            ++i;
        }

        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
    }

    //this is mostly copied from the quark mod, credit for the original method: Vazkki & Yrsegal
    @SideOnly(Side.CLIENT)
    public static void renderQuarkBlueFireInThirdPerson(@Nonnull Entity entity, double x, double y, double z) {
        GlStateManager.disableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        final TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("njarm:blocks/blue_fire_layer_0");
        final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        final float size = Math.min(entity.width, entity.height) * 1.8f;
        y = entity.posY - entity.getEntityBoundingBox().minY;

        GlStateManager.scale(size, size, size);
        GlStateManager.color(1, 1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        int iterations = 8;
        final float rot = (360f / iterations);
        while(iterations --> 0) {
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(0.5, -y, 0).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
            buffer.pos(-0.5, -y, 0).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
            buffer.pos(-0.5, 1.4 - y, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
            buffer.pos(0.5, 1.4 - y, 0).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
            Tessellator.getInstance().draw();
            GlStateManager.rotate(rot, 0, 1, 0);
        }

        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
    }
}
