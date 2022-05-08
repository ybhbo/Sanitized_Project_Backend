package edu.sjsu.robot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.robot.model.SanitizedReport;

interface ReportRepository extends JpaRepository<SanitizedReport, Long> {

   SanitizedReport findTopByOrderByIdDesc();

   public List<SanitizedReport> findTop30ByOrderByIdDesc();

   List<SanitizedReport> findByStartGreaterThan(Long time);

}