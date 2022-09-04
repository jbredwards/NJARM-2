package git.jbredwards.njarm.mod.common.entity.monster;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleBreaking;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public abstract class AbstractSlime extends EntitySlime
{
    public AbstractSlime(@Nonnull World worldIn) { super(worldIn); }

    @Nonnull
    @Override
    public abstract AbstractSlime createInstance();

    @Nonnull
    public abstract Item getItemForParticle();

    @Nonnull
    public abstract ResourceLocation getLootTableForSmall();

    @Nonnull
    @Override
    protected EnumParticleTypes getParticleType() { return EnumParticleTypes.ITEM_CRACK; }

    @Override
    protected boolean spawnCustomParticles() {
        if(world.isRemote) {
            final Item item = getItemForParticle();
            final int slimeSize = getSlimeSize();

            for(int j = 0; j < slimeSize * 8; ++j) {
                float f = rand.nextFloat() * ((float)Math.PI * 2);
                float f1 = rand.nextFloat() * 0.5f + 0.5f;
                final double x = MathHelper.sin(f) * slimeSize * 0.5 * f1;
                final double z = MathHelper.cos(f) * slimeSize * 0.5 * f1;
                Minecraft.getMinecraft().effectRenderer.addEffect(
                        new ParticleBreaking(world, posX + x, posY, posZ + z, item));
            }
        }

        return true;
    }

    @Override
    public boolean getCanSpawnHere() {
        return world.getDifficulty() != EnumDifficulty.PEACEFUL
                && !world.getWorldInfo().getTerrainType().handleSlimeSpawnReduction(rand, world)
                && world.getBlockState(new BlockPos(this).down()).canEntitySpawn(this)
                && isValidLightLevel();
    }

    protected boolean isValidLightLevel() {
        if(world.getLightFor(EnumSkyBlock.SKY, new BlockPos(this)) > rand.nextInt(32)) return false;
        int light = world.getLightFromNeighbors(new BlockPos(this));

        if(world.isThundering()) {
            final int prevSkylightSubtracted = this.world.getSkylightSubtracted();
            world.setSkylightSubtracted(10);
            light = world.getLightFromNeighbors(new BlockPos(this));
            world.setSkylightSubtracted(prevSkylightSubtracted);
        }

        return light <= rand.nextInt(8);
    }

    //use loot table instead
    @Nullable
    @Override
    protected Item getDropItem() { return null; }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() { return isSmallSlime() ? getLootTableForSmall() : null; }
}
