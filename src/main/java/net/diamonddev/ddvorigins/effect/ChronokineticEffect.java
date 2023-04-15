package net.diamonddev.ddvorigins.effect;

import net.diamonddev.ddvorigins.cca.ChronokinesisComponent;
import net.diamonddev.ddvorigins.impl.CCAEntityInitializerImpl;
import net.diamonddev.ddvorigins.util.FXUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.function.Consumer;

public class ChronokineticEffect extends StatusEffect {
    public ChronokineticEffect() {
        super(StatusEffectCategory.NEUTRAL, 0x8ba5c4);
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MathHelper.randomUuid().toString(), 1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, MathHelper.randomUuid().toString(), 1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_FLYING_SPEED, MathHelper.randomUuid().toString(), 1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }



    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        ChronokinesisComponent.ChronokinesisComponentData data = CCAEntityInitializerImpl.ChronokinesisManager.get(entity);
        if (data.allPresent()) {
            FXUtil.spawnParticles(new FXUtil.ParticlesData<>(ParticleTypes.END_ROD, true, data.origin().x, data.origin().y + 2, data.origin().z, 0, 1, 0, 0.1f, 25), entity.world);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        ChronokinesisComponent.ChronokinesisComponentData data = CCAEntityInitializerImpl.ChronokinesisManager.get(entity);
        if (data.allPresent()) {
            rayFromEndToOrigin(data.origin().add(0,.5,0), entity.getPos().add(0,.5,0), (vec) -> {
                BlockPos pos = new BlockPos(vec);
                FXUtil.spawnParticles(new FXUtil.ParticlesData<>(ParticleTypes.ENCHANTED_HIT, true, pos.toCenterPos().getX(), pos.toCenterPos().getY(), pos.toCenterPos().getZ(), 0f, 0f, 0f, 0f, 5), entity.world);
            });
            entity.teleport(data.origin().x, data.origin().y, data.origin().z);
        }
        CCAEntityInitializerImpl.ChronokinesisManager.reset(entity);
    }

    private static void rayFromEndToOrigin(Vec3d origin, Vec3d end, Consumer<Vec3d> step) {
        Vec3d direction = origin.subtract(end).normalize();
        double length = end.distanceTo(origin);
        for(double current = 0; current <= length; current += .125) {
            Vec3d pos = end.add(direction.multiply(current));
            step.accept(pos);
        }
    }
}