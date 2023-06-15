package com.arc.app.service.report.impl;

import com.arc.app.entity.report.MTCReport;
import com.arc.app.entity.report.MTCReportContent;
import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.*;
import com.arc.app.service.report.MTCReportService;
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
public class MTCReportServiceImpl implements MTCReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public MTCReportRequest getMTCReport(Long id) {
        MTCReportRequest result = new MTCReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.MTC.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    MTCReportContentRequest request = new MTCReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }

    @Override
    public MTCReport setData(MTCReportRequest request) {
        if (request != null) {
            MTCReport entity =new MTCReport();
            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<MTCReportContent> contents = new HashSet<>();
                for (MTCReportContentRequest item : request.getContents()) {
                    MTCReportContent mtcReportContent = new MTCReportContent();
                    mtcReportContent.setMtcReport(entity);
                    mtcReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    mtcReportContent.setAmount(item.getAmount());
                    contents.add(mtcReportContent);
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
