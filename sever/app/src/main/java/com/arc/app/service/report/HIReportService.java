package com.arc.app.service.report;

import com.arc.app.entity.report.HIReport;
import com.arc.app.request.report.HIReportRequest;

/**
 * author: NMDuc
 **/
public interface HIReportService {
    HIReportRequest getHIReport(Long id);
    HIReport setData(HIReportRequest request);
}
