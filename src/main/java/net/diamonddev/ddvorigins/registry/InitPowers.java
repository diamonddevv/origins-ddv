package net.diamonddev.ddvorigins.registry;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.ddvorigins.power.action.entity.vai.VaiRelocateEntityAction;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registry;

public class InitPowers implements RegistryInitializer {
    @Override
    public void register() {
        // Powers

        // Actions
        registerEntityAction(VaiRelocateEntityAction.getFactory());

        // Conditions
        registerEntityCondition(new ConditionFactory<>(
                DDVOrigins.id("is_living"),
                new SerializableData(),
                (instance, entity) -> entity instanceof LivingEntity
        ));
    }

    private static void registerPower(PowerFactory<?> factory) {
        Registry.register(ApoliRegistries.POWER_FACTORY, factory.getSerializerId(), factory);
    }
    private static void registerEntityAction(ActionFactory<Entity> factory) {
        Registry.register(ApoliRegistries.ENTITY_ACTION, factory.getSerializerId(), factory);
    }
    private static void registerEntityCondition(ConditionFactory<Entity> factory) {
        Registry.register(ApoliRegistries.ENTITY_CONDITION, factory.getSerializerId(), factory);
    }
}
