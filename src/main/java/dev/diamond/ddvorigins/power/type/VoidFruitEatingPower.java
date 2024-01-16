package dev.diamond.ddvorigins.power.type;

import dev.diamond.ddvorigins.DDVOrigins;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class VoidFruitEatingPower extends Power {
    private final List<Identifier> whitelistedFoods;

    public VoidFruitEatingPower(SerializableData.Instance data, PowerType<?> type, LivingEntity entity) {
        super(type, entity);

        this.whitelistedFoods = data.get("whitelist");
    }

    public List<Identifier> getWhitelistedFoods() {
        return whitelistedFoods;
    }

    @SuppressWarnings("unchecked")
    public static PowerFactory<VoidFruitEatingPower> getFactory() {
        return new PowerFactory<>(DDVOrigins.id("void_fruit_eating"),
                new SerializableData()
                        .add("whitelist", SerializableDataTypes.IDENTIFIERS),
                (data) -> (t, e) -> new VoidFruitEatingPower(data, t, e));
    }
}
