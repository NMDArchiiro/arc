package com.arc.app.service.report.impl;

import com.arc.app.entity.report.ReportContent;
import com.arc.app.entity.report.SPReport;
import com.arc.app.entity.report.SPReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.*;
import com.arc.app.service.report.SPReportService;
import com.arc.app.utils.enums.ReportEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author: NMDuc
 **/
@Transactional
@Service
public class SPReportServiceImpl implements SPReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public SPReportRequest getSPReport(Long id) {
        SPReportRequest result = new SPReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.SP.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    SPReportContentRequest request = new SPReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }

    @Override
    public SPReport setData(SPReportRequest request) {
        if (request != null) {
            SPReport entity = new SPReport();
            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<SPReportContent> contents = new HashSet<>();
                for (SPReportContentRequest item : request.getContents()) {
                    SPReportContent spReportContent = new SPReportContent();
                    spReportContent.setSpReport(entity);
                    spReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    spReportContent.setAmount(item.getAmount());
                    contents.add(spReportContent);
                }
                if (entity.getContents() != null) {
                    entity.getContents().clear();
                    entity.getContents().addAll(contents);
                } else {
                    entity.setContents(contents);
                }
            }

            return entity;
        }
        return null;
    }
}
