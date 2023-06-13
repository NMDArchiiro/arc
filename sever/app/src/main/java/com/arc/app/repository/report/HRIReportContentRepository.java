package com.arc.app.repository.report;

import com.arc.app.entity.report.HRIReportContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author: NMDuc
 **/
@Repository
public interface HRIReportContentRepository extends JpaRepository<HRIReportContent, Long> {

}
