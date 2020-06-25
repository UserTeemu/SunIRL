package io.github.tivj.sunirl.mixin.daylength;

import com.mojang.authlib.GameProfile;
import io.github.tivj.sunirl.CalculationHelper;
import io.github.tivj.sunirl.DayLengthPacketHandler;
import io.github.tivj.sunirl.SunIRL;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.UserCache;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.WorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void sendDayLengthPacket(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci, GameProfile gameProfile, UserCache userCache, String string, CompoundTag compoundTag, RegistryKey<?> registryKey, ServerWorld serverWorld, ServerWorld serverWorld3, String string2, WorldProperties worldProperties, ServerPlayNetworkHandler packetHandler) {
        if (SunIRL.instance.config.irlDayLength) {
            PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
            passedData.writeLong(CalculationHelper.ticksIn24h);

            ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, DayLengthPacketHandler.DAYLENGTH_PACKET_ID, passedData);
        }
    }
}