package net.diamonddev.ddvorigins.impl;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.ddvorigins.cca.DoubleComponent;
import net.minecraft.entity.LivingEntity;

import java.util.function.Function;

public class CCAEntityInitializerImpl implements EntityComponentInitializer {

    public static final ComponentKey<DoubleComponent> GRAVITY_MODIFIER = ComponentRegistryV3.INSTANCE.getOrCreate(DDVOrigins.id("gravity_modifier"), DoubleComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, GRAVITY_MODIFIER, livingEntity -> new DoubleComponent(LivingEntity.GRAVITY, "value"));
    }

    public static class GravityModifierManager {
        public static void setGravity(LivingEntity living, double d) {
            GRAVITY_MODIFIER.get(living).write(d);
        }

        public static double getGravity(LivingEntity living) {
            return GRAVITY_MODIFIER.get(living).read();
        }

        public static void modifyGravity(LivingEntity living, Function<Double, Double> modifyFunction) {
            GRAVITY_MODIFIER.get(living).modify(modifyFunction);
        }

        public static void resetGravity(LivingEntity living) {
            GRAVITY_MODIFIER.get(living).write(GRAVITY_MODIFIER.get(living).getDefault());
        }
    }
}
