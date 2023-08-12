package net.diamonddev.ddvorigins.registry;

import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.sound.SoundEvent;

public class InitSoundEvents implements RegistryInitializer {

    public static SoundEvent
            VAI_CHRONOKINESIS_END, VAI_RELOCATE_ORIGIN, VAI_RELOCATE_END, VAI_RELOCATE_AMPLIFY, VAI_RELOCATE_CANCEL;

    public static SoundEvent
            SKYKIN_FLY;

    @Override
    public void register() {
        VAI_CHRONOKINESIS_END = create("vai.chronokinesis.end");

        VAI_RELOCATE_ORIGIN = create("vai.relocate.origin");
        VAI_RELOCATE_END = create("vai.relocate.end");
        VAI_RELOCATE_AMPLIFY = create("vai.relocate.amplify");
        VAI_RELOCATE_CANCEL = create("vai.relocate.cancel");


        SKYKIN_FLY = create("skykin.fly");
    }

    private static SoundEvent create(String key) {
        return SoundEvent.of(DDVOrigins.id(key));
    }
}
