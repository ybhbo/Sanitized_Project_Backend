package edu.sjsu.robot;

import org.springframework.data.jpa.repository.JpaRepository;

interface ReportRepository extends JpaRepository<SanitizedReport, Long> {


}