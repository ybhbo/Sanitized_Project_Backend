package edu.sjsu.robot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.sjsu.robot.model.IncomingData;
import edu.sjsu.robot.model.SanitizedReport;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    final static List<String> LOCATIONS = List.of("ENGR 312", "ENGR 333", "ENGR 211", "ENGR 232", "ENGR 201",
            "ENGR 189", "ENGR 180");

    @Bean
    CommandLineRunner initDatabase(ReportRepository repository) {

        return args -> {
            if (repository.count() == 0) {
                insert(repository, 10, 8);
                insert(repository, 9, 10);
                insert(repository, 8, 12);
                insert(repository, 7, 12);
                insert(repository, 6, 16);
                insert(repository, 5, 12);
                insert(repository, 4, 4);
                insert(repository, 3, 7);
                insert(repository, 2, 8);
                insert(repository, 1, 8);
                insert(repository, 0, 2);

            }
        };
    }

    SanitizedReport build(String now, Integer duration, String location, Integer batteryPercentage) {
        var idata = new IncomingData();
        idata.setDuration(duration);
        idata.setLocation(location);
        idata.setBatteryPercentage(batteryPercentage);
        return idata.toSanitizedReport(now);
    }

    private void insert(ReportRepository repository, int daysAgo, int numRecords) {
        var pst = ZoneOffset.of("-08:00");
        var random = ThreadLocalRandom.current();

        LocalDateTime today = LocalDateTime.now().minus(daysAgo, ChronoUnit.DAYS)
                .truncatedTo(ChronoUnit.DAYS);
        System.out.println(today);
        var start = today.toEpochSecond(pst);
        var end = start + 86400;

        List<LocalDateTime> startTimes = new ArrayList<>();
        while (startTimes.size() < numRecords + 1) {
            var randTime = random.nextInt((int) start, (int) end + 1);
            startTimes.add(LocalDateTime.ofEpochSecond(randTime, 0, pst));
        }
        Collections.sort(startTimes);

        for (int i = 0; i < startTimes.size() - 1; i++) {
            var maxDuration = Math.min(15 * 60,
                    (int) Duration.between(startTimes.get(i), startTimes.get(i + 1)).toSeconds());
            maxDuration = Math.max(15 * 60, maxDuration);
            int duration = random.nextInt(10 * 60, maxDuration + 1);
            duration = (int) TimeUnit.SECONDS.toMinutes(duration);

            var location = LOCATIONS.get(random.nextInt(LOCATIONS.size()));
            var instant = startTimes.get(i).toInstant(pst);
            log.info("Preloading " + repository.save(build(instant.toString(), duration, location, 100)));
        }
    }
}
