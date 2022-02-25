package edu.sjsu.robot;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReportController {

    private final ReportRepository repository;

    ReportController(ReportRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/Reports")
    List<SanitizedReport> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/Reports")
    SanitizedReport newReport(@RequestBody SanitizedReport newReport) {
        return repository.save(newReport);
    }

    // Single item

    // @GetMapping("/Reports/{timestamp}")
    // SanitizedReport one(@PathVariable Long timestamp) {

    // return repository.findById(id)
    // .orElseThrow(() -> new NotFoundException(Long.toString(id)));
    // }

}