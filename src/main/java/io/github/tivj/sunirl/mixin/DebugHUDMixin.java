package io.github.tivj.sunirl.mixin;

import io.github.tivj.sunirl.SunIRL;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(DebugHud.class)
public class DebugHUDMixin {
    /* For debug purposes
    @Inject(method = "getLeftText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/DebugHud;method_27871()Ljava/lang/String;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void addLine(CallbackInfoReturnable<List<String>> cir, String string2, BlockPos blockPos, Entity entity, Direction direction, String string7, World world, LongSet longSet, List<String> list) {
        if (MinecraftClient.getInstance().world != null) {
            list.add("Twilight: " + SunIRL.commandValue);
            list.add("Sun: " + SunIRL.instance.positionCalculator.getSunAzimuthAngleDegrees());

            Vector3f horiPlane = MinecraftClient.getInstance().gameRenderer.getCamera().getHorizontalPlane();
            list.add("CameraHorizontalPlane: "+horiPlane.getX()+" "+horiPlane.getY() +" "+ horiPlane.getZ());
        }
    }*/

    @ModifyConstant(method = "getLeftText", constant = @Constant(longValue = 24000L))
    private static long getDayLength(long og) {
        return SunIRL.instance.config.irlDayLength ? SunIRL.ticksIn24h : og;
    }
}
