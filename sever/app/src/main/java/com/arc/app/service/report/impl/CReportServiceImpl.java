package com.arc.app.service.report.impl;

import com.arc.app.entity.report.CReport;
import com.arc.app.entity.report.CReportContent;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public CReport setData(CReportRequest request) {
        if (request != null) {
            CReport entity = new CReport();
            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<CReportContent> contents = new HashSet<>();
                for (CReportContentRequest item : request.getContents()) {
                    CReportContent cReportContent = new CReportContent();
                    cReportContent.setCReport(entity);
                    cReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    cReportContent.setNumberNegative(item.getNumberNegative());
                    cReportContent.setNumberPositive(item.getNumberPositive());
                    cReportContent.setNumberUnknown(item.getNumberUnknown());
                    cReportContent.setTotal(item.getTotal());
                    contents.add(cReportContent);
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
