package com.arc.app.service.report.impl;

import com.arc.app.entity.report.ATHReport;
import com.arc.app.entity.report.ATHReportContent;
import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.ATHReportContentRequest;
import com.arc.app.request.report.ATHReportRequest;
import com.arc.app.request.report.HRIReportRequest;
import com.arc.app.request.report.ReportContentRequest;
import com.arc.app.service.report.ATHReportService;
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
public class ATHReportServiceImpl implements ATHReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public ATHReportRequest getATHReport(Long id) {
        ATHReportRequest result = new ATHReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.ATH.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    ATHReportContentRequest request = new ATHReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }

    @Override
    public ATHReport setData(ATHReportRequest request) {
        if (request != null) {
            ATHReport entity = new ATHReport();
            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<ATHReportContent> content = new HashSet<>();
                for (ATHReportContentRequest item : request.getContents()) {
                    ATHReportContent athReportContent = new ATHReportContent();

                    athReportContent.setAthReport(entity);
                    athReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    athReportContent.setNumberAdviseFemale(item.getNumberAdviseFemale());
                    athReportContent.setNumberAdviseMale(item.getNumberAdviseMale());
                    athReportContent.setNumberAdviseTotal(item.getNumberAdviseTotal());
                    athReportContent.setNumberTestingFemale(item.getNumberTestingFemale());
                    athReportContent.setNumberTestingMale(item.getNumberTestingMale());
                    athReportContent.setNumberTestingTotal(item.getNumberTestingTotal());
                    athReportContent.setNumberPersonAdviseFemale(item.getNumberPersonAdviseFemale());
                    athReportContent.setNumberPersonAdviseMale(item.getNumberPersonAdviseMale());
                    athReportContent.setNumberPersonAdviseTotal(item.getNumberPersonAdviseTotal());
                    athReportContent.setNumberPersonTestingFemale(item.getNumberPersonTestingFemale());
                    athReportContent.setNumberPersonTestingMale(item.getNumberPersonTestingMale());
                    athReportContent.setNumberPersonTestingTotal(item.getNumberPersonTestingTotal());
                    content.add(athReportContent);
                }
                if (!CollectionUtils.isEmpty(entity.getContents())) {
                    entity.getContents().clear();
                    entity.getContents().addAll(content);
                } else {
                    entity.setContents(content);
                }
            }
            return entity;
        }
        return null;
    }
}
