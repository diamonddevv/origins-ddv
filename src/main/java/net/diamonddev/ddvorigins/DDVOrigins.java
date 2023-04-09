package net.diamonddev.ddvorigins;

import io.github.apace100.origins.registry.ModItems;
import net.diamonddev.ddvorigins.item.LayerOriginSwitchItem;
import net.diamonddev.ddvorigins.registry.InitItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DDVOrigins implements ModInitializer {

	public static final String modid = "ddvorigins";
	public static final Logger LOGGER = LoggerFactory.getLogger("DDV Origins Plugin");

	@Override
	public void onInitialize() {
		long start = System.currentTimeMillis();
		//
		new InitItems().register();

		ItemGroupModifications.apply();
		//
		long initTime = System.currentTimeMillis() - start;
		LOGGER.info("Mod " + modid + " initialized in " + initTime + " millisecond(s)!");
	}

	public static Identifier id(String path) {
		return new Identifier(modid, path);
	}

	public static class ItemGroupModifications {
		public static void apply() {
			ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
				content.addAfter(ModItems.ORB_OF_ORIGIN, LayerOriginSwitchItem.getStackWithDefaultLayer(InitItems.LAYER_SWITCHER));
			});
		}
	}
}
