package net.diamonddev.ddvorigins.effect;

import net.diamonddev.ddvorigins.impl.CCAEntityInitializerImpl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class TemporalDecelerationStatusEffect extends StatusEffect {
    public TemporalDecelerationStatusEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xcbd1b3);
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
