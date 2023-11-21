package link.botwmcs.samchai.realmshost.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import link.botwmcs.samchai.realmshost.util.PlayerUtilities;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;

import javax.swing.text.html.parser.Entity;

public class HudCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("hud")
                .requires(source -> source.hasPermission(4))
                .then(Commands.literal("trainbar")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("component", StringArgumentType.string())
                                        .then(Commands.argument("stayTime", IntegerArgumentType.integer(1))
                                                .executes(context -> {
                                                    EntityArgument.getPlayers(context, "target").forEach(player -> {
                                                        PlayerUtilities.sendTrainBarHud(player, StringArgumentType.getString(context, "component"), IntegerArgumentType.getInteger(context, "stayTime"));
                                                    });
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )
                .then(Commands.literal("bossbar")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("component", StringArgumentType.string())
                                        .then(Commands.argument("stayTime", IntegerArgumentType.integer(1))
                                                .executes(context -> {
                                                    EntityArgument.getPlayers(context, "target").forEach(player -> {
                                                        PlayerUtilities.sendBossBarHud(player, StringArgumentType.getString(context, "component"), IntegerArgumentType.getInteger(context, "stayTime"));
                                                    });
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )
        );
    }
}
