package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.block.model.MultiLayerModel;
import git.jbredwards.njarm.mod.common.tileentity.*;
import net.darkhax.bookshelf.item.ICustomModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.DataSerializerEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Handles changes made to the vanilla & forge registries
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class RegistryHandler
{
    @SubscribeEvent
    public static void registerBlocks(@Nonnull RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(ModBlocks.INIT.toArray(new Block[0]));
        TileEntity.register(Constants.MODID + ":nether_core", TileEntityNetherCore.class);
    }

    @SubscribeEvent
    public static void registerDataSerializers(@Nonnull RegistryEvent.Register<DataSerializerEntry> event) {
        event.getRegistry().registerAll(ModDataSerializers.INIT.toArray(new DataSerializerEntry[0]));
    }

    @SubscribeEvent
    public static void registerEntities(@Nonnull RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().registerAll(ModEntities.INIT.toArray(new EntityEntry[0]));
    }

    @SubscribeEvent
    public static void registerItems(@Nonnull RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ModItems.INIT.toArray(new Item[0]));
        ModItems.registerOres();
    }

    @SubscribeEvent
    public static void registerPotions(@Nonnull RegistryEvent.Register<Potion> event) {
        event.getRegistry().registerAll(ModPotions.INIT.toArray(new Potion[0]));
    }

    @SubscribeEvent
    public static void registerPotionTypes(@Nonnull RegistryEvent.Register<PotionType> event) {
        event.getRegistry().registerAll(ModPotionTypes.INIT.toArray(new PotionType[0]));
    }

    @SubscribeEvent
    public static void registerSounds(@Nonnull RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(ModSounds.INIT.toArray(new SoundEvent[0]));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerItemModels(@Nullable ModelRegistryEvent event) {
        //blocks
        for(Block block : ModBlocks.INIT)
            if(block instanceof ICustomModel) ((ICustomModel)block).registerMeshModels();

        //items
        for(Item item : ModItems.INIT) {
            if(item instanceof ICustomModel) ((ICustomModel)item).registerMeshModels();
            else ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(String.valueOf(item.getRegistryName()), "inventory"));
        }

        //loaders
        ModelLoaderRegistry.registerLoader(MultiLayerModel.Loader.INSTANCE);
    }
}
