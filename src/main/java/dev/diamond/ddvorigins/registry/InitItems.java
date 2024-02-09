package dev.diamond.ddvorigins.registry;

import dev.diamond.ddvorigins.DDVOrigins;
import dev.diamond.ddvorigins.item.LayerOriginSwitchItem;
import dev.diamond.ddvorigins.item.VoidFruitItem;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class InitItems implements RegistryInitializer {
    //public static LayerOriginSwitchItem LAYER_SWITCHER = new LayerOriginSwitchItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));
    public static VoidFruitItem VOID_FRUIT = new VoidFruitItem();

    @Override
    public void register() {
        //Registry.register(Registries.ITEM, DDVOrigins.id("layered_orb"), LAYER_SWITCHER);
        Registry.register(Registries.ITEM, DDVOrigins.id("void_fruit"), VOID_FRUIT);
    }
}
