package dev.diamond.ddvorigins.client.gui;

import dev.diamond.ddvorigins.util.DDVOriginsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class IHudIconImpl implements IHudIcon {

    private final Text text;
    private final int col;

    public IHudIconImpl(Text text, int color) {
        this.text = text;
        this.col = color;
    }

    @Override
    public void onRender(DrawContext context, float tickDelta, MinecraftClient client, TextRenderer textRenderer, TextureData data, WindowData window, int x, int y) {
        boolean icons = DDVOriginsConfig.CLIENT.guiConfig.renderHudIconTextures;
        boolean strings = DDVOriginsConfig.CLIENT.guiConfig.renderHudIconMessages;

        if (icons && strings) {
            IHudIcon.drawTextureWithText(context, data, x, y, textRenderer, this.text, this.col);
        } else if (icons) {
            IHudIcon.drawTexture(context, data, x, y);
        } else if (strings) {
            context.drawTextWithShadow(textRenderer, this.text, x, y, this.col);
        }
    }
}
