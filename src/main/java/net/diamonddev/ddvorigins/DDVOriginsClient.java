package net.diamonddev.ddvorigins;

import net.diamonddev.ddvorigins.network.Netcode;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacketRegistry;
import net.fabricmc.api.ClientModInitializer;

public class DDVOriginsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NervePacketRegistry.initClientS2CReciever(Netcode.SELECT_SPECIFIC_LAYER_ORIGIN_PACKET);
    }
}
