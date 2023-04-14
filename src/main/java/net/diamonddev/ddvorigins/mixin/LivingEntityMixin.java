package net.diamonddev.ddvorigins.mixin;

import net.diamonddev.ddvorigins.impl.CCAEntityInitializerImpl;
import net.diamonddev.ddvorigins.registry.InitDamageSources;
import net.diamonddev.ddvorigins.registry.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract void readCustomDataFromNbt(NbtCompound nbt);


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyArg(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;travel(Lnet/minecraft/util/math/Vec3d;)V"))
    private Vec3d ddvorigins$doubleTimeHalfTimeMovement(Vec3d movementInput) {
        if (this.hasStatusEffect(InitEffects.ACCELERATION)) return movementInput.multiply(4);
        if (this.hasStatusEffect(InitEffects.DECELERATION)) return movementInput.multiply(0.25);
        return movementInput;
    }

    @ModifyVariable(method = "travel", at = @At("STORE"))
    private double ddvorigins$doubleTimeHalfTimeFallSpeed(double d) {
        if (!this.isOnGround()) return CCAEntityInitializerImpl.GravityModifierManager.getGravity((LivingEntity)(Object)this);
        else return d;
    }


    @Inject(method = "tick", at = @At("HEAD"))
    private void ddvorigins$tickEffects(CallbackInfo ci) {
        if (this.hasStatusEffect(InitEffects.ACCELERATION)) {
            this.damage(InitDamageSources.TimeDamageSource.create(null), 0.25f); // quarter heart per tick time damage
            if ((LivingEntity)(Object)this instanceof PlayerEntity player) player.addExhaustion(0.125f);  // quarter shank per tick exhaustion. there isnt exhaustion sources
        }

        if (this.hasStatusEffect(InitEffects.DECELERATION)) {
            this.damage(InitDamageSources.TimeDamageSource.create(null), 0.25f); // quarter heart per tick time damage
        }
    }}
