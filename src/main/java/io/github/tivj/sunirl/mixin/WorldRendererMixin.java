package io.github.tivj.sunirl.mixin;

import io.github.tivj.sunirl.SunIRL;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Quaternion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    // Rotation (sun)
    @Redirect(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/Vector3f;getDegreesQuaternion(F)Lnet/minecraft/util/math/Quaternion;", ordinal = 3))
    private Quaternion getSunAzimuthAngle(Vector3f vector3f, float angle) {
        return Vector3f.POSITIVE_Y.getDegreesQuaternion(SunIRL.instance.positionCalculator.getSunAzimuthAngleDegrees());
    }

    @Redirect(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/Vector3f;getDegreesQuaternion(F)Lnet/minecraft/util/math/Quaternion;", ordinal = 4))
    private Quaternion getSunElevationAngle(Vector3f vector3f, float angle) {
        return Vector3f.POSITIVE_X.getDegreesQuaternion(SunIRL.instance.positionCalculator.getSunElevationAngleDegrees());
    }

    // Rotation (moon)
    @Inject(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;bindTexture(Lnet/minecraft/util/Identifier;)V", ordinal = 1))
    private void applyMoonRotations(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        matrices.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(SunIRL.instance.positionCalculator.getSunElevationAngleDegrees()));
        matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(SunIRL.instance.positionCalculator.getSunAzimuthAngleDegrees()));

        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(SunIRL.instance.positionCalculator.getMoonAzimuthAngleDegrees()));
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(SunIRL.instance.positionCalculator.getMoonElevationAngleDegrees()));
    }

    // Size
    @ModifyConstant(method = "renderSky", constant = @Constant(floatValue = 30F))
    private float getSunSize(float og) {
        return SunIRL.instance.positionCalculator.getMinecraftSunSize();
    }

    @ModifyConstant(method = "renderSky", constant = @Constant(floatValue = 20F))
    private float getMoonSize(float og) {
        return SunIRL.instance.positionCalculator.getMinecraftMoonSize();
    }

    //Sun twilight
    @Inject(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/Vector3f;getDegreesQuaternion(F)Lnet/minecraft/util/math/Quaternion;", ordinal = 0))
    private void rotateTwilight(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(SunIRL.instance.positionCalculator.getSunAzimuthAngleDegrees() + 90F));
    }
}