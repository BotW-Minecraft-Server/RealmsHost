package link.botwmcs.samchai.realmshost.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ServerConfig {
    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final ServerConfig CONFIG;

    public final ForgeConfigSpec.BooleanValue enableFirstJoinServerOpenMenu;
    public final ForgeConfigSpec.BooleanValue enableFriendFeature;
    public final ForgeConfigSpec.BooleanValue enableTownFeature;
    public final ForgeConfigSpec.BooleanValue enableHomeFeature;
    public final ForgeConfigSpec.BooleanValue enableRespawnFeature;
    public final ForgeConfigSpec.BooleanValue enableLtsxFeature;
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
                .define("enableFirstJoinServerOpenMenu", false);
        enableFriendFeature = builder
                .comment("If true, players will be able to use the friend feature.")
                .define("enableFriendFeature", false);
        enableTownFeature = builder
                .comment("If true, players will be able to use the town feature.")
                .define("enableTownFeature", false);
        enableHomeFeature = builder
                .comment("If true, players will be able to use the home feature.")
                .define("enableHomeFeature", false);
        enableRespawnFeature = builder
                .comment("If true, players will be able to use the respawn feature.")
                .define("enableRespawnFeature", false);
        enableLtsxFeature = builder
                .comment("If true, the LTSX server feature will be able (SET TO FALSE WHEN THIRD PARTY USE).")
                .comment("eg: MinecraftServer.class feature")
                .define("enableLtsxFeature", false);
        builder.pop();
        builder.push("player");
        canSetHomeNumbers = builder
                .comment("The number of homes that players can set.")
                .defineInRange("canSetHomeNumbers", 5, 0, 100);
        savedDeathCounterNumbers = builder
                .comment("The number of death locations that players can save.")
                .defineInRange("savedDeathCounterNumbers", 10, 0, 100);
        builder.pop();
    }
}
