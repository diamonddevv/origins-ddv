package net.diamonddev.ddvorigins.registry;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.diamonddev.ddvorigins.power.type.PreventFallFlyingPower;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;

public class InitEventCallbacks implements RegistryInitializer {
    @Override
    public void register() {
        EntityElytraEvents.ALLOW.register((entity -> PowerHolderComponent.hasPower(entity, PreventFallFlyingPower.class)));
    }
}
