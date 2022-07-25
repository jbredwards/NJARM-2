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
public class ModelHighlandCoo extends ModelQuadruped
{
    public ModelHighlandCoo() {
        super(12, 0);
        
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4, -4, -6, 8, 8, 6, 0);
        head.setRotationPoint(0, 4, -8);
        head.setTextureOffset(22, 0).addBox(-9, -4, -4, 5, 1, 1, 0);
        head.setTextureOffset(34, 0).addBox(4, -4, -4, 5, 1, 1, 0);
        head.setTextureOffset(46, 0).addBox(-9, -7, -4, 1, 3, 1, 0);
        head.setTextureOffset(46, 0).addBox(8, -7, -4, 1, 3, 1, 0);
        head.setTextureOffset(0, 0).addBox(-5.5f, -4, -4, 1, 1, 1, 0);
        head.setTextureOffset(0, 0).addBox(4.5f, -4, -4, 1, 1, 1, 0);

        body = new ModelRenderer(this, 18, 4);
        body.addBox(-6, -10, -7, 12, 18, 10, 0);
        body.setRotationPoint(0, 5, 2);
        body.setTextureOffset(52, 0).addBox(-2, 2, -8, 4, 6, 1);

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
