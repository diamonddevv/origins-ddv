package dev.diamond.ddvorigins.power.type;

import dev.diamond.ddvorigins.DDVOrigins;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class VoidwalkPower extends Power {

    private final int threshold;
    private final boolean nightvision;
    private final boolean icy;
    private final int floor;

    public VoidwalkPower(SerializableData.Instance data, PowerType<?> type, LivingEntity entity) {
        super(type, entity);


        this.threshold = data.getInt("elevation_threshold");
        this.floor = data.getInt("floor_threshold");
        this.nightvision = data.getBoolean("night_vision");
        this.icy = data.getBoolean("ice_physics");
    }

    public int getMaxYLevel(World world) {
        return world.getBottomY() + threshold;
    }

    public int getFloor(World world) {
        return world.getBottomY() - floor;
    }

    public boolean getShouldApplyNightVision() {
        return nightvision;
    }

    public boolean getIsIcy() {
        return icy;
    }

    @SuppressWarnings("unchecked")
    public static PowerFactory<VoidwalkPower> getFactory() {
        return new PowerFactory<>(DDVOrigins.id("voidwalk"),
                new SerializableData()
                        .add("elevation_threshold", SerializableDataTypes.INT, 20)
                        .add("floor_threshold", SerializableDataTypes.INT, -12)
                        .add("night_vision", SerializableDataTypes.BOOLEAN, true)
                        .add("ice_physics", SerializableDataTypes.BOOLEAN, true),
                (data) -> (t, e) -> new VoidwalkPower(data, t, e));
    }
}
