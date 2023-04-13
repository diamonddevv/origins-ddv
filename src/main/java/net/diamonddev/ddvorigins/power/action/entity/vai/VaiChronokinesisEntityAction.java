package net.diamonddev.ddvorigins.power.action.entity.vai;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.ddvorigins.registry.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.Box;

import java.util.List;

public class VaiChronokinesisEntityAction {

    private static void action(SerializableData.Instance data, Entity entity) {
        VaiChronokinesisActionInstance instance = new VaiChronokinesisActionInstance(data, entity, data.getInt("radius"));
        instance.execute();
    }



    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(DDVOrigins.id("vai_chronokinesis"),
                new SerializableData().add("radius", SerializableDataTypes.INT),
                VaiChronokinesisEntityAction::action
        );
    }


    private record VaiChronokinesisActionInstance(SerializableData.Instance data, Entity entity, int radius) {

        public void execute() {
            if (entity instanceof LivingEntity living) living.addStatusEffect(new StatusEffectInstance(InitEffects.ACCELERATION, 200, 0));
            getLivingInRadius(radius, entity).forEach(other -> {
                    if (other instanceof LivingEntity living) {
                        living.addStatusEffect(new StatusEffectInstance(InitEffects.DECELERATION, 200, 0), entity);
                    }
                });
            }

            private List<Entity> getLivingInRadius(int radius, Entity entity) {
                return entity.world.getOtherEntities(entity, new Box(entity.getPos().subtract(radius, radius, radius), entity.getPos().add(radius, radius, radius)), (other) -> other instanceof LivingEntity);
            }
        }
}
