package com.arc.app.service.report;

import com.arc.app.entity.report.HRIReport;
import com.arc.app.request.report.HRIReportRequest;

/**
 * author: NMDuc
 **/
public interface HRIReportService {
    HRIReportRequest getHRIReport(Long id);
    HRIReport setData(HRIReportRequest request);
}
