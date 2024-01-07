package dev.diamond.ddvorigins.power.type;

import dev.diamond.ddvorigins.DDVOrigins;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class ConstantStandardVelocityModifierPower extends Power {
    private final Vec3d multiplier;

    public ConstantStandardVelocityModifierPower(PowerType<?> type, LivingEntity entity, Vec3d multiplier) {
        super(type, entity);
        this.multiplier = multiplier;
    }

    public Vec3d getMultiplier() {
        return multiplier;
    }

    public static PowerFactory<ConstantStandardVelocityModifierPower> getFactory() {
        return new PowerFactory<>(
                DDVOrigins.id("constant_standard_velocity_modifier"),
                new SerializableData()
                        .add("vector", SerializableDataTypes.VECTOR),
                (data) -> (power, living) -> new ConstantStandardVelocityModifierPower(power, living, data.get("vector"))
        );
    }
}
