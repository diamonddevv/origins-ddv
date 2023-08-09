package net.diamonddev.ddvorigins.mixin;

import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.ddvorigins.DDVOriginsClient;
import net.diamonddev.ddvorigins.effect.ChronokineticEffect;
import net.diamonddev.ddvorigins.registry.InitParticles;
import net.diamonddev.ddvorigins.util.FXUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Nullable public ClientWorld world;

    @Inject(method = "tick", at = @At("TAIL"))
    private void ddvorigins$tickClientThings(CallbackInfo ci) {
        DDVOriginsClient.CHRONOKINETICS.removeIf(DDVOriginsClient.ReceivedChronokineticData::shouldRemove);

        for (var data : DDVOriginsClient.CHRONOKINETICS) {
            if (this.world != null) {
                // Clocks (Trail)
                Entity user = data.user;

                if (user != null) {
                    FXUtil.spawnParticles(new FXUtil.ParticlesData<>(
                            InitParticles.CLOCK, true,
                            user.getPos().x, user.getPos().y + .5, user.getPos().z,
                            0f, 0f, 0f,
                            0f, 2),
                            world);
                }

                // Rip (Start Pos)
                FXUtil.spawnParticles(new FXUtil.ParticlesData<>(
                        ParticleTypes.END_ROD, true,
                        data.origin.x, data.origin.y + 2, data.origin.z,
                        0f, 0.25f,
                        0f, 0f, 20),
                        world);



                // Decrement Timer
                data.remainingTime -= 1;

                if (data.remainingTime <= 0) {
                    if (user != null) {
                        ChronokineticEffect.rayFromEndToOrigin(data.origin.add(0, .5, 0), user.getPos().add(0, .5, 0), (vec) -> {
                            FXUtil.spawnParticles(new FXUtil.ParticlesData<>(
                                    ParticleTypes.ENCHANTED_HIT, true,
                                    vec.x, vec.y, vec.z,
                                    0f, 0f, 0f,
                                    0f, 5), world);
                        });
                    }

                    // Mark for removal
                    data.shouldRemove = true;
                }
            }
        }
    }
}
