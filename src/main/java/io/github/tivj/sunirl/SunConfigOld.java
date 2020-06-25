package io.github.tivj.sunirl;

import net.minecraft.client.MinecraftClient;

/**
 * Used to store previous (before config gui is opened) option values.
 * This information can be used to determine if
 */
public class SunConfigOld {
    private SunConfig config;
    public boolean irlDayLength;
    public double latitude;
    public double longitude;

    public SunConfigOld(SunConfig config) {
        this.config = config;
        updateValues();
    }

    public void updateValues() {
        this.irlDayLength = this.config.irlDayLength;
        this.latitude = this.config.location.latitude;
        this.longitude = this.config.location.longitude;
    }

    public void onSettingsGuiClosed(MinecraftClient mc) {
        if (this.config.irlDayLength != this.irlDayLength && mc.isInSingleplayer()) {
            SunIRL.instance.dayLength = this.config.irlDayLength ? 1728000L : 24000L;
        }
        if (this.config.location.latitude != this.latitude || this.config.location.longitude != this.longitude) {
            SunIRL.instance.positionCalculator.setPosition(this.config.location.latitude, this.config.location.longitude);
        }
    }
}
