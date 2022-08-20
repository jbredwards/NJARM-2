package git.jbredwards.njarm.mod.client.entity.model;

import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class ModelBlestem extends ModelBlaze
{
    public ModelBlestem() {
        blazeHead = new ModelRenderer(this, 0, 0);
        blazeHead.addBox(-3, -3, -3, 6, 6, 6, 0);
    }
}
