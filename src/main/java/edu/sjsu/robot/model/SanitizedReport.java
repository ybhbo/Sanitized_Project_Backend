package edu.sjsu.robot.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "SANITIZED_REPORT")
public class SanitizedReport {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String location;
    private float batteryPercentage;
    private Long start;
    private Long end;
    private float duration;

    // //
    // [{"id":1,"location":"ENG187","hours":10.0,"batteryPercentage":50.0,"timestamp":1645833825},

    SanitizedReport() {
    }

    public SanitizedReport(String location, float batteryPercentage, Long start, Long end, float duration) {
        super();
        this.location = location;
        this.batteryPercentage = batteryPercentage;
        this.start = start;
        this.end = end;
        this.duration = duration;
    }

    public Long getId() {
        return id;
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

    public float getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(float batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(batteryPercentage, duration, end, location, start);
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
                && Float.floatToIntBits(duration) == Float.floatToIntBits(other.duration)
                && Objects.equals(end, other.end) && Objects.equals(location, other.location)
                && Objects.equals(start, other.start);
    }

    @Override
    public String toString() {
        return "SanitizedReport [location=" + location + ", batteryPercentage=" + batteryPercentage + ", start=" + start
                + ", end=" + end + ", duration=" + duration + "]";
    }

}
