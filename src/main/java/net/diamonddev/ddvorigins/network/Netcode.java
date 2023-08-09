package net.diamonddev.ddvorigins.network;

import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacketRegistry;

public class Netcode {
    public static final NervePacketRegistry.NervePacketRegistryEntry<SendSelectLayeredOrigin, SendSelectLayeredOrigin.Data>
            SEND_SELECT_LAYERED_ORIGIN = NervePacketRegistry.register(
                    DDVOrigins.id("open_specific_layer_origin_select_packet"), new SendSelectLayeredOrigin());

    public static final NervePacketRegistry.NervePacketRegistryEntry<SendHudIcon, SendHudIcon.Data>
            SEND_HUD_ICON = NervePacketRegistry.register(
                    DDVOrigins.id("send_hud_icon"), new SendHudIcon());

    public static final NervePacketRegistry.NervePacketRegistryEntry<SendChronokineticUserData, SendChronokineticUserData.ChronokineticUserData>
            SEND_CHRONOKINETIC_USER_DATA = NervePacketRegistry.register(
                    DDVOrigins.id("send_chronokinetic_user_data"), new SendChronokineticUserData());
}
