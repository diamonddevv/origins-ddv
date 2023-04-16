package net.diamonddev.ddvorigins.network;

import net.diamonddev.ddvorigins.client.gui.CheckmarkHudIcon;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SendCheckmarkIconPacket implements NerveS2CPacket<SendCheckmarkIconPacket, SendCheckmarkIconPacket.Data> {
    @Override
    public ClientPlayNetworking.PlayChannelHandler receive(Identifier channel) {
        return ((client, handler, buf, responseSender) -> {
            var data = read(buf);
            client.execute(() -> {
                CheckmarkHudIcon icon = new CheckmarkHudIcon(data.isCheck);
                icon.addHudIcon(icon, data.duration);
            });
        });
    }

    @Override
    public PacketByteBuf write(Data data) {
        var buf = NerveNetworker.getNewBuf();
        buf.writeBoolean(data.isCheck);
        buf.writeInt(data.duration);
        return buf;
    }

    @Override
    public Data read(PacketByteBuf buf) {
        var data = new Data();
        data.isCheck = buf.readBoolean();
        data.duration = buf.readInt();
        return data;
    }

    public static class Data implements NerveS2CPacket.NervePacketData {
        public boolean isCheck;
        public int duration;
    }
}
