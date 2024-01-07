package dev.diamond.ddvorigins.power.type;

import dev.diamond.ddvorigins.DDVOrigins;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import net.minecraft.entity.LivingEntity;

public class PreventWaterAmbientSoundsPower extends Power {
    public PreventWaterAmbientSoundsPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    @SuppressWarnings("unchecked")
    public static PowerFactory<AerialAffinityPower> getFactory() {
        return Power.createSimpleFactory(PreventWaterAmbientSoundsPower::new, DDVOrigins.id("prevent_water_ambience"));
    }
}
