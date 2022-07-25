package git.jbredwards.njarm.mod.client.entity.model;

import git.jbredwards.njarm.mod.common.entity.passive.EntityHighlandCoo;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class ModelHighlandCooWool extends ModelQuadruped
{
    public ModelHighlandCooWool() {
        super(12, 0);

        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4.5f, -4.5f, -6.5f, 9, 9, 7, 0);
        head.setRotationPoint(0, 4, -8);

        body = new ModelRenderer(this, 18, 8);
        body.addBox(-6.5f, -10.5f, -11.5f, 13, 19, 15, 0);
        body.setRotationPoint(0, 5, 2);

        --leg1.rotationPointX;
        ++leg2.rotationPointX;
        --leg3.rotationPointX;
        --leg3.rotationPointZ;
        ++leg4.rotationPointX;
        --leg4.rotationPointZ;

        childZOffset += 2;
    }

    @Override
    public void setLivingAnimations(@Nonnull EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTickTime);
        head.rotationPointY = ((EntityHighlandCoo)entity).getHeadRotationPointY(partialTickTime);
        head.rotateAngleX = ((EntityHighlandCoo)entity).getHeadRotationAngleX(partialTickTime);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, @Nonnull Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, head.rotateAngleX / 0.017453292f, scaleFactor, entityIn);
    }
}
