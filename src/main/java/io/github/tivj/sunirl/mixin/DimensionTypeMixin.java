package io.github.tivj.sunirl.mixin;

import io.github.tivj.sunirl.SunIRL;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {
	@Inject(at = @At("HEAD"), method = "method_28528(J)F", cancellable = true)
	private void init(long l, CallbackInfoReturnable<Float> cir) {
		if (SunIRL.instance.config.changeSunPositionInCode) cir.setReturnValue(SunIRL.instance.positionCalculator.getSunElevationAngleDegrees() / 360F);
	}
}