package net.diamonddev.ddvorigins.power.action.entity.vai;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.ddvorigins.cca.ChronokinesisComponent;
import net.diamonddev.ddvorigins.effect.ChronokineticEffect;
import net.diamonddev.ddvorigins.impl.CCAEntityInitializerImpl;
import net.diamonddev.ddvorigins.registry.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Objects;

public class VaiChronokinesisEntityAction {

    private static void action(SerializableData.Instance data, Entity entity) {
        VaiChronokinesisActionInstance instance = new VaiChronokinesisActionInstance(data, entity, data.getInt("duration") * 20);
        instance.execute();
    }

    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(DDVOrigins.id("vai_chronokinesis"),
                new SerializableData().add("duration", SerializableDataTypes.INT),
                VaiChronokinesisEntityAction::action
        );
    }

    private static final class VaiChronokinesisActionInstance {
        private final SerializableData.Instance data;
        private final Entity entity;
        private final int duration;

        private VaiChronokinesisActionInstance(SerializableData.Instance data, Entity entity, int duration) {
            this.data = data;
            this.entity = entity;
            this.duration = duration;

        }

        public void execute() {
            if (entity instanceof LivingEntity living) {
                living.addStatusEffect(new StatusEffectInstance(InitEffects.CHRONOKINETIC, duration, 0));
            }
        }

    }
}
