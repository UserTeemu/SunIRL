package io.github.tivj.sunirl.mixin.daylength;

import io.github.tivj.sunirl.SunIRL;
import net.minecraft.command.arguments.TimeArgumentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(TimeArgumentType.class)
public class IntConstantModifierMixin {
    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 24000))
    private static int getDayLength(int og) {
        return SunIRL.instance.config.irlDayLength ? (int) SunIRL.ticksIn24h : og;
    }
}
