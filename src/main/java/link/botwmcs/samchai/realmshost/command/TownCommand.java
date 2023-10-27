package link.botwmcs.samchai.realmshost.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import link.botwmcs.samchai.realmshost.capability.town.Town;
import link.botwmcs.samchai.realmshost.capability.town.TownCompoundHandler;
import link.botwmcs.samchai.realmshost.util.CapabilitiesHandler;
import link.botwmcs.samchai.realmshost.util.ServerUtilities;
import link.botwmcs.samchai.realmshost.util.TownHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.*;

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
                                                                                                    if (world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns().containsKey(townName)) {
                                                                                                        context.getSource().sendFailure(Component.translatable("chat.botwmcs.realmshost.town.create.fail"));
                                                                                                        return 0;
                                                                                                    }
                                                                                                    TownHandler.createTown(world, townOwner, townName, townComment, isPublic, isOpen, isStared, townLevel, townFunds, townSpawn, townHall, townMarket, townBank, townJobBoard, townYard);
                                                                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.name", town.townName));
                                                                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.comment", town.townComment));
                                                                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.owner", town.owner.toString()));
                                                                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.isPublic", town.isPublic));
                                                                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.isOpen", town.isOpen));
                                                                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.isStared", town.isStared));
                                                                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townLevel", town.townLevel));
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
                                    if (TownHandler.removeTown(context.getSource().getLevel(), town)) {
                                        context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.remove"));
                                        return 1;
                                    } else {
                                        context.getSource().sendFailure(Component.translatable("chat.botwmcs.realmshost.town.remove.fail"));
                                        return 0;
                                    }
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
                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.name", townName, newTownName));
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
                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.comment", townName, newTownComment));
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("owner")
                                        .then(Commands.argument("newOwner", EntityArgument.player())
                                                .executes(context -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    ServerPlayer newOwner = EntityArgument.getPlayer(context, "newOwner");
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    town.setOwner(newOwner);
                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.owner", townName, newOwner.getName().getString()));
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
                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.isPublic", townName, isPublic));
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
                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.isOpen", townName, isOpen));
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
                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.isStared", townName, isStared));
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
                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.townLevel", townName, townLevel));
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
                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.townFunds", townName, townFunds));
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
                                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.townMembers.add", target.getName().getString(), townName));
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
                                                    if (TownHandler.removeTownResidents(town, target)) {
                                                        context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.townMembers.remove", target.getName().getString(), townName));
                                                        return 1;
                                                    } else {
                                                        context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.fail"));
                                                        return 0;
                                                    }
                                                })
                                        )
                                )
                                .then(Commands.literal("addClaimedChunk")
                                        .then(Commands.argument("blockPos", BlockPosArgument.blockPos())
                                                .executes(context -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    ChunkPos chunkPos = ServerUtilities.blockPosToChunkPos(BlockPosArgument.getBlockPos(context, "blockPos"));
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    if (TownHandler.addTownClaimedChunks(town, chunkPos)) {
                                                        context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.townChunks.add", chunkPos.toString(), townName));
                                                        return 1;
                                                    } else {
                                                        context.getSource().sendFailure(Component.translatable("chat.botwmcs.realmshost.town.modify.fail"));
                                                        return 0;
                                                    }
                                                })
                                        )
                                )
                                .then(Commands.literal("removeClaimedChunk")
                                        .then(Commands.argument("blockPos", BlockPosArgument.blockPos())
                                                .executes(context -> {
                                                    String townName = StringArgumentType.getString(context, "townName");
                                                    ChunkPos chunkPos = ServerUtilities.blockPosToChunkPos(BlockPosArgument.getBlockPos(context, "blockPos"));
                                                    Town town = context.getSource().getLevel().getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
                                                    if (TownHandler.removeTownClaimedChunks(town, chunkPos)) {
                                                        context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.modify.townChunks.remove", chunkPos.toString(), townName));
                                                        return 1;
                                                    } else {
                                                        context.getSource().sendFailure(Component.translatable("chat.botwmcs.realmshost.town.modify.fail"));
                                                        return 0;
                                                    }
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
                            if (townList.isEmpty()) {
                                context.getSource().sendFailure(Component.translatable("chat.botwmcs.realmshost.town.list.fail"));
                            } else {
                                context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.list.raw", townList.toString()));
                            }
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
                                    String residentsList = "";
                                    String claimedChunksList = "";
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.name", town.townName));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.comment", town.townComment));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.owner", town.owner.toString()));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.isPublic", town.isPublic));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.isOpen", town.isOpen));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.isStared", town.isStared));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townLevel", town.townLevel));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townFunds", town.townFunds));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townFunds", town.townFunds));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townSpawn", town.townSpawn.toString()));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townHall", town.townHall.toString()));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townMarket", town.townMarket.toString()));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townBank", town.townBank.toString()));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townJobBoard", town.townJobBoard.toString()));
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townYard", town.townYard.toString()));
                                    for (UUID residentUUIDs : town.residentUUIDs.stream().toList()) {
                                        residentsList += residentUUIDs.toString() + ", ";
                                    }
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townMembers", residentsList));
                                    for (ChunkPos pos : town.townClaimedChunks.stream().toList()) {
                                        claimedChunksList += pos.toString() + ", ";
                                    }
                                    context.getSource().sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.town.check.townChunks", claimedChunksList));
                                    return 1;
                                })
                        )
                )
        );
    }
}
