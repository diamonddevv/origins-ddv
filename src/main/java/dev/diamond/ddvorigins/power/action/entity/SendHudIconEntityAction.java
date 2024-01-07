package dev.diamond.ddvorigins.power.action.entity;

import com.google.gson.JsonObject;
import dev.diamond.ddvorigins.client.gui.IHudIcon;
import dev.diamond.ddvorigins.network.Netcode;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import dev.diamond.ddvorigins.DDVOrigins;
import dev.diamond.ddvorigins.network.SendHudIcon;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SendHudIconEntityAction {

    public static final SerializableDataType<IHudIcon.TextureData> TEXTURE_DATA_SERIALIZABLE_DATA_TYPE = new SerializableDataType<>(
            IHudIcon.TextureData.class, IHudIcon.TextureData::writeTextureData, IHudIcon.TextureData::readTextureData,
            element -> {
                JsonObject obj = element.getAsJsonObject();
                Identifier id = new Identifier(obj.get("texture").getAsString());
                int u = obj.get("u").getAsInt();
                int v = obj.get("v").getAsInt();
                int w = obj.get("width").getAsInt();
                int h = obj.get("height").getAsInt();
                int sw = obj.get("sheet_width").getAsInt();
                int sh = obj.get("sheet_height").getAsInt();
                return new IHudIcon.TextureData(id,u,v,w,h,sw,sh);
            }
    );

    private static void action(SerializableData.Instance data, Entity entity) {
        if (entity instanceof ServerPlayerEntity spe) {
            var d = new SendHudIcon.Data();

            d.duration = data.getInt("duration");
            d.text = data.get("text");
            d.textureData = data.get("texture_data");
            d.color = data.getInt("color");

            NerveNetworker.send(spe, Netcode.SEND_HUD_ICON, d);
        }
    }
    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(DDVOrigins.id("send_hud_icon"),
                new SerializableData()
                        .add("texture_data", TEXTURE_DATA_SERIALIZABLE_DATA_TYPE)
                        .add("duration", SerializableDataTypes.INT, 40)
                        .add("text", SerializableDataTypes.TEXT, null)
                        .add("color", SerializableDataTypes.INT, 0xffffff),
                SendHudIconEntityAction::action
        );
    }
}
