package edu.sjsu.robot;

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

    @Bean
    CommandLineRunner initDatabase(ReportRepository repository) {

        return args -> {
            if (repository.count() == 0) {
                log.info("Preloading " + repository.save(build("2022-02-18T18:35:24.00Z", 30, "ABC123", 44)));// FRI
                log.info("Preloading " + repository.save(build("2022-02-19T18:35:24.00Z", 12, "ABC123", 44)));// SAT
                log.info("Preloading " + repository.save(build("2022-02-20T18:35:24.00Z", 15, "ABC123", 44)));
                log.info("Preloading " + repository.save(build("2022-02-21T18:35:24.00Z", 45, "ABC123", 44)));
                log.info("Preloading " + repository.save(build("2022-02-22T18:35:24.00Z", 3, "ABC123", 44)));
                log.info("Preloading " + repository.save(build("2022-02-23T18:35:24.00Z", 15, "ABC123", 44)));
                log.info("Preloading " + repository.save(build("2022-02-24T18:35:24.00Z", 45, "ABC123", 44)));
                log.info("Preloading " + repository.save(build("2022-02-24T18:35:24.00Z", 15, "ABC123", 44)));
                log.info("Preloading " + repository.save(build("2022-02-25T18:35:24.00Z", 15, "ABC123", 44)));
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
}