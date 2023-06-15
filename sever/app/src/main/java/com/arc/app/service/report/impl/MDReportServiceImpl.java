package com.arc.app.service.report.impl;

import com.arc.app.entity.report.MDReport;
import com.arc.app.entity.report.MDReportContent;
import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.ATHReportContentRequest;
import com.arc.app.request.report.MDReportContentRequest;
import com.arc.app.request.report.MDReportRequest;
import com.arc.app.request.report.ReportContentRequest;
import com.arc.app.service.report.MDReportService;
import com.arc.app.utils.enums.ReportEnum;
import lombok.Setter;
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
public class MDReportServiceImpl implements MDReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public MDReportRequest getMDReport(Long id) {
        MDReportRequest result = new MDReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.MD.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    MDReportContentRequest request = new MDReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }

    @Override
    public MDReport setData(MDReportRequest request) {
        if (request != null) {
            MDReport entity = new MDReport();

            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<MDReportContent> contents = new HashSet<>();
                for (MDReportContentRequest item : request.getContents()) {
                    MDReportContent mdReportContent =  new MDReportContent();
                    mdReportContent.setMdReport(entity);
                    mdReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    mdReportContent.setNumberMalePatient(item.getNumberMalePatient());
                    mdReportContent.setNumberFemalePatient(item.getNumberFemalePatient());
                    mdReportContent.setNumberTotalPatient(item.getNumberTotalPatient());
                    contents.add(mdReportContent);
                }
                if (!CollectionUtils.isEmpty(entity.getContents())) {
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
