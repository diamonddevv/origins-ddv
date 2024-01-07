package dev.diamond.ddvorigins.power.type;

import dev.diamond.ddvorigins.DDVOrigins;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import net.minecraft.entity.LivingEntity;

public class AerialAffinityPower extends Power {

    public AerialAffinityPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    @SuppressWarnings("unchecked")
    public static PowerFactory<AerialAffinityPower> getFactory() {
        return Power.createSimpleFactory(AerialAffinityPower::new, DDVOrigins.id("aerial_affinity"));
    }

}
