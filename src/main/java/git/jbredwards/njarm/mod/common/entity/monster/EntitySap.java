package git.jbredwards.njarm.mod.common.entity.monster;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class EntitySap extends AbstractSlime
{
    public EntitySap(@Nonnull World worldIn) { super(worldIn); }

    @Nonnull
    @Override
    public EntitySap createInstance() { return new EntitySap(world); }

    @Nonnull
    @Override
    public Item getItemForParticle() { return ModItems.SAP_BALL; }

    @Nonnull
    @Override
    public ResourceLocation getLootTableForSmall() { return new ResourceLocation(Constants.MODID, "entities/sap"); }
}
