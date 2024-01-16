package dev.diamond.ddvorigins.mixin;

import dev.diamond.ddvorigins.effect.AbysmalIntoxicationEffect;
import dev.diamond.ddvorigins.item.VoidFruitItem;
import dev.diamond.ddvorigins.power.type.VoidFruitEatingPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import dev.diamond.ddvorigins.power.type.ConstantStandardVelocityModifierPower;
import dev.diamond.ddvorigins.registry.InitDamageSources;
import dev.diamond.ddvorigins.registry.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static dev.diamond.ddvorigins.util.Util.test;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {


    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void ddvorigins$setDamageTimeWhenChronokinetic(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!(source.isOf(InitDamageSources.VAI_TIME))) {
            if (this.hasStatusEffect(InitEffects.CHRONOKINETIC)) {
                this.damage(InitDamageSources.get(this, InitDamageSources.VAI_TIME, source.getSource(), source.getAttacker()), amount * 1.05f);
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "eatFood", at = @At("HEAD"), cancellable = true)
    private void ddvorigins$addAbysmalIntoxication(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.getItem() instanceof VoidFruitItem) return;

        if (stack.getItem().isFood()) {
            if (PowerHolderComponent.hasPower(this, VoidFruitEatingPower.class)) {

                List<VoidFruitEatingPower> powers = PowerHolderComponent.getPowers(this, VoidFruitEatingPower.class);

                Identifier foodId = Registries.ITEM.getId(stack.getItem());
                boolean whitelisted = false;
                for (VoidFruitEatingPower power : powers)
                    for (Identifier id : power.getWhitelistedFoods()) {
                        if (whitelisted) break;

                        if (id.equals(foodId)) {
                            whitelisted = true;
                            break;
                        }
                    }

                if (!whitelisted) {
                    int duration = AbysmalIntoxicationEffect.calculateLengthFood(stack.getItem()) * 20;
                    this.addStatusEffect(new StatusEffectInstance(InitEffects.ABYSMAL_INTOXICATION, duration, 0));
                    cir.setReturnValue(stack);
                }

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
