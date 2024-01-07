package dev.diamond.ddvorigins.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

public class ChronokinesisComponent implements Component {
    public static final ChronokinesisComponentData EMPTY = new ChronokinesisComponentData(null);

    private final String key;

    public ChronokinesisComponent(String key) {
        this.key = key;
    }

    private ChronokinesisComponentData data = EMPTY;

    @Override public void readFromNbt(NbtCompound tag) {
       write(ChronokinesisComponentData.deserialize(tag));
    }
    @Override public void writeToNbt(NbtCompound tag) {
        if (read().allPresent()) ChronokinesisComponentData.serialize(read(), tag);
    }

    public void write(ChronokinesisComponentData data) {
        this.data = data;
    }
    public ChronokinesisComponentData read() {
        return this.data;
    }


    public record ChronokinesisComponentData(Vec3d origin) {
        public boolean allPresent() {
            return origin != null;
        }

        private static final String ORIGIN_KEY = "origin";

        public static ChronokinesisComponentData deserialize(NbtCompound tag) {
            // Vec
            NbtCompound originCmpd = tag.getCompound(ORIGIN_KEY);
            double x = originCmpd.getDouble("x");
            double y = originCmpd.getDouble("y");
            double z = originCmpd.getDouble("z");
            Vec3d origin = new Vec3d(x,y,z);

            // Write
            return new ChronokinesisComponentData(origin);
        }
        public static void serialize(ChronokinesisComponentData data, NbtCompound tag) {
            // Vec
            NbtCompound originCmpd = new NbtCompound();
            originCmpd.putDouble("x", data.origin.x);
            originCmpd.putDouble("y", data.origin.y);
            originCmpd.putDouble("z", data.origin.z);
            tag.put(ORIGIN_KEY, originCmpd);
        }
    }
}
