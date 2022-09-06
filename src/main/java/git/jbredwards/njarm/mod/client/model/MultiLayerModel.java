package git.jbredwards.njarm.mod.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import git.jbredwards.njarm.mod.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Same as forge's {@link net.minecraftforge.client.model.MultiLayerModel MultiLayerModel}, but with a better baked model
 * @author jbred
 *
 */
public final class MultiLayerModel implements IModel
{
    @Nonnull
    public static final MultiLayerModel INSTANCE = new MultiLayerModel(ImmutableMap.of());

    @Nonnull
    final ImmutableMap<Optional<BlockRenderLayer>, ModelResourceLocation> models;
    public MultiLayerModel(@Nonnull ImmutableMap<Optional<BlockRenderLayer>, ModelResourceLocation> models) {
        this.models = models;
    }

    @Nonnull
    @Override
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableList.copyOf(models.values());
    }

    @Nonnull
    static ImmutableMap<Optional<BlockRenderLayer>, IBakedModel> buildModels(@Nonnull ImmutableMap<Optional<BlockRenderLayer>, ModelResourceLocation> models, @Nonnull IModelState state, @Nonnull VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        final ImmutableMap.Builder<Optional<BlockRenderLayer>, IBakedModel> builder = ImmutableMap.builder();
        for(Optional<BlockRenderLayer> key : models.keySet()) {
            final IModel model = ModelLoaderRegistry.getModelOrLogError(models.get(key), "Couldn't load MultiLayerModel dependency: " + models.get(key));
            builder.put(key, model.bake(new ModelStateComposition(state, model.getDefaultState()), format, bakedTextureGetter));
        }

        return builder.build();
    }

    @Nonnull
    @Override
    public IBakedModel bake(@Nonnull IModelState state, @Nonnull VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        final IModel missing = ModelLoaderRegistry.getMissingModel();
        return new MultiLayerBakedModel(
                buildModels(models, state, format, bakedTextureGetter),
                missing.bake(missing.getDefaultState(), format, bakedTextureGetter),
                PerspectiveMapWrapper.getTransforms(state)
        );
    }

    @Nonnull
    ModelResourceLocation getLocation(@Nonnull String json) {
        final JsonElement e = new JsonParser().parse(json);
        if(e.isJsonPrimitive() && e.getAsJsonPrimitive().isString())
            return new ModelResourceLocation(e.getAsString());

        FMLLog.log.fatal("Expect ModelResourceLocation, got: {}", json);
        return new ModelResourceLocation("builtin/missing", "missing");
    }

    @Nonnull
    @Override
    public MultiLayerModel process(@Nonnull ImmutableMap<String, String> customData) {
        ImmutableMap.Builder<Optional<BlockRenderLayer>, ModelResourceLocation> builder = ImmutableMap.builder();
        for(String key : customData.keySet()) {
            if("base".equals(key))
                builder.put(Optional.empty(), getLocation(customData.get(key)));

            for(BlockRenderLayer layer : BlockRenderLayer.values()) {
                if(layer.toString().equals(key)) {
                    builder.put(Optional.of(layer), getLocation(customData.get(key)));
                }
            }
        }

        ImmutableMap<Optional<BlockRenderLayer>, ModelResourceLocation> models = builder.build();
        if(models.isEmpty()) return INSTANCE;
        return new MultiLayerModel(models);
    }

    static final class MultiLayerBakedModel implements IBakedModel
    {
        @Nonnull final ImmutableMap<Optional<BlockRenderLayer>, IBakedModel> models;
        @Nonnull final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> cameraTransforms;
        @Nonnull final IBakedModel base;
        @Nonnull final IBakedModel missing;

        public MultiLayerBakedModel(@Nonnull ImmutableMap<Optional<BlockRenderLayer>, IBakedModel> models, @Nonnull IBakedModel missing, @Nonnull ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> cameraTransforms)
        {
            this.models = models;
            this.cameraTransforms = cameraTransforms;
            this.missing = missing;
            this.base = models.getOrDefault(Optional.empty(), missing);
        }

        @Nonnull
        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            final BlockRenderLayer layer = MinecraftForgeClient.getRenderLayer();
            if(layer == null) {
                final ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
                for(IBakedModel model : models.values())
                    builder.addAll(model.getQuads(state, side, rand));

                return builder.build();
            }

            return models.getOrDefault(Optional.of(layer), missing).getQuads(state, side, rand);
        }

        @Override
        public boolean isAmbientOcclusion() { return get().isAmbientOcclusion(); }

        @Override
        public boolean isAmbientOcclusion(@Nonnull IBlockState state) { return get().isAmbientOcclusion(state); }

        @Override
        public boolean isGui3d() { return get().isGui3d(); }

        @Override
        public boolean isBuiltInRenderer() { return get().isBuiltInRenderer(); }

        @Nonnull
        @Override
        public TextureAtlasSprite getParticleTexture() { return get().getParticleTexture(); }

        @Nonnull
        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(@Nonnull ItemCameraTransforms.TransformType cameraTransformType) {
            return PerspectiveMapWrapper.handlePerspective(this, cameraTransforms, cameraTransformType);
        }

        @Nonnull
        @Override
        public ItemOverrideList getOverrides() { return ItemOverrideList.NONE; }

        @Nonnull
        IBakedModel get() {
            final BlockRenderLayer layer = MinecraftForgeClient.getRenderLayer();
            return layer != null ? models.getOrDefault(Optional.of(layer), base) : base;
        }
    }

    public enum Loader implements ICustomModelLoader
    {
        INSTANCE;

        @Override
        public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {}

        @Override
        public boolean accepts(ResourceLocation modelLocation) {
            return modelLocation.getNamespace().equals(Constants.MODID) && (
                    modelLocation.getPath().equals("multi-layer") ||
                            modelLocation.getPath().equals("models/block/multi-layer") ||
                            modelLocation.getPath().equals("models/item/multi-layer"));
        }

        @Nonnull
        @Override
        public IModel loadModel(@Nonnull ResourceLocation modelLocation) { return MultiLayerModel.INSTANCE; }
    }
}
