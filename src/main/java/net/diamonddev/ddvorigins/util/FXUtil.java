package net.diamonddev.ddvorigins.util;

import net.diamonddev.ddvorigins.DDVOrigins;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class FXUtil {
    public record ParticlesData<T extends ParticleEffect>(T parameters, boolean longDistance, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
    }
    public record SoundsData(SoundEvent sound, SoundCategory category, double x, double y, double z, float volume, float pitch) {

    }

    public static void spawnParticles(ParticlesData<?> data, World world) {
        if (data.count == 0) {
            double d = data.speed * data.offsetX;
            double e = data.speed * data.offsetY;
            double f = data.speed * data.offsetZ;

            try {
                world.addParticle(data.parameters, data.longDistance, data.x, data.y, data.z, d, e, f);
            } catch (Throwable var17) {
                DDVOrigins.LOGGER.warn("Could not spawn particle effect {}", data.parameters);
            }
        } else {
            for(int i = 0; i < data.count; ++i) {
                double g = world.random.nextGaussian() * data.offsetX;
                double h = world.random.nextGaussian() * data.offsetY;
                double j = world.random.nextGaussian() * data.offsetZ;
                double k = world.random.nextGaussian() * data.speed;
                double l = world.random.nextGaussian() * data.speed;
                double m = world.random.nextGaussian() * data.speed;

                try {
                    world.addParticle(data.parameters, data.longDistance, data.x + g, data.y + h, data.z + j, k, l, m);
                } catch (Throwable var16) {
                    DDVOrigins.LOGGER.warn("Could not spawn particle effect {}", data.parameters);
                    return;
                }
            }
        }
    }
    public static void spawnParticles(List<ParticlesData<?>> data, World world) {
        data.forEach(particlesData -> spawnParticles(particlesData, world));
    }

    public static void playSounds(SoundsData data, World world) {
        world.playSound(null, new BlockPos(data.x, data.y, data.z), data.sound, data.category, data.volume, data.pitch);
    }
    public static void playSounds(List<SoundsData> data, World world) {
        data.forEach(soundsData -> playSounds(soundsData, world));
    }
}
