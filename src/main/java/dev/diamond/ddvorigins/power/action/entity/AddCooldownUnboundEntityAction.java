package dev.diamond.ddvorigins.power.action.entity;

import dev.diamond.ddvorigins.DDVOrigins;
import dev.diamond.ddvorigins.mixin.CooldownPowerAccessor;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.CooldownPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class AddCooldownUnboundEntityAction {
    private static void action(SerializableData.Instance data, Entity entity) {
        if (entity instanceof LivingEntity) {
            PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);
            PowerType<?> powerType = data.get("resource");
            Power p = component.getPower(powerType);

            int change = data.getInt("add");

            if (p instanceof CooldownPower cp) {

                CooldownPowerAccessor accessed = (CooldownPowerAccessor)cp;


                accessed.setLastUseTime(accessed.getLastUseTime() + change);

                PowerHolderComponent.syncPower(entity, powerType);
            }
        }
    }

    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(DDVOrigins.id("add_cooldown_unbound"),
                new SerializableData()
                        .add("resource", ApoliDataTypes.POWER_TYPE)
                        .add("add", SerializableDataTypes.INT),
                AddCooldownUnboundEntityAction::action
        );
    }
}
