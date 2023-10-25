package link.botwmcs.samchai.realmshost.util;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ServerUtilities {
    public static void saveAll(MinecraftServer server) {
        server.saveEverything(true, true, true);
    }
    public static void saveChunks(MinecraftServer server) {
        server.saveAllChunks(true, true, true);
    }
    public static ChunkPos blockPosToChunkPos(BlockPos blockPos) {
        return new ChunkPos(blockPos);
    }
    public static String runCommandBlock(ServerLevel serverLevel, @Nullable Vec3 vec, String command) {
        BaseCommandBlock bcb = new BaseCommandBlock() {
            @Override
            public @NotNull ServerLevel getLevel() {
                return serverLevel;
            }

            @Override
            public void onUpdated() { }

            @Override
            public @NotNull Vec3 getPosition() {
                return Objects.requireNonNullElseGet(vec, () -> new Vec3(0, 0, 0));
            }

            @Override
            public @NotNull CommandSourceStack createCommandSourceStack() {
                return new CommandSourceStack(this, getPosition(), Vec2.ZERO, serverLevel, 2, "dev", Component.literal("dev"), serverLevel.getServer(), null);
            }

            @Override
            public boolean isValid() {
                return true;
            }

            @Override
            public boolean performCommand(Level pLevel) {
                if (!pLevel.isClientSide) {
                    if ("Searge".equalsIgnoreCase(this.getCommand())) {
                        this.setLastOutput(Component.literal("#itzlipofutzli"));
                        this.setSuccessCount(1);
                    }
                    else {
                        this.setSuccessCount(0);
                        MinecraftServer minecraftserver = this.getLevel().getServer();
                        if (!StringUtil.isNullOrEmpty(this.getCommand())) {
                            try {
                                this.setLastOutput(null);
                                CommandSourceStack commandsourcestack = this.createCommandSourceStack().withCallback((p_45417_, p_45418_, p_45419_) -> {
                                    if (p_45418_) {
                                        this.setSuccessCount(this.getSuccessCount()+1);
                                    }

                                });
                                minecraftserver.getCommands().performPrefixedCommand(commandsourcestack, this.getCommand());
                            }
                            catch (Throwable throwable) {
                                CrashReport crashreport = CrashReport.forThrowable(throwable, "Executing command block");
                                CrashReportCategory crashreportcategory = crashreport.addCategory("Command to be executed");
                                crashreportcategory.setDetail("Command", this::getCommand);
                                crashreportcategory.setDetail("Name", () -> this.getName().getString());
                                throw new ReportedException(crashreport);
                            }
                        }
                    }
                    return true;
                }
                else {
                    return false;
                }
            }
        };
        bcb.setCommand(command);
        bcb.setTrackOutput(true);
        bcb.performCommand(serverLevel);
        return bcb.getLastOutput().getString();
    }
    public static String runCommandBlock(ServerLevel serverLevel, String command) {
        return runCommandBlock(serverLevel, null, command);
    }

}
