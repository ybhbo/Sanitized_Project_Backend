package edu.sjsu.robot;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.catalina.mapper.Mapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.robot.model.IncomingData;
import edu.sjsu.robot.model.Record;
import edu.sjsu.robot.model.SanitizedReport;

@RestController
class ReportController {

    private final ReportRepository repository;

    ReportController(ReportRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @CrossOrigin
    @GetMapping("/report")
    List<Record> all() {
        var all = repository.findTop10ByOrderByIdDesc();
        return all.stream().map(sr -> {
            return new Record(sr);
        }).collect(Collectors.toList());

    }
    // end::get-aggregate-root[]

    @PostMapping("/report")
    SanitizedReport newReport(@RequestBody IncomingData newData) {
        return repository.save(newData.toSanitizedReport());
    }

    @CrossOrigin
    @GetMapping("/battery")
    Float getBattery() {
        var record = repository.findTopByOrderByIdDesc();
        if (record == null) {
            return 0.0f;
        }
        return record.getBatteryPercentage();
    }

    @CrossOrigin
    @GetMapping("/today/workload")
    Float getTodayWorkload() {
        ZonedDateTime today = ZonedDateTime.ofInstant(Instant.now().truncatedTo(ChronoUnit.DAYS),
                ZoneId.of("America/Los_Angeles"));

        var record = repository.findByStartGreaterThan(today.toEpochSecond());
        if (record == null) {
            return 0.0f;
        }
        return (float) record.stream().mapToDouble(r -> r.getDuration()).sum();
    }

    @CrossOrigin
    @GetMapping("/month/workload")
    Float getMonthWorkload() {
        var firstOfMonth = LocalDateTime.now().withDayOfMonth(1);

        var record = repository.findByStartGreaterThan(firstOfMonth.toEpochSecond(ZoneOffset.of("-08:00")));
        if (record == null) {
            return 0.0f;
        }
        return (float) record.stream().mapToDouble(r -> r.getDuration()).sum();
    }

}