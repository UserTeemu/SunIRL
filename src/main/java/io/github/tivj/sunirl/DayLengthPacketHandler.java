package io.github.tivj.sunirl;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.util.Identifier;

public class DayLengthPacketHandler implements ClientModInitializer {
    public static final Identifier DAYLENGTH_PACKET_ID = new Identifier("sunirl", "daylength");

    @Override
    public void onInitializeClient() {
        ClientSidePacketRegistry.INSTANCE.register(DAYLENGTH_PACKET_ID,
                (packetContext, attachedData) ->
                        packetContext.getTaskQueue().execute(() -> {
                            SunIRL.instance.dayLength = attachedData.readLong();
                        }
                )
        );
    }
}
