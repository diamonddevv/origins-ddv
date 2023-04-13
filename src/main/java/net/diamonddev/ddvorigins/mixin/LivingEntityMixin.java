package net.diamonddev.ddvorigins.mixin;

import net.diamonddev.ddvorigins.impl.CCAEntityInitializerImpl;
import net.diamonddev.ddvorigins.registry.InitDamageSources;
import net.diamonddev.ddvorigins.registry.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract double getAttributeValue(EntityAttribute attribute);

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyConstant(method = "travel", constant = @Constant(doubleValue = 0.08))
    private double ddvorigins$setGravityWithModifier(double d) {
        return CCAEntityInitializerImpl.GravityModifierManager.getGravity((LivingEntity)(Object)this);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void ddvorigins$tickEffects(CallbackInfo ci) {
        if (this.hasStatusEffect(InitEffects.ACCELERATION)) {
            this.damage(InitDamageSources.TIME, 0.25f); // quarter heart per tick time damage
            if ((LivingEntity)(Object)this instanceof PlayerEntity player) player.addExhaustion(0.125f);  // quarter shank per tick exhaustion. there isnt exhaustion sources
        }

        if (this.hasStatusEffect(InitEffects.DECELERATION)) {

        }
    }}
