package link.botwmcs.samchai.realmshost.capability.town;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import link.botwmcs.samchai.realmshost.RealmsHost;
import net.minecraft.resources.ResourceLocation;

public class TownCompoundHandler implements WorldComponentInitializer {
    public static final ComponentKey<ITownCompound> TOWN_COMPONENT_KEY =
            ComponentRegistry.getOrCreate(new ResourceLocation(RealmsHost.MODID, "town_data"), ITownCompound.class);
    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
//        registry.register(TOWN_COMPONENT_KEY, world -> new BaseTownCompound());
        registry.register(TOWN_COMPONENT_KEY, TownCompound.WorldTown.class, TownCompound.WorldTown::new);
    }
}
