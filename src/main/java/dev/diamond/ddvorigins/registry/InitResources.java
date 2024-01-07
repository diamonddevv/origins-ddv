package dev.diamond.ddvorigins.registry;

import dev.diamond.ddvorigins.resource.ResourceListener;
import dev.diamond.ddvorigins.resource.WeightedLayersType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionRegistry;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitResources implements RegistryInitializer {

    // LISTENERS
    public static final ResourceListener DDVORIGINS_LISTENER = new ResourceListener();

    // TYPES
    public static final WeightedLayersType WEIGHTED_LAYERS_TYPE = new WeightedLayersType();

    @Override
    public void register() {
        CognitionRegistry.registerListener(DDVORIGINS_LISTENER);
        DDVORIGINS_LISTENER.getManager().registerType(WEIGHTED_LAYERS_TYPE);
    }
}
