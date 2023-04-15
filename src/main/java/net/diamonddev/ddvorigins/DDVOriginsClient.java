package net.diamonddev.ddvorigins;

import net.diamonddev.ddvorigins.network.Netcode;
import net.diamonddev.ddvorigins.util.DDVOriginsConfig;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFileRegistry;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacketRegistry;
import net.fabricmc.api.ClientModInitializer;

public class DDVOriginsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DDVOriginsConfig.CLIENT = ChromosomeConfigFileRegistry.registerAndReadAsSelf(DDVOrigins.id("client_config"), new DDVOriginsConfig.ClientCfg(), DDVOriginsConfig.ClientCfg.class);

        NervePacketRegistry.initClientS2CReciever(Netcode.SELECT_SPECIFIC_LAYER_ORIGIN_PACKET);
    }
}
