package com.arc.app.service.report.impl;

import com.arc.app.entity.report.POCReport;
import com.arc.app.entity.report.POCReportContent;
import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.*;
import com.arc.app.service.report.POCReportService;
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
public class POCReportServiceImpl implements POCReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public POCReportRequest getPOCReport(Long id) {
        POCReportRequest result = new POCReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.POC.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    POCReportContentRequest request = new POCReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }

    @Override
    public POCReport setData(POCReportRequest request) {
        if (request != null) {
            POCReport entity = new POCReport();
            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<POCReportContent> contents = new HashSet<>();
                for (POCReportContentRequest item : request.getContents()) {
                    POCReportContent pocReportContent = new POCReportContent();
                    pocReportContent.setPocReport(entity);
                    pocReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    pocReportContent.setAmount(item.getAmount());
                    contents.add(pocReportContent);
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
