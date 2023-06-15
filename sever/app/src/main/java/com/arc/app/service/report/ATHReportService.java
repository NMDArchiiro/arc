package com.arc.app.service.report;

import com.arc.app.entity.report.ATHReport;
import com.arc.app.request.report.ATHReportRequest;
import com.arc.app.request.report.HRIReportRequest;

/**
 * author: NMDuc
 **/
public interface ATHReportService {
    ATHReportRequest getATHReport(Long id);
    ATHReport setData(ATHReportRequest request);
}
