package dev.diamond.ddvorigins.registry;

import dev.diamond.ddvorigins.DDVOrigins;
import dev.diamond.ddvorigins.effect.ChronokineticEffect;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitEffects implements RegistryInitializer {

    public static final ChronokineticEffect CHRONOKINETIC = new ChronokineticEffect();

    @Override
    public void register() {
        Registry.register(Registries.STATUS_EFFECT, DDVOrigins.id("chronokinetic"), CHRONOKINETIC);
    }
}
