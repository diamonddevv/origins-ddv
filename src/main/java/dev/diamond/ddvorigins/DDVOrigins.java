package dev.diamond.ddvorigins;

import dev.diamond.ddvorigins.item.LayerOriginSwitchItem;
import dev.diamond.ddvorigins.registry.*;
import dev.diamond.ddvorigins.util.DDVOriginsConfig;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.registry.ModItems;
import dev.diamond.ddvorigins.resource.WeightedLayersType;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFileRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DDVOrigins implements ModInitializer {

	public static final String modid = "ddvorigins";
	public static final Logger LOGGER = LoggerFactory.getLogger("DDV Origins");

	@Override
	public void onInitialize() {
		long start = System.currentTimeMillis();
		//
		DDVOriginsConfig.SERVER = ChromosomeConfigFileRegistry.registerAndReadAsSelf(DDVOrigins.id("server_config"), new DDVOriginsConfig.ServerCfg(), DDVOriginsConfig.ServerCfg.class);

		new InitItems().register();
		new InitEffects().register();
		new InitParticles().register();
		new InitResources().register();
		new InitPowers().register();
		new InitDamageSources().register();
		new InitEventCallbacks().register();
		new InitSoundEvents().register();

		/*TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
			if (DDVOriginsConfig.SERVER.modConfig.canObtainLayeredOrbs) { // only register if enabled in config
				factories.add(new SellLayeredOrbFactory());
			}
		});*/

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
				//content.addAfter(ModItems.ORB_OF_ORIGIN, LayerOriginSwitchItem.getStackWithDefaultLay//er(InitItems.LAYER_SWITCHER));
			});
		}
	}

	/*private static class SellLayeredOrbFactory implements TradeOffers.Factory {
		@Nullable
		@Override
		public TradeOffer create(Entity entity, Random random) {
			OriginLayer layer = WeightedLayersType.WEIGHTMAP.getRandom(random);
			ItemStack stack = LayerOriginSwitchItem.getStackWithLayer(InitItems.LAYER_SWITCHER, layer.getIdentifier());
			return new TradeOffer(new ItemStack(Items.EMERALD_BLOCK, 16), stack, 1, 100, 10);
		}
	}*/
}
