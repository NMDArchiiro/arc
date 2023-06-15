package com.arc.app.service.report;

import com.arc.app.entity.report.HGReport;
import com.arc.app.request.report.HGReportRequest;

/**
 * author: NMDuc
 **/
public interface HGReportService {
    HGReportRequest getHGReport(Long id);
    HGReport setData(HGReportRequest request);
}
