package com.arc.app.service.report.impl;

import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.ReportContentRequest;
import com.arc.app.response.ResponseList;
import com.arc.app.response.ResponseObject;
import com.arc.app.service.report.ReportContentService;
import com.arc.app.utils.enums.ResponseEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * author: NMDuc
 **/
@Transactional
@Service
public class ReportContentServiceImpl implements ReportContentService {
    @Resource
    private ReportContentRepository reportContentRepository;


    @Override
    public ResponseObject saveReportContent(ReportContentRequest request) {
        try {
            ReportContent reportContent = null;
            if(request.getId() != null) {
                reportContent = reportContentRepository.findById(request.getId()).orElse(null);
            }
            if(reportContent == null) {
                reportContent = new ReportContent();
            }
            reportContent.setIndexNumber(request.getIndexNumber());
            reportContent.setSubContent(request.getSubContent());
            reportContent.setTitle(request.getTitle());
            reportContent.setReportNumber(request.getReportNumber());
            reportContent.setCanWrite(request.getCanWrite());
            reportContent.setBold(request.getBold());
            reportContent.setItalics(request.getItalics());
            reportContent = reportContentRepository.save(reportContent);
            return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), reportContent);
        } catch (Exception e) {
            return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
        }
    }

    @Override
    public ResponseObject getReportContent(Long id) {
        try {
            ReportContent reportContent = reportContentRepository.findById(id).orElse(null);
            return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), new ReportContentRequest(reportContent));
        } catch (Exception e) {
            return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
        }
    }

    @Override
    public ResponseList<ReportContent> getByIndexNumber(Integer indexNumber, Integer reportNumber) {
        try {
            List<ReportContent> listData = reportContentRepository.findByNumber(indexNumber, reportNumber);
            return new ResponseList<>(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), listData);
        } catch (Exception e) {
            return new ResponseList<>(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), null);
        }
    }

    @Override
    public ResponseList<ReportContent> getAllContentByReportNumber(Integer reportNumber) {
        try {
            List<ReportContent> listData = reportContentRepository.getByReportNumber(reportNumber);
            return new ResponseList<>(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), listData);
        } catch (Exception e) {
            return new ResponseList<>(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), null);
        }
    }
}
