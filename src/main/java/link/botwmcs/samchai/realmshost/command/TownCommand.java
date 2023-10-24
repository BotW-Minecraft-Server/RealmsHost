package link.botwmcs.samchai.realmshost.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import link.botwmcs.samchai.realmshost.util.CapabilitiesHandler;
import link.botwmcs.samchai.realmshost.util.PlayerUtilities;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.Collection;

public class TownCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("town")
                .then(Commands.literal("create")
                        .then(Commands.argument("townName", StringArgumentType.greedyString())
                                .executes(context -> {
                                    Level world = context.getSource().getLevel();
                                    CapabilitiesHandler.createTown(world, context.getSource().getPlayer(), StringArgumentType.getString(context, "townName"));
                                    return 1;
                                })
                        )
                )
        );
    }
}
