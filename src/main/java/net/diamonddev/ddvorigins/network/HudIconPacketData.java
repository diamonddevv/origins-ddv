package net.diamonddev.ddvorigins.network;

import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.ddvorigins.client.gui.IHudIcon;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class HudIconPacketData {
    public static final Identifier PLACEHOLDER = DDVOrigins.id("textures/gui/placeholder.png");
    private static final Identifier TICK_AND_CROSS = DDVOrigins.id("textures/gui/tick_and_cross.png");

    public static final HudIconPacketDataWrapper PLACEHOLDER_DATA = () -> {
        var data = new SendHudIcon.Data();
        data.textureData = new IHudIcon.TextureData(PLACEHOLDER, 0, 0, 16, 16, 16, 16);
        data.color = 0x000000;
        data.text = Text.translatable("text.ddvorigins.vai.relocate_cancelled");
        data.duration = 40;
        return data;
    };
    public static final HudIconPacketDataWrapper VAI_RELOCATE_FAILED = () -> {
        var data = new SendHudIcon.Data();
        data.textureData = new IHudIcon.TextureData(TICK_AND_CROSS, 0, 0, 16, 16, 32, 16);
        data.color = Formatting.DARK_RED.getColorValue();
        data.text = Text.translatable("text.ddvorigins.vai.relocate_cancelled");
        data.duration = 40;
        return data;
    };
}
