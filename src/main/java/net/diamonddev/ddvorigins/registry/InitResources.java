package net.diamonddev.ddvorigins.registry;

import net.diamonddev.ddvorigins.resource.ResourceListener;
import net.diamonddev.ddvorigins.resource.WeightedLayersType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionRegistry;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitResources implements RegistryInitializer {

    // LISTENERS
    public static final ResourceListener ROOT_RESOURCE_LISTENER = new ResourceListener();

    // TYPES
    public static final WeightedLayersType WEIGHTED_LAYERS_TYPE = new WeightedLayersType();

    @Override
    public void register() {
        CognitionRegistry.registerListener(ROOT_RESOURCE_LISTENER);
        ROOT_RESOURCE_LISTENER.getManager().registerType(WEIGHTED_LAYERS_TYPE);
    }
}
