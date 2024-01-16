package dev.diamond.ddvorigins.registry;

import dev.diamond.ddvorigins.DDVOrigins;
import dev.diamond.ddvorigins.effect.AbysmalIntoxicationEffect;
import dev.diamond.ddvorigins.effect.ChronokineticEffect;
import dev.diamond.ddvorigins.effect.VoidStungEffect;
import dev.diamond.ddvorigins.effect.WormholesBlessingEffect;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitEffects implements RegistryInitializer {

    public static final ChronokineticEffect CHRONOKINETIC = new ChronokineticEffect();
    public static final AbysmalIntoxicationEffect ABYSMAL_INTOXICATION = new AbysmalIntoxicationEffect();
    public static final WormholesBlessingEffect WORMHOLES_BLESSING = new WormholesBlessingEffect();
    public static final VoidStungEffect VOID_STUNG = new VoidStungEffect();

    @Override
    public void register() {
        Registry.register(Registries.STATUS_EFFECT, DDVOrigins.id("chronokinetic"), CHRONOKINETIC);
        Registry.register(Registries.STATUS_EFFECT, DDVOrigins.id("abysmal_intoxication"), ABYSMAL_INTOXICATION);
        Registry.register(Registries.STATUS_EFFECT, DDVOrigins.id("wormholes_blessing"), WORMHOLES_BLESSING);
        Registry.register(Registries.STATUS_EFFECT, DDVOrigins.id("void_stung"), VOID_STUNG);
    }
}
