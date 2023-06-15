package com.arc.app.service.report;

import com.arc.app.entity.report.ICReportContent;
import com.arc.app.request.report.ICReportRequest;
import com.arc.app.request.report.MDReportRequest;

/**
 * author: NMDuc
 **/
public interface ICReportService {
    ICReportRequest getICReport(Long id);
}
