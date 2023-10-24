package link.botwmcs.samchai.realmshost.capability;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import link.botwmcs.samchai.realmshost.RealmsHost;
import net.minecraft.resources.ResourceLocation;

public class AccountHandler implements EntityComponentInitializer {
    public static final ComponentKey<IAccount> ACCOUNT_COMPONENT_KEY =
            ComponentRegistry.getOrCreate(new ResourceLocation(RealmsHost.MODID, "player_data"), IAccount.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(ACCOUNT_COMPONENT_KEY, Account::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
