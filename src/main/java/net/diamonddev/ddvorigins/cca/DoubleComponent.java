package net.diamonddev.ddvorigins.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

public class DoubleComponent implements Component {

    private final String key;
    private final double def;
    private double val;

    public DoubleComponent(double def, String key) {
        this.val = def;
        this.def = def;
        this.key = key;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        write(tag.getDouble(key));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble(key, read());
    }

    public void write(double d) {
        this.val = d;
    }
    public double read() {
        return val;
    }

    public double getDefault() {
        return def;
    }
}
