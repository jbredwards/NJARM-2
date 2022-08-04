package git.jbredwards.njarm.mod.client.compat.jei;

import git.jbredwards.njarm.mod.common.config.block.NetherCoreConfig;
import git.jbredwards.njarm.mod.common.config.item.ChargedSunstoneConfig;
import git.jbredwards.njarm.mod.common.init.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@JEIPlugin
public final class JEIGlobalHandler implements IModPlugin
{
    @Override
    public void register(@Nonnull IModRegistry registry) {
        if(NetherCoreConfig.getAltReactorBehavior())
            registry.addIngredientInfo(new ItemStack(ModItems.GLOWING_OBSIDIAN), VanillaTypes.ITEM, "jei.njarm.desc.glowingObsidian");

        if(ChargedSunstoneConfig.fromLightning())
            registry.addIngredientInfo(new ItemStack(ModItems.CHARGED_SUNSTONE), VanillaTypes.ITEM, "jei.njarm.desc.sunstoneCharged");

        registry.addIngredientInfo(new ItemStack(ModItems.SUNSTONE), VanillaTypes.ITEM, "jei.njarm.desc.sunstone");
    }
}
