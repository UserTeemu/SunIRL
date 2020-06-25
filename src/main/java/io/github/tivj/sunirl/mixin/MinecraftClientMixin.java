package io.github.tivj.sunirl.mixin;

import io.github.tivj.sunirl.SunIRL;
import me.shedaniel.clothconfig2.gui.ClothConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    private Screen lastScreen = null;

    /**
     * As far as I know, AutoConfig doesn't allow you to set a listener for changed settings, so I did it myself.
     */
    @Inject(method = "openScreen", at = @At("RETURN"))
    private void onGuiChanged(Screen screen, CallbackInfo ci) {
        if (screen instanceof ClothConfigScreen) {
            this.lastScreen = screen;
            SunIRL.instance.oldConfig.updateValues();
        } else if (this.lastScreen instanceof ClothConfigScreen) SunIRL.instance.oldConfig.onSettingsGuiClosed((MinecraftClient) ((Object) this));
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("RETURN"))
    private void onDisconnect(Screen screen, CallbackInfo ci) {
        SunIRL.instance.dayLength = 24000L;
    }
}
