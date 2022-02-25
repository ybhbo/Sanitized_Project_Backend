package edu.sjsu.robot;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SanitizedReport {
    private @Id @GeneratedValue Long id;
    private String location;
    private float hours;
    private float batteryPercentage;
    private Long timestamp;

    SanitizedReport(String location, float hours, float batteryPercentage, Long timestamp) {
        super();
        this.location = location;
        this.hours = hours;
        this.batteryPercentage = batteryPercentage;
        this.timestamp = Instant.now().getEpochSecond();
    }

    SanitizedReport() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public float getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(float batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(batteryPercentage, hours, location, timestamp);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SanitizedReport other = (SanitizedReport) obj;
        return Float.floatToIntBits(batteryPercentage) == Float.floatToIntBits(other.batteryPercentage)
                && Float.floatToIntBits(hours) == Float.floatToIntBits(other.hours)
                && Objects.equals(location, other.location) && Objects.equals(timestamp, other.timestamp);
    }

    @Override
    public String toString() {
        return "SanitizedReport [location=" + location + ", hours=" + hours + ", batteryPercentage=" + batteryPercentage
                + ", timestamp=" + Instant.ofEpochSecond(timestamp).toString() + "]";
    }

}
