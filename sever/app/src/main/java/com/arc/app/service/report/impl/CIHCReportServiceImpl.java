package com.arc.app.service.report.impl;

import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.CIHCReportContentRequest;
import com.arc.app.request.report.CIHCReportRequest;
import com.arc.app.request.report.ReportContentRequest;
import com.arc.app.service.report.CIHCReportService;
import com.arc.app.utils.enums.ReportEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * author: NMDuc
 **/
@Transactional
@Service
public class CIHCReportServiceImpl implements CIHCReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public CIHCReportRequest getCIHCReport(Long id) {
        CIHCReportRequest result = new CIHCReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.CIHC.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    CIHCReportContentRequest request = new CIHCReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }
}
