package net.diamonddev.ddvorigins.network;

import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacketRegistry;

public class Netcode {
    public static final NervePacketRegistry.NervePacketRegistryEntry<OpenSelectSpecificLayerOriginScreen, OpenSelectSpecificLayerOriginScreen.Data>
            SELECT_SPECIFIC_LAYER_ORIGIN_PACKET = NervePacketRegistry.register(
                    DDVOrigins.id("open_specific_layer_origin_select_packet"), new OpenSelectSpecificLayerOriginScreen());

    public static final NervePacketRegistry.NervePacketRegistryEntry<SendCheckmarkIconPacket, SendCheckmarkIconPacket.Data>
            SEND_CHECKMARK_ICON_PACKET = NervePacketRegistry.register(
                    DDVOrigins.id("send_checkmark_icon_packet"), new SendCheckmarkIconPacket());
}
