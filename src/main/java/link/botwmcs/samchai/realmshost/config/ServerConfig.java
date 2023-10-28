package link.botwmcs.samchai.realmshost.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ServerConfig {
    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final ServerConfig CONFIG;

    public final ForgeConfigSpec.BooleanValue enableFirstJoinServerOpenMenu;
    public final ForgeConfigSpec.IntValue canSetHomeNumbers;
    public final ForgeConfigSpec.IntValue savedDeathCounterNumbers;


    static {
        Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        CONFIG_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    ServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("gerenal");
        enableFirstJoinServerOpenMenu = builder
                .comment("If true, players will be able to open the server open menu when they join the server for the first time.")
                .define("enableFirstJoinServerOpenMenu", true);
        canSetHomeNumbers = builder
                .comment("The number of homes that players can set.")
                .defineInRange("canSetHomeNumbers", 5, 0, 100);
        savedDeathCounterNumbers = builder
                .comment("The number of death locations that players can save.")
                .defineInRange("savedDeathCounterNumbers", 10, 0, 100);
        builder.pop();
    }
}
