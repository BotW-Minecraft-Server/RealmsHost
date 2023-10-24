package link.botwmcs.samchai.realmshost.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import link.botwmcs.samchai.realmshost.capability.town.Town;
import link.botwmcs.samchai.realmshost.capability.town.TownCompoundHandler;
import link.botwmcs.samchai.realmshost.util.CapabilitiesHandler;
import link.botwmcs.samchai.realmshost.util.PlayerUtilities;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TownCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("town")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                .then(Commands.literal("create")
                        .then(Commands.argument("townName", StringArgumentType.string())
                                .then(Commands.argument("townComment", StringArgumentType.string())
                                        .then(Commands.argument("townOwner", EntityArgument.player())
                                                .then(Commands.argument("isPublic", BoolArgumentType.bool())
                                                        .then(Commands.argument("isOpen", BoolArgumentType.bool())
                                                                .then(Commands.argument("isStared", BoolArgumentType.bool())
                                                                        .then(Commands.argument("townLevel", IntegerArgumentType.integer(0, 100))
                                                                                .then(Commands.argument("townFunds", IntegerArgumentType.integer(0))
                                                                                        .then(Commands.argument("spawnTownManagerBlock", BoolArgumentType.bool())
                                                                                                .executes(context -> {
                                                                                                    String townName = StringArgumentType.getString(context, "townName");
                                                                                                    String townComment = StringArgumentType.getString(context, "townComment");
                                                                                                    ServerPlayer townOwner = EntityArgument.getPlayer(context, "townOwner");
                                                                                                    boolean isPublic = BoolArgumentType.getBool(context, "isPublic");
                                                                                                    boolean isOpen = BoolArgumentType.getBool(context, "isOpen");
                                                                                                    boolean isStared = BoolArgumentType.getBool(context, "isStared");
                                                                                                    int townLevel = IntegerArgumentType.getInteger(context, "townLevel");
                                                                                                    int townFunds = IntegerArgumentType.getInteger(context, "townFunds");
                                                                                                    boolean spawnTownManagerBlock = BoolArgumentType.getBool(context, "spawnTownManagerBlock");
                                                                                                    Level world = context.getSource().getLevel();
                                                                                                    BlockPos townSpawn = townOwner.blockPosition();
                                                                                                    BlockPos townHall = townOwner.blockPosition();
                                                                                                    BlockPos townMarket = townOwner.blockPosition();
                                                                                                    BlockPos townBank = townOwner.blockPosition();
                                                                                                    BlockPos townJobBoard = townOwner.blockPosition();
                                                                                                    BlockPos townYard = townOwner.blockPosition();
                                                                                                    if (spawnTownManagerBlock) {
                                                                                                        // TODO：Give player some town manager block that have NBT data
                                                                                                    }
                                                                                                    CapabilitiesHandler.createTown(world, townOwner, townName, townComment, isPublic, isOpen, isStared, townLevel, townFunds, townSpawn, townHall, townMarket, townBank, townJobBoard, townYard);
                                                                                                    return 1;
                                                                                                })
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("townName", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    Map<String, Town> towns = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns();
                                    for (Map.Entry<String, Town> entry : towns.entrySet()) {
                                        builder.suggest(entry.getKey());
                                    }
                                    return builder.buildFuture();
                                })
                                .executes(context -> {
                                    String townName = StringArgumentType.getString(context, "townName");
                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                    context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).removeTown(town);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("modify")
                        .then(Commands.argument("townName", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    Map<String, Town> towns = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns();
                                    for (Map.Entry<String, Town> entry : towns.entrySet()) {
                                        builder.suggest(entry.getKey());
                                    }
                                    return builder.buildFuture();
                                })
                                .then(Commands.literal("name")
                                        .then(Commands.argument("newTownName", StringArgumentType.greedyString())
                                                .executes(context -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    String newTownName = StringArgumentType.getString(context, "newTownName");
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    town.setTownName(newTownName);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("comment")
                                        .then(Commands.argument("newTownComment", StringArgumentType.greedyString())
                                                .executes(context -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    String newTownComment = StringArgumentType.getString(context, "newTownComment");
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    town.setTownComment(newTownComment);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("isPublic")
                                        .then(Commands.argument("isPublic", BoolArgumentType.bool())
                                                .executes(context -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    boolean isPublic = BoolArgumentType.getBool(context, "isPublic");
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    town.isPublic = isPublic;
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("isOpen")
                                        .then(Commands.argument("isOpen", BoolArgumentType.bool())
                                                .executes(context -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    boolean isOpen = BoolArgumentType.getBool(context, "isOpen");
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    town.isOpen = isOpen;
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("isStared")
                                        .then(Commands.argument("isStared", BoolArgumentType.bool())
                                                .executes(context -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    boolean isStared = BoolArgumentType.getBool(context, "isStared");
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    town.isStared = isStared;
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("townLevel")
                                        .then(Commands.argument("townLevel", IntegerArgumentType.integer(0, 100))
                                                .executes(context -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    int townLevel = IntegerArgumentType.getInteger(context, "townLevel");
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    town.setTownLevel(townLevel);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("townFunds")
                                        .then(Commands.argument("townFunds", IntegerArgumentType.integer(0))
                                                .executes(context -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    int townFunds = IntegerArgumentType.getInteger(context, "townFunds");
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    town.setTownFunds(townFunds);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("addResident")
                                        .then(Commands.argument("target", EntityArgument.player())
                                                .executes(context -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    ServerPlayer target = EntityArgument.getPlayer(context, "target");
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    town.addResident(target);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("removeResident")
                                        .then(Commands.argument("target", EntityArgument.player())
                                                .suggests((context, builder) -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    List<ServerPlayer> residents = town.residentUUIDs.stream().map(uuid -> context.getSource().getServer().getPlayerList().getPlayer(uuid)).toList();
                                                    for (ServerPlayer resident : residents) {
                                                        builder.suggest(resident.getName().getString());
                                                    }
                                                    return builder.buildFuture();
                                                })
                                                .executes(context -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    ServerPlayer target = EntityArgument.getPlayer(context, "target");
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    town.removeResident(target);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("giveManagerBlocks")
                                        .then(Commands.argument("target", EntityArgument.player())
                                                .executes(context -> {
                                                    // TODO: Give player some town manager block that have NBT data
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )
                .then(Commands.literal("list")
                        .executes(context -> {
                            Map<String, Town> towns = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns();
                            StringBuilder townList = new StringBuilder();
                            for (Map.Entry<String, Town> entry : towns.entrySet()) {
                                townList.append(entry.getKey()).append(", ");
                            }
                            context.getSource().sendSystemMessage(Component.nullToEmpty(townList.toString()));
                            return 1;
                        })
                )
                .then(Commands.literal("check")
                        .then(Commands.argument("townName", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    Map<String, Town> towns = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns();
                                    for (Map.Entry<String, Town> entry : towns.entrySet()) {
                                        builder.suggest(entry.getKey());
                                    }
                                    return builder.buildFuture();
                                })
                                .executes(context -> {
                                    String townName = StringArgumentType.getString(context, "townName");
                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                    ServerPlayer townOwner = context.getSource().getServer().getPlayerList().getPlayer(town.owner);
                                    String residentsList = "";
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.name", town.townName));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.comment", town.townComment));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.owner", townOwner.getName().getString()));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.isPublic", town.isPublic));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.isOpen", town.isOpen));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.isStared", town.isStared));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townLevel", town.townLevel));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townFunds", town.townFunds));
                                    for (UUID residentUUIDs : town.residentUUIDs.stream().toList()) {
                                        ServerPlayer residents = context.getSource().getServer().getPlayerList().getPlayer(residentUUIDs);
                                        residentsList += residents.getName().getString() + ", ";
                                    }
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townMembers", residentsList));
                                    return 1;
                                })
                        )
                )
        );
    }
}
