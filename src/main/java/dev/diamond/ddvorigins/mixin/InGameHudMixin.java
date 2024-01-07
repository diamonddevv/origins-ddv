package dev.diamond.ddvorigins.mixin;

import dev.diamond.ddvorigins.client.gui.IHudIcon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
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
}