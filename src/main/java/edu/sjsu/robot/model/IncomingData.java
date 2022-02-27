package edu.sjsu.robot.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class IncomingData {
    private Integer duration;
    private String location;
    private Integer batteryPercentage;

    public SanitizedReport toSanitizedReport() {
        var end = Instant.now();
        var start = end.minus(duration, ChronoUnit.MINUTES);

        return new SanitizedReport(location, batteryPercentage, start.getEpochSecond(), end.getEpochSecond(), duration);
    }

    public SanitizedReport toSanitizedReport(String now) {
        var end = Instant.parse(now);
        var start = end.minus(duration, ChronoUnit.MINUTES);

        return new SanitizedReport(location, batteryPercentage, start.getEpochSecond(), end.getEpochSecond(), duration);
    }

    /**
     * @return Integer return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Integer duration) {
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
     * @return Integer return the batteryPercentage
     */
    public Integer getBatteryPercentage() {
        return batteryPercentage;
    }

    /**
     * @param batteryPercentage the batteryPercentage to set
     */
    public void setBatteryPercentage(Integer batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }
}
