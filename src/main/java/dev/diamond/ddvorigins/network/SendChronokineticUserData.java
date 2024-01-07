package dev.diamond.ddvorigins.network;

import dev.diamond.ddvorigins.DDVOriginsClient;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public class SendChronokineticUserData implements NerveS2CPacket<SendChronokineticUserData, SendChronokineticUserData.ChronokineticUserData> {

    @Override
    public ClientPlayNetworking.PlayChannelHandler receive(Identifier channel) {
        return (client, handler, buf, responseSender) -> {
            var data = read(buf);
            Entity entity = null;

            if (client.world != null) {
                for (Entity candidate : client.world.getEntities()) {
                    if (candidate.getUuid().equals(data.user)) {
                        entity = candidate;
                        break;
                    }
                }

                if (entity != null) {
                    if (data.syncShouldShow) {
                        var received = new DDVOriginsClient.ReceivedChronokineticData();
                        received.user = entity;
                        received.origin = data.origin;
                        received.remainingTime = data.duration;
                        DDVOriginsClient.CHRONOKINETICS.add(received);
                    } else {
                        for (var d : DDVOriginsClient.CHRONOKINETICS) {
                            if (d.user.equals(entity)) {
                                d.shouldRemove = true;
                            }
                        }
                    }
                }
            }
        };
    }

    @Override
    public PacketByteBuf write(ChronokineticUserData data) {
        var buf = NerveNetworker.getNewBuf();

        buf.writeBoolean(data.syncShouldShow);

        buf.writeDouble(data.origin.x);
        buf.writeDouble(data.origin.y);
        buf.writeDouble(data.origin.z);

        buf.writeInt(data.duration);

        buf.writeUuid(data.user);

        return buf;
    }

    @Override
    public ChronokineticUserData read(PacketByteBuf buf) {

        double x, y, z;

        boolean shouldShow = buf.readBoolean();

        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();

        Vec3d origin = new Vec3d(x, y, z);
        int duration = buf.readInt();
        UUID user = buf.readUuid();

        return new ChronokineticUserData(shouldShow, origin, duration, user);
    }

    public record ChronokineticUserData(boolean syncShouldShow, Vec3d origin, int duration, UUID user) implements NerveS2CPacket.NervePacketData {}
}
