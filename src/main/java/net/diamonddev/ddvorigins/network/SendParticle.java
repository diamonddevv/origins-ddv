package net.diamonddev.ddvorigins.network;

import net.diamonddev.ddvorigins.util.FXUtil;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class SendParticle implements NerveS2CPacket<SendParticle, SendParticle.Data> {

    @Override
    public ClientPlayNetworking.PlayChannelHandler receive(Identifier channel) {
        return (client, handler, buf, responseSender) -> {
            FXUtil.spawnParticles(read(buf).particlesData, client.world);
        };
    }

    @Override
    public PacketByteBuf write(Data data) {
        var buf = NerveNetworker.getNewBuf();

        buf.writeIdentifier(Registries.PARTICLE_TYPE.getId(data.particlesData.parameters().getType()));
        buf.writeBoolean(data.particlesData.longDistance());

        buf.writeDouble(data.particlesData.x());
        buf.writeDouble(data.particlesData.y());
        buf.writeDouble(data.particlesData.z());

        buf.writeFloat(data.particlesData.offsetX());
        buf.writeFloat(data.particlesData.offsetY());
        buf.writeFloat(data.particlesData.offsetZ());

        buf.writeFloat(data.particlesData.speed());
        buf.writeInt(data.particlesData.count());

        return buf;
    }
    @Override
    public Data read(PacketByteBuf buf) {
        var data = new Data(null);

        Identifier id = buf.readIdentifier();
        Optional<ParticleType<?>> effect = Registries.PARTICLE_TYPE.getOrEmpty(id);
        if (effect.isEmpty()) throw new RuntimeException("Could not find Particle from packet -> '" + id + "'");

        var ld = buf.readBoolean();
        var x = buf.readDouble();
        var y = buf.readDouble();
        var z = buf.readDouble();
        var oX = buf.readFloat();
        var oY = buf.readFloat();
        var oZ = buf.readFloat();
        var s = buf.readFloat();
        var c = buf.readInt();

        data.particlesData = new FXUtil.ParticlesData<>((ParticleEffect)effect.get(), ld, x, y, z, oX, oY, oZ, s, c);
        return data;
    }

    public static class Data implements NerveS2CPacket.NervePacketData {
        public FXUtil.ParticlesData<?> particlesData;

        public Data(FXUtil.ParticlesData<DefaultParticleType> pdata) {
            this.particlesData = pdata;
        }
    }
}
