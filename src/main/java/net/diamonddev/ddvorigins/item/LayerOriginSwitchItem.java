package net.diamonddev.ddvorigins.item;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.registry.ModComponents;
import net.diamonddev.ddvorigins.nbt.DDVOriginsNbtManager;
import net.diamonddev.ddvorigins.network.Netcode;
import net.diamonddev.ddvorigins.network.SendSelectLayeredOrigin;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LayerOriginSwitchItem extends Item {
    public LayerOriginSwitchItem(Settings settings) {
        super(settings);
    }

    public static ItemStack getStackWithDefaultLayer(LayerOriginSwitchItem instance) {
        return getStackWithLayer(instance, Origins.identifier("origin"));
    }

    public static ItemStack getStackWithLayer(LayerOriginSwitchItem instance, Identifier layerId) {
        ItemStack stack = new ItemStack(instance);
        DDVOriginsNbtManager.LayerComponentManager.setLayer(stack, OriginLayers.getLayer(layerId));
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            OriginLayer layer = DDVOriginsNbtManager.LayerComponentManager.getLayer(stack);
            ModComponents.ORIGIN.get(user).setOrigin(layer, Origin.EMPTY);
            SendSelectLayeredOrigin.Data data = new SendSelectLayeredOrigin.Data();
            data.layerId = layer.getIdentifier();
            NerveNetworker.send((ServerPlayerEntity) user, Netcode.SEND_SELECT_LAYERED_ORIGIN, data);

            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        } return new TypedActionResult<>(ActionResult.SUCCESS, stack);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (DDVOriginsNbtManager.LayerComponentManager.getLayer(stack) != null) {
            OriginLayer layer = DDVOriginsNbtManager.LayerComponentManager.getLayer(stack);
            tooltip.add(Text.translatable("ddvorigins.tooltip.layer", parseLayerAsNiceString(layer)));
        } else tooltip.add(Text.literal("Invalid Layer!").formatted(Formatting.DARK_RED));
    }

    public static String parseLayerAsNiceString(OriginLayer layer) {
        return I18n.translate(layer.getOrCreateTranslationKey());
    }
}
