package com.arc.app.service.report;

import com.arc.app.entity.report.MDReport;
import com.arc.app.entity.report.POCReport;
import com.arc.app.request.report.ATHReportRequest;
import com.arc.app.request.report.MDReportRequest;
import com.arc.app.request.report.POCReportRequest;

/**
 * author: NMDuc
 **/
public interface MDReportService {
    MDReportRequest getMDReport(Long id);
    MDReport setData(MDReportRequest request);
}
