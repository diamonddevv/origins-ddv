package net.diamonddev.ddvorigins.registry;

import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.ddvorigins.effect.TemporalAccelerationStatusEffect;
import net.diamonddev.ddvorigins.effect.TemporalDecelerationStatusEffect;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitEffects implements RegistryInitializer {

    public static final TemporalAccelerationStatusEffect ACCELERATION = new TemporalAccelerationStatusEffect();
    public static final TemporalDecelerationStatusEffect DECELERATION = new TemporalDecelerationStatusEffect();

    @Override
    public void register() {
        Registry.register(Registries.STATUS_EFFECT, DDVOrigins.id("temporal_acceleration"), ACCELERATION);
        Registry.register(Registries.STATUS_EFFECT, DDVOrigins.id("temporal_deceleration"), DECELERATION);
    }
}
