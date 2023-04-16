package net.diamonddev.ddvorigins.client.gui;

import net.diamonddev.ddvorigins.DDVOrigins;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CheckmarkHudIcon implements IHudIcon {
    private final boolean isCheck;

    public CheckmarkHudIcon(boolean isCheck) {
        this.isCheck = isCheck;
    }

    private static final Identifier TEXTURE = DDVOrigins.id("textures/gui/tick_and_cross.png");

    public void addHudIcon(IHudIcon icon, int duration) {
        IHudIcon.HudIconHelper.addHudIcon(icon, duration, new TextureData(TEXTURE, isCheck ? 16 : 0, 0, 16, 16));
    }

    @Override
    public void onRender(MatrixStack matrices, float tickDelta, MinecraftClient client, TextRenderer textRenderer, TextureData data, WindowData window, int x, int y) {
        initializeRenderingForTexture(data);
        drawTextureWithText(matrices, data, x, y, textRenderer, Text.translatable("text.ddvorigins.vai.relocate_cancelled"), Formatting.DARK_RED.getColorValue());
    }
}
