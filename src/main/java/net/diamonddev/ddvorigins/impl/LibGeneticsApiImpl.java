package net.diamonddev.ddvorigins.impl;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.apace100.origins.command.LayerArgumentType;
import io.github.apace100.origins.origin.OriginLayer;
import net.diamonddev.ddvorigins.client.gui.IHudIcon;
import net.diamonddev.ddvorigins.item.LayerOriginSwitchItem;
import net.diamonddev.ddvorigins.network.Netcode;
import net.diamonddev.ddvorigins.network.SendHudIcon;
import net.diamonddev.ddvorigins.registry.InitItems;
import net.diamonddev.libgenetics.common.api.LibGeneticsApi;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.minecraft.command.argument.ColorArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class LibGeneticsApiImpl implements LibGeneticsApi {
    @Override
    public void addLibGeneticsCommandBranches(LiteralArgumentBuilder<ServerCommandSource> root, ArrayList<ArgumentBuilder<ServerCommandSource, ?>> branches) {
        branches.add(
                root
                        .then(literal("ddvorigins")
                                .then(literal("giveLayeredOrb")
                                        .then(argument("players", EntityArgumentType.players())
                                                .executes(ApiImplCommandExecutors::exeGiveOrbDefaultLayer)

                                                .then(argument("layer", LayerArgumentType.layer())
                                                        .executes(ApiImplCommandExecutors::exeGiveOrbWithLayer)
                                                )
                                        )
                                )
                        )
        );
    }

    public static class ApiImplCommandExecutors {
        public static int exeGiveOrbDefaultLayer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

            Collection<ServerPlayerEntity> spes = EntityArgumentType.getPlayers(context, "players");

            spes.forEach(spe -> spe.giveItemStack(LayerOriginSwitchItem.getStackWithDefaultLayer(InitItems.LAYER_SWITCHER)));
            context.getSource().sendFeedback(() -> Text.literal("Gave layered orb with default layer"), true);
            return 1;
        }
        public static int exeGiveOrbWithLayer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

            Collection<ServerPlayerEntity> spes = EntityArgumentType.getPlayers(context, "players");

            OriginLayer layer = LayerArgumentType.getLayer(context, "layer");
            spes.forEach(spe -> spe.giveItemStack(LayerOriginSwitchItem.getStackWithLayer(InitItems.LAYER_SWITCHER, layer.getIdentifier())));

            context.getSource().sendFeedback(() -> Text.literal("Gave layered orb with layer: " + LayerOriginSwitchItem.parseLayerAsNiceString(layer)), true);
            return 1;
        }
    }
}
