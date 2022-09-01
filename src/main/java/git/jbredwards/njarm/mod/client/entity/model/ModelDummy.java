package git.jbredwards.njarm.mod.client.entity.model;

import git.jbredwards.njarm.mod.common.entity.item.EntityDummy;
import net.darkhax.bookshelf.client.model.ModelPlayerMob;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class ModelDummy extends ModelPlayerMob
{
    public ModelDummy() { super(0, false); }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, @Nonnull Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        if(entityIn instanceof EntityDummy) {
            final EntityDummy dummy = (EntityDummy)entityIn;
            bipedHead.rotateAngleX = 0.0175f * dummy.getHeadRotation().getX();
            bipedHead.rotateAngleY = 0.0175f * dummy.getHeadRotation().getY();
            bipedHead.rotateAngleZ = 0.0175f * dummy.getHeadRotation().getZ();
            bipedHead.setRotationPoint(0, 1, 0);
            bipedBody.rotateAngleX = 0.0175f * dummy.getBodyRotation().getX();
            bipedBody.rotateAngleY = 0.0175f * dummy.getBodyRotation().getY();
            bipedBody.rotateAngleZ = 0.0175f * dummy.getBodyRotation().getZ();
            bipedLeftArm.rotateAngleX = 0.0175f * dummy.getLeftArmRotation().getX();
            bipedLeftArm.rotateAngleY = 0.0175f * dummy.getLeftArmRotation().getY();
            bipedLeftArm.rotateAngleZ = 0.0175f * dummy.getLeftArmRotation().getZ();
            bipedRightArm.rotateAngleX = 0.0175f * dummy.getRightArmRotation().getX();
            bipedRightArm.rotateAngleY = 0.0175f * dummy.getRightArmRotation().getY();
            bipedRightArm.rotateAngleZ = 0.0175f * dummy.getRightArmRotation().getZ();
            bipedLeftLeg.rotateAngleX = 0.0175f * dummy.getLeftLegRotation().getX();
            bipedLeftLeg.rotateAngleY = 0.0175f * dummy.getLeftLegRotation().getY();
            bipedLeftLeg.rotateAngleZ = 0.0175f * dummy.getLeftLegRotation().getZ();
            bipedLeftLeg.setRotationPoint(1.9f, 11, 0);
            bipedRightLeg.rotateAngleX = 0.0175f * dummy.getRightLegRotation().getX();
            bipedRightLeg.rotateAngleY = 0.0175f * dummy.getRightLegRotation().getY();
            bipedRightLeg.rotateAngleZ = 0.0175f * dummy.getRightLegRotation().getZ();
            bipedRightLeg.setRotationPoint(-1.9f, 11, 0);

            copyModelAngles(bipedHead, bipedHeadwear);
            copyModelAngles(bipedLeftLeg, leftLegOverlay);
            copyModelAngles(bipedRightLeg, rightLegLverlay); //bookshelf "lverlay" rly? lol
            copyModelAngles(bipedLeftArm, leftArmOverlay);
            copyModelAngles(bipedRightArm, rightArmOverlay);
            copyModelAngles(bipedBody, bodyOverlay);

            bipedLeftArm.showModel = dummy.getShowArms();
            bipedRightArm.showModel = dummy.getShowArms();
            bipedLeftLeg.setRotationPoint(1.9f, 12, 0);
            bipedRightLeg.setRotationPoint(-1.9f, 12, 0);
        }
    }
}
