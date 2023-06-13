package com.arc.app.service.report.impl;

import com.arc.app.entity.report.HRIReportContent;
import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.HRIReportContentRepository;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.HRIReportContentRequest;
import com.arc.app.request.report.HRIReportRequest;
import com.arc.app.request.report.ReportContentRequest;
import com.arc.app.service.report.HRIReportService;
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
public class HRIReportServiceImpl implements HRIReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public HRIReportRequest getHRIReport(Long id) {
        HRIReportRequest result = new HRIReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.HRI.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    HRIReportContentRequest request = new HRIReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return null;
    }
}
