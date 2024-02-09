package dev.diamond.ddvorigins.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class Util {
    public static void chorusWarp(LivingEntity user, float radius, boolean keepSafe, float attemptsToTeleport, float chance) {

        Random random = new Random(user.age);
        if (random.nextFloat() <= chance) {

            if (keepSafe) { // ordinary chorus fruit code
                double d = user.getX();
                double e = user.getY();
                double f = user.getZ();

                for (int i = 0; i < attemptsToTeleport; ++i) {
                    double g = user.getX() + (user.getRandom().nextDouble() - 0.5) * radius;
                    double h = MathHelper.clamp(user.getY() + (double) (user.getRandom().nextInt(16) - 8), (double) user.getWorld().getBottomY(), (double) (user.getWorld().getBottomY() + ((ServerWorld) user.getWorld()).getLogicalHeight() - 1));
                    double j = user.getZ() + (user.getRandom().nextDouble() - 0.5) * radius;
                    if (user.hasVehicle()) {
                        user.stopRiding();
                    }

                    Vec3d vec3d = user.getPos();
                    if (user.teleport(g, h, j, true)) {
                        user.getWorld().emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(user));
                        SoundEvent soundEvent = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                        user.getWorld().playSound((PlayerEntity) null, d, e, f, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        user.playSound(soundEvent, 1.0F, 1.0F);
                        break;
                    }
                }
            } else {

                for (int i = 0; i < attemptsToTeleport; i++) {
                    double x = user.getX() + (user.getRandom().nextDouble() - 0.5) * radius;
                    double y = user.getY() + (user.getRandom().nextDouble() - 0.5) * radius;
                    double z = user.getZ() + (user.getRandom().nextDouble() - 0.5) * radius;

                    if (user.hasVehicle()) {
                        user.stopRiding();
                    }

                    Vec3d pos = user.getPos();
                    if (user.teleport(x, y, z, true)) {
                        user.getWorld().emitGameEvent(GameEvent.TELEPORT, pos, GameEvent.Emitter.of(user));

                        SoundEvent soundEvent = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                        user.getWorld().playSound(null, pos.x, pos.y, pos.z, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        user.playSound(soundEvent, 1.0F, 1.0F);

                        break;
                    }
                }
            }
        }
    }




    public static <T> T test(T t) {
        System.out.println(t);
        return t;
    }
}
