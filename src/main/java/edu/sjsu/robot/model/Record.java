package edu.sjsu.robot.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import ch.qos.logback.core.util.Duration;

public class Record {
    // { id: 1, date: "02/12/2022", time: "9:00-9:30", sanitizedHours: "3",
    // location: "Eng187" },
    // [{"id":1,"location":"ENG187","hours":10.0,"batteryPercentage":50.0,"timestamp":1645833825},

    private Long id;
    private String location;
    private String date;
    private String time;
    private Float sanitizedHours;

    public Record(SanitizedReport sanitizedReport) {
        id = sanitizedReport.getId();
        sanitizedHours = sanitizedReport.getDuration();
        location = sanitizedReport.getLocation();
        date = Instant.ofEpochSecond(sanitizedReport.getStart()).toString();
        LocalDateTime localStart = LocalDateTime.ofEpochSecond(sanitizedReport.getStart(), 0, ZoneOffset.UTC);
        LocalDateTime localEnd = LocalDateTime.ofEpochSecond(sanitizedReport.getEnd(), 0, ZoneOffset.UTC);
        time = String.format("%02d:%02d-%02d:%02d", localStart.getHour(), localStart.getMinute(), localEnd.getHour(),
                localEnd.getMinute());
    }

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return String return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return Integer return the sanitizedHours
     */
    public Float getSanitizedHours() {
        return sanitizedHours;
    }

    /**
     * @param sanitizedHours the sanitizedHours to set
     */
    public void setSanitizedHours(Float sanitizedHours) {
        this.sanitizedHours = sanitizedHours;
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

}
