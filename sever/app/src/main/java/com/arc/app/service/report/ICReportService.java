package com.arc.app.service.report;

import com.arc.app.entity.report.ICReport;
import com.arc.app.request.report.ICReportRequest;

/**
 * author: NMDuc
 **/
public interface ICReportService {
    ICReportRequest getICReport(Long id);
    ICReport setData(ICReportRequest request);
}
