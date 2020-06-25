package io.github.tivj.sunirl.mixin.daylength;

import io.github.tivj.sunirl.CalculationHelper;
import io.github.tivj.sunirl.SunIRL;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DimensionType.class)
public class DoubleConstantModifierMixin {
    @ModifyConstant(method = "method_28528", constant = @Constant(doubleValue = 24000D))
    private static double getDayLength(double og) {
        return SunIRL.instance.config.irlDayLength ? (double) CalculationHelper.ticksIn24h : og;
    }
}
