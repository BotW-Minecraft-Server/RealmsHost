package link.botwmcs.samchai.realmshost.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import link.botwmcs.samchai.realmshost.capability.Home;
import link.botwmcs.samchai.realmshost.util.CapabilitiesHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.List;

public class HomeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home")
                .requires(CommandSourceStack::isPlayer)
                .then(Commands.literal("set")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                        .executes(context -> {
                                            ServerPlayer serverPlayer = context.getSource().getPlayer();
                                            Level level = serverPlayer.level();
                                            BlockPos homePos = BlockPosArgument.getLoadedBlockPos(context, "pos");
                                            String homeName = StringArgumentType.getString(context, "name");
                                            CapabilitiesHandler.addHome(serverPlayer, level, homePos, homeName);
                                            serverPlayer.sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.home.set", homeName, homePos.getX() + ", " + homePos.getY() + ", " + homePos.getZ()));
                                            return 1;
                                        })
                                )
                        )
                )
                .then(Commands.literal("list")
                        .executes(context -> {
                            ServerPlayer serverPlayer = context.getSource().getPlayer();
                            List<Home> homeList = CapabilitiesHandler.getPlayerHomeList(serverPlayer);
                            String homeListString = "";
                            for (Home home : homeList) {
                                homeListString += home.homeName + ", ";
                            }
                            serverPlayer.sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.home.list", homeListString));
                            return 1;
                        })
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("homeName", StringArgumentType.string())
                                .suggests((context, builder) -> {
                                    ServerPlayer serverPlayer = context.getSource().getPlayer();
                                    CapabilitiesHandler.getPlayerHomeList(serverPlayer).forEach(home -> {
                                        builder.suggest(home.homeName);
                                    });
                                    return builder.buildFuture();
                                })
                                .executes(context -> {
                                    ServerPlayer serverPlayer = context.getSource().getPlayer();
                                    String homeName = StringArgumentType.getString(context, "homeName");
                                    CapabilitiesHandler.removeHome(serverPlayer, homeName);
                                    serverPlayer.sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.home.remove", homeName));
                                    return 1;
                                })
                        )
                )
        );
    }
}
