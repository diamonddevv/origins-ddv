package net.diamonddev.ddvorigins.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public interface IHudIcon {

    void onRender(MatrixStack matrices, float tickDelta, MinecraftClient client, TextRenderer textRenderer, TextureData data, WindowData window, int x, int y);

    static void drawTexture(MatrixStack matrices, TextureData data, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, data.path);
        DrawableHelper.drawTexture(matrices, x, y, data.u, data.v, data.width, data.height, data.sheetWidth, data.sheetHeight);
    }

    static void drawTextureWithText(MatrixStack matrices, TextureData data, int x, int y, TextRenderer renderer, Text text, int color) {
        drawTexture(matrices, data, x, y);
        DrawableHelper.drawTextWithShadow(matrices, renderer, text, x + data.width + 5, y + (data.height / 4), color);
    }

    record WindowData(int height, int width) {
        public static WindowData create(MinecraftClient client) {
            return new WindowData(client.getWindow().getScaledHeight(), client.getWindow().getScaledWidth());
        }
    }
    record TextureData(Identifier path, int u, int v, int width, int height, int sheetWidth, int sheetHeight) {
        public static void writeTextureData(PacketByteBuf buf, IHudIcon.TextureData textureData) {
            buf.writeIdentifier(textureData.path());
            buf.writeInt(textureData.u());
            buf.writeInt(textureData.v());
            buf.writeInt(textureData.width());
            buf.writeInt(textureData.height());
            buf.writeInt(textureData.sheetWidth());
            buf.writeInt(textureData.sheetHeight());
        }
        public static IHudIcon.TextureData readTextureData(PacketByteBuf buf) {
            var path = buf.readIdentifier();
            int u = buf.readInt(), v = buf.readInt(), width = buf.readInt(), height = buf.readInt(), sw = buf.readInt(), sh = buf.readInt();

            return new IHudIcon.TextureData(path, u, v, width, height, sw, sh);
        }
    }

    class HudIconHelper {
        public static final class HudIconData {
            private final IHudIcon impl;
            private int duration;
            private final TextureData texture;

            public HudIconData(IHudIcon impl, int duration, TextureData texture) {
                this.impl = impl;
                this.duration = duration;
                this.texture = texture;
            }

            public IHudIcon getImpl() {
                return impl;
            }
            public TextureData getTexture() {
                return texture;
            }
            public int getDuration() {
                return duration;
            }

            public void tickDuration() {
                this.duration--;
            }
        }

        public static List<HudIconData> HUD_ICONS = new ArrayList<>();

        public static void addHudIcon(IHudIcon icon, int duration, TextureData texture) {
            HudIconHelper.HudIconData data = new HudIconHelper.HudIconData(icon, duration, texture);
            HudIconHelper.HUD_ICONS.add(data);
        }
    }
}
