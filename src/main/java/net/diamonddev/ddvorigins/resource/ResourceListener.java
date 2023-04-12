package net.diamonddev.ddvorigins.resource;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.origin.OriginLayers;
import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class ResourceListener extends CognitionDataListener {

    public ResourceListener() {
        super("DDV Origins Resources", DDVOrigins.id("res"), "ddv_origins_data", ResourceType.SERVER_DATA);
    }

    @Override
    public void onReloadForEachResource(CognitionDataResource resource, Identifier path) {
        if (resource.getType() instanceof WeightedLayersType) {
            WeightedLayersType.Serialized serialized = resource.getAsClass(WeightedLayersType.Serialized.class);
            serialized.weights.forEach(weight -> {
                WeightedLayersType.WEIGHTMAP.add(OriginLayers.getLayer(new Identifier(weight.key)), weight.weight);
            });
        }
    }

    @Override
    public void onFinishReload() {

    }

    @Override
    public void onClearCachePhase() {
        WeightedLayersType.WEIGHTMAP.clear();
    }

    @Override
    public Function<CognitionDataResource, Boolean> forEachShouldExclude() {
        return (resource -> {
            if (resource.getType() instanceof WeightedLayersType) {
                return !resource.getId().getPath().matches(DDVOrigins.modid);
            }

            return false;
        });
    }

    @Override
    public Collection<Identifier> getFabricDependencies() {
        return List.of(Origins.identifier("origin_layers"));
    }
}
