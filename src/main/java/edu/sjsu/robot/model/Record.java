package edu.sjsu.robot.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Record {
    // { id: 1, date: "02/12/2022", time: "9:00-9:30", sanitizedHours: "3",
    // location: "Eng187" },
    // [{"id":1,"location":"ENG187","hours":10.0,"batteryPercentage":50.0,"timestamp":1645833825},

    private Long id;
    private String location;
    private String date;
    private String time;
    private Integer duration;

    public Record(SanitizedReport sanitizedReport) {
        id = sanitizedReport.getId();
        duration = sanitizedReport.getDuration();
        location = sanitizedReport.getLocation();
        LocalDateTime localStart = LocalDateTime.ofEpochSecond(sanitizedReport.getStart(), 0, ZoneOffset.of("-08:00"));
        LocalDateTime localEnd = LocalDateTime.ofEpochSecond(sanitizedReport.getEnd(), 0, ZoneOffset.of("-08:00"));
        date = localStart.toLocalDate().toString();
        time = String.format("%02d:%02d-%02d:%02d", localStart.getHour(), localStart.getMinute(), localEnd.getHour(),
                localEnd.getMinute());
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
