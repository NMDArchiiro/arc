package com.arc.app.service.report;

import com.arc.app.request.report.ATHReportRequest;
import com.arc.app.request.report.MDReportRequest;

/**
 * author: NMDuc
 **/
public interface MDReportService {
    MDReportRequest getMDReport(Long id);

}
