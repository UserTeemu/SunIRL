package io.github.tivj.sunirl.mixin;

import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    /**
     * TODO: Better idea would be to just rotate the thing to the correct place, but I dont have any idea how Camera.horizontalPlane works and what it is
     * https://discordapp.com/channels/674795434509598731/690280886398091285/725722170390610000
     * This just removes the fog completely, because of above.
     */
    @ModifyVariable(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;getZ()D", ordinal = 0), index = 3, argsOnly = true)
    private static int noWeirdFogBasedOnYawAndPitch(int og) {
        return 3;
    }
}
