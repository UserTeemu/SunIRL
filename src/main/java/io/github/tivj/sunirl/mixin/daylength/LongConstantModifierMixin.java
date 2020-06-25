package io.github.tivj.sunirl.mixin.daylength;

import io.github.tivj.sunirl.CalculationHelper;
import io.github.tivj.sunirl.SunIRL;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(targets = {
        "net.minecraft.server.world.ServerWorld",
        "net.minecraft.entity.ai.brain.Brain",
        "net.minecraft.entity.passive.VillagerEntity",
        "net.minecraft.server.command.TimeCommand",
        "net.minecraft.world.dimension.DimensionType",
        "net.minecraft.world.gen.PillagerSpawner",
        "net.minecraft.server.network.DemoServerPlayerInteractionManager"
})
public class LongConstantModifierMixin {
    @ModifyConstant(method = {
            /*ServerWorld*/"tick",
            /*Brain*/"refreshActivities",
            /*VillagerEntity*/"shouldRestock",
            /*VillagerEntity*/"decayGossip",
            /*VillagerEntity*/"hasRecentlyWorkedAndSlept",
            /*TimeCommand*/"register",
            /*TimeCommand*/"getDayTime",
            /*DimensionType*/"method_28531",
            /*PillagerSpawner*/"spawn",
            /*DemoServerPlayerInteractionManager*/"update"
    }, constant = @Constant(longValue = 24000L))
    private static long getDayLength(long og) {
        return SunIRL.instance.config.irlDayLength ? CalculationHelper.ticksIn24h : og;
    }
}
