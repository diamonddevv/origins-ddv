package net.diamonddev.ddvorigins.effect;

import net.diamonddev.ddvorigins.impl.CCAEntityInitializerImpl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;


public class TemporalAccelerationStatusEffect extends StatusEffect {
    public TemporalAccelerationStatusEffect() {
        super(StatusEffectCategory.NEUTRAL, 0x8ba5c4);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        CCAEntityInitializerImpl.GravityModifierManager.modifyGravity(entity, old -> old * 2);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        CCAEntityInitializerImpl.GravityModifierManager.resetGravity(entity);
    }
}
