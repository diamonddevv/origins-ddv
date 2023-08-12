package net.diamonddev.ddvorigins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.diamonddev.ddvorigins.power.type.ConstantStandardVelocityModifierPower;
import net.diamonddev.ddvorigins.registry.InitDamageSources;
import net.diamonddev.ddvorigins.registry.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.concurrent.atomic.AtomicReference;

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

    @ModifyArgs(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V", ordinal = 2)
    )
    private void ddvorigins$modifyVelocityNoDrag(Args args) {
        if (PowerHolderComponent.hasPower(this, ConstantStandardVelocityModifierPower.class)) {
            Vec3d vec = multiplyVelocity(args);
            args.setAll(vec.x, vec.y, vec.z);
        }
    }

    @ModifyArgs(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V", ordinal = 3)
    )
    private void ddvorigins$modifyVelocity(Args args) {
        if (PowerHolderComponent.hasPower(this, ConstantStandardVelocityModifierPower.class)) {
            Vec3d vec = multiplyVelocity(args);
            args.setAll(vec.x, vec.y, vec.z);
        }
    }

    private Vec3d multiplyVelocity(Args args) {
        Vec3d vec = new Vec3d(args.get(0), args.get(1), args.get(2));
        AtomicReference<Vec3d> m = new AtomicReference<>();
        PowerHolderComponent.withPower(this, ConstantStandardVelocityModifierPower.class, null,
                (power -> m.set(power.getMultiplier())));
        return vec.multiply(m.get());
    }
}
