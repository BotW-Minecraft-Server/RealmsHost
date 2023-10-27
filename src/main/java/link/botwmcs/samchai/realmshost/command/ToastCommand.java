package link.botwmcs.samchai.realmshost.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import link.botwmcs.samchai.realmshost.util.PlayerUtilities;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;

public class ToastCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("toast")
//                .requires(source -> source.hasPermission(0))
                .then(Commands.literal("system")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("title", StringArgumentType.string())
                                        .then(Commands.argument("subtitle", StringArgumentType.string())
                                                .executes(context -> {
                                                    EntityArgument.getPlayers(context, "target").forEach(player -> {
                                                        PlayerUtilities.sendToast(player, StringArgumentType.getString(context, "title"), StringArgumentType.getString(context, "subtitle"));
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
