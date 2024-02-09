package dev.diamond.ddvorigins;

import dev.diamond.ddvorigins.network.Netcode;
import dev.diamond.ddvorigins.util.DDVOriginsConfig;
import dev.diamond.ddvorigins.registry.InitParticles;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFileRegistry;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacketRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class DDVOriginsClient implements ClientModInitializer {

    public static class ReceivedChronokineticData {
        public Entity user;
        public Vec3d origin;
        public int remainingTime;

        public boolean shouldRemove = false;

        public boolean shouldRemove() {
            return shouldRemove || !user.isAlive();
        }
    }

    public static ArrayList<ReceivedChronokineticData> CHRONOKINETICS = new ArrayList<>();


    @Override
    public void onInitializeClient() {
        DDVOriginsConfig.CLIENT = ChromosomeConfigFileRegistry.registerAndReadAsSelf(DDVOrigins.id("client_config"), new DDVOriginsConfig.ClientCfg(), DDVOriginsConfig.ClientCfg.class);

        ParticleFactoryRegistry.getInstance().register(InitParticles.CLOCK, EndRodParticle.Factory::new);

        //NervePacketRegistry.initClientS2CReciever(Netcode.SEND_SELECT_LAYERED_ORIGIN);
        NervePacketRegistry.initClientS2CReciever(Netcode.SEND_HUD_ICON);
        NervePacketRegistry.initClientS2CReciever(Netcode.SEND_CHRONOKINETIC_USER_DATA);
    }
}
