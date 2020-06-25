package io.github.tivj.sunirl;

import org.shredzone.commons.suncalc.MoonPosition;
import org.shredzone.commons.suncalc.SunPosition;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class CalculationHelper {
    public static final float sunDiameterKM = 1392700F;
    public static final float moonDiameterKM = 3474.2F;
    public int ticksUntilNextCalculation = 0;

    private MoonPosition.Parameters moonPosCalculator;
    private SunPosition.Parameters sunPosCalculator;

    public CalculationHelper() {
        setPosition(SunIRL.instance.config.location.latitude, SunIRL.instance.config.location.longitude);
    }

    private float minecraftSunAzimuthAngle = 0F;
    private float minecraftSunElevationAngle = 0F;
    private float minecraftSunSize = 0F;

    private float fogCalculationAzimuthAngle = 0F;
    private float fogCalculationElevationAngle = 0F;

    private float minecraftMoonAzimuthAngle = 0F;
    private float minecraftMoonElevationAngle = 0F;
    private float minecraftMoonSize = 0F;

    public float getSunAzimuthAngleDegrees() {
        return minecraftSunAzimuthAngle;
    }
    public float getSunElevationAngleDegrees() {
        return minecraftSunElevationAngle;
    }
    public float getMinecraftSunSize() {
        return minecraftSunSize;
    }

    public float getFogCalculationAzimuthAngle() {
        return fogCalculationAzimuthAngle;
    }
    public float getFogCalculationElevationAngle() {
        return fogCalculationElevationAngle;
    }

    public float getMoonAzimuthAngleDegrees() {
        return minecraftMoonAzimuthAngle;
    }
    public float getMoonElevationAngleDegrees() {
        return minecraftMoonElevationAngle;
    }
    public float getMinecraftMoonSize() {
        return minecraftMoonSize;
    }

    public void recalculate(long minecraftTime) {
        TimeZone timezone = TimeZone.getTimeZone(SunIRL.instance.config.location.timezone);
        ZonedDateTime time = ZonedDateTime.of(
                LocalDateTime.of(
                        LocalDate.now(timezone.toZoneId()),
                        SunIRL.instance.config.useMCTime ?
                                LocalTime.ofSecondOfDay(((minecraftTime % SunIRL.instance.dayLength) / 20L) * (SunIRL.instance.config.irlDayLength ? 1 : 72)) :
                                LocalTime.now(timezone.toZoneId()).plusHours(timezone.useDaylightTime() ? 1 : 0)
                ),
                timezone.toZoneId()
        );

        SunPosition sunPos = this.sunPosCalculator.copy().on(time).execute();
        this.minecraftSunElevationAngle = (float) sunPos.getAltitude() - 90F;
        this.minecraftSunAzimuthAngle = (float) -sunPos.getAzimuth();
        this.minecraftSunSize = (float) getMCSize(sunPos.getDistance(), sunDiameterKM) * SunIRL.instance.config.sunAndMoonSizeMultiplier;

        this.fogCalculationAzimuthAngle = -minecraftSunAzimuthAngle - 180F;
        this.fogCalculationElevationAngle = -(minecraftSunElevationAngle + 90F);

        MoonPosition moonPos = this.moonPosCalculator.copy().on(time).execute();
        this.minecraftMoonElevationAngle = (float) -moonPos.getAltitude() - 90F;
        this.minecraftMoonAzimuthAngle = (float) -moonPos.getAzimuth() + 180F;
        this.minecraftMoonSize = (float) getMCSize(moonPos.getDistance(), moonDiameterKM) * SunIRL.instance.config.sunAndMoonSizeMultiplier;
    }

    public static double getMCSize(double distance, double diameter) {
        double angleDeg = (diameter * 57.29) / distance;
        return angleDeg * 3.488372093023256D; // approximate multiplier, measured approximately by hand, but works well enough
    }

    public void setPosition(double latitude, double longitude) {
        this.sunPosCalculator = SunPosition.compute().at(latitude, longitude);
        this.moonPosCalculator = MoonPosition.compute().at(latitude, longitude);
    }
}