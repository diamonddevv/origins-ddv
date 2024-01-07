package dev.diamond.ddvorigins.registry;

import dev.diamond.ddvorigins.DDVOrigins;
import dev.diamond.ddvorigins.power.action.entity.AddCooldownUnboundEntityAction;
import dev.diamond.ddvorigins.power.action.entity.ChorusWarpEntityAction;
import dev.diamond.ddvorigins.power.action.entity.SendHudIconEntityAction;
import dev.diamond.ddvorigins.power.badge.TagBadge;
import dev.diamond.ddvorigins.power.type.PreventWaterAmbientSoundsPower;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import dev.diamond.ddvorigins.power.action.entity.AtomicRelocateEntityAction;
import dev.diamond.ddvorigins.power.type.AerialAffinityPower;
import dev.diamond.ddvorigins.power.type.ConstantStandardVelocityModifierPower;
import io.github.apace100.origins.badge.BadgeFactory;
import io.github.apace100.origins.badge.BadgeManager;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registry;

public class InitPowers implements RegistryInitializer {

    @Override
    public void register() {
        // Powers
        registerPower(AerialAffinityPower.getFactory());
        registerPower(ConstantStandardVelocityModifierPower.getFactory());
        registerPower(PreventWaterAmbientSoundsPower.getFactory());

        // Actions
        registerEntityAction(AtomicRelocateEntityAction.getFactory());
        registerEntityAction(SendHudIconEntityAction.getFactory());
        registerEntityAction(ChorusWarpEntityAction.getFactory());
        registerEntityAction(AddCooldownUnboundEntityAction.getFactory());

        // Conditions
        registerEntityCondition(new ConditionFactory<>(
                DDVOrigins.id("is_living"),
                new SerializableData(),
                (instance, entity) -> entity instanceof LivingEntity
        ));

        // Badge Types
        registerPowerBadge(TagBadge.buildFactory(TagBadge.TagType.Block));
        registerPowerBadge(TagBadge.buildFactory(TagBadge.TagType.Item));
        registerPowerBadge(TagBadge.buildFactory(TagBadge.TagType.EntityType));
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
    private static void registerPowerBadge(BadgeFactory badge) {
        BadgeManager.REGISTRY.registerFactory(badge.id(), badge);
    }
}
