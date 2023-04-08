package net.diamonddev.ddvorigins.nbt;

import io.github.apace100.origins.origin.OriginLayer;
import net.minecraft.item.ItemStack;

public class NbtManager {
    private static final NbtOriginLayerComponent LAYER = new NbtOriginLayerComponent("layer");

    public static class LayerComponentManager {
        public static OriginLayer getLayer(ItemStack stack) {
            return LAYER.read(stack.getOrCreateNbt());
        }

        public static void setLayer(ItemStack stack, OriginLayer layer) {
            LAYER.write(stack.getOrCreateNbt(), layer);
        }
    }
}
