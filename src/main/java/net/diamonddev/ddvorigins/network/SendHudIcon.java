package net.diamonddev.ddvorigins.network;

import net.diamonddev.ddvorigins.client.gui.IHudIcon;
import net.diamonddev.ddvorigins.client.gui.IHudIconImpl;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SendHudIcon implements NerveS2CPacket<SendHudIcon, SendHudIcon.Data> {
    @Override
    public ClientPlayNetworking.PlayChannelHandler receive(Identifier channel) {
        return ((client, handler, buf, responseSender) -> {
            var data = read(buf);
            client.execute(() -> {
                IHudIconImpl icon = new IHudIconImpl(data.text, data.color);
                IHudIcon.HudIconHelper.addHudIcon(icon, data.duration, data.textureData);
            });
        });
    }

    @Override
    public PacketByteBuf write(Data data) {
        var buf = NerveNetworker.getNewBuf();
        IHudIcon.TextureData.writeTextureData(buf, data.textureData);
        buf.writeText(data.text);
        buf.writeInt(data.duration);
        buf.writeInt(data.color);
        return buf;
    }

    @Override
    public Data read(PacketByteBuf buf) {
        var data = new Data();
        data.textureData = IHudIcon.TextureData.readTextureData(buf);
        data.text = buf.readText();
        data.duration = buf.readInt();
        data.color = buf.readInt();
        return data;
    }

    public static class Data implements NerveS2CPacket.NervePacketData {
        public IHudIcon.TextureData textureData;
        public Text text;
        public int duration;
        public int color;
    }
}
