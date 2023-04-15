package net.diamonddev.ddvorigins.impl;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.apace100.origins.command.LayerArgumentType;
import io.github.apace100.origins.origin.OriginLayer;
import net.diamonddev.ddvorigins.item.LayerOriginSwitchItem;
import net.diamonddev.ddvorigins.registry.InitItems;
import net.diamonddev.libgenetics.common.api.LibGeneticsApi;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.ArrayList;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class LibGeneticsApiImpl implements LibGeneticsApi {
    @Override
    public void addLibGeneticsCommandBranches(LiteralArgumentBuilder<ServerCommandSource> root, ArrayList<ArgumentBuilder<ServerCommandSource, ?>> branches) {
        branches.add(
                root
                        .then(literal("ddvorigins")
                                .then(literal("giveLayeredOrb")
                                        .executes(ApiImplCommandExecutors::exeGiveOrbDefaultLayer)

                                        .then(argument("layer", LayerArgumentType.layer())
                                                .executes(ApiImplCommandExecutors::exeGiveOrbWithLayer)
                                        )
                                )
                        )
        );
    }

    public static class ApiImplCommandExecutors {
        public static int exeGiveOrbDefaultLayer(CommandContext<ServerCommandSource> context) {
            context.getSource().getPlayer().giveItemStack(LayerOriginSwitchItem.getStackWithDefaultLayer(InitItems.LAYER_SWITCHER));
            context.getSource().sendFeedback(Text.literal("Gave layered orb with default layer"), true);
            return 1;
        }
        public static int exeGiveOrbWithLayer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
            OriginLayer layer = LayerArgumentType.getLayer(context, "layer");
            context.getSource().getPlayer().giveItemStack(LayerOriginSwitchItem.getStackWithLayer(InitItems.LAYER_SWITCHER, layer.getIdentifier()));
            context.getSource().sendFeedback(Text.literal("Gave layered orb with layer: " + LayerOriginSwitchItem.parseLayerAsNiceString(layer)), true);
            return 1;
        }
    }
}
