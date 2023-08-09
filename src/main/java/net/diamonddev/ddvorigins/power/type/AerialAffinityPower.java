package net.diamonddev.ddvorigins.power.type;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import net.diamonddev.ddvorigins.DDVOrigins;
import net.minecraft.entity.LivingEntity;

public class AerialAffinityPower extends Power {

    public AerialAffinityPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    public static PowerFactory<AerialAffinityPower> getFactory() {
        return Power.createSimpleFactory(AerialAffinityPower::new, DDVOrigins.id("aerial_affinity"));
    }

}
