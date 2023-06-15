package com.arc.app.service.report.impl;

import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.ICReportContentRequest;
import com.arc.app.request.report.ICReportRequest;
import com.arc.app.request.report.MDReportContentRequest;
import com.arc.app.request.report.ReportContentRequest;
import com.arc.app.service.report.ICReportService;
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
public class ICReportServiceImpl implements ICReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public ICReportRequest getICReport(Long id) {
        ICReportRequest result = new ICReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.IC.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    ICReportContentRequest request = new ICReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }
}
