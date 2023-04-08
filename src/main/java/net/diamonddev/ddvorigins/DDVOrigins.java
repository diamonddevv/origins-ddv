package net.diamonddev.ddvorigins;

import net.fabricmc.api.ModInitializer;
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



		//
		long initTime = System.currentTimeMillis() - start;
		LOGGER.info("Mod " + modid + " initialized in " + initTime + " millisecond(s)!");
	}

	public static Identifier id(String path) {
		return new Identifier(modid, path);
	}
}
