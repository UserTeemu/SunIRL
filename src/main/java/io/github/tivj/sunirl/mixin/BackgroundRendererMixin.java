package io.github.tivj.sunirl.mixin;

import io.github.tivj.sunirl.SunIRL;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Quaternion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/Vector3f;dot(Lnet/minecraft/client/util/math/Vector3f;)F"))
    private static float rotate(Vector3f horizontalPlane, Vector3f other, Camera camera, float tickDelta, ClientWorld world, int i, float f) {
        if (SunIRL.instance.config.sunDirectionalFog) {
            Quaternion rotation = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
            Vector3f horizontalPlane2 = new Vector3f(0.0F, 0.0F, 1.0F);

            rotation.hamiltonProduct(Vector3f.NEGATIVE_Y.getDegreesQuaternion(SunIRL.instance.positionCalculator.getFogCalculationAzimuthAngle()));
            rotation.hamiltonProduct(Vector3f.POSITIVE_X.getDegreesQuaternion(SunIRL.instance.positionCalculator.getFogCalculationElevationAngle()));
            horizontalPlane2.rotate(rotation);

            return horizontalPlane.dot(horizontalPlane2);
        } else return 0F;
    }
}
