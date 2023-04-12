package net.diamonddev.ddvorigins.nbt;

import io.github.apace100.origins.origin.OriginLayer;
import net.minecraft.item.ItemStack;

public class DDVOriginsNbtManager {
    private static final NbtOriginLayerComponent LAYER = new NbtOriginLayerComponent("layer");

    public static class LayerComponentManager {
        public static OriginLayer getLayer(ItemStack stack) {
            try {
                return LAYER.read(stack.getOrCreateNbt());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        public static void setLayer(ItemStack stack, OriginLayer layer) {
            LAYER.write(stack.getOrCreateNbt(), layer);
        }
    }
}
