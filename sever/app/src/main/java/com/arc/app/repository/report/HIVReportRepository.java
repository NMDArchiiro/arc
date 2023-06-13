package com.arc.app.repository.report;

import com.arc.app.entity.report.HIVReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author: NMDuc
 **/
@Repository
public interface HIVReportRepository extends JpaRepository<HIVReport, Long> {

}
