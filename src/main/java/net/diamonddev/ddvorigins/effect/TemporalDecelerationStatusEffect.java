package net.diamonddev.ddvorigins.effect;

import net.diamonddev.ddvorigins.impl.CCAEntityInitializerImpl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.MathHelper;

public class TemporalDecelerationStatusEffect extends StatusEffect {
    public TemporalDecelerationStatusEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xcbd1b3);
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, String.valueOf(MathHelper.randomUuid()), -0.20000000298023224 * 5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, String.valueOf(MathHelper.randomUuid()), -0.10000000149011612 * 5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_FLYING_SPEED, String.valueOf(MathHelper.randomUuid()), -0.20000000298023224 * 5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        CCAEntityInitializerImpl.GravityModifierManager.modifyGravity(entity, old -> old / 2);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        CCAEntityInitializerImpl.GravityModifierManager.resetGravity(entity);
    }
}
