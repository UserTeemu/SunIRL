package io.github.tivj.sunirl.mixin.daylength;

import io.github.tivj.sunirl.SunIRL;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TimeCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TimeCommand.class)
public class TimeCommandMixin {
    //name mclength = irl length
    //day 1000 = 72000
    //noon 6000 = 432000
    //night 13000 = 936000.06
    //midnight 18000 = 1296000

    @Redirect(method = "method_13792", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/TimeCommand;executeSet(Lnet/minecraft/server/command/ServerCommandSource;I)I"))
    private static int redirectDay(ServerCommandSource source, int time) {
        return TimeCommand.executeSet(source, SunIRL.instance.config.irlDayLength ? 72000 : time);
    }

    @Redirect(method = "method_13794", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/TimeCommand;executeSet(Lnet/minecraft/server/command/ServerCommandSource;I)I"))
    private static int redirectNoon(ServerCommandSource source, int time) {
        return TimeCommand.executeSet(source, SunIRL.instance.config.irlDayLength ? 432000 : time);
    }

    @Redirect(method = "method_13797", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/TimeCommand;executeSet(Lnet/minecraft/server/command/ServerCommandSource;I)I"))
    private static int redirectNight(ServerCommandSource source, int time) {
        return TimeCommand.executeSet(source, SunIRL.instance.config.irlDayLength ? 936000 : time);
    }

    @Redirect(method = "method_13785", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/TimeCommand;executeSet(Lnet/minecraft/server/command/ServerCommandSource;I)I"))
    private static int redirectMidnight(ServerCommandSource source, int time) {
        return TimeCommand.executeSet(source, SunIRL.instance.config.irlDayLength ? 1296000 : time);
    }
}
