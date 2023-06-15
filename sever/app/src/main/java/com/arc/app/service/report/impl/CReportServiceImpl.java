package com.arc.app.service.report.impl;

import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.*;
import com.arc.app.service.report.CReportService;
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
public class CReportServiceImpl implements CReportService {
    @Resource
    private ReportContentRepository reportContentRepository;
    @Override
    public CReportRequest getCReport(Long id) {
        CReportRequest result = new CReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.C.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    CReportContentRequest request = new CReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }
}
