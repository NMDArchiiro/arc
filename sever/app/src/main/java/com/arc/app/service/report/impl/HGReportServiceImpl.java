package com.arc.app.service.report.impl;

import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.HGReportContentRequest;
import com.arc.app.request.report.HGReportRequest;
import com.arc.app.request.report.ReportContentRequest;
import com.arc.app.service.report.HGReportService;
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
public class HGReportServiceImpl implements HGReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public HGReportRequest getHGReport(Long id) {
        HGReportRequest result = new HGReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.HG.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    HGReportContentRequest request = new HGReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }
}
