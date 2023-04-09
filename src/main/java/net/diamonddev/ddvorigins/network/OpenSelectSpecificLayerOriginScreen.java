package net.diamonddev.ddvorigins.network;

import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.screen.ChooseOriginScreen;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacket;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class OpenSelectSpecificLayerOriginScreen implements NerveS2CPacket<OpenSelectSpecificLayerOriginScreen, OpenSelectSpecificLayerOriginScreen.Data> {
    @Override
    public ClientPlayNetworking.PlayChannelHandler receive(Identifier channel) {
        return ((client, handler, buf, responseSender) -> {
            OriginLayer layer = OriginLayers.getLayer(read(buf).layerId);
            client.execute(() -> {
                ArrayList<OriginLayer> layers = new ArrayList<>(List.of(layer));
                client.setScreen(new ChooseOriginScreen(layers, 0, false));
            });
        });
    }

    @Override
    public PacketByteBuf write(Data data) {
        PacketByteBuf buf = NervePacket.getNewBuf();
        buf.writeIdentifier(data.layerId);
        return buf;
    }

    @Override
    public Data read(PacketByteBuf buf) {
        Data data = new Data();
        data.layerId = buf.readIdentifier();
        return data;
    }

    public static class Data implements NervePacketData {
        public Identifier layerId;
    }
}
