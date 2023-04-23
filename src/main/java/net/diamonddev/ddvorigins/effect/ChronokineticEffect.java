package net.diamonddev.ddvorigins.effect;

import net.diamonddev.ddvorigins.cca.ChronokinesisComponent;
import net.diamonddev.ddvorigins.impl.CCAEntityInitializerImpl;
import net.diamonddev.ddvorigins.registry.InitParticles;
import net.diamonddev.ddvorigins.util.FXUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

import java.util.function.Consumer;

public class ChronokineticEffect extends StatusEffect {
    public ChronokineticEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xEFDE5F); // https://colornames.org/color/efde5f
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MathHelper.randomUuid().toString(), 1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, MathHelper.randomUuid().toString(), 1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_FLYING_SPEED, MathHelper.randomUuid().toString(), 1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, MathHelper.randomUuid().toString(), 1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, MathHelper.randomUuid().toString(), 1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, MathHelper.randomUuid().toString(), 1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }




    public static void tick(LivingEntity entity) {
        ChronokinesisComponent.ChronokinesisComponentData data = CCAEntityInitializerImpl.ChronokinesisManager.get(entity);
        if (data.allPresent()) {
            FXUtil.spawnParticles(new FXUtil.ParticlesData<>(ParticleTypes.END_ROD, true, data.origin().x, data.origin().y + 2, data.origin().z, 0f, 0.5f, 0f, 0f, 25), entity.world);
        }

        if (!(entity instanceof ServerPlayerEntity player) || player.interactionManager.getGameMode() != GameMode.SPECTATOR) {
            FXUtil.spawnParticles(new FXUtil.ParticlesData<>(InitParticles.CLOCK, true, entity.getPos().x, entity.getPos().y + .5, entity.getPos().z, 0f, 0f, 0f, 0f, 3), entity.world);
        }
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);

        Vec3d origin = entity.getPos();
        ChronokinesisComponent.ChronokinesisComponentData data = new ChronokinesisComponent.ChronokinesisComponentData(origin);
        CCAEntityInitializerImpl.ChronokinesisManager.set(entity, data);
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
