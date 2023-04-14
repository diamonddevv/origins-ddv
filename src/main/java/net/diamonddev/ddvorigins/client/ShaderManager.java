package net.diamonddev.ddvorigins.client;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.ddvorigins.registry.InitEffects;
import net.diamonddev.ddvorigins.util.DDVOriginsConfig;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffect;


public class ShaderManager implements RegistryInitializer {

    public static final FlaggedManagedShaderEffect SEPIATONE =
            new FlaggedManagedShaderEffect(ShaderEffectManager.getInstance().manage(DDVOrigins.id("shaders/post/sepiatone.json")),
                    DDVOriginsConfig.CLIENT.shaderEffectConfig.useTemporalDecelerationShader && hasEffect(InitEffects.DECELERATION));

    public static final FlaggedManagedShaderEffect SATURATIVE =
            new FlaggedManagedShaderEffect(ShaderEffectManager.getInstance().manage(DDVOrigins.id("shaders/post/saturation.json")),
                    DDVOriginsConfig.CLIENT.shaderEffectConfig.useTemporalAccelerationShader && hasEffect(InitEffects.ACCELERATION));

    @Override
    public void register() {
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            SEPIATONE.renderIfFlag(tickDelta);
            SATURATIVE.renderIfFlag(tickDelta);
        });
    }

    public static final class FlaggedManagedShaderEffect {
        private final ManagedShaderEffect shader;
        private final boolean flag;

        public FlaggedManagedShaderEffect(ManagedShaderEffect shader, boolean flag) {
            this.shader = shader;
            this.flag = flag;
        }

        public void renderIfFlag(float tickDelta) {
            if (flag) shader.render(tickDelta);
        }

    }

    private static boolean hasEffect(StatusEffect effect) {
        if (MinecraftClient.getInstance().player != null) return MinecraftClient.getInstance().player.hasStatusEffect(effect);
        return false;
    }
}
