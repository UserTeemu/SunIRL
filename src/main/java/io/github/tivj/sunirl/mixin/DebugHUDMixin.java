package io.github.tivj.sunirl.mixin;

import io.github.tivj.sunirl.SunIRL;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DebugHud.class)
public class DebugHUDMixin {
    @ModifyConstant(method = "getLeftText", constant = @Constant(longValue = 24000L))
    private static long getDayLength(long og) {
        return SunIRL.instance.config.irlDayLength ? SunIRL.ticksIn24h : og;
    }
}
