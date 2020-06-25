package io.github.tivj.sunirl;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

import java.util.TimeZone;

@Config(name = "sunirl")
@Config.Gui.Background("minecraft:textures/block/oak_planks.png")
public class SunConfig implements ConfigData {
    public boolean useMCTime = true; // if time (not date) should be the same as in Minecraft
    public boolean irlDayLength = false; // if 1 in-game day = 24000 ticks

    @ConfigEntry.BoundedDiscrete(min = 0, max = 6000)
    public int updateDelay = 100;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public float sunAndMoonSizeMultiplier = 1F;

    @ConfigEntry.Category("location")
    @ConfigEntry.Gui.TransitiveObject
    public LocationModule location = new LocationModule();

    @Config(name = "location")
    public static class LocationModule implements ConfigData {
        @ConfigEntry.Gui.Tooltip()
        public String timezone = TimeZone.getDefault().getID();
        @ConfigEntry.Gui.Tooltip()
        public double latitude = 61.143235D;
        @ConfigEntry.Gui.Tooltip()
        public double longitude = 4D;
    }
}