package com.ssafy.community.report.domain.repository;

import com.ssafy.community.report.domain.entity.MonthlyReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonthlyReportsRepository extends JpaRepository<MonthlyReports, Integer> {
    // groupId, reportYear, reportMonth 기반으로 레코드 조회
    Optional<MonthlyReports> findByGroupIdAndReportYearAndReportMonth(Integer groupId, Integer reportYear, Integer reportMonth);
}