package git.jbredwards.njarm.mod.common.entity.passive;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.particle.util.ParticleProviders;
import git.jbredwards.njarm.mod.common.config.entity.GlowSquidConfig;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import git.jbredwards.njarm.mod.common.util.EntityUtils;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class EntityGlowSquid extends EntitySquid
{
    public EntityGlowSquid(@Nonnull World worldIn) { super(worldIn); }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(world.isRemote)
            ParticleProviders.GLOW_SQUID_AURA
                .spawnClient(world, posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0, 0, 0);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getBrightnessForRender() { return 15728880; }

    @Override
    public float getBrightness() { return 1; }

    @Nonnull
    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.GLOW_SQUID_AMBIENT; }

    @Nonnull
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) { return ModSounds.GLOW_SQUID_HURT; }

    @Nonnull
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.GLOW_SQUID_DEATH; }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() { return new ResourceLocation(Constants.MODID, "entities/glow_squid"); }

    @SubscribeEvent
    public static void overrideSquidSpawn(@Nonnull LivingSpawnEvent.CheckSpawn event) {
        if(!event.isSpawner() && event.getEntityLiving().getClass() == EntitySquid.class) {
            if(GlowSquidConfig.OVERRIDE_BIOMES.contains(event.getWorld().getBiome(new BlockPos(event.getX(), event.getY(), event.getZ())))) {
                final EntityGlowSquid glowSquid = new EntityGlowSquid(event.getWorld());
                if(ForgeEventFactory.canEntitySpawn(glowSquid, event.getWorld(), event.getX(), event.getY(), event.getZ(), null) != Event.Result.DENY) {
                    EntityUtils.deserializeFromEntity(glowSquid, event.getEntityLiving());
                    glowSquid.setLocationAndAngles(event.getX(), event.getY(), event.getZ(),
                            glowSquid.getRNG().nextFloat() * 360, 0);

                    event.getWorld().spawnEntity(glowSquid);
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
