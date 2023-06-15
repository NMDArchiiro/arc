package com.arc.app.service.report;

import com.arc.app.entity.report.SPReport;
import com.arc.app.request.report.SPReportRequest;

/**
 * author: NMDuc
 **/
public interface SPReportService {
    SPReportRequest getSPReport(Long id);
    SPReport setData(SPReportRequest request);
}
