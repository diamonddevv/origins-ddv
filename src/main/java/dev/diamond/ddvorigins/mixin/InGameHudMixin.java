package dev.diamond.ddvorigins.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.diamond.ddvorigins.client.gui.IHudIcon;
import dev.diamond.ddvorigins.effect.AbysmalIntoxicationEffect;
import dev.diamond.ddvorigins.effect.VoidStungEffect;
import dev.diamond.ddvorigins.registry.InitEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract TextRenderer getTextRenderer();

    @Inject(method = "render", at = @At("HEAD"))
    private void ddvorigins$drawHudIcons(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (!client.options.hudHidden) {
            IHudIcon.HudIconHelper.HUD_ICONS.forEach(hudIconData -> {
                int ordinal = IHudIcon.HudIconHelper.HUD_ICONS.indexOf(hudIconData);
                hudIconData.getImpl().onRender(
                        context, tickDelta,
                        client, getTextRenderer(),
                        hudIconData.getTexture(),
                        IHudIcon.WindowData.create(client),
                        15, (16 * (ordinal + 1))
                );
            });
        }
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void ddvorigins$tickHudIcons(CallbackInfo ci) {
        List<IHudIcon.HudIconHelper.HudIconData> toRemove = new ArrayList<>();
        IHudIcon.HudIconHelper.HUD_ICONS.forEach(hudIconData -> {
            hudIconData.tickDuration();
            if (hudIconData.getDuration() <= 0) toRemove.add(hudIconData);
        });
        IHudIcon.HudIconHelper.HUD_ICONS.removeAll(toRemove);
    }


    // CUSTOM EFFECT ICONS ; doesnt account for non full health oop

    @WrapOperation(
            method = "drawHeart",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"
            )
    )
    private void ddvorigins$replaceHeartTexture(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original,
                                                DrawContext context, InGameHud.HeartType type, int x1, int y1, int v1, boolean blinking, boolean halfHeart) {
        Identifier tex = texture;

        if (client.cameraEntity instanceof PlayerEntity player) {

            if (player.hasStatusEffect(InitEffects.ABYSMAL_INTOXICATION)) tex = AbysmalIntoxicationEffect.HEARTS;
            if (player.hasStatusEffect(InitEffects.VOID_STUNG)) tex = VoidStungEffect.HEARTS;

            if (type == InGameHud.HeartType.CONTAINER) {
                tex = texture;
            }

            if (texture != tex) {
                u = halfHeart ? 9 : 0;
            }
        }

        original.call(instance, tex, x, y, u, v, width, height);
    }
}
