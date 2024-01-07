package dev.diamond.ddvorigins.registry;

import dev.diamond.ddvorigins.DDVOrigins;
import dev.diamond.ddvorigins.item.LayerOriginSwitchItem;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class InitItems implements RegistryInitializer {
    public static LayerOriginSwitchItem LAYER_SWITCHER = new LayerOriginSwitchItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));

    @Override
    public void register() {
        Registry.register(Registries.ITEM, DDVOrigins.id("layered_orb"), LAYER_SWITCHER);
    }
}
