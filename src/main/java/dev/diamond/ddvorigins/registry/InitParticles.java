package dev.diamond.ddvorigins.registry;

import dev.diamond.ddvorigins.DDVOrigins;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitParticles implements RegistryInitializer {

    public static final DefaultParticleType CLOCK = FabricParticleTypes.simple();

    @Override
    public void register() {
        Registry.register(Registries.PARTICLE_TYPE, DDVOrigins.id("clock"), CLOCK);
    }
}
