package edu.sjsu.robot;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import org.apache.catalina.mapper.Mapper;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.sjsu.robot.model.IncomingData;
import edu.sjsu.robot.model.Record;
import edu.sjsu.robot.model.SanitizedReport;
import edu.sjsu.robot.model.WeekCurveData;

@RestController
class ReportController {
    private static final Logger log = LoggerFactory.getLogger(ReportController.class);

    private final ReportRepository repository;

    ReportController(ReportRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @CrossOrigin
    @GetMapping("/report")
    List<Record> all() {
        log.info("GET /report called");
        var all = repository.findTop10ByOrderByIdDesc();
        return all.stream().map(sr -> {
            return new Record(sr);
        }).collect(Collectors.toList());
    }
    // end::get-aggregate-root[]

    @PostMapping("/report")
    SanitizedReport newReport(@RequestBody IncomingData newData) {
        log.info("POST /report called");
        return repository.save(newData.toSanitizedReport());
    }

    @CrossOrigin
    @GetMapping("/battery")
    Integer getBattery() {
        log.info("GET /battery called");
        var record = repository.findTopByOrderByIdDesc();
        if (record == null) {
            return 0;
        }
        return record.getBatteryPercentage();
    }

    @CrossOrigin
    @GetMapping("/today/workload")
    Float getTodayWorkload() {
        log.info("GET /today/workload called");

        ZonedDateTime today = ZonedDateTime.ofInstant(Instant.now().truncatedTo(ChronoUnit.DAYS),
                ZoneId.of("America/Los_Angeles"));

        var record = repository.findByStartGreaterThan(today.toEpochSecond());
        if (record == null) {
            return 0.0f;
        }

        var minutes = record.stream().mapToInt(r -> r.getDuration()).sum();
        return minutes / 60.0f;
    }

    @CrossOrigin
    @GetMapping("/week/workload")
    WeekCurveData getWeekWorkload() {
        log.info("GET /week/workload called");

        var start = Instant.now().minus(9, ChronoUnit.DAYS).atZone(ZoneId.of("America/Los_Angeles"));
        var records = repository.findByStartGreaterThan(start.toEpochSecond() + 100000);
        if (records == null) {
            return new WeekCurveData();
        }

        var today = from(Instant.now().getEpochSecond());
        Map<String, Float> dataMap = new HashMap<>();
        for (var record : records) {
            var day = from(record.getStart());
            if (isSameDay(day, today)) {
                continue;
            }
            var key = day.getDayOfWeek().toString();
            var current = dataMap.getOrDefault(key, 0f);
            current += record.getDuration() / 60.0f;
            dataMap.put(key, current);
        }

        List<String> weekDays = new ArrayList<>();
        List<List<Float>> totalHours = new ArrayList<>();
        List<Float> total = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            var key = today.getDayOfWeek().toString();
            weekDays.add(key);
            total.add(dataMap.containsKey(key) ? dataMap.get(key) : 0.0f);
            today = today.plus(1, ChronoUnit.DAYS);
        }

        var weekCurveData = new WeekCurveData();
        weekCurveData.setLabels(weekDays);
        totalHours.add(total);
        weekCurveData.setSeries(totalHours);
        return weekCurveData;
    }

    @CrossOrigin
    @GetMapping("/month/workload")
    Float getMonthWorkload() {
        log.info("GET /month/workload called");

        var firstOfMonth = LocalDateTime.now().withDayOfMonth(1);

        var record = repository.findByStartGreaterThan(firstOfMonth.toEpochSecond(ZoneOffset.of("-08:00")));
        if (record == null) {
            return 0.0f;
        }
        var minutes = record.stream().mapToInt(r -> r.getDuration()).sum();
        return minutes / 60.0f;
    }

    private ZonedDateTime from(long sec) {
        return Instant.ofEpochSecond(sec).atZone(ZoneId.of("America/Los_Angeles"));
    }

    private boolean isSameDay(ZonedDateTime date1, ZonedDateTime date2) {
        return date1.truncatedTo(ChronoUnit.DAYS).equals(date2.truncatedTo(ChronoUnit.DAYS));
    }
}