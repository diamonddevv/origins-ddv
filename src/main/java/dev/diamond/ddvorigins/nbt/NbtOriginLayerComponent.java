package dev.diamond.ddvorigins.nbt;

import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.origin.OriginLayers;
import net.diamonddev.libgenetics.common.api.v1.nbt.cerebellum.CerebellumNbtComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class NbtOriginLayerComponent extends CerebellumNbtComponent<OriginLayer> {
    public NbtOriginLayerComponent(String key) {
        super(key);
    }

    @Override
    public OriginLayer read(NbtCompound nbt) {
        return OriginLayers.getLayer(new Identifier(nbt.getString(key)));
    }

    @Override
    public void write(NbtCompound nbt, OriginLayer data) {
        nbt.putString(key, data.getIdentifier().toString());
    }
}
