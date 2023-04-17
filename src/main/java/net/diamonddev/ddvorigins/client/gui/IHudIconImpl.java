package net.diamonddev.ddvorigins.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class IHudIconImpl implements IHudIcon {

    private final Text text;
    private final int col;

    public IHudIconImpl(Text text, int color) {
        this.text = text;
        this.col = color;
    }

    @Override
    public void onRender(MatrixStack matrices, float tickDelta, MinecraftClient client, TextRenderer textRenderer, TextureData data, WindowData window, int x, int y) {
        IHudIcon.drawTextureWithText(matrices, data, x, y, textRenderer, this.text, this.col);
    }
}
