package dev.diamond.ddvorigins.power.type;

import dev.diamond.ddvorigins.DDVOrigins;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class VoidspawnPower extends Power {

    public VoidspawnPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }


    @SuppressWarnings("unchecked")
    public static PowerFactory<VoidspawnPower> getFactory() {
        return Power.createSimpleFactory(VoidspawnPower::new, DDVOrigins.id("voidspawn"));
    }
}
