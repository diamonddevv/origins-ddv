package net.diamonddev.ddvorigins.power.action.entity.vai;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.diamonddev.ddvorigins.DDVOrigins;
import net.minecraft.entity.Entity;

public class VaiChronokinesisEntityAction {

    private static void action(SerializableData.Instance data, Entity entity) {

    }



    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(DDVOrigins.id("vai_chronokinesis"),
                new SerializableData(),
                VaiChronokinesisEntityAction::action
        );
    }


}
