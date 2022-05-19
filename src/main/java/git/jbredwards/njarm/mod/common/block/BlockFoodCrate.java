package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.fluidlogged_api.mod.asm.plugins.ASMHooks;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

/**
 *
 * @author jbred
 *
 */
public class BlockFoodCrate extends Block
{
    @Nonnull
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockFoodCrate(@Nonnull Material materialIn) { this(materialIn, materialIn.getMaterialMapColor()); }
    public BlockFoodCrate(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn) {
        super(materialIn, mapColorIn);
        Blocks.FIRE.setFireInfo(this, 5, 10);
        setDefaultState(getDefaultState().withProperty(TYPE, Type.WHEAT_SEEDS));
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return state.getValue(TYPE).getItem();
    }

    @Override
    public int damageDropped(@Nonnull IBlockState state) { return state.getValue(TYPE).itemMeta; }

    @Override
    public int quantityDropped(@Nonnull Random random) { return 9; }

    @Override
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, @Nonnull NonNullList<ItemStack> items) {
        for(int meta = 0; meta < Type.values().length; meta++) { items.add(new ItemStack(this, 1, meta)); }
    }

    @Override
    public boolean canRenderInLayer(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
        return layer == BlockRenderLayer.CUTOUT || super.canRenderInLayer(state, layer);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, TYPE); }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) { return state.getValue(TYPE).ordinal(); }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, Type.values()[meta < Type.values().length ? meta : 0]);
    }

    @Override
    public float getSlipperiness(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nullable Entity entity) {
        return state.getValue(TYPE).isGolden() ? 0.6f : 0.7f;
    }

    @Nonnull
    @Override
    public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return new ItemStack(this, 1, getMetaFromState(state));
    }

    @Nonnull
    @Override
    public SoundType getSoundType(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nullable Entity entity) {
        final SoundEvent stepSound = state.getValue(TYPE).isGolden() ? ModSounds.NETHER_ORE.getStepSound() : ModSounds.WET_GRASS.getStepSound();
        return new SoundType(1, 1, SoundEvents.BLOCK_WOOD_BREAK, stepSound, SoundEvents.BLOCK_WOOD_PLACE, SoundEvents.BLOCK_WOOD_HIT, SoundEvents.BLOCK_WOOD_FALL);
    }

    @Override
    public void onEntityWalk(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Entity entityIn) {
        if(!worldIn.isRemote && entityIn instanceof EntityLivingBase) {
            final Type type = worldIn.getBlockState(pos).getValue(TYPE);
            if(type == Type.POISONOUS_POTATO) ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, 65));
            else if(type == Type.GOLDEN_APPLE) ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 65));
            else if(type == Type.GOLDEN_CARROT) ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 65));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        final Type type = stateIn.getValue(TYPE);
        if((type.isGolden() || type == Type.POISONOUS_POTATO) && rand.nextInt(5) == 0 && ASMHooks.shouldHavePost(worldIn, pos.up())) {
            final int color;
            switch(type) {
                case POISONOUS_POTATO: color = MobEffects.POISON.getLiquidColor();
                    break;
                case GOLDEN_APPLE: color = MobEffects.REGENERATION.getLiquidColor();
                    break;
                default: color = MobEffects.NIGHT_VISION.getLiquidColor();
            }

            //spawn potion particle with no velocity
            final double r = (color >> 16 & 255) / 255.0, g = (color >> 8 & 255) / 255.0, b = (color & 255) / 255.0;
            final @Nullable Particle particle = Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID(),
                    pos.getX() + rand.nextDouble(), pos.getY() + 1, pos.getZ() + rand.nextDouble(), r, g, b);
            if(particle != null) particle.multiplyVelocity(0);
        }
    }

    public enum Type implements IStringSerializable
    {
        @Nonnull WHEAT_SEEDS("wheat_seeds", () -> Items.WHEAT_SEEDS),
        @Nonnull CARROT("carrot", () -> Items.CARROT),
        @Nonnull POTATO("potato", () -> Items.POTATO),
        @Nonnull POISONOUS_POTATO("poisonous_potato", () -> Items.POISONOUS_POTATO),
        @Nonnull BEETROOT("beetroot", () -> Items.BEETROOT),
        @Nonnull BEETROOT_SEEDS("beetroot_seeds", () -> Items.BEETROOT_SEEDS),
        @Nonnull APPLE("apple", () -> Items.APPLE),
        @Nonnull GOLDEN_APPLE("golden_apple", () -> Items.GOLDEN_APPLE),
        @Nonnull GOLDEN_CARROT("golden_carrot", () -> Items.GOLDEN_CARROT),
        @Nonnull FISH("fish", () -> Items.FISH),
        @Nonnull SALMON("salmon", () -> Items.FISH, 1),
        @Nonnull NEMO("nemo", () -> Items.FISH, 2),
        @Nonnull PUFFERFISH("pufferfish", () -> Items.FISH, 3);

        @Nonnull public static final Type[] COMMONS = new Type[] {WHEAT_SEEDS, CARROT, POTATO, BEETROOT, BEETROOT_SEEDS, APPLE, FISH, SALMON, PUFFERFISH};
        @Nonnull public static final Type[] RARES = new Type[] {POISONOUS_POTATO, GOLDEN_APPLE, GOLDEN_CARROT, NEMO};
        public boolean isGolden() { return this == GOLDEN_APPLE || this == GOLDEN_CARROT; }

        @Nonnull private final String name;
        @Nonnull private final Supplier<Item> item;
        public final int itemMeta;

        Type(@Nonnull String name, @Nonnull Supplier<Item> item) { this(name, item, 0); }
        Type(@Nonnull String name, @Nonnull Supplier<Item> item, int itemMeta) {
            this.name = name;
            this.item = item;
            this.itemMeta = itemMeta;
        }

        @Nonnull
        @Override
        public String getName() { return name; }

        @Nonnull
        public Item getItem() { return item.get(); }
    }
}
