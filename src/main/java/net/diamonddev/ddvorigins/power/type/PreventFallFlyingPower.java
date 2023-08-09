package net.diamonddev.ddvorigins.power.type;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import net.diamonddev.ddvorigins.DDVOrigins;
import net.minecraft.entity.LivingEntity;

public class PreventFallFlyingPower extends Power {

    public PreventFallFlyingPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    public static PowerFactory<PreventFallFlyingPower> getFactory() {
        return Power.createSimpleFactory(PreventFallFlyingPower::new, DDVOrigins.id("prevent_fall_flying"));
    }

}
