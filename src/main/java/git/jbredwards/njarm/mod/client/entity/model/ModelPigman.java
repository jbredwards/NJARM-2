package git.jbredwards.njarm.mod.client.entity.model;

import git.jbredwards.njarm.mod.common.entity.monster.EntityPigman;
import net.darkhax.bookshelf.client.model.ModelPlayerMob;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public abstract class ModelPigman extends ModelPlayerMob
{
    public static class Base extends ModelPigman
    {
        public Base(float modelSize) {
            super(modelSize);
            bipedHead.setTextureOffset(55, 16).addBox(-2, -4, -5, 4, 3, 1, modelSize);
        }
    }

    public static class Armor extends ModelPigman
    {
        public Armor(float modelSize) { super(modelSize); }
    }

    public ModelPigman(float modelSize) { super(modelSize, false); }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, @Nonnull Entity entityIn) {
        if(entityIn instanceof EntityPigman) {
            final EntityPigman entity = (EntityPigman)entityIn;

            if(entity.isTrading() && entity.canTradeWith()) {
                netHeadYaw = (getMainHand(entityIn) == EnumHandSide.LEFT ? -35f : 35f);
                headPitch = 35;
            }

            if(getMainHand(entityIn) == EnumHandSide.RIGHT) {
                if(!entity.getHeldItem(EnumHand.OFF_HAND).isEmpty()) rightArmPose = ArmPose.ITEM;
                else rightArmPose = ArmPose.EMPTY;

                if(!entity.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) leftArmPose = ArmPose.ITEM;
                else leftArmPose = ArmPose.EMPTY;
            }

            else {
                if(!entity.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) rightArmPose = ArmPose.ITEM;
                else rightArmPose = ArmPose.EMPTY;

                if(!entity.getHeldItem(EnumHand.OFF_HAND).isEmpty()) leftArmPose = ArmPose.ITEM;
                else leftArmPose = ArmPose.EMPTY;
            }

            if(entity.isChild()) scaleFactor = 0.5f;
        }

        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    }
}
