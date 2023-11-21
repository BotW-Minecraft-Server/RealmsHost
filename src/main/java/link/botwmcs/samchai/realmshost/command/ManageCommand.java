package link.botwmcs.samchai.realmshost.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import link.botwmcs.samchai.realmshost.client.gui.ChooseJobScreen;
import link.botwmcs.samchai.realmshost.util.CapabilitiesHandler;
import link.botwmcs.samchai.realmshost.util.PlayerUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;

import java.util.Collection;

public class ManageCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("manage")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                .then(Commands.literal("player")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.literal("job")
                                        .then(Commands.literal("get")
                                                .executes(context -> {
                                                    return 1;
                                                })
                                        )
                                        .then(Commands.literal("set")
                                                .then(Commands.literal("farmer")
                                                        .executes(context -> {
                                                            for (ServerPlayer targetPlayer : EntityArgument.getPlayers(context, "target")) {
                                                                CapabilitiesHandler.setPlayerJobAsFarmer(targetPlayer);
                                                            }
                                                            return 1;
                                                        })
                                                )
                                                .then(Commands.literal("miner")
                                                        .executes(context -> {
                                                            for (ServerPlayer targetPlayer : EntityArgument.getPlayers(context, "target")) {
                                                                CapabilitiesHandler.setPlayerJobAsMiner(targetPlayer);
                                                            }
                                                            return 1;

                                                        })
                                                )
                                                .then(Commands.literal("knight")
                                                        .executes(context -> {
                                                            for (ServerPlayer targetPlayer : EntityArgument.getPlayers(context, "target")) {
                                                                CapabilitiesHandler.setPlayerJobAsKnight(targetPlayer);
                                                            }
                                                            return 1;
                                                        })
                                                )
                                                .then(Commands.literal("default")
                                                        .executes(context -> {
                                                            for (ServerPlayer targetPlayer : EntityArgument.getPlayers(context, "target")) {
                                                                CapabilitiesHandler.setPlayerJobAsDefault(targetPlayer);
                                                            }
                                                            return 1;
                                                        })
                                                )
                                                .then(Commands.literal("openGui")
                                                        .executes(context -> {
                                                            Collection<ServerPlayer> target = EntityArgument.getPlayers(context, "target");
                                                            for (ServerPlayer targetPlayer : target) {
                                                                PlayerUtilities.openJobChooseScreen(targetPlayer, false);
                                                            }
                                                            return 1;
                                                        })
                                                )
                                        )
                                )
                                .then(Commands.literal("jobXp")
                                        .then(Commands.literal("get")
                                                .executes(context -> {
                                                    // todo: get job xp
                                                    return 1;
                                                })
                                        )
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("level", IntegerArgumentType.integer(0, 100))
                                                        .executes(context -> {
                                                            // TODO: set job xp
                                                            return 1;
                                                        })
                                                )
                                        )
                                )
                                .then(Commands.literal("town")
                                        .then(Commands.literal("get"))
                                        .then(Commands.literal("set")
                                                .then(Commands.literal("openGui")
                                                        .executes(context -> {
                                                            Collection<ServerPlayer> target = EntityArgument.getPlayers(context, "target");
                                                            for (ServerPlayer targetPlayer : target) {
                                                                PlayerUtilities.openTownChooseScreen(targetPlayer, false);
                                                            }
                                                            return 1;
                                                        })
                                                )
                                        )
                                )
                        )
                )
                // TODO: other manage commands
                .then(Commands.literal("teleport")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.literal("request")
                                        .then(Commands.argument("destination", EntityArgument.player())
                                                .executes(context -> {
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("force")
                                        .then(Commands.argument("destination", EntityArgument.player())
                                                .executes(context -> {
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("dimension")
                                        .then(Commands.argument("dimension", DimensionArgument.dimension())
                                                .executes(context -> {
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )
                .then(Commands.literal("inventory")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.literal("openGui")
                                        .executes(context -> {

                                            return 1;
                                        })
                                )
                        )
                )
                .then(Commands.literal("seeScreen")
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(context -> {
                                    return 1;
                                })
                        )
                )

        );
    }
}
