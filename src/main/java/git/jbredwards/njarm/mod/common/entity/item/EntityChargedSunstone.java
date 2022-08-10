package git.jbredwards.njarm.mod.common.entity.item;

import git.jbredwards.njarm.mod.common.config.item.ChargedSunstoneConfig;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import git.jbredwards.njarm.mod.common.util.SoundUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class EntityChargedSunstone extends EntityThrowable
{
    public EntityChargedSunstone(@Nonnull World worldIn) { super(worldIn); }
    public EntityChargedSunstone(@Nonnull World worldIn, double x, double y, double z) { super(worldIn, x, y, z); }
    public EntityChargedSunstone(@Nonnull World worldIn, @Nonnull EntityLivingBase throwerIn) { super(worldIn, throwerIn); }

    @Override
    protected void onImpact(@Nonnull RayTraceResult result) {
        if(!world.isRemote) {
            double x = posX, y = posY, z = posZ;
            if(ChargedSunstoneConfig.lightning()) {
                final EntityLightningBolt bolt = new EntityLightningBolt(world, posX, posY, posZ, false);
                //checks the bolt's position in case it changed due to the event
                if(world.addWeatherEffect(bolt)) { x = bolt.posX; y = bolt.posY; z = bolt.posZ; }
            }

            if(ChargedSunstoneConfig.explode()) {
                world.newExplosion(this, x + 0.5, y + 0.5, z + 0.5, ChargedSunstoneConfig.explodeStrength(),
                        ChargedSunstoneConfig.explodeFire(), ChargedSunstoneConfig.explodeDmg());

                SoundUtils.playSound(world, x, y, z, ModSounds.ELECTRIC_EXPLOSION, SoundCategory.WEATHER, 4.1f,
                        (1 + (rand.nextFloat() - rand.nextFloat()) * 0.2f) * 0.7f);
            }

            setDead();
        }
    }
}
