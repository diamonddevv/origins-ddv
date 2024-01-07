package dev.diamond.ddvorigins.impl;

import dev.diamond.ddvorigins.DDVOrigins;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.diamond.ddvorigins.cca.ChronokinesisComponent;
import net.minecraft.entity.LivingEntity;

public class CCAEntityInitializerImpl implements EntityComponentInitializer {

    private static final ComponentKey<ChronokinesisComponent> CHRONOKINESIS = ComponentRegistryV3.INSTANCE.getOrCreate(DDVOrigins.id("chronokinesis"), ChronokinesisComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, CHRONOKINESIS, (living -> new ChronokinesisComponent("chronokinesis_data")));
    }

    public static class ChronokinesisManager {
        public static ChronokinesisComponent.ChronokinesisComponentData get(LivingEntity living) {
            return CHRONOKINESIS.get(living).read();
        }

        public static void set(LivingEntity living, ChronokinesisComponent.ChronokinesisComponentData data) {
            CHRONOKINESIS.get(living).write(data);
        }

        public static void reset(LivingEntity living) {
            set(living, ChronokinesisComponent.EMPTY);
        }
    }

}
