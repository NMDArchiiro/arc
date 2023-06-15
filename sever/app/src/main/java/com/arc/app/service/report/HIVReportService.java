package com.arc.app.service.report;

import com.arc.app.request.report.HIVReportRequest;
import com.arc.app.request.search.HIVReportSearchRequest;
import org.springframework.data.domain.Page;

/**
 * author: NMDuc
 **/
public interface HIVReportService {
    HIVReportRequest getHIVReportForm(Long id); // Tim theo id
    HIVReportRequest exitsQuarter(HIVReportRequest request); // Quy da ton tai
    HIVReportRequest exitsYear(HIVReportRequest request); // Nam da tong tai
    HIVReportRequest createQuarterCommune(); // Khoi tao bao cao quy xa
    HIVReportRequest createQuarterDistrict(); // Khoi tao bao cao quy huyen
    HIVReportRequest createYearDistrict(); // Khoi tao bao cao nam huyen
    HIVReportRequest createQuarterProvince(); // Khoi tao bao cao quy tinh
    HIVReportRequest createYearProvince(); // Khoi tao bao cao nam tinh
    HIVReportRequest setRole(HIVReportRequest request, Integer... type); // Set role
    HIVReportRequest saveHIVReport(HIVReportRequest request); // Luu
    void confirmHIVReport(Long idReport, Integer status); // confirm bao cao
    HIVReportRequest changeStatus(Long idReport, Integer status, HIVReportRequest request); // Thay doi trang thai
    Page<HIVReportRequest> paging(HIVReportSearchRequest request);
    Page<HIVReportRequest> pagingQuarter(HIVReportSearchRequest dto);
    Page<HIVReportRequest> pagingYear(HIVReportSearchRequest dto);

}
