package com.arc.app.service.report;

import com.arc.app.entity.report.CIReport;
import com.arc.app.request.report.CIReportRequest;

/**
 * author: NMDuc
 **/
public interface CIReportService {
    CIReportRequest getCIReport(Long id);
    CIReport setData(CIReportRequest request);
}
