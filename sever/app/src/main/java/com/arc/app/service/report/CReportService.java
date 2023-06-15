package com.arc.app.service.report;

import com.arc.app.entity.report.CReport;
import com.arc.app.request.report.CReportRequest;

/**
 * author: NMDuc
 **/
public interface CReportService {
    CReportRequest getCReport(Long id);
    CReport setData(CReportRequest request);
}
