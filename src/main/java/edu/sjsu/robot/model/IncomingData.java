package edu.sjsu.robot.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class IncomingData {
    private float duration;
    private String location;
    private float batteryPercentage;

    public SanitizedReport toSanitizedReport() {
        var end = Instant.now();

        var minDuration = (long) (duration * 60);

        var start = end.minus(minDuration, ChronoUnit.MINUTES);

        return new SanitizedReport(location, batteryPercentage, start.getEpochSecond(), end.getEpochSecond(), duration);

    }

    /**
     * @return float return the duration
     */
    public float getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(float duration) {
        this.duration = duration;
    }

    /**
     * @return String return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return float return the batteryPercentage
     */
    public float getBatteryPercentage() {
        return batteryPercentage;
    }

    /**
     * @param batteryPercentage the batteryPercentage to set
     */
    public void setBatteryPercentage(float batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

}
