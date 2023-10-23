package link.botwmcs.samchai.realmshost.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import link.botwmcs.samchai.realmshost.client.gui.ChooseJobScreen;
import link.botwmcs.samchai.realmshost.util.CapabilitiesHandler;
import link.botwmcs.samchai.realmshost.util.PlayerUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;

import java.util.Collection;

public class ManageCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("manage")
                .then(Commands.argument("target", EntityArgument.player())
                        .then(Commands.literal("job")
                                .then(Commands.literal("get")
                                        .executes(context -> {
                                            return 1;
                                        })
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.literal("farmer")
                                                .executes(context -> {
                                                    CapabilitiesHandler.setPlayerJobAsFarmer(EntityArgument.getPlayer(context, "target"));
                                                    return 1;
                                                })
                                        )
                                        .then(Commands.literal("miner")
                                                .executes(context -> {
                                                    CapabilitiesHandler.setPlayerJobAsMiner(EntityArgument.getPlayer(context, "target"));
                                                    return 1;

                                                })
                                        )
                                        .then(Commands.literal("knight")
                                                .executes(context -> {
                                                    CapabilitiesHandler.setPlayerJobAsKnight(EntityArgument.getPlayer(context, "target"));
                                                    return 1;
                                                })
                                        )
                                        .then(Commands.literal("default")
                                                .executes(context -> {
                                                    CapabilitiesHandler.setPlayerJobAsDefault(EntityArgument.getPlayer(context, "target"));
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
                                .then(Commands.literal("get"))
                                .then(Commands.literal("set")
                                        .then(Commands.argument("level", IntegerArgumentType.integer(0, 100))
                                                .executes(context -> {
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
                                                        PlayerUtilities.openJobChooseScreen(targetPlayer, false);
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )
        );
    }
}
