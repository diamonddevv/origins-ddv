package net.diamonddev.ddvorigins.effect;

import net.diamonddev.ddvorigins.cca.ChronokinesisComponent;
import net.diamonddev.ddvorigins.impl.CCAEntityInitializerImpl;
import net.diamonddev.ddvorigins.network.Netcode;
import net.diamonddev.ddvorigins.network.SendChronokineticUserData;
import net.diamonddev.ddvorigins.registry.InitEffects;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.function.Consumer;

public class ChronokineticEffect extends StatusEffect {
    private static final double FACTOR = 1.5;

    public ChronokineticEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xEFDE5F); // https://colornames.org/color/efde5f
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MathHelper.randomUuid().toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, MathHelper.randomUuid().toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_FLYING_SPEED, MathHelper.randomUuid().toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, MathHelper.randomUuid().toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, MathHelper.randomUuid().toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, MathHelper.randomUuid().toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);

        Vec3d origin = entity.getPos();
        ChronokinesisComponent.ChronokinesisComponentData data = new ChronokinesisComponent.ChronokinesisComponentData(origin);
        CCAEntityInitializerImpl.ChronokinesisManager.set(entity, data);

        if (entity.getWorld().getServer() != null) {
            var instance = entity.getStatusEffect(InitEffects.CHRONOKINETIC);
            if (instance != null) {
                int duration = instance.getDuration();

                entity.getWorld().getServer().getPlayerManager().getPlayerList().forEach((p) ->
                        NerveNetworker.send(p, Netcode.SEND_CHRONOKINETIC_USER_DATA,
                                new SendChronokineticUserData.ChronokineticUserData(true, data.origin(), duration, entity.getUuid())));
            }
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        ChronokinesisComponent.ChronokinesisComponentData data = CCAEntityInitializerImpl.ChronokinesisManager.get(entity);
        if (data.allPresent()) entity.teleport(data.origin().x, data.origin().y, data.origin().z);

        if (entity.getWorld().getServer() != null) entity.getWorld().getServer().getPlayerManager().getPlayerList().forEach(p -> {
            NerveNetworker.send(p, Netcode.SEND_CHRONOKINETIC_USER_DATA,
                    new SendChronokineticUserData.ChronokineticUserData(false, data.origin(), 0, entity.getUuid()));
        });

        CCAEntityInitializerImpl.ChronokinesisManager.reset(entity);
    }

    public static void rayFromEndToOrigin(Vec3d origin, Vec3d end, Consumer<Vec3d> step) {
        Vec3d direction = origin.subtract(end).normalize();
        double length = end.distanceTo(origin);
        for(double current = 0; current <= length; current += .125) {
            Vec3d pos = end.add(direction.multiply(current));
            step.accept(pos);
        }
    }
}
