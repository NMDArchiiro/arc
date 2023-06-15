package com.arc.app.service.report;

import com.arc.app.request.report.HIVReportRequest;

/**
 * author: NMDuc
 **/
public interface HIVReportService {
    HIVReportRequest getHIVReportForm(Long id); // Tim theo id
    HIVReportRequest createQuarterCommune(); // Khoi tao bao cao quy xa
    HIVReportRequest createQuarterDistrict(); // Khoi tao bao cao quy huyen
    HIVReportRequest createYearDistrict(); // Khoi tao bao cao nam huyen
    HIVReportRequest createQuarterProvince(); // Khoi tao bao cao quy tinh
    HIVReportRequest createYearProvince(); // Khoi tao bao cao nam tinh
    HIVReportRequest setRole(HIVReportRequest request, Integer... type); // Set role
    HIVReportRequest saveHIVReport(Long id);


}
