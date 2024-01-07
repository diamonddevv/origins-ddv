package dev.diamond.ddvorigins.power.action.entity;

import dev.diamond.ddvorigins.DDVOrigins;
import dev.diamond.ddvorigins.util.Util;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ChorusWarpEntityAction {
    private static void action(SerializableData.Instance data, Entity entity) {
        if (entity instanceof LivingEntity living) {
            int damageRecordSize = living.getDamageTracker().recentDamage.size();
            float recentDamageTaken;
            if (damageRecordSize == 0) {
                recentDamageTaken = 0;
            } else {
                recentDamageTaken = living.getDamageTracker().recentDamage.get(damageRecordSize - 1).damage(); // get most recent damage amount
            }

            Util.chorusWarp(living, data.getFloat("radius"), data.getBoolean("keep_safe"), 16,
                    data.getBoolean("damage_based_chance") ? calculateChance(recentDamageTaken) : data.getFloat("chance"));
        }
    }

    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(DDVOrigins.id("chorus_warp"),
                new SerializableData()
                        .add("radius", SerializableDataTypes.FLOAT, 16f)
                        .add("keep_safe", SerializableDataTypes.BOOLEAN, true)
                        .add("damage_based_chance", SerializableDataTypes.BOOLEAN, false)
                        .add("chance", SerializableDataTypes.FLOAT, 100f),
                ChorusWarpEntityAction::action
        );
    }

    public static float calculateChance(float damageTaken) {
        return (float)Math.pow(2, damageTaken)/100;
    }
}
