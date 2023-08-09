package net.diamonddev.ddvorigins.mixin;

import net.diamonddev.ddvorigins.registry.InitDamageSources;
import net.diamonddev.ddvorigins.registry.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {


    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void ddvorigins$setDamageTimeWhenChronokinetic(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!(source.isOf(InitDamageSources.VAI_TIME))) {
            if (this.hasStatusEffect(InitEffects.CHRONOKINETIC)) {
                this.damage(InitDamageSources.get(this, InitDamageSources.VAI_TIME, source.getSource(), source.getAttacker()), amount);
                cir.setReturnValue(false);
            }
        }
    }
}
