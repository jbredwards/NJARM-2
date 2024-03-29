package git.jbredwards.njarm.asm;

import com.google.common.collect.ImmutableMap;
import git.jbredwards.njarm.asm.plugins.IASMPlugin;
import git.jbredwards.njarm.asm.plugins.forge.*;
import git.jbredwards.njarm.asm.plugins.modded.PluginBiomesOPlenty;
import git.jbredwards.njarm.asm.plugins.vanilla.*;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Handles this mod's transformers
 * @author jbred
 *
 */
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.Name("NJARM 2 Plugin")
@IFMLLoadingPlugin.MCVersion("1.12.2")
public final class ASMHandler implements IFMLLoadingPlugin
{
    /**
     * This class exists because the launcher don't allow {@link IClassTransformer IClassTransformers}
     * to be the same class as {@link IFMLLoadingPlugin IFMLLoadingPlugins}
     */
    @SuppressWarnings("unused")
    public static final class Transformer implements IClassTransformer
    {
        /**
         * Indexes the plugins used by this mod based on the class they're transforming
         */
        @Nonnull
        private static final Map<String, IASMPlugin> PLUGINS = ImmutableMap.<String, IASMPlugin>builder()
                //forge
                .put("net.minecraftforge.fluids.FluidRegistry", new PluginFluidRegistry()) //Changes the water textures to allow for better coloring
                //modded
                .put("biomesoplenty.common.block.BlockBOPGrass", new PluginBiomesOPlenty()) //Apply the 1.16 sounds to hardcoded BOP blocks
                //vanilla
                .put("net.minecraft.client.particle.ParticleDrip", new PluginParticleDrip()) //Water droplet particles keep the color set by this mod
                .put("net.minecraft.client.particle.ParticleManager", new PluginParticleManager()) //Initialize IHasDestroyEffects
                .put("net.minecraft.client.renderer.entity.Render", new PluginRender()) //Bedrock edition entity shadow size parody
                .put("net.minecraft.client.renderer.entity.RenderFallingBlock", new PluginRenderFallingBlock()) //Fix rendering with ILayeredFallingBlock
                .put("net.minecraft.client.renderer.entity.RenderManager", new PluginRenderManager()) //Fixes blue fire related entity rendering lighting bugs
                .put("net.minecraft.client.renderer.tileentity.TileEntityEndPortalRenderer", new PluginTileEntityEndPortalRenderer()) //Render the bottom of end portals
                .put("net.minecraft.client.renderer.EntityRenderer", new PluginEntityRenderer()) //Color rain & remove the annoying night vision flashing
                .put("net.minecraft.client.renderer.ItemRenderer", new PluginItemRenderer()) //Render blue fire without "quirkiness"
                .put("net.minecraft.block.Block", new PluginBlock()) //Swap hardcoded values for biome fog color
                .put("net.minecraft.block.BlockBasePressurePlate", new PluginIHasRunningEffects()) //Fix running effects for pressure plates
                .put("net.minecraft.block.BlockCauldron", new PluginBlockCauldron()) //Allows cauldrons to have transparent water
                .put("net.minecraft.block.BlockCarpet", new PluginIHasRunningEffects()) //Fix running effects for carpets
                .put("net.minecraft.block.BlockDirt", new PluginBlockGrass()) //Use snowy state for any snow block instance
                .put("net.minecraft.block.BlockFalling", new PluginBlockFalling()) //Allow falling blocks to fall through modded blocks
                .put("net.minecraft.block.BlockFire", new PluginBlockFire()) //Turn fire into blue fire if it's on soul sand
                .put("net.minecraft.block.BlockGrass", new PluginBlockGrass()) //Use snowy state for any snow block instance
                .put("net.minecraft.block.BlockLilyPad", new PluginIHasRunningEffects()) //Fix running effects for lily pads
                .put("net.minecraft.block.BlockMycelium", new PluginBlockGrass()) //Use snowy state for any snow block instance
                .put("net.minecraft.block.BlockRailBase", new PluginIHasRunningEffects()) //Fix running effects for rails
                .put("net.minecraft.block.BlockRedstoneDiode", new PluginIHasRunningEffects()) //Fix running effects for repeaters & comparators
                .put("net.minecraft.block.BlockSkull", new PluginBlockSkull()) //Use soul soil instead of soul sand for withers
                .put("net.minecraft.block.BlockSnow", new PluginBlockSnow()) //Snow layers have gravity
                .put("net.minecraft.block.BlockStoneSlab", new PluginBlockStoneSlab()) //Nether brick slabs have the same nether brick sound as vanilla 1.16+
                .put("net.minecraft.block.BlockTrapDoor", new PluginIHasRunningEffects()) //Fix running effects for trapdoors
                .put("net.minecraft.entity.effect.EntityLightningBolt", new PluginEntityLightningBolt()) //Lightning entities only create fire if they're added to the world
                .put("net.minecraft.entity.item.EntityFallingBlock", new PluginEntityFallingBlock()) //Implement IFancyFallingBlock functionality
                .put("net.minecraft.entity.item.EntityItem", new PluginEntityItem()) //ResistantItemsConfig implementation
                .put("net.minecraft.entity.monster.EntityMob", new PluginEntityMob()) //When a mob attacks with a blue fire weapon, the target is lit on blue fire
                .put("net.minecraft.entity.monster.EntitySnowman", new PluginEntitySnowman()) //Snow golems cannot generate falling snow layers
                .put("net.minecraft.entity.passive.AbstractHorse", new PluginAbstractHorse()) //Lets horses be ridden by holding a carrot on a stick
                .put("net.minecraft.entity.player.EntityPlayer", new PluginEntityPlayer()) //Plays the fire damage sound at the exact moment when the player takes damage from blue fire
                .put("net.minecraft.entity.Entity", new PluginEntity()) //Fix MC-1691
                .put("net.minecraft.entity.EntityLivingBase", new PluginEntityLivingBase()) //Fix MC-1691 & fix fire damage sound
                .put("net.minecraft.item.Item", new PluginItem()) //Overrides the totem of undying item
                .put("net.minecraft.item.ItemSnow", new PluginItemSnow()) //Fix snow layer placement check for snow layers & snow grass
                .put("net.minecraft.tileentity.TileEntityBeacon", new PluginTileEntityBeacon()) //Backport the vanilla 1.13+ beacon sounds
                .put("net.minecraft.world.biome.BiomeColorHelper", new PluginBiomeColorHelper()) //Add 1.13+ biome color blend slider functionality
                .put("net.minecraft.world.gen.feature.WorldGenLakes", new PluginWorldGenLakes()) //Add magma surrounding lava lakes
                .put("net.minecraft.world.gen.structure.StructureComponent", new PluginStructureComponent()) //Generate cracked nether bricks with nether fortresses
                .put("net.minecraft.world.World", new PluginWorld()) //Implement IHasWorldState functionality
                .build();

        @Nonnull
        @Override
        public byte[] transform(@Nonnull String name, @Nonnull String transformedName, @Nonnull byte[] basicClass) {
            final @Nullable IASMPlugin plugin = PLUGINS.get(transformedName);
            return plugin == null ? basicClass : plugin.transform(basicClass, !FMLLaunchHandler.isDeobfuscatedEnvironment());
        }
    }

    @Nonnull
    @Override
    public String[] getASMTransformerClass() {
        return new String[] {"git.jbredwards.njarm.asm.ASMHandler$Transformer"};
    }

    @Nullable
    @Override
    public String getModContainerClass() { return null; }

    @Nullable
    @Override
    public String getSetupClass() { return null; }

    @Override
    public void injectData(@Nonnull Map<String, Object> map) { }

    @Nullable
    @Override
    public String getAccessTransformerClass() { return null; }
}
